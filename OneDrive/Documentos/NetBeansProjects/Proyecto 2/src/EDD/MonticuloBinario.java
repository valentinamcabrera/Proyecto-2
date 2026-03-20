/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EDD;

import Modelos.Documento;

/**
 * Implementación de una Cola de Prioridad basada en un Montículo Binario de minimos (Min Heap)
 * Utiliza un arreglo para representar el árbol binario completo, asegurando
 * una complejidad de tiempo óptima para la navegación.
 * @author valen
 */
public class MonticuloBinario {
    private RegistroImpresion[] monticulo;
    private int tamano;
    private int capmax; //capacidad maxima
    
    /**
     * Constructor del Montículo Binario.
     * @param capmax La cantidad máxima de elementos que el arreglo puede almacenar.
     */
    public MonticuloBinario(int capmax) {
        this.capmax = capmax;
        this.tamano = 0;
        this.monticulo = new RegistroImpresion[this.capmax];
    }
    /**
     * Calcula el índice del nodo padre correspondiente a un índice dado.
     * @param indice El índice del nodo hijo en el arreglo.
     * @return El índice del nodo padre.
     */
    private int getPadre(int indice) {
        return (indice - 1) / 2;
    }

    /**
     * Calcula el índice del nodo hijo izquierdo correspondiente a un índice dado.
     * @param indice El índice del nodo padre en el arreglo.
     * @return El índice del nodo hijo izquierdo.
     */
    private int getHijoIzquierdo(int indice) {
        return (2 * indice) + 1;
    }
    /**
     * Calcula el índice del nodo hijo derecho correspondiente a un índice dado.
     * @param indice El índice del nodo padre en el arreglo.
     * @return El índice del nodo hijo derecho.
     */
    private int getHijoDerecho(int indice) {
        return (2 * indice) + 2;
    }
    
    /**
     * Indica si el monticulo binario esta vacio
     * @return 
     */
    public boolean esVacio() {
        return tamano == 0;
    }
    /**
     * Indica si el monticulo binario esta completamente lleno
     * @return 
     */
    public boolean estaLleno() {
        return tamano == capmax;
    }
    /**
     * Obtiene el tamano actual del monticulo binario.
     * @return 
     */
    public int getTamano() {
        return tamano;
    }
    /**
     * Inserta un nuevo documento en la cola de prioridad sin información del propietario.
     * @param doc El documento a encolar.
     * @param etiquetaT El valor numérico de prioridad calculado por el reloj y el sistema.
     * @throws IllegalStateException Si el montículo ha alcanzado su capacidad máxima.
     */
    public void insertar(Documento doc, long etiquetaT) {
        if (estaLleno()) {
            throw new IllegalStateException("La cola de impresión está llena. Capacidad máxima alcanzada.");
        }
        // Crear el registro y lo coloca en la última posición disponible del arreglo
        RegistroImpresion nuevoRegistro = new RegistroImpresion(doc, etiquetaT);
        monticulo[tamano] = nuevoRegistro;
        //Reordenar el árbol hacia arriba para mantener la propiedad del Min-Heap
        flotar(tamano);
        tamano++;
    }

    /**
     * Restaura la propiedad del montículo de mínimos desplazando un nodo hacia la raíz.
     * Se ejecuta iterativamente mientras el nodo actual sea menor que su nodo padre.
     * @param indice El índice en el arreglo del nodo que acaba de ser insertado o modificado.
     */
    private void flotar(int indice) {
        int indiceActual = indice;
        int indicePadre = getPadre(indiceActual);

        while (indiceActual > 0 && monticulo[indiceActual].getEtiquetaT() < monticulo[indicePadre].getEtiquetaT()) {
            intercambiar(indiceActual, indicePadre);
            indiceActual = indicePadre;
            indicePadre = getPadre(indiceActual);
        }
    }

    /**
     * Método auxiliar de uso interno para intercambiar las posiciones de dos registros en el arreglo.
     * @param i Índice del primer elemento.
     * @param j Índice del segundo elemento.
     */
    private void intercambiar(int i, int j) {
        RegistroImpresion temp = monticulo[i];
        monticulo[i] = monticulo[j];
        monticulo[j] = temp;
    }
    
    /**
     * Extrae y retorna el documento con mayor prioridad (menor tiempo en etiqueta)
     * simulando la acción de "Liberar la impresora".
     * @return El Documento con mayor prioridad, o null si la cola está vacía.
     */
    public Documento extraerMinimo() {
        if (esVacio()) {
            return null; 
        }
        Documento documento = monticulo[0].getDocumento();
        monticulo[0] = monticulo[tamano - 1];
        monticulo[tamano - 1] = null; 
        tamano--;

        // 4. Restaurar la propiedad del Min-Heap hacia abajo
        if (tamano > 0) {
            hundir(0);
        }
        return documento;
    }

    /**
     * Restaura la propiedad del min heap desplazando un nodo hacia las hojas.
     * Se ejecuta de forma recursiva comparando el nodo con sus hijos.
     *
     * @param indice El índice en el arreglo del nodo que debe ser evaluado y posiblemente hundido.
     */
    private void hundir(int indice) {
        int indiceMinimo = indice;
        int hijoIzq = getHijoIzquierdo(indice);
        int hijoDer = getHijoDerecho(indice);

        if (hijoIzq < tamano && monticulo[hijoIzq].getEtiquetaT() < monticulo[indiceMinimo].getEtiquetaT()) {
            indiceMinimo = hijoIzq;
        }
        if (hijoDer < tamano && monticulo[hijoDer].getEtiquetaT() < monticulo[indiceMinimo].getEtiquetaT()) {
            indiceMinimo = hijoDer;
        }
        if (indiceMinimo != indice) {
            intercambiar(indice, indiceMinimo);
            hundir(indiceMinimo); 
        }
    }
}
