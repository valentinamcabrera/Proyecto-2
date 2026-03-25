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
 * y la localización rápida de documentos a través de hash tables.
 * 
 * Se emplean dos tablas de dispersión, una para ubicar registros por la clave
 * compuesta usuario-documento durante la cancelación de impresiones, y otra para 
 * acceder a los registros por su identificador interno cuando se requiere mantener 
 * referencias consistentes dentro de la cola implementada con montículo binario.
 *
 * Además, mantiene un reloj lógico que permite asignar prioridades temporales
 * a los documentos enviados a impresión.
 */
public class Impresora {
    private MonticuloBinario colaImpresion;
    private HashTable tablaPorUsuarioDocumento;
    private HashTable tablaPorIdRegistro;
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
        this.tablaPorUsuarioDocumento = new HashTable(53);
        this.tablaPorIdRegistro = new HashTable(53);
        this.usuariosRegistrados = new ListaDocs<>();
        this.reloj = System.currentTimeMillis();;
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
    * Genera la clave única asociada a un registro de impresión a partir de su identificador.
    *
    * @param idRegistro identificador del registro.
    * @return representación en texto del id del registro.
    */
   private String claveRegistro(int idRegistro) {
       return String.valueOf(idRegistro);
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
 * El método incrementa el reloj lógico, calcula la etiqueta temporal,
 * crea un registro de impresión, lo inserta en el montículo binario
 * y lo registra en dos tablas hash:
 * una por usuario-documento y otra por identificador de registro.
 *
 * @param nombreUsuario nombre del usuario propietario del documento.
 * @param idDoc identificador del documento a enviar.
 * @param prioritario indica si el documento debe tratarse como prioritario.
 * @return {@code true} si el documento fue enviado correctamente;
 *         {@code false} en caso contrario.
 */
    public boolean enviarImprimir(String nombreUsuario, String idDoc, boolean prioritario) {
        Usuario usuario = buscarUsuario(nombreUsuario);
        if (usuario == null) return false;

        Documento doc = usuario.buscarDocumento(idDoc);
        if (doc == null || doc.isEnCola()) return false;

        
        long Reloj = tiempoTranscurrido();
        long etiqueta;

        if (!prioritario) {
            etiqueta = 2_000_000L + Reloj;
        } else {
            String prioridad = usuario.getTipo().toLowerCase();

            if (prioridad.equals("prioridad_alta")) {
                etiqueta = Reloj;
            } else if (prioridad.equals("prioridad_media")) {
                etiqueta = 1_000_000L + Reloj;
            } else {
                etiqueta = 2_000_000L + Reloj;
            }
        }
        int paginas = doc.getTamano();
        etiqueta += paginas * 10;
        
        RegistroImpresion registro = new RegistroImpresion(
            siguienteIdRegistro++,
            doc,
            etiqueta,
            -1
        );
        colaImpresion.insertar(registro);

        String claveUsuarioDocumento = clave(nombreUsuario, idDoc);
        String claveIdRegistro = claveRegistro(registro.getIdRegistro());

        tablaPorUsuarioDocumento.insertarRegistro(claveUsuarioDocumento, usuario, doc, registro);
        tablaPorIdRegistro.insertarRegistro(claveIdRegistro, usuario, doc, registro);

        doc.setEnCola(true);
        doc.setIdRegistroActivo(registro.getIdRegistro());

        return true;
    }

    /**
     * Libera la impresora lógica atendiendo el documento con mayor prioridad
     * actualmente presente en la cola.
     * Extrae el mínimo del montículo binario, localiza su información asociada
     * mediante la tabla hash por id de registro y elimina las referencias del
     * registro en ambas tablas hash.
     *
     * @return el documento retirado de la cola para ser atendido,
     *         o {@code null} si no había documentos pendientes.
     */
    public Documento liberarImpresora() {
        RegistroImpresion registro = colaImpresion.extraerMinimo();
        if (registro == null) return null;

        String claveIdRegistro = claveRegistro(registro.getIdRegistro());
        NodoHash nodoId = tablaPorIdRegistro.buscarNodo(claveIdRegistro);

        if (nodoId != null) {
            String claveUsuarioDocumento = clave(
                nodoId.getUsuario().getNombre(),
                nodoId.getDocumento().getId()
            );

            tablaPorUsuarioDocumento.eliminarRegistro(claveUsuarioDocumento);
            tablaPorIdRegistro.eliminarRegistro(claveIdRegistro);
        }

        Documento doc = registro.getDocumento();
        doc.setEnCola(false);
        doc.setIdRegistroActivo(-1);

        return doc;
    }
    /**
     * Cancela la impresión de un documento que ya se encuentra en la cola.
     * Localiza el registro por medio de la tabla hash usuario-documento,
     * elimina el elemento del montículo usando su índice actual y limpia
     * ambas tablas hash asociadas al registro.
     *
     * @param nombreUsuario nombre del usuario propietario del documento.
     * @param nombreDocumento identificador del documento cuya impresión se desea cancelar.
     * @return {@code true} si la impresión fue cancelada correctamente;
     *         {@code false} si no se encontró el documento en cola.
     */
    public boolean cancelarImpresion(String nombreUsuario, String nombreDocumento) {
        String claveUsuarioDocumento = clave(nombreUsuario, nombreDocumento);
        NodoHash nodo = tablaPorUsuarioDocumento.buscarNodo(claveUsuarioDocumento);

        if (nodo == null) {
            return false;
        }

        RegistroImpresion registro = nodo.getRegistro();
        boolean eliminado = colaImpresion.eliminarEnIndice(registro.getIndiceHeap());

        if (eliminado) {
            Documento doc = nodo.getDocumento();
            doc.setEnCola(false);
            doc.setIdRegistroActivo(-1);

            tablaPorUsuarioDocumento.eliminarRegistro(claveUsuarioDocumento);
            tablaPorIdRegistro.eliminarRegistro(claveRegistro(registro.getIdRegistro()));
        }

        return eliminado;
    }
    
    /**
    * Obtiene la lista de nombres de usuarios que actualmente tienen al menos
    * un documento presente en la cola de impresión. La información se construye
    * a partir de los registros activos del montículo y la tabla hash auxiliar.
    *
    * @return lista de nombres de usuarios con documentos en cola
    */
    public ListaDocs<String> getUsuariosConDocumentosEnCola() {
        ListaDocs<String> usuariosEnCola = new ListaDocs<>();
        RegistroImpresion[] registros = colaImpresion.getArregloInterno();
        int tam = colaImpresion.getTamano();

        for (int i = 0; i < tam; i++) {
            RegistroImpresion registro = registros[i];
            if (registro == null) continue;

            NodoHash nodo = tablaPorIdRegistro.buscarNodo(claveRegistro(registro.getIdRegistro()));
            if (nodo == null || nodo.getUsuario() == null) continue;

            String nombreUsuario = nodo.getUsuario().getNombre();
            if (!listaContiene(usuariosEnCola, nombreUsuario)) {
                usuariosEnCola.insertar(nombreUsuario);
            }
        }
    return usuariosEnCola;
    }
    
    /**
     * Obtiene la lista de identificadores de documentos que actualmente se
     * encuentran en la cola de impresión para un usuario específico. La búsqueda
     * se realiza sobre los registros activos de la cola.
     *
     * @param nombreUsuario nombre del usuario cuyos documentos en cola se desean consultar
     * @return lista de identificadores de documentos en cola asociados al usuario indicado
     */
    public ListaDocs<String> getDocumentosEnColaDeUsuario(String nombreUsuario) {
        ListaDocs<String> documentosEnCola = new ListaDocs<>();
        if (nombreUsuario == null || nombreUsuario.isEmpty()) return documentosEnCola;

        RegistroImpresion[] registros = colaImpresion.getArregloInterno();
        int tam = colaImpresion.getTamano();

        for (int i = 0; i < tam; i++) {
            RegistroImpresion registro = registros[i];
            if (registro == null) continue;

            NodoHash nodo = tablaPorIdRegistro.buscarNodo(claveRegistro(registro.getIdRegistro()));
            if (nodo == null || nodo.getUsuario() == null || nodo.getDocumento() == null) continue;

            if (nombreUsuario.equals(nodo.getUsuario().getNombre())) {
                String nombreDocumento = nodo.getDocumento().getId();
                if (!listaContiene(documentosEnCola, nombreDocumento)) {
                    documentosEnCola.insertar(nombreDocumento);
                }
            }
        }
        return documentosEnCola;
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
        int indice = 0;

        while (actual != null) {
            if (actual.getInfo().getNombre().equals(nombre)) {
                return usuariosRegistrados.eliminarPorIndice(indice);
            }
            actual = actual.getNext();
            indice++;
        }

        return false;
    }
    /**
    * Verifica si una lista de cadenas contiene un valor específico.
    *
    * @param lista lista en la que se desea buscar
    * @param valor valor a localizar dentro de la lista
    * @return true si el valor existe en la lista; false en caso contrario
    */
    private boolean listaContiene(ListaDocs<String> lista, String valor) {
        if (lista == null || valor == null) return false;

        NodoLista<String> actual = lista.getFirst();
        while (actual != null) {
            if (valor.equals(actual.getInfo())) {
                return true;
            }
            actual = actual.getNext();
        }
        return false;
    }
    /**
    * Calcula el tiempo transcurrido desde el inicio de la simulación.
    *
    * @return tiempo transcurrido en milisegundos desde la inicialización del sistema.
    */
   private long tiempoTranscurrido() {
       return System.currentTimeMillis() - reloj;
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
        return tiempoTranscurrido(); }

   
}
