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
 * Representa la conexión entre dos proteínas y su resistencia (peso).
 */

public class Interaccion {
    private Proteina PA;
    private Proteina PB;
    private double resistencia;
    
    /**
     * Constructor de una interaccion, recibe ambas proteinas a conectar
     * @param PA conectar con
     * @param PB
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
    
    /**Obtiene la proteina B de la interaccion (el destino)
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
