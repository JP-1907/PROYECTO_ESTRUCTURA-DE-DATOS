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
 * Clase Proteina (Vértice del Grafo)
 * Contiene la información biológica y su propia lista de conexiones.
 */

public class Proteina {
    
    private String id;// Identificador único ej: "P9"
    private Lista adyacentes; // Esta lista guardará objetos de tipo 'Interaccion'
    private int grado; //Aumenta con interacciones que tenga la proteina e 
                       //identidicar hubs
                        
    // Constructor
    public Proteina(String id) {
        this.id = id;
        this.grado = 0; // Nace con grado 0
        this.adyacentes = new Lista(); // Nace con la lista vacía (pero creada)
    }

    /**
     * Agrega la conexión y actualiza el grado automáticamente.
     * Se llama desde GrafoBio.
     * @param interaccion
     */
    public void addadyacencia(Interaccion interaccion) {
        adyacentes.insertar(interaccion); // Agregamos la interacción a la lista interna de la proteína
        grado++; //aumenta grado de la proteina
    }

    // --- Getters ---
    public String getID () {
        return id;
    }

    public Lista getAdyacentes() {
        return adyacentes;
    }

    public int getGrado() {
        return grado;
    }
    
    // Para comparar correctamente (Importante para buscar)
    public boolean equals(Object obj) {
        if (obj instanceof Proteina) {
            return this.id.equals(((Proteina)obj).id);
        }
        return false;
    }

    @Override
    public String toString() {
        return id + " (Grado: " + grado + ")";
    }
}
