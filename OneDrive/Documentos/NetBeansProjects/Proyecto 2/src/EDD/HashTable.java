/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EDD;

import Modelos.Documento;
import Modelos.Usuario;

/**
 * Implementación de una Tabla de Dispersión (Hash Table) con resolución
 * de colisiones mediante encadenamiento (Separate Chaining).
 * Diseñada para mantener un registro en tiempo O(1) de los documentos enviados por los usuarios.
 * Cada entrada de la tabla almacena una clave única asociada a un usuario,
 * un documento y su respectivo registro de impresión, permitiendo ubicar
 * rápidamente la información necesaria sin recorrer linealmente el montículo binario.
 * @author valen
 */
public class HashTable {
    private NodoHash[] tabla;
    private int capacidad;
    private int tamano;

     /**
     * Construye una tabla hash vacía con la capacidad indicada.
     * Inicializa el arreglo interno y deja la estructura lista para almacenar
     * nodos asociados a documentos en cola de impresión.
     *
     * @param capacidad número de posiciones que tendrá la tabla hash.
     */
    public HashTable(int capacidad) {
        this.capacidad = capacidad;
        this.tabla = new NodoHash[capacidad];
        this.tamano = 0;
    }

    /**
     * Función de dispersión matemática polinomial.
     * @param clave El nombre de usuario que sirve como identificador.
     * @return El índice calculado estrictamente dentro de los límites del arreglo.
     * Se usa 31 un primo pequeño que ofrece buena dispersión al construir hashes
     * con bajo costo y uso ampliamente aceptado en implementaciones de Java.
     */
    private int hashFunction(String clave) {
        int hash = 0;
        for (int i = 0; i < clave.length(); i++) {
            hash = (31 * hash + clave.charAt(i)) % capacidad;
        }
        if (hash < 0) {
            hash += capacidad;
        }
        return hash;
    }
    /**
     * Inserta un nuevo registro en la tabla hash.
     * La entrada creada vincula una clave única con el usuario propietario,
     * el documento correspondiente y su registro de impresión activo.
     * Si ocurre una colisión, el nuevo nodo se enlaza al inicio de la lista
     * asociada a la posición calculada.
     *
     * @param clave identificador único utilizado para ubicar el registro.
     * @param usuario usuario asociado al documento.
     * @param documento documento que se encuentra registrado en la cola de impresión.
     * @param registro registro de impresión vinculado al documento dentro del heap.
     */
   public void insertarRegistro(String clave, Usuario usuario, Documento documento, RegistroImpresion registro) {
        int indice = hashFunction(clave);
        NodoHash nuevo = new NodoHash(clave, usuario, documento, registro);
        nuevo.setNext(tabla[indice]);
        tabla[indice] = nuevo;
        tamano++;
    }

    /**
     * Busca y retorna el nodo completo asociado a una clave dentro de la tabla hash.
     * El recorrido se realiza únicamente sobre la lista enlazada correspondiente
     * al índice calculado para esa clave.
     *
     * @param clave identificador único del registro que se desea buscar.
     * @return nodo asociado a la clave dada, o {@code null} si no existe.
     */
        public NodoHash buscarNodo(String clave) {
        int indice = hashFunction(clave);
        NodoHash actual = tabla[indice];
        while (actual != null) {
            if (actual.getClave().equals(clave)) return actual;
            actual = actual.getNext();
        }
        return null;
    }
    /**
     * Busca el registro de impresión asociado a una clave específica.
     * Este método permite obtener directamente la referencia al registro activo
     * sin necesidad de manipular externamente el nodo de la tabla hash.
     *
     * @param clave identificador único del registro que se desea localizar.
     * @return registro de impresión asociado a la clave, o {@code null} si no existe.
     */
    public RegistroImpresion buscarRegistro(String clave) {
        NodoHash nodo = buscarNodo(clave);
        return nodo != null ? nodo.getRegistro() : null;
    }
    /**
     * Elimina de la tabla hash el registro asociado a una clave específica.
     * Si el nodo existe, se reajustan los enlaces de la lista enlazada
     * correspondiente para excluirlo de la estructura.
     *
     * @param clave identificador único del registro que se desea eliminar.
     * @return {@code true} si el registro fue eliminado correctamente;
     *         {@code false} si no se encontró ningún nodo con esa clave.
     */
    public boolean eliminarRegistro(String clave) {
        int indice = hashFunction(clave);
        NodoHash actual = tabla[indice];
        NodoHash anterior = null;

        while (actual != null) {
            if (actual.getClave().equals(clave)) {
                if (anterior == null) {
                    tabla[indice] = actual.getNext();
                } else {
                    anterior.setNext(actual.getNext());
                }
                tamano--;
                return true;
            }
            anterior = actual;
            actual = actual.getNext();
        }
        return false;
    }
}
