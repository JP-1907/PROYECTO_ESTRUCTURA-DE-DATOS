/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1;

/**
 *En esta clase se implementan los algoritmos de busqueda BFS y Dijikstra
 * @author valen
 */
public class AnalisisGrafo {
    private GrafoBio grafo;

    public AnalisisGrafo(GrafoBio grafo) {
        this.grafo = grafo;
    }
    /**
     * Se eligio BFS sobre DFS para la busqueda de complejos proteicos,
     * identificando componentes conexos
     * @return 
     */
    
    public Lista<Lista<Proteina>> encontrarConexas(){
        Lista<Lista<Proteina>> islas = new Lista();
        Lista<Proteina> visitados = new Lista();
        Nodo<Proteina> aux = grafo.getListaProteinas().getInicio();
        while(aux!=null){
            Proteina pinicial = aux.getDato();
            if(visitados.buscar(pinicial)==null){
                Lista<Proteina> nuevaIsla = new Lista();
                
                ejecutarBFS(pinicial, visitados, nuevaIsla);
               
                islas.insertarFinal(nuevaIsla);
            }
            aux = aux.getNext();
        }
        return islas;
    }
    
    /**
     * Lógica interna del BFS usando la lista como cola.
     */
    private void ejecutarBFS(Proteina inicio, Lista<Proteina> visitados, Lista<Proteina> isla) {
        Lista<Proteina> cola = new Lista();
        cola.insertarFinal(inicio);
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
     * Encuentra el camino de menor resistencia entre dos proteínas.
     * Se almacena el estado (distancia, padre y visitado) directamente en cada
     * proteína para evitar búsquedas lineales repetidas en estructuras paralelas,
     * mejorando la eficiencia.
     * @param idA
     * @param idB
     * @return 
     */
    public Lista<Proteina> rutaMasCorta(String idA, String idB) {

        Proteina origen = grafo.buscarProteina(idA);
        Proteina destino = grafo.buscarProteina(idB);
        if (origen == null || destino == null) return null;

        // Inicializar estado Dijkstra en todas las proteínas
        Nodo<Proteina> aux = grafo.getListaProteinas().getInicio();
        while (aux != null) {
            Proteina p = aux.getDato();
            p.resetDijkstra(p.getID().equals(idA));
            aux = aux.getNext();
        }

        while (true) {
        Proteina u = extraerMinimoNoVisitado(); // O(V)
        if (u == null) break;
        if (u.getDist() == Double.MAX_VALUE) break; // inalcanzables
        if (u.equals(destino)) break;

        u.setVisitado(true);

        Nodo<Interaccion> vecinoNodo = u.getAdyacentes().getInicio();
        while (vecinoNodo != null) {
            Interaccion arista = vecinoNodo.getDato();
            Proteina v = vecinoDe(u, arista); 
            if (v != null && !v.isVisitado()) {
                double nuevaDist = u.getDist() + arista.getResistencia();
                if (nuevaDist < v.getDist()) {
                    v.setDist(nuevaDist);
                    v.setPadre(u); //Relajacion
                }
            }
            vecinoNodo = vecinoNodo.getNext();
        }
    }

    return reconstruirCamino(destino);
}
    // Métodos auxiliares Privados para Dijkstra
    /**
    * Devuelve la proteína vecina de la actual en una interacción dada.
    * Funciona independientemente del orden PA/PB.
    */
    private Proteina vecinoDe(Proteina actual, Interaccion i) {
        if (i.getPA().equals(actual)) return i.getPB();
        if (i.getPB().equals(actual)) return i.getPA();
        return null; // no debería ocurrir si el grafo es consistente
    }
    /**
     * Selecciona la proteína no visitada con menor distancia acumulada.
     *
     * Implementa la fase de extracción del mínimo del algoritmo de Dijkstra.
     * @return 
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
     * utilizando los punteros padre establecidos durante la relajación.
     * El recorrido se realiza hacia atrás (destino a origen)y se inserta
     * cada proteína al inicio de la lista para devolver el camino en orden correcto
     * @param destino
     * @return 
     */
    private Lista<Proteina> reconstruirCamino(Proteina destino) {
        if (destino.getDist() == Double.MAX_VALUE) return null;

        Lista<Proteina> camino = new Lista();
        Proteina actual = destino;

        while (actual != null) {
            camino.insertarInicio(actual);
            actual = actual.getPadre();
        }
        return camino;
    }

    /**
     * Metodo para identificar los Hubs.
     * @param gradoMinimo
     * @return 
     */
    public Lista<Proteina> encontrarHubs(int gradoMinimo) {
        Lista<Proteina> hubs = new Lista();
        Nodo<Proteina> aux = grafo.getListaProteinas().getInicio();
        while(aux != null) {
            Proteina p = (Proteina) aux.getDato();
            if (p.getGrado() >= gradoMinimo) {
                hubs.insertarFinal(p);
            }
            aux = aux.getNext();
        }
        return hubs;
    }
}
                
            
            
            
        
    
    
   

