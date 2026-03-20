/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EDD;

/**
 *
 * @author valen
 */
public class NodoLista<T> {
    private T info;
    private NodoLista<T> next;

    public NodoLista(T info) {
        this.info = info;
        this.next = null;
    }

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
