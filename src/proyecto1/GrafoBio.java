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
 * Clase GrafoBio (TDA Grafo)
 * Gestiona la red completa usando Listas de Adyacencia.
 */
public class GrafoBio {
    private Lista listaProteinas; // La lista maestra de todos los vértices

    public GrafoBio() {
        this.listaProteinas = new Lista();
    }
    
   
    public Proteina buscarProteina(String id) {
        Proteina proteinaBuscada = new Proteina(id); 

        Nodo nodoEncontrado = listaProteinas.buscar(proteinaBuscada);

        if (nodoEncontrado != null) {
            return (Proteina) nodoEncontrado.getDato();
        } else{
        return null; // P era NIL (no existe)
        }
    }

    /**
     * Agrega un nuevo vértice al grafo verificando que no exista.
     * @param id Nombre de la proteína.
     * @return true si se agregó, false si ya existía.
     */
    public boolean addProteina(String id) {
        if (buscarProteina(id) != null) {
            return false; // Ya existe, evitamos duplicados (Robustez)
        }
        Proteina nueva = new Proteina(id);
        listaProteinas.insertar(nueva);
        return true;
    }

    /**
     * Crea una arista entre dos proteínas existentes.
     * Maneja grafo no dirigido agregando la referencia en ambos sentidos.
     */
    public boolean addInteraccion(String id1, String id2, double resistencia) {
        Proteina p1 = buscarProteina(id1);
        Proteina p2 = buscarProteina(id2);

        if (p1 == null || p2 == null) {
            return false; // Validación de integridad
        }

        // Creamos la interacción (Arista)
        Interaccion i1 = new Interaccion(p1, p2, resistencia);
        
        // Agregamos a las listas de adyacencia de cada proteína
        p1.addadyacencia(i1);
        p2.addadyacencia(new Interaccion(p2, p1, resistencia));
        
        return true;
    }
    
    /**
     * Elimina una proteína del grafo y todas sus conexiones incidentes.
     * Garantiza que no queden referencias "fantasmas" (Punteros a null).
     * @param id El nombre de la proteína a borrar.
     * @return true si se eliminó, false si no existía.
     */
    public boolean eliminarProteina(String id) {
        Proteina pABorrar = buscarProteina(id);
        
        if (pABorrar == null) return false; // No existe

        // Ir a cada vecino y decirles que me borren de su lista
        Lista vecinos = pABorrar.getAdyacentes();
        Nodo nodoVecino = vecinos.getInicio();
        
        while (nodoVecino != null) {
            Interaccion i = (Interaccion) nodoVecino.getDato();
            Proteina vecino = i.getPB();
            
            // El vecino borra la conexión hacia mí
            vecino.eliminarVecino(pABorrar); 
            
            nodoVecino = nodoVecino.getNext();
        }

        // PASO 2: Finalmente, me borro a mí mismo de la lista maestra del grafo
        listaProteinas.eliminar(pABorrar);
        
        return true;
    }
    
    public Lista Adyacentes(String idProteina) {
 
        Proteina p = buscarProteina(idProteina);
        if (p != null) {  //si existe, se llama el metodo adyacentes de las proteinas
            return p.getAdyacentes(); 
    }
    
    return null; 
}
    
    public Lista getListaProteinas() {
        return listaProteinas;
    }
}