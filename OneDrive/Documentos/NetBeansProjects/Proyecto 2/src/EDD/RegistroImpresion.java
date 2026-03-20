/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EDD;

import Modelos.Documento;

/**
 *
 * @author valen
 */
public class RegistroImpresion {
    private Documento documento;
    private long timetag; 

    public RegistroImpresion(Documento documento, long etiquetaT) {
        this.documento = documento;
        this.timetag = etiquetaT;
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
    
    
    
}
