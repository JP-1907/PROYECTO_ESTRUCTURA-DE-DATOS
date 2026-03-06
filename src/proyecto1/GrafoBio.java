/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1;

/**
 * Clase GrafoBio
 * Implementa el TDA Grafo para modelar la red de interacción proteína-proteína.
 * Gestiona la red completa usando Listas de Adyacencia.
 * Mantiene la lista maestra de proteínas y permite agregar, eliminar,
 * buscar y consultar conexiones dentro de la red.
 * @author valen
 */
public class GrafoBio {
    private Lista<Proteina> listaProteinas; // La lista maestra de todos los vértices
    
    /**
    * Construye un grafo biológico vacío.
    */
    public GrafoBio() {
        this.listaProteinas = new Lista<>();
    }
    /**Busca una proteína por su identificador en la lista del grafo.
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
     * Agrega una nueva proteina  al grafo verificando que no exista previamente.
     * @param id Nombre de la proteína 
     * @return true si se agregó, false si ya existía.
     */
    public boolean addProteina(String id) {
        if (buscarProteina(id) != null) {
            return false; 
        }
        Proteina nueva = new Proteina(id);
        listaProteinas.insertarFinal(nueva);
        return true;
    }

    /**
     * Crea una arista entre dos proteínas existentes.
     * La red se modela como no dirigida, por lo que la conexión
     * se registra en ambas listas de adyacencia.
     * @param id1 primera proteina
     * @param id2 segunda proteina
     * @param resistencia o peso de la interaccion
     * @return true si la interacción fue agregada; false si hubo error,
     * si alguna proteína no existe, si la interacción ya existe
     * o si los datos son inválidos
     */
    public boolean addInteraccion(String id1, String id2, double resistencia) {
        Proteina p1 = buscarProteina(id1);
        if (p1 == null) return false;
        Proteina p2 = buscarProteina(id2);
        if (p2 == null) return false;
        if (id1.equals(id2)) return false;
        if (resistencia < 0) return false;
        if (p1.tieneVecino(p2))return false; /**Evitando duplicados*/
     
        /** Se crea la interacción (arista) en ambas direcciones y se agrega
         * a las listas de adyacencia de cada proteína*/
        p1.addAdyacencia(new Interaccion(p1, p2, resistencia));
        p2.addAdyacencia(new Interaccion(p2, p1, resistencia));
        return true;
    }
    /**Elimina interaccion (arista) entre dos proteinas del grafo.
     * @param id1 de la primera proteina
     * @param id2 de la segunda 
     * @return true si se elimino, false si no existía la interaccion o las proteínas.
     */
    public boolean eliminarInteraccion(String id1, String id2) {
        Proteina p1 = buscarProteina(id1);
        if (p1 == null) return false;

        Proteina p2 = buscarProteina(id2);
        if (p2 == null) return false;
        if (!p1.tieneVecino(p2)) return false;

        /**Grafo no dirigido tambien se borra en ambos sentidos*/
        p1.eliminarVecino(p2);
        p2.eliminarVecino(p1);

        return true;
    }
    /**
     * Elimina una proteína del grafo y todas sus conexiones incidentes.
     * Primero elimina las referencias desde las proteínas vecinas y luego elimina
     * la proteína de la lista maestra.
     * Garantiza que no queden referencias "fantasmas" (Punteros a null).
     * @param id El nombre de la proteína a borrar.
     * @return true si se eliminó, false si no existía.
     */
    
    public boolean eliminarProteina(String id){
        Proteina pAborrar = buscarProteina(id);
        if (pAborrar == null) return false; 
        Lista<Interaccion> vecinos = pAborrar.getAdyacentes(); /**Busco los adyacentes de la que quiero borrar*/
        Nodo<Interaccion> nodoVecino = vecinos.getInicio();/**Obtengo el primero de lista de adyacentes*/
        
        while (nodoVecino != null){
            Interaccion i = nodoVecino.getDato();
            Proteina vecino = i.getPB();
            vecino.eliminarVecino(pAborrar); /**Se pide al vecino que borre su conexion con la proteina que se quiere borra*/
            nodoVecino = nodoVecino.getNext();
        }
        listaProteinas.eliminar(pAborrar); /**Se elima de la lista maestra del grafo*/
        return true;
    }
    
    /**Obtiene la lista de adyacentes de una proteina buscando por su id, se usa getAdyacentes 
     * clase proteina
     * @param idProteina 
     * @return lista de interacciones adyacentes; null si la proteína no existe
     */
    public Lista<Interaccion> adyacentes(String idProteina) {
        Proteina p = buscarProteina(idProteina);
        if (p != null) { 
            return p.getAdyacentes(); 
        }
    return null; 
    }
    
    /**Obtiene lista maestra de las proteinas del grafo.
     * @return lista de proteinas en el grafo 
     */
    public Lista<Proteina> getListaProteinas() {
        return listaProteinas;
    }
     
    /**Muestra el estado actual del grafo. Solo para depuracion interna.
     */
    public void mostrarGrafo() {
        if (listaProteinas.esVacia()) {
            System.out.println("El grafo está vacío.");
            return;
        }
        Nodo<Proteina> aux = listaProteinas.getInicio();
        while (aux != null) {
            Proteina p = aux.getDato();
            System.out.print("Proteína " + p.getID() + " conecta con: ");
            
            Nodo<Interaccion> auxVecino = p.getAdyacentes().getInicio();
            if (auxVecino == null) {
                System.out.print("Nadie.");
            }
            
            while (auxVecino != null) {
                Interaccion i = auxVecino.getDato();
                System.out.print("[" + i.getPB().getID() + " | " + i.getResistencia() + "] -> ");
                auxVecino = auxVecino.getNext();
            }
            System.out.println(""); // Salto de línea
            aux = aux.getNext();
        }
            
    }

}

