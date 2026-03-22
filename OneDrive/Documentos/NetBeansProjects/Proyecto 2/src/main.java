
import Interfaz.Interfaz;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * Clase principal del sistema.
 * Se encarga de iniciar la aplicación y mostrar la interfaz gráfica.
 *
 * @author valen
 */
public class main {
        /**
     * Método principal de ejecución del programa.
     * Inicia la interfaz gráfica dentro del hilo de eventos de Swing.
     *
     * @param args argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new Interfaz().setVisible(true));
    }
    
}
