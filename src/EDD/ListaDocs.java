/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EDD;

import Modelos.Documento;

/**
 * Implementación de Lista Enlazada Simple genérica.
 * Sse utiliza para guardar los documentos creados por cada usuario.
 * @author valen
 * @param <T>
 */
public class ListaDocs<T> {
    private NodoLista<T> first;
    private int tamano;
    
    /**
     * Constructor de la Lista.
     * Inicializa una lista vacía, estableciendo la cabeza en nulo y el tamaño en cero.
     */
    public ListaDocs(){
        this.first = null;
        this.tamano =0;
    }
    /** Inserta un nuevo elemento al final de la lista enlazada.
     * Recorre la estructura hasta encontrar el último nodo y enlaza el nuevo elemento allí.
     * Si esta vacia se inserta de primero.
     * @param data El objeto de tipo T que se desea agregar a la lista.
     */
    public void insertar(T data) {
        NodoLista<T> nuevoNodo = new NodoLista<>(data);
        if (this.esVacia()) {
            first = nuevoNodo;
        } else {
            NodoLista<T> actual = first;
            while (actual.getNext() != null) {
                actual = actual.getNext();
            }
            actual.setNext(nuevoNodo);
        }
        tamano++;
    }
    
    /**
     * Obtiene el elemento almacenado en una posición específica de la lista.
     * El recorrido se realiza desde el primer nodo hasta alcanzar el índice solicitado.
     *
     *  @param indice posición del elemento a recuperar, basada en cero.
     * @return el elemento ubicado en el índice indicado, o {@code null} si el índice
     *         está fuera de los límites válidos de la lista.
     */
    public T obtener(int indice) {
        if (indice < 0 || indice >= tamano) {
            return null; // Opcional: lanzar IndexOutOfBoundsException
        }
        NodoLista<T> actual = first;
        for (int i = 0; i < indice; i++) {
            actual = actual.getNext();
        }
        return actual.getInfo();
    }
    /**
    * Elimina el elemento ubicado en una posición específica de la lista enlazada.
    * Si el índice corresponde al primer nodo, la cabeza de la lista se actualiza
    * al siguiente elemento. En cualquier otro caso, se recorre la estructura hasta
    * el nodo anterior al índice indicado y se reajustan los enlaces para excluir
    * el nodo objetivo.
    *
    * @param indice posición del elemento que se desea eliminar, basada en cero.
    * @return {@code true} si el elemento fue eliminado correctamente;
    *         {@code false} si el índice está fuera de los límites válidos de la lista.
    */
     public boolean eliminarPorIndice(int indice) {
        if (indice < 0 || indice >= tamano) return false;
        if (indice == 0) {
            first = first.getNext();
            tamano--;
            return true;
        }
        NodoLista<T> anterior = first;
        for (int i = 0; i < indice - 1; i++) {
            anterior = anterior.getNext();
        }
        anterior.setNext(anterior.getNext().getNext());
        tamano--;
        return true;
    }
   /**
    * Elimina de la lista un documento que aún no haya sido enviado a la cola de impresión.
    * El método recorre la estructura buscando un objeto {@code Documento} cuyo identificador
    * coincida con el recibido y cuyo estado indique que no está actualmente en cola.
    * Si lo encuentra, reajusta los enlaces de la lista para removerlo.
    *
    * @param idDocumento identificador del documento que se desea eliminar.
    * @return {@code true} si el documento fue encontrado y eliminado correctamente;
    *         {@code false} si no existe en la lista o si ya fue enviado a impresión.
    */

      public boolean eliminarDocumentoNoEnviado(String idDocumento) {
        NodoLista<T> actual = first;
        NodoLista<T> anterior = null;

        while (actual != null) {
            if (actual.getInfo() instanceof Documento doc) {
                if (doc.getId().equals(idDocumento) && !doc.isEnCola()) {
                    if (anterior == null) {
                        first = actual.getNext();
                    } else {
                        anterior.setNext(actual.getNext());
                    }
                    tamano--;
                    return true;
                }
            }
            anterior = actual;
            actual = actual.getNext();
        }
        return false;
    }
      /**
     * Obtiene la cantidad de elementos actualmente almacenados en la lista.
     * @return El tamaño entero de la lista.
     */
    public int getTamaño() { 
        return tamano; 
    }

    /**
     * Verifica si la lista se encuentra vacía.
     * @return true si la lista no contiene elementos, false en caso contrario.
     */
    public boolean esVacia() { 
        return first == null; 
    }
    /**
     * Obtiene la referencia al primer nodo de la lista.
     * Este método permite acceder al inicio de la estructura para recorridos externos.
     *
     * @return nodo inicial de la lista, o {@code null} si la lista está vacía.
     */
    public NodoLista<T> getFirst() {
        return first;
    }
    /**
     * Establece una nueva referencia al primer nodo de la lista.
     * Se utiliza cuando es necesario modificar manualmente la cabeza de la estructura.
     *
     * @param first nuevo nodo que pasará a ser el primero de la lista.
     */
    public void setFirst(NodoLista<T> first) {
        this.first = first;
    }
}
