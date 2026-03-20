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
 * 
 * Cada elemento almacenado es un {@code RegistroImpresion}, cuya prioridad está determinada
 * por su {@code timetag}, mientras menor sea su valor, antes será atendido.
 *
 * Además de las operaciones tradicionales de inserción y extracción del mínimo, esta versión
 * mantiene actualizada la posición de cada registro dentro del heap para permitir eliminaciones
 * eficientes sin recorrer linealmente toda la estructura.
 * @author valen
 */
public class MonticuloBinario {
    private RegistroImpresion[] monticulo;
    private int tamano; //tamano actual de elementos en el monticulo
    private int capmax; //capacidad maxima
    
    /**
     * Constructor del Montículo Binario.
     * @param capmax La cantidad máxima de elementos que el arreglo puede almacenar.
     * lo inicializa en 0.
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
    * Inserta un nuevo registro de impresión en el montículo.
    * El elemento se agrega inicialmente al final del arreglo y luego se reubica
    * mediante la operación de flotación hasta restaurar la propiedad de montículo mínimo.
    *
    * Durante la inserción también se actualiza la posición del registro dentro del heap.
    *
    * @param registro registro de impresión que se desea agregar a la cola.
    * @throws IllegalStateException si el montículo ya alcanzó su capacidad máxima.
    */
    public void insertar(RegistroImpresion registro) {
        if (estaLleno()) {
            throw new IllegalStateException("La cola de impresión está llena. Capacidad máxima alcanzada.");
        }
        monticulo[tamano] = registro;
        registro.setIndiceHeap(tamano);
        flotar(tamano);
        tamano++;
    }
    

    /**
     * Reubica un elemento hacia niveles superiores del montículo mientras su prioridad
     * sea menor que la de su nodo padre.
     * Este proceso se utiliza después de insertar un nuevo registro o al disminuir
     * artificialmente su {@code timetag} para llevarlo hacia la raíz.
     *
     * @param indice posición inicial del elemento que debe flotar.
     */
    private void flotar(int indice) {
        int actual = indice;
        while (actual > 0) {
            int padre = getPadre(actual);
            if (monticulo[actual].getTimetag() < monticulo[padre].getTimetag()) {
                intercambiar(actual, padre);
                actual = padre;
            } else {
                break;
            }
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
        monticulo[i].setIndiceHeap(i);
        monticulo[j].setIndiceHeap(j);
    }
    
    /**
     * Extrae y retorna el registro de impresión con mayor prioridad del montículo,
     * es decir, aquel cuyo {@code timetag} es el menor de toda la estructura.
     *
     * Para mantener la propiedad del heap, el último elemento del arreglo ocupa
     * temporalmente la raíz y luego se reajusta mediante la operación de hundimiento.
     * El registro extraído queda marcado con índice inválido dentro del heap.
     *
     * @return registro con la menor etiqueta temporal, o {@code null} si el montículo está vacío.
     */
    public RegistroImpresion extraerMinimo() {
        if (esVacio()) {
            return null;  }
        RegistroImpresion min = monticulo[0];
        tamano--;
        if (tamano > 0) {
            monticulo[0] = monticulo[tamano];
            monticulo[0].setIndiceHeap(0);
            monticulo[tamano] = null;
            hundir(0);
        } else {
            monticulo[0] = null;
        }

        min.setIndiceHeap(-1);
        return min;
    }

    /**
     * Reubica un elemento hacia niveles inferiores del montículo mientras alguno
     * de sus hijos tenga mayor prioridad, es decir, un {@code timetag} menor.
     * Este proceso se utiliza principalmente después de extraer el mínimo y mover
     * el último elemento a la raíz.
     *
     * @param indice posición inicial del elemento que debe hundirse.
     */
    private void hundir(int indice) {
        int min = indice;
        int izq = getHijoIzquierdo(indice);
        int der = getHijoDerecho(indice);

        if (izq < tamano && monticulo[izq].getTimetag() < monticulo[min].getTimetag()) {
            min = izq;
        }
        if (der < tamano && monticulo[der].getTimetag() < monticulo[min].getTimetag()) {
            min = der;
        }
        if (min != indice) {
            intercambiar(indice, min);
            hundir(min); 
        }
    }
    
    /**
     * Elimina un registro ubicado en una posición específica del montículo sin necesidad
     * de recorrer linealmente toda la estructura. Se disminuye artificialmente su 
     * {@code timetag} hasta convertirlo en el elemento de mayor prioridad, se hace
     * flotar hasta la raíz y luego se elimina aplicando extraerMínimo.
     *
     * Este método permite cancelar eficientemente un documento en cola cuando su posición
     * actual ya es conocida mediante apoyo externo, como una tabla hash.
     *
     * @param indice posición del registro que se desea eliminar dentro del heap.
     * @return {@code true} si el registro fue eliminado correctamente;
     *         {@code false} si el índice es inválido.
     */
    public boolean eliminarEnIndice(int indice) {
        if (indice < 0 || indice >= tamano) return false;

        monticulo[indice].setTimetag(Long.MIN_VALUE);
        flotar(indice);
        extraerMinimo();
        return true;
    }
    /**
     * Retorna el arreglo interno utilizado para representar el montículo.
     * Este método puede emplearse para visualización, depuración o recorridos externos
     * controlados sobre la estructura.
     * @return arreglo que contiene los registros almacenados en el montículo.
     */
    public RegistroImpresion[] getArregloInterno() {
        return monticulo;
    }
}
