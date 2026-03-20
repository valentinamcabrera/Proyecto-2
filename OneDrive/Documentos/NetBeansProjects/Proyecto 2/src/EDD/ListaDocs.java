/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EDD;

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
     *
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
     * Recupera un elemento de la lista dada su posición (índice).
     * Funciona de manera similar al acceso por índice en un arreglo tradicional.
     *
     * @param indice La posición (basada en cero) del elemento a recuperar.
     * @return El objeto almacenado en ese índice, o null si el índice está fuera de los límites.
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


    public NodoLista<T> getFirst() {
        return first;
    }

    public void setFirst(NodoLista<T> first) {
        this.first = first;
    }
    
}
