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
 * Contiene la información biológica de la proteona  y su propia lista de adyacentes.
 * Implementacion de Lista de Adyacencia -- la red es dispersa
 * Nota: esta clase también almacena estado temporal para Dijkstra (distancia, padre, visitado),
 * el cual debe reiniciarse con resetDijkstra antes de ejecutar el algoritmo.
 * 
 */

public class Proteina {
    
    private String id; /**Identificador único de la proteina ej: "P9"*/
    private Lista<Interaccion> adyacentes; /**Guarda objetos 'Interaccion'*/
    private int grado; /**Aumenta con interacciones (adyacencias) que tenga 
                         *la proteina, ayuda a identidicar hubs*/
                        
    /**Constructor de una proteina sin conexiones
     *@param id unico dado 
    */
    public Proteina(String id) {
        this.id = id;
        this.grado = 0;     /**Nace sin adyacencias, grado 0*/
        this.adyacentes = new Lista<>();     /**Nace con lista vacia */
    }

    /**
     * Agrega la interaccion dada a la lista de adyacencia  y actualiza el grado.
     * Se llama desde GrafoBio donde se verifica que no haya duplicados.
     * @param interaccion
     */
    public void addAdyacencia(Interaccion interaccion) {
        adyacentes.insertarFinal(interaccion); /**Agregamos la interacción a la lista interna de la proteína*/
        grado++; /**Aumenta grado de la proteina*/
    }
    
    /** 
     * Verifica si esta proteína tiene una interaccion con la proteína (vecina) indicada.
     * Recorre la lista de adyacencias de esta proteína y revisa si alguna interacción
     * tiene como destino a la proteína vecina.
     * @param vecina proteína con la que se desea comprobar la conexión
     * @return true si existe una interacción entre ambas, false en caso contrario
     */
    public boolean tieneVecino(Proteina vecina) {
        Nodo<Interaccion> actual = adyacentes.getInicio();
        while (actual != null) {
            Interaccion i = actual.getDato();
            if (i.getPB().equals(vecina)) {
                return true;
            }
            actual = actual.getNext();
        }
        return false;
    }
    
    /**
     * Elimina la interaccion de esta proteina y la proteína indicada (vecina).
     * Busca en la lista de adyacencias la interacción cuyo destino corresponde
     * a la proteína vecina indicada y la elimina de la lista. También actualiza el grado
     * de la proteína para reflejar la eliminación de la conexión.
     * Se utiliza al eliminar nodos o interacciones del grafo para mantener
     * consistencia en las listas de adyacencia.
     * @param vecina proteína cuya conexión debe eliminarse
     */
    public void eliminarVecino(Proteina vecina) {
        Nodo<Interaccion> actual = adyacentes.getInicio();
        while (actual != null) {
            Interaccion i = actual.getDato();
            if (i.getPB().equals(vecina)) {
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
    public Lista<Interaccion> getAdyacentes() {
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
     /* ------------------- Estado para Dijkstra ------------------- */
    
    private double dist;   /** Distancia acumulada desde el origen*/
    private Proteina padre; /** Padre en el camino más corto */
    private boolean visitado; /**Marca de visitado  */

    /**
     * reinicia el estado usado por Dijkstra en esta proteína.
     * Se utiliza antes de ejecutar el algoritmo para asegurar que todas las proteínas
     * comiencen con una distancia infinita y estado no visitado, excepto el nodo origen,
     * cuya distancia se inicializa en 0. Se llama antes de ejecutar para evitar que queden
     * valores residuales de ejecuciones previas.
     * Evita el uso de estructuras externas adicionales y permite
     * acceder al estado del algoritmo en tiempo O(1) por proteína.
     * @param esOrigen
     */
    public void resetDijkstra(boolean esOrigen) {
        dist = esOrigen ? 0.0 : Double.MAX_VALUE;
        padre = null;
        visitado = false;
    }
    /**Getters y Setters*/
    
    /**
     * Obtiene la distancia de la proteina desde el origen (Dijkstra).
     * @return 
     */
    public double getDist() { 
        return dist; 
    }
     /**
     * Actualiza la distancia acumulada (Dijkstra).
     * @param d nueva distancia
     */
    public void setDist(double d) { 
        dist = d; 
    }
    
    /**
     * Obtiene el padre en el camino más corto (Dijkstra).
     * Durante la ejecución del algoritmo, cada proteína guarda una referencia a su
     * proteína "padre", el nodo desde el cual se alcanzó con menor distancia.
     * @return proteína padre o null si no tiene
     */
    public Proteina getPadre() {
        return padre; 
    }
    
    /**
     * Define el padre en el camino más corto (Dijkstra).
     * @param p nueva proteína padre
     */
    public void setPadre(Proteina p) {
        padre = p; 
    }
    
    /**
     * Indica si la proteína ya fue visitada por Dijkstra.
     * @return true si está marcada como visitada; false si no
     */
    public boolean isVisitado() { 
        return visitado; 
    }
      /**
     * Marca o desmarca la proteína como visitada (Dijkstra).
     * @param v nuevo valor de visitado
     */
    public void setVisitado(boolean v) { 
        visitado = v; 
    }

}



   
    