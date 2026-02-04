/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1;

/**
 * Clase GrafoBio (TDA Grafo)
 * Gestiona la red completa usando Listas de Adyacencia.
 * @author valen
 */
public class GrafoBio {
    private Lista<Proteina> listaProteinas; // La lista maestra de todos los vértices

    public GrafoBio() {
        this.listaProteinas = new Lista<>();
    }
    /**Busca una proteína por su identificador.
     * @param id identificador único de la proteína
     * @return la proteína si existe, o null si no se encuentra
     */
    public Proteina buscarProteina(String id) {
         Nodo<Proteina> n = listaProteinas.getInicio();
    while (n != null) {
        Proteina p = n.getDato();
        if (p.getID().equals(id)) return p;
        n = n.getNext();
        }
    return null;
    }
    /**
     * Agrega un nuevo vértice al grafo verificando que no exista.
     * @param id Nombre de la proteína.
     * @return true si se agregó, false si ya existía.
     */
    public boolean addProteina(String id) {
        if (buscarProteina(id) != null) {
            return false; /**Ya existe, evitamos duplicados (*/
        }
        Proteina nueva = new Proteina(id);
        listaProteinas.insertar(nueva);
        return true;
    }

    /**
     * Crea una arista entre dos proteínas existentes.
     * Frafo no dirigido, se agrega en ambos sentidos.
     */
    public boolean addInteraccion(String id1, String id2, double resistencia) {
        Proteina p1 = buscarProteina(id1);
        Proteina p2 = buscarProteina(id2);

        if (p1 == null || p2 == null) return false;
        if (id1.equals(id2)) return false;
        if (resistencia < 0) return false;
        if (p1.tieneVecino(p2))return false; /**Evitando dublicados*/
     
        /** Creamos la interacción (arista) en ambas direcciones        
        * y agregamos a las listas de adyacencia de cada proteína*/
        p1.addAdyacencia(new Interaccion(p1, p2, resistencia));
        p2.addAdyacencia(new Interaccion(p2, p1, resistencia));
        return true;
    }
    /**Elimina interaccion (arista) entre dos proteinas
     * @param id1 de la primera proteina
     * @param id2 de la segunda 
     * @return true si se elimino, false si no existía o proteínas no existen
     */
    public boolean eliminarInteraccion(String id1, String id2) {
        Proteina p1 = buscarProteina(id1);
        Proteina p2 = buscarProteina(id2);
        if (p1 == null || p2 == null) return false;
        if (!p1.tieneVecino(p2)) return false;

        /**Grafo no dirigido tambien se borra en ambos sentidos*/
        p1.eliminarVecino(p2);
        p2.eliminarVecino(p1);

        return true;
    }
    /**
     * Elimina una proteína del grafo y todas sus conexiones incidentes.
     * Garantiza que no queden referencias "fantasmas" (Punteros a null).
     * @param id El nombre de la proteína a borrar.
     * @return true si se eliminó, false si no existía.
     */
    
    public boolean eliminarProteina(String id){
        
        Proteina pAborrar = buscarProteina(id);
        if (pAborrar == null) return false; 
        Lista<Interaccion> vecinos = pAborrar.getAdyacentes(); /**Busco los adyacentes de la que quiero borrar*/
        Nodo<Interaccion> nodoVecino = vecinos.getInicio();/**Obtengo el primero de lista de adyacentes*/
        
        while (nodoVecino != null){ /**Recorriendo las interacciones*/
            Interaccion i = nodoVecino.getDato();
            Proteina vecino = i.getPB();
            vecino.eliminarVecino(pAborrar); /**Se pide al vecino que borre su conexion con la que se borra*/
            nodoVecino = nodoVecino.getNext();
        }
        listaProteinas.eliminar(pAborrar); /**De la lista del grafo*/
        return true;
    }
    
    /**Obtiene la lista de adyacentes de una proteina buscando por su id, se usa gerAdyacentes 
     * clase proteina
     * @param idProteina
     */
    public Lista adyacentes(String idProteina) {
        Proteina p = buscarProteina(idProteina);
        if (p != null) {  //si existe, se llama el metodo adyacentes de las proteinas
            return p.getAdyacentes(); 
        }
    return null; 
    }
    
    /**Obtiene lista maestra de las proteinas del grafo
     * @return lista de proteinas en el grafo 
     */
    public Lista getListaProteinas() {
        return listaProteinas;
    }
    
    
    /**Muestra el estado actual del grafo.
     */
    public void mostrarGrafo() {
        if (listaProteinas.esVacia()) {
            System.out.println("El grafo está vacío.");
            return;
        }

        Nodo aux = listaProteinas.getInicio();
        while (aux != null) {
            Proteina p = (Proteina) aux.getDato();
            System.out.print("Proteína " + p.getID() + " conecta con: ");
            
            // Recorremos sus adyacentes
            Nodo auxVecino = p.getAdyacentes().getInicio();
            if (auxVecino == null) {
                System.out.print("Nadie.");
            }
            
            while (auxVecino != null) {
                Interaccion i = (Interaccion) auxVecino.getDato();
                // Mostramos Destino y Peso
                System.out.print("[" + i.getPB().getID() + " | " + i.getResistencia() + "] -> ");
                auxVecino = auxVecino.getNext();
            }
            System.out.println(""); // Salto de línea
            aux = aux.getNext();
        }
    }
}