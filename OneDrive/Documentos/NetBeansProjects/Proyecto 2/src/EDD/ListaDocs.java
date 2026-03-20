/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EDD;

/**
 * Lista simple enlazada para 
 * @author valen
 * @param <T>
 */
public class ListaDocs<T> {
    private NodoLista<T> first;
    
    public ListaDocs(){
        this.first = null;
    }

    public NodoLista<T> getFirst() {
        return first;
    }

    public void setFirst(NodoLista<T> first) {
        this.first = first;
    }
    
}
