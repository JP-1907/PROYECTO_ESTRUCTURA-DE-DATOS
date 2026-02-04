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
    
    private String id; /**Identificador único de la proteina ej: "P9"*/
    private Lista<Interaccion> adyacentes; /**Guarda objetos 'Interaccion'*/
    private int grado; /**Aumenta con interacciones (adyacencias) que tenga 
                         *la proteina, ayuda a identidicar hubs*/
                        
    /**Constructor de una proteina dado su
     *@param id dado
    */
    public Proteina(String id) {
        this.id = id;
        this.grado = 0;     /**Nace sin adyacencias, grado 0*/
        this.adyacentes = new Lista();     /**Nace con lista vacia */
    }

    /**
     * Agrega la conexión dada una interaccion y actualiza el grado.
     * Se llama desde GrafoBio.
     * @param interaccion
     */
    public void addAdyacencia(Interaccion interaccion) {
        adyacentes.insertar(interaccion); /**Agregamos la interacción a la lista interna de la proteína*/
        grado++; /**Aumenta grado de la proteina*/
    }
    
    /** Verifica si esta proteína tiene una conexión con la proteína vecina indicada.
    * @param vecina proteína a verificar
    * @return true si existe una interacción entre ambas, false en caso contrario
    */
    public boolean tieneVecino(Proteina vecina) {
        Nodo<Interaccion> actual = adyacentes.getInicio();
        while (actual != null) {
            Interaccion i = actual.getDato();
            boolean tocaEsta = i.getPA().equals(this) || i.getPB().equals(this); //Ver si interacción toca a esta proteína (this puede estar en PA o PB)
            boolean tocaVecina = i.getPA().equals(vecina) || i.getPB().equals(vecina);  // Y si toca a la vecina(vecina puede estar en PA o PB)
            if (tocaEsta && tocaVecina) return true; //Existe la conexion si toca a ambas
            actual = actual.getNext();
        }
        return false;
    }
    
    /**
     * Elimina la conexión de esta proteina a la proteína indicada.
     * Importante para mantener la integridad del grafo cuando se borra un nodo.
     * @param vecina
     */
    public void eliminarVecino(Proteina vecina) {
        Nodo<Interaccion> actual = adyacentes.getInicio();
        while (actual != null) {
            Interaccion i = actual.getDato();
            boolean tocaEsta = i.getPA().equals(this)|| i.getPB().equals(this);  // Verificando si esta interacción conecta a this con vecina
            boolean tocaVecina = i.getPA().equals(vecina)||i.getPB().equals(vecina);
            if (tocaEsta && tocaVecina) { // Si las conecta hay que borrar
                adyacentes.eliminar(i);
                grado--;
                return;
            }
            actual = actual.getNext();
        }
    }
    
    // Getters 
    /**Obtiene el identificador ID de la proteina
     * @return ID
     */
    public String getID () {
        return id;
    }
    /**
     * Obtiene la lista de interacciones adyacentes a esta proteína.
     * @return lista de interacciones
     */
    public Lista getAdyacentes() {
        return adyacentes;
    }
    
    /**Obtiene el grado dado por cantidad de conexiones de proteina
     * @return ID
     */
    public int getGrado() {
        return grado;
    }
    /** Compara esta proteína con otro objeto para verificar igualdad.
     * Dos proteínas son iguales si poseen el mismo identificador.
     * @param obj objeto a comparar
     * @return true si ambos objetos representan la misma proteína
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Proteina) {
            return this.id.equals(((Proteina)obj).id);
        }
        return false;
    }
     /**Retorna una representación en texto de la proteína.
     * @return cadena con el identificador y el grado de la proteína
     */
    @Override
    public String toString(){
         return id + " (Grado: " + grado + ")";
    }
    @Override
    public int hashCode() {
        return id.hashCode();
    }
}



   
    