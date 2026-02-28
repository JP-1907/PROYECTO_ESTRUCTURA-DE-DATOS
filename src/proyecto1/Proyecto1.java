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
            
            // 2. Le decimos que al darle a la 'X', el programa se cierre de verdad
            ventana.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
            
            // 3. Creamos tu panel (tu diseño)
            Interfaz miPanel = new Interfaz();
            
            // 4. Metemos tu diseño dentro de la ventana
            ventana.setContentPane(miPanel);
            
            // 5. Ajustamos el tamaño de la ventana al tamaño de tu diseño
            ventana.pack();
            
            // 6. La centramos en la pantalla
            ventana.setLocationRelativeTo(null);
            
            // 7. ¡Hágase la luz!
            ventana.setVisible(true);
        });
    
    
    }
}
