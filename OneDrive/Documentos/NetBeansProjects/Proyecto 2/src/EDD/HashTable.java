/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EDD;

import Modelos.Documento;
import Modelos.Usuario;

/**
 * Implementación de una Tabla de Dispersión (Hash Table) con resolución
 * de colisiones mediante encadenamiento (Separate Chaining).
 * Diseñada para mantener un registro en tiempo O(1) de los documentos enviados por los usuarios.
 * @author valen
 */
public class HashTable {
    private NodoHash[] tabla;
    private int capacidad;
    private int tamano;

    /**
     * Constructor
     * @param capacidad Tamaño del arreglo base. Se exige un número primo (ej. 53, 97) 
     * para minimizar el agrupamiento de colisiones.
     */
    public HashTable(int capacidad) {
        this.capacidad = capacidad;
        this.tabla = new NodoHash[capacidad];
        this.tamano = 0;
    }

    /**
     * Función de dispersión matemática polinomial.
     * @param clave El nombre de usuario que sirve como identificador.
     * @return El índice calculado estrictamente dentro de los límites del arreglo.
     */
    private int hashFunction(String clave) {
        int hash = 0;
        for (int i = 0; i < clave.length(); i++) {
            hash = (31 * hash + clave.charAt(i)) % capacidad;
        }
        if (hash < 0) {
            hash += capacidad;
        }
        return hash;
    }
    /** Inserta un nuevo registro en la tabla. Si ocurre colisión, 
     * enlaza el nuevo nodo al inicio de la lista correspondiente (O(1)).
     * @param usuario El propietario del documento.
     * @param documento El archivo enviado a la cola.
     * @param etiquetaT El puente lógico que conecta con la posición en el Montículo.
     */
    public void insertarRegistro(Usuario usuario, Documento documento, long etiquetaT) {
        int indice = hashFunction(usuario.getNombre());
        NodoHash nuevoNodo = new NodoHash(usuario, documento, etiquetaT);

        if (tabla[indice] == null) {
            tabla[indice] = nuevoNodo;
        } else {
            nuevoNodo.setNext(tabla[indice]);
            tabla[indice] = nuevoNodo;
        }
        tamano++;
    }

    /**
     * Busca la etiqueta de tiempo de un documento específico de un usuario.
     * Este método es el paso previo y obligatorio para eliminar un nodo del heap
     *
     * @param nombreUsuario El identificador del usuario.
     * @param nombreDocumento El título del documento a buscar.
     * @return La etiqueta de tiempo (long) si se encuentra, o -1 si el registro no existe.
     */
    public long buscarEtiquetaDocumento(String nombreUsuario, String nombreDocumento) {
        int indice = hashFunction(nombreUsuario);
        NodoHash actual = tabla[indice];
        while (actual != null) {
            if (actual.getUsuario().getNombre().equals(nombreUsuario) &&
                actual.getDocumentoEnCola().getId().equals(nombreDocumento)) {
                return actual.getEtiqueta();
            }
            actual = actual.getNext();
        }
        return -1; 
    }

    /**
     * Elimina lógicamente un registro de la tabla hash reestructurando los punteros
     * de la lista enlazada correspondiente.
     *
     * @param nombreUsuario El identificador del usuario.
     * @param nombreDocumento El título del documento a eliminar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     */
    public boolean eliminarRegistro(String nombreUsuario, String nombreDocumento) {
        int indice = hashFunction(nombreUsuario);
        NodoHash actual = tabla[indice];
        NodoHash anterior = null;

        while (actual != null) {
            if (actual.getUsuario().getNombre().equals(nombreUsuario) &&
                actual.getDocumentoEnCola().getId().equals(nombreDocumento)) {
                
                if (anterior == null) {
                    // El nodo a eliminar es la cabeza de la lista
                    tabla[indice] = actual.getNext();
                } else {
                    // El nodo a eliminar se encuentra en el medio o al final
                    anterior.setNext(actual.getNext());
                }
                tamano--;
                return true;
            }
            anterior = actual;
            actual = actual.getNext();
        }
        return false;
    }
}
