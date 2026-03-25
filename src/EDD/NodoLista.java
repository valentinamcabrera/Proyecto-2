/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EDD;

/**
 * Nodo genérico para la construcción de una lista enlazada simple.
 
 * @param <T> El tipo de dato abstracto que almacenará este nodo.
 * @author valen
 */
 
public class NodoLista<T> {
    private T info;
    private NodoLista<T> next;
    /**
     * Constructor del nodo
     * Inicializando con dato suministrado y apuntador a siguiente en null
     * @param info 
     */
    public NodoLista(T info) {
        this.info = info;
        this.next = null;
    }
//Getters y Setters
    public T getInfo() {
        return info;
    }

    public void setInfo(T info) {
        this.info = info;
    }

    public NodoLista<T> getNext() {
        return next;
    }

    public void setNext(NodoLista<T> next) {
        this.next = next;
    }
    
    
}
