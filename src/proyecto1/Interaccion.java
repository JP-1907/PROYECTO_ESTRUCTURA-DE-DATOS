/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1;

/**
 *
 * @author valen
 */
/**
 * Clase Interaccion (Aristas del Grafo)
 * Representa una arista del grafo de interacción proteica.
 * Cada objeto Interaccion conecta dos proteínas y almacena
 * la resistencia asociada a esa conexión.
 *
 * En el grafo no dirigido, cada conexión se representa internamente
 * mediante dos objetos Interaccion: uno en la lista de adyacencia
 * de cada proteína.
 *
 * PA representa la proteína desde cuya lista de adyacencia se accede
 * a la interacción, mientras que PB representa la proteína vecina.
 * 
 */

public class Interaccion {
    private Proteina PA;
    private Proteina PB; 
    private double resistencia;
    
    /**
     * Constructor de una interaccion, recibe ambas proteinas a conectar
     * @param PA origen (desde su lista se registra la interaccion)
     * @param PB destino o vecina
     * @param resistencia peso de la interaccion
     */
    public Interaccion(Proteina PA, Proteina PB, double resistencia) {
        this.PA = PA;
        this.PB = PB;
        this.resistencia = resistencia;
    }
    //Getters
    /**Obtiene la proteina A de la interaccion (el origen)
     * @return 
     */
    public Proteina getPA() { return PA; }
    
    /**Obtiene la proteina B de la interaccion (el destino/ vecina)
     * @return 
    */
    public Proteina getPB() { return PB; }
    
    /**Obtiene el peso de la interaccion.
     * @return 
    */
    public double getResistencia() { return resistencia; }
    
    /**Retorna una representación en texto de la interaccion.
     * @return cadena con el id de PA, la resistencia y el id de PB
     */
    @Override
    public String toString() {
        return PA.getID() + "--(" + resistencia + ")--" + PB.getID();
    }
}
