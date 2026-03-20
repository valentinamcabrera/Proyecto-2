package Modelos;

import EDD.ListaDocs;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * Representa a un usuario del sistema operativo simulado.
 * Gestiona la información de su nivel de prioridad y mantiene un registro
 * de los documentos que ha creado antes de enviarlos a la cola de impresión.
 * @author valen
 */
public class Usuario {
    private String nombre;
    private String tipo; 
    private ListaDocs documentosCreados; 
    
    /**
     * Constructor de la clase Usuario.
     * Inicializa la lista interna de documentos vacía.
     * @param nombre El identificador único del usuario en el sistema.
     * @param tipo El nivel de prioridad asignado (ej. alta, media, baja).
     */
    public Usuario(String nombre, String tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.documentosCreados = new ListaDocs<>();
    }
    /**
     * Retorna nombre del usuario
     * @return 
     */
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    /**
     * Retorna el tipo de prioridad del usuario.
     * @return 
     */
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    /**
     * Retorna lista de documentos que el usuario ha creado.
     * @return 
     */
    public ListaDocs getDocumentosCreados() {
        return documentosCreados;
    }

    
    
}
