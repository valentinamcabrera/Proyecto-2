/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaz;

import EDD.ListaDocs;
import Modelos.Usuario;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
  *Clase encargada de manejar la lectura de archivos.
 * 
 * @author valen
 */
public class GestorArchivos {
    
    /**
     * Lee un archivo CSV y genera una lista de usuarios.
     * @param archivo El archivo físico seleccionado previamente por el usuario de la interfaz.
     * @return Una Lista genérica que contiene los objetos Usuario.
     * @throws IOException Si ocurre un error al intentar leer el archivo.
     */
    public ListaDocs<Usuario> cargarUsuarios(File archivo) throws IOException {
        ListaDocs<Usuario> usuariosCargados = new ListaDocs<>();
        
        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue;
                }
                //Obviar encabezado si lo hay:
                String lineaMinuscula = linea.toLowerCase();
                if (lineaMinuscula.contains("usuario") || lineaMinuscula.contains("tipo")) {
                    continue; // Es el título, pasamos a la siguiente línea
                }

                String[] partes = linea.split(",");
                
                if (partes.length == 2) {
                    String nombre = partes[0].trim();
                    String prioridad = partes[1].trim();
                    usuariosCargados.insertar(new Usuario(nombre, prioridad));
                }
            }
        } 
        return usuariosCargados;
    }
    
}
