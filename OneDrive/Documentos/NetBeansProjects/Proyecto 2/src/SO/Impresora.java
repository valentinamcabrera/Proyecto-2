/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SO;

import EDD.HashTable;
import EDD.ListaDocs;
import EDD.MonticuloBinario;
import Interfaz.GestorArchivos;
import Modelos.Documento;
import Modelos.Usuario;
import java.io.File;
import java.io.IOException;

/**
 * El sistema operativo, controlador principal que gestiona la lógica de negocio 
 * del sistema de impresión.
 * Conecta las estructuras de datos y simula el avance del tiempo del reloj.
 *  @author valen
 */
public class Impresora {
    private MonticuloBinario colaImpresion;
    private HashTable tablaDocumentos;
    private ListaDocs<Usuario> usuariosRegistrados;
    private long reloj;
    
    /**
     * Constructor del Gestor.
     * Inicializa las estructuras con capacidades predefinidas.
     */
    public Impresora() {
        this.colaImpresion = new MonticuloBinario(100);
        this.tablaDocumentos = new HashTable(53);
        this.usuariosRegistrados = new ListaDocs<>();
        this.reloj = 0;
    }
    /**
     * Envía un documento a la cola de impresión calculando su prioridad.
     *
     * @param usuario El usuario propietario del documento.
     * @param doc El documento a imprimir.
     */
    public void enviarImprimir(Usuario usuario, Documento doc) {
        reloj++; 
        long tiempoOriginal = reloj;
        long deltaP = 0;

        String prioridad = usuario.getTipo().toLowerCase();
        if (prioridad.equals("prioridad_alta")) {
            deltaP = 1000; 
        } else if (prioridad.equals("prioridad_media")) {
            deltaP = 500;
        }
        long etiquetaTiempoFinal = tiempoOriginal - deltaP;

        colaImpresion.insertar(doc, etiquetaTiempoFinal);
        tablaDocumentos.insertarRegistro(usuario, doc, etiquetaTiempoFinal);
    }

    /**
     * Extrae el documento con mayor prioridad de la cola para ser impreso.
     *
     * @return El documento impreso, o null si la cola está vacía.
     * Por la restricción de que el montículo no sabe de quién es el documento,
     * no se elimina el registro de la hash table aquí, la tab;a se usa principalmente
     * para las cancelaciones.
     */
    public Documento liberarImpresora() {
        return colaImpresion.extraerMinimo(); 
    }
    /**
     * Cancela la impresión de un documento específico utilizando el hash table
     * para ubicarlo y el heap y eliminarlo.
     *
     * @param nombreUsuario Nombre del propietario.
     * @param nombreDocumento Nombre del documento a cancelar.
     * @return true si se canceló correctamente, false si no se encontró.
     */
    public boolean cancelarImpresion(String nombreUsuario, String nombreDocumento) {
        long etiqueta = tablaDocumentos.buscarEtiquetaDocumento(nombreUsuario, nombreDocumento);
      
        if (etiqueta == -1) {
            return false; 
        }

        // 2. Eliminar del montículo usando el algoritmo de Decrease Key
        boolean borradoMonticulo = colaImpresion.eliminarDocumento(etiqueta);

        if (borradoMonticulo) {
            tablaDocumentos.eliminarRegistro(nombreUsuario, nombreDocumento);
        }

        return borradoMonticulo;
    }

    /**
     * Getter que servira para la interfaz
     * @return 
     */
    public MonticuloBinario getColaImpresion() {
        return colaImpresion;
    }
    public void inicializarUsuariosDesdeCSV(File archivo) throws IOException {
        GestorArchivos lector = new GestorArchivos();
        this.usuariosRegistrados = lector.cargarUsuarios(archivo);
    }

    
}
