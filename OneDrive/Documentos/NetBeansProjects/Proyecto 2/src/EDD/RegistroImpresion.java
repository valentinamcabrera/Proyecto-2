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
    private long etiquetaT; 

    public RegistroImpresion(Documento documento, long etiquetaT) {
        this.documento = documento;
        this.etiquetaT = etiquetaT;
    }

    public Documento getDocumento() {
        return documento;
    }

    public long getEtiquetaT() {
        return etiquetaT;
    }

    public void setEtiquetaT(long etiquetaT) {
        this.etiquetaT = etiquetaT;
    }
    
    
    
}
