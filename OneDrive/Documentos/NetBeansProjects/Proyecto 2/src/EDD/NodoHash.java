package EDD;

import Modelos.Documento;
import Modelos.Usuario;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * Nodo para la implementación de la Tabla de Dispersión utilizando el método 
 * de resolución de colisiones por encadenamiento.
 * Actúa como un puente lógico entre el usuario y su documento en el montículo.
 * @author valen
 */
public class NodoHash {
    private Usuario usuario;
    private Documento documentoEnCola;
    private long etiqueta;
    private NodoHash next; 
    /**
     * Constructor del NodoHash.
     * @param usuario El usuario propietario del documento.
     * @param documentoEnCola La referencia al documento enviado a imprimir.
     * @param etiqueta valor numérico que identifica la posición o prioridad del 
     * documento en el Montículo Binario.
     */
    public NodoHash(Usuario usuario, Documento documentoEnCola, long etiqueta) {
        this.usuario = usuario;
        this.documentoEnCola = documentoEnCola;
        this.etiqueta = etiqueta;
        this.next = null;
    }

    //GETTERS Y SETTERS. 
    public Usuario getUsuario() {
        return usuario;
    }

    public Documento getDocumentoEnCola() {
        return documentoEnCola;
    }

    public long getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(long etiqueta) {
        this.etiqueta = etiqueta;
    }

    public NodoHash getNext() {
        return next;
    }

    public void setNext(NodoHash next) {
        this.next = next;
    }
    
    
}
