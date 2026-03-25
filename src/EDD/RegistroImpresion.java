/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EDD;

import Modelos.Documento;

/**
 * Construye un nuevo registro de impresión asociado a un documento dentro
 * de la cola gestionada por el montículo binario. ada registro representa la entrada
 * del documento en la cola e incluye un identificador único, su etiqueta temporal de 
 * prioridad y la posición actual que ocupa dentro del heap.
 *
 * @author valen
 */
public class RegistroImpresion {
    private int idRegistro;
    private Documento documento;
    private long timetag; 
    private int indiceHeap;
    
    /**
     * @param idRegistro identificador único del registro de impresión. Para distinguir 
     * cada envío a cola, incluso si un mismo documento ha sido gestionado en distintos momentos.
     * @param documento documento asociado a este registro dentro de la cola de impresión.
     * @param timetag etiqueta temporal que determina la prioridad del documento
     * en el montículo binario; mientras menor sea su valor, antes será atendido.
     * @param indiceHeap posición actual del registro dentro del arreglo que
     * implementa el montículo binario. Permite ubicar el elemento directamente sin recorrer 
     * linealmente la estructura.
     */
    public RegistroImpresion(int idRegistro, Documento documento, long timetag, int indiceHeap) {
        this.idRegistro = idRegistro;
        this.documento = documento;
        this.timetag = timetag;
        this.indiceHeap = indiceHeap;

    }
    //GETTERS Y SETTERS
    public int getIdRegistro() { 
        return idRegistro;
    } 

    public Documento getDocumento() {
        return documento;
    }

    public long getTimetag() {
        return timetag;
    }
    public void setTimetag(long timetag) {
        this.timetag = timetag;
    }
    public int getIndiceHeap() { 
        return indiceHeap; }
    public void setIndiceHeap(int indiceHeap) { 
        this.indiceHeap = indiceHeap; 
    }
    
   

    
    
    
}
