/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SO;

import EDD.HashTable;
import EDD.ListaDocs;
import EDD.MonticuloBinario;
import EDD.NodoHash;
import EDD.NodoLista;
import EDD.RegistroImpresion;
import Interfaz.GestorArchivos;
import Modelos.Documento;
import Modelos.Usuario;
import java.io.File;
import java.io.IOException;

/**
 * Representa el sistema operativo principal de gestión de impresión.
 * Esta clase coordina el registro de usuarios, la creación y administración
 * de documentos, la cola de impresión implementada mediante un montículo binario
 * y la localización rápida de documentos en cola a través de hash table.
 *
 * Además, mantiene un reloj lógico que permite asignar prioridades temporales
 * a los documentos enviados a impresión.
 */
public class Impresora {
    private MonticuloBinario colaImpresion;
    private HashTable tablaDocumentos;
    private ListaDocs<Usuario> usuariosRegistrados;
    private long reloj;
    private int siguienteIdRegistro;

   /**
    * Construye una nueva impresora lógica con sus estructuras inicializadas.
    * Crea una cola de impresión vacía, una tabla hash para documentos en cola,
    * una lista vacía de usuarios registrados y deja el reloj lógico en cero.
    */
    public Impresora() {
        this.colaImpresion = new MonticuloBinario(100);
        this.tablaDocumentos = new HashTable(53);
        this.usuariosRegistrados = new ListaDocs<>();
        this.reloj = 0;
        this.siguienteIdRegistro = 1;
    }
    /**
     * Genera la clave única asociada a un documento dentro de la tabla hash.
     * La clave se forma a partir de la combinación del nombre del usuario y el
     * identificador del documento, permitiendo distinguir registros de manera unívoca.
     *
     * @param nombreUsuario nombre del usuario propietario del documento.
     * @param nombreDocumento identificador del documento.
     * @return clave única compuesta para ubicar el documento en la tabla hash.
     */
    private String clave(String nombreUsuario, String nombreDocumento) {
        return nombreUsuario + "::" + nombreDocumento;
    }
    /**
     * Registra un nuevo usuario en el sistema si aún no existe otro con el mismo nombre.
     * @param nombre nombre del usuario que se desea agregar.
     * @param tipo tipo o categoría del usuario dentro del sistema.
     * @return {@code true} si el usuario fue agregado correctamente;
     *         {@code false} si ya existía un usuario con ese nombre.
     */
    public boolean agregarUsuario(String nombre, String tipo) {
        if (buscarUsuario(nombre) != null) return false;
        usuariosRegistrados.insertar(new Usuario(nombre, tipo));
        return true;
    }
    /**
     * Busca un usuario registrado en el sistema a partir de su nombre.
     *
     * @param nombre nombre del usuario que se desea localizar.
     * @return el usuario correspondiente si existe; {@code null} en caso contrario.
     */
    public Usuario buscarUsuario(String nombre) {
        NodoLista<Usuario> actual = usuariosRegistrados.getFirst();
        while (actual != null) {
            if (actual.getInfo().getNombre().equals(nombre)) return actual.getInfo();
            actual = actual.getNext();
        }
        return null;
    }
    /**
     * Crea y asocia un nuevo documento a un usuario existente.
     * El documento se agrega únicamente a la colección personal del usuario
     * y aún no entra a la cola de impresión.
     *
     * @param nombreUsuario nombre del usuario propietario del documento.
     * @param idDoc identificador del documento.
     * @param tamano tamaño del documento.
     * @param tipoDoc tipo o formato del documento.
     * @return {@code true} si el documento fue creado correctamente;
     *         {@code false} si el usuario no existe o si ya posee un documento con ese identificador.
     */
    public boolean crearDocumento(String nombreUsuario, String idDoc, int tamano, String tipoDoc) {
        Usuario usuario = buscarUsuario(nombreUsuario);
        if (usuario == null) return false;
        return usuario.agregarDocumento(new Documento(idDoc, tamano, tipoDoc));
    }
    /**
     * Elimina un documento de la colección de un usuario siempre que todavía no
     * haya sido enviado a la cola de impresión.
     *
     * @param nombreUsuario nombre del usuario propietario del documento.
     * @param idDoc identificador del documento que se desea eliminar.
     * @return {@code true} si el documento fue eliminado correctamente;
     *         {@code false} si el usuario no existe, el documento no existe o ya está en cola.
     */
    public boolean eliminarDocumentoNoEnviado(String nombreUsuario, String idDoc) {
        Usuario usuario = buscarUsuario(nombreUsuario);
        if (usuario == null) return false;
        return usuario.eliminarDocumentoNoEnviado(idDoc);
    }
    /**
     * Envía un documento a la cola de impresión del sistema.
     * Incrementa el reloj lógico, calcula la etiqueta temporal de prioridad,
     * crea un nuevo registro de impresión, lo inserta en el montículo binario y lo
     * registra también en la tabla hash para su localización rápida.
     * Si el envío se realiza como prioritario, la etiqueta temporal puede ajustarse
     * de acuerdo con el tipo de usuario para adelantar su posición en la cola.
     *
     * @param nombreUsuario nombre del usuario propietario del documento.
     * @param idDoc identificador del documento que se desea enviar a impresión.
     * @param prioritario indica si el documento debe enviarse con prioridad especial.
     * @return {@code true} si el documento fue enviado correctamente a la cola;
     *         {@code false} si el usuario no existe, el documento no existe o ya estaba en cola.
     */
    public boolean enviarImprimir(String nombreUsuario, String idDoc, boolean prioritario) {
        Usuario usuario = buscarUsuario(nombreUsuario);
        if (usuario == null) return false;

        Documento doc = usuario.buscarDocumento(idDoc);
        if (doc == null || doc.isEnCola()) return false;

        reloj++;
        long etiqueta = reloj;

        if (prioritario) {
            String prioridad = usuario.getTipo().toLowerCase();
            if (prioridad.equals("prioridad_alta")) etiqueta -= 1000;
            else if (prioridad.equals("prioridad_media")) etiqueta -= 500;
        }
       String clave= clave(nombreUsuario, idDoc);


        RegistroImpresion registro = new RegistroImpresion(
            siguienteIdRegistro++,
            doc,
            etiqueta,
            -1,
            clave);

        colaImpresion.insertar(registro);
        tablaDocumentos.insertarRegistro(clave(nombreUsuario, idDoc), usuario, doc, registro);

        doc.setEnCola(true);
        doc.setIdRegistroActivo(registro.getIdRegistro());
        return true;
    }

    /**
    * Libera la impresora lógica atendiendo el documento con mayor prioridad
    * actualmente presente en la cola.Extrae el mínimo del montículo binario, elimina
    * su referencia en la hash, actualiza el estado del documento correspondiente y lo
    * remueve de su condición de documento en cola.
    *
    * @return el documento que fue retirado de la cola para ser atendido,
    *         o {@code null} si no había documentos pendientes.
    */
   public Documento liberarImpresora()  {
       RegistroImpresion registro = colaImpresion.extraerMinimo();
       if (registro == null) return null;

       tablaDocumentos.eliminarRegistro(registro.getClaveHash());

       Documento doc = registro.getDocumento();
       doc.setEnCola(false);
       doc.setIdRegistroActivo(-1);
       return doc;
}
    /**
     * Cancela la impresión de un documento que ya se encuentra en la cola.
     * El método localiza el registro correspondiente mediante la tabla hash y,
     * una vez obtenida su posición actual dentro del heap, elimina el elemento
     * del montículo sin necesidad de recorrer linealmente toda la estructura.
     *
     * Si la cancelación se realiza con éxito, también se actualiza el estado del
     * documento y se elimina su referencia de la tabla hash.
     *
     * @param nombreUsuario nombre del usuario propietario del documento.
     * @param nombreDocumento identificador del documento cuya impresión se desea cancelar.
     * @return {@code true} si la impresión fue cancelada correctamente;
     *         {@code false} si el documento no estaba registrado en la cola.
     */
    public boolean cancelarImpresion(String nombreUsuario, String nombreDocumento) {
        String clave = clave(nombreUsuario, nombreDocumento);
        NodoHash nodo = tablaDocumentos.buscarNodo(clave);
        if (nodo == null) return false;

        RegistroImpresion registro = nodo.getRegistro();
        boolean eliminado = colaImpresion.eliminarEnIndice(registro.getIndiceHeap());

        if (eliminado) {
            Documento doc = nodo.getDocumento();
            doc.setEnCola(false);
            doc.setIdRegistroActivo(-1);
            tablaDocumentos.eliminarRegistro(clave);
        }
        return eliminado;
    }
    /**
    * Elimina un usuario del sistema y remueve de forma implícita su colección
    * de documentos asociados que aún no hayan sido enviados a impresión.
    *
    * Los documentos que ya se encuentren en la cola de impresión no son
    * eliminados por esta operación, ya que continúan siendo gestionados por
    * el montículo binario y la tabla hash hasta que sean atendidos o cancelados.
    *
    * @param nombre nombre del usuario que se desea eliminar.
    * @return {@code true} si el usuario fue encontrado y eliminado correctamente;
    *         {@code false} si no existe un usuario registrado con ese nombre.
    */
   public boolean eliminarUsuario(String nombre) {
       NodoLista<Usuario> actual = usuariosRegistrados.getFirst();
       NodoLista<Usuario> anterior = null;

       while (actual != null) {
           if (actual.getInfo().getNombre().equals(nombre)) {
               if (anterior == null) {
                   usuariosRegistrados.setFirst(actual.getNext());
               } else {
                   anterior.setNext(actual.getNext());
               }
               return true;
           }
           anterior = actual;
           actual = actual.getNext();
       }

       return false;
   }
    /**
     * Carga usuarios en el sistema a partir de un archivo CSV.
     * La lectura y construcción de la lista se delega a la clase encargada
     * de manejar archivos externos.
     *
     * @param archivo archivo CSV desde el cual se cargarán los usuarios.
     * @throws IOException si ocurre un error durante la lectura del archivo.
     */
    public void inicializarUsuariosDesdeCSV(File archivo) throws IOException {
        GestorArchivos lector = new GestorArchivos();
        this.usuariosRegistrados = lector.cargarUsuarios(archivo);
    }
    /**
     * Retorna la cola de impresión administrada por el sistema.
     *
     * @return montículo binario que contiene los registros de impresión activos.
     */
    public MonticuloBinario getColaImpresion() { 
        return colaImpresion; }
    /**
     * Retorna la lista de usuarios registrados actualmente en el sistema.
     *
     * @return lista de usuarios registrados.
     */
    public ListaDocs<Usuario> getUsuariosRegistrados() {
        return usuariosRegistrados; }
    /**
     * Retorna el valor actual del reloj lógico del sistema.
     *
     * @return valor del reloj lógico.
     */
    public long getReloj() { 
        return reloj; }
}
