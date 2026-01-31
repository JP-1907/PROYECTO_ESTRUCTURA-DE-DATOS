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
 * Clase Interaccion (Arista del Grafo)
 * Representa la conexión entre dos proteínas y su peso (resistencia).
 */
public class Interaccion {
    private Proteina PA;
    private Proteina PB;
    private double resistencia; // Peso para Dijkstra

    public Interaccion(Proteina PA, Proteina PB, double resistencia) {
        this.PA = PA;
        this.PB = PB;
        this.resistencia = resistencia;
    }
    
    public Proteina getPA() { return PA; }
    public Proteina getPB() { return PB; }
    public double getResistencia() { return resistencia; } //Getters
    
    @Override
    public String toString() {
        return PA.getID() + "--(" + resistencia + ")--" + PB.getID();
    }
}
