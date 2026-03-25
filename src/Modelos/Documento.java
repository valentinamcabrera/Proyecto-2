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
 */
public class Documento {
    private String id;
    private int tamano;
    private String tipo;
    private boolean encola;
    private int idRegistroActivo;


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
        this.encola = false;
        this.idRegistroActivo=-1;
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
    /**
     * Indica si documento esta en cola o no
     * @return 
     */
    public boolean isEnCola() { 
        return encola; 
    }
    public void setEnCola(boolean enCola) { 
        this.encola = enCola; 
    }

    public int getIdRegistroActivo() { 
        return idRegistroActivo; }
    public void setIdRegistroActivo(int idRegistroActivo) {
        this.idRegistroActivo = idRegistroActivo;
    }
    
}
