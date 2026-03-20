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
    private String clave;
    private Usuario usuario;
    private Documento documento;
    private RegistroImpresion registro;
    private NodoHash next; 
    /**
     * Constructor del NodoHash.
     * @param clave Clave única asociada al nodo dentro de la tabla hash.
     * Se utiliza como identificador de búsqueda para localizar rápidamente
     * un documento registrado en la cola de impresión.
     * @param usuario El usuario propietario del documento.
     * @param documento documento asociado al registro.
     * @param registro La referencia al documento enviado a imprimir.
     * 
     */
    public NodoHash(String clave, Usuario usuario, Documento documento, RegistroImpresion registro) {
        this.clave = clave;
        this.usuario = usuario;
        this.documento = documento;
        this.registro = registro;
        this.next = null;
    }

    //GETTERS Y SETTERS. 
    public Usuario getUsuario() {
        return usuario;
    }
    public RegistroImpresion getRegistro() {
        return registro; }

    public Documento getDocumento() {
        return documento;}

    public String getClave() {
        return clave;}

    public NodoHash getNext() {
        return next;}
    
    public void setNext(NodoHash next) {
        this.next = next;}
    
}
