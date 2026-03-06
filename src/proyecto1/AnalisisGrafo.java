/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1;

/**
 * Implementa los algoritmos de análisis sobre el grafo de interacción proteica.
 * Incluye detección de componentes conexos mediante BFS, cálculo de rutas de
 * menor resistencia mediante Dijkstra e identificación de proteínas hub.
 * @author valen
 */
public class AnalisisGrafo {
    private GrafoBio grafo;
    /**
     * Crea un analizador asociado a un grafo biológico.
     * @param grafo grafo de interacción sobre el cual se ejecutarán los algoritmos
     */
    public AnalisisGrafo(GrafoBio grafo) {
        this.grafo = grafo;
    }
    /**
     * Identifica los componentes conexos del grafo utilizando el algoritmo BFS.
     * Estos componentes se interpretan como complejos proteicos.

     * El algoritmo recorre todas las proteínas del grafo. Cuando encuentra una
     * proteína que aún no ha sido visitada, ejecuta un BFS desde ella para
     * descubrir todas las proteínas conectadas. Todas esas proteínas forman
     * un mismo complejo.
     * @return lista de complejos, donde cada complejo es una lista de proteínas conectadas
     */
    
    public Lista<Lista<Proteina>> encontrarConexas(){
        Lista<Lista<Proteina>> islas = new Lista<>();
        Lista<Proteina> visitados = new Lista<>();
        Nodo<Proteina> aux = grafo.getListaProteinas().getInicio();
        while(aux!=null){
            Proteina pinicial = aux.getDato();
            if(visitados.buscar(pinicial)==null){
                Lista<Proteina> nuevaIsla = new Lista<>();
                ejecutarBFS(pinicial, visitados, nuevaIsla);
                islas.insertarFinal(nuevaIsla);
            }
            aux = aux.getNext();
        }
        return islas;
    }
    
    /**
    * Ejecuta el recorrido BFS a partir de una proteína inicial para identificar
    * todas las proteínas conectadas a ella.
    *
    * Utiliza una lista como cola (FIFO) para explorar la red nivel por nivel.
    * Primero visita los vecinos directos del nodo inicial, luego los vecinos de 
    * esos vecinos, y así sucesivamente.
    * Cada proteína descubierta se marca como visitada para evitar recorrerla
    * múltiples veces y se añade al componente actual ("isla").
    * @param inicio proteína inicial del recorrido
    * @param visitados lista de proteínas ya visitadas
    * @param isla lista donde se almacenan las proteínas del componente actual
     */
    private void ejecutarBFS(Proteina inicio, Lista<Proteina> visitados, Lista<Proteina> isla) {
        Lista<Proteina> cola = new Lista<>();
        cola.insertarFinal(inicio); //encolar
        visitados.insertarFinal(inicio);
        isla.insertarFinal(inicio);

        while (!cola.esVacia()) {
            Proteina p = cola.eliminarInicio(); //desencolando
            Nodo<Interaccion> nodoVecino = p.getAdyacentes().getInicio();
            while (nodoVecino != null) {
                Interaccion i = nodoVecino.getDato();
                Proteina v = vecinoDe(p,i);

                if (v!= null && visitados.buscar(v) == null) {
                    visitados.insertarFinal(v);
                    isla.insertarFinal(v);
                    cola.insertarFinal(v); // Encolar
                }
                nodoVecino = nodoVecino.getNext();
            }
        }
    }
   /**
    * Calcula el camino de menor resistencia entre dos proteínas usando Dijkstra.
    *
    * El estado del algoritmo (distancia, padre y visitado) se almacena
    * directamente en cada proteína para evitar estructuras auxiliares.
    *
    * @param idA identificador de la proteína origen
    * @param idB identificador de la proteína destino
    * @return lista de proteínas que representan la ruta más corta,
    *         o null si no existe conexión entre ellas
    */
    public Lista<Proteina> rutaMasCorta(String idA, String idB) {

        Proteina origen = grafo.buscarProteina(idA);
        Proteina destino = grafo.buscarProteina(idB);
        if (origen == null || destino == null) return null;

        /**Inicializar (reiniciar) estado Dijkstra en todas las proteínas*/
        Nodo<Proteina> aux = grafo.getListaProteinas().getInicio();
        while (aux != null) {
            Proteina p = aux.getDato();
            p.resetDijkstra(p.getID().equals(idA));
            aux = aux.getNext();
        }

        while (true) {
        Proteina u = extraerMinimoNoVisitado(); /**Se selecciona repetidamente la proteína no visitada con menor distancia acumulada*/
        if (u == null) break;
        if (u.getDist() == Double.MAX_VALUE) break; 
        if (u.equals(destino)) break;

        u.setVisitado(true);
         /**Relajacion: Para cada vecino del nodo actual se evalúa si pasar por ese nodo produce un camino más corto.*/
        Nodo<Interaccion> vecinoNodo = u.getAdyacentes().getInicio();
        while (vecinoNodo != null) {
            Interaccion arista = vecinoNodo.getDato();
            Proteina v = vecinoDe(u, arista); 
            if (v != null && !v.isVisitado()) {
                double nuevaDist = u.getDist() + arista.getResistencia();
                if (nuevaDist < v.getDist()) {
                    v.setDist(nuevaDist);
                    v.setPadre(u); 
                }
            }
            vecinoNodo = vecinoNodo.getNext();
        }
    }

    return reconstruirCamino(destino);
}
    /**Métodos auxiliares Privados*/
    
    /**
    * Obtiene la proteína vecina de una interacción dada respecto a una proteína dada.
    * Permite obtener el vértice opuesto de la arista independientemente del orden PA/PB.
    * Esto permite recorrer el grafo sin depender del orden en que las proteínas fueron 
    * almacenadas en la arista, aunque no deberia de ser necesario si el grafo es 
    * construido es consistentemente.
    */
    private Proteina vecinoDe(Proteina actual, Interaccion i) {
        if (i.getPA().equals(actual)) return i.getPB();
        if (i.getPB().equals(actual)) return i.getPA();
        return null; 
    }
    /**
     * Selecciona la proteína no visitada con menor distancia acumulada.
     * Implementa la fase de extracción del mínimo del algoritmo de Dijkstra.
     * @return proteína con menor distancia entre las no visitadas,
     * o null si no quedan nodos válidos
     */
    private Proteina extraerMinimoNoVisitado() {
        Proteina min = null;
        double minVal = Double.MAX_VALUE;

        Nodo<Proteina> aux = grafo.getListaProteinas().getInicio();
        while (aux != null) {
            Proteina p = aux.getDato();
            if (!p.isVisitado() && p.getDist() < minVal) {
                minVal = p.getDist();
                min = p;
            }
            aux = aux.getNext();
        }
        return min;
    }
  
    /**
     * Reconstruye la ruta más corta desde el nodo destino hasta el origen,
     * siguiendo los punteros padre establecidos durante la relajación de Dijkstra.
     * El recorrido se realiza hacia atrás (destino a origen) y se inserta
     * cada proteína al inicio de la lista para devolver el camino en orden correcto
     * @param destino lista con la ruta desde el origen hasta el destino
     * @return 
     */
    private Lista<Proteina> reconstruirCamino(Proteina destino) {
        if (destino.getDist() == Double.MAX_VALUE) return null;

        Lista<Proteina> camino = new Lista<>();
        Proteina actual = destino;

        while (actual != null) {
            camino.insertarInicio(actual);
            actual = actual.getPadre();
        }
        return camino;
    }

    /**
     * Metodo que encuentra los hubs (llamando a encontrarHubs) calculando un umbral 
     * dinámico basado en el promedio de conexiones del grafo.
     * Considera como Hub a cualquier proteína que tenga el doble de conexiones
     * que el promedio de conexiones.
     * @return lista de hubs
     */
    public Lista<Proteina> encontrarHubsAutomatico() {
        int totalProteinas = grafo.getListaProteinas().getSize();
        if (totalProteinas == 0) return new Lista<>();
        int sumaGrados = 0;
        Nodo<Proteina> aux = grafo.getListaProteinas().getInicio();
        
        while(aux != null) {
            sumaGrados += aux.getDato().getGrado();
            aux = aux.getNext();
        }
        int promedio = sumaGrados / totalProteinas;
        
        int umbralDinamico = promedio * 2;
        
        if (umbralDinamico < 3) {
            umbralDinamico = 3;
        }
        
        return encontrarHubs(umbralDinamico);
    }

    /**
     * Metodo para que identifica los Hubs tomando como argumento un grado dado. 
     * (para la funcionalidad el gardo se calcula con encontrarHubsAutomatico). 
     * @param grado 
     * @return 
     */
    
    public Lista<Proteina> encontrarHubs(int grado) {
        Lista<Proteina> hubs = new Lista<>();
        Nodo<Proteina> aux = grafo.getListaProteinas().getInicio();
        while(aux != null) {
            Proteina p = aux.getDato();
            if (p.getGrado() >= grado) {
                hubs.insertarFinal(p);
            }
            aux = aux.getNext();
        }
        return hubs;
    }
}
                
            
            
            
        
    
    
   

