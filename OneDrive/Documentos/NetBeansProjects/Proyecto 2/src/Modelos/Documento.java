package Modelos;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 ***
 * Representa un documento creado que será enviado a la cola de impresión.
 * Contiene la información básica del archivo sin referencias a su propietario,
 * permitiendo su encapsulamiento dentro de las estructuras de datos.
 * @author valen
 * @param <T>
 */
public class Documento<T> {
    private String id;
    private int tamano;
    private String tipo;

    /**
     * Constructor de la clase Documento.
     * @param id El nombre o título del documento.
     * @param tamano El tamaño del documento (cantidad de páginas o KB).
     * @param tipo El formato o extensión del documento .
     */
    public Documento(String id, int tamano, String tipo) {
        this.id = id;
        this.tamano = tamano;
        this.tipo = tipo;
    }
    /**
     * Retorna el nombre del documento.
     * @return 
     */
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    /**
     * Retorna el tamano del documento.
     * @return 
     */
    public int getTamano() {
        return tamano;
    }
    public void setTamano(int tamano) {
        this.tamano = tamano;
    }
    /**
     * Retorna el tipo de documento.
     * @return 
     */
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
}
