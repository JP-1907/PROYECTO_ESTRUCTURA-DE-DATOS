/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyecto1;

import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author valen
 */
public class Proyecto1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
           
            javax.swing.JFrame ventana = new javax.swing.JFrame("Proyecto 1 - Analizador de Proteínas");
            ventana.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
            
            Interfaz miPanel = new Interfaz();
            
            ventana.setContentPane(miPanel);
            
            ventana.pack();
            ventana.setLocationRelativeTo(null);
            
            ventana.setVisible(true);
        });
    
    
    }
}
