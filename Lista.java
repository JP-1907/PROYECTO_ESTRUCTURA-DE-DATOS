package proyecto1;



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *Clase Lista generica para lista enlazada simple, estructura 
 * dinámica y escalable para almacenar datos.
 * @author valen
 */
public class Lista<T> {
    
    private Nodo<T> pFirst; /**Primer elemento de la lista*/
    private Nodo<T> pLast;
    private int iN;  /**Contador de elementos de la lista acceso rápido*/
    
 /**Crea una lista vacia
 */
    public Lista() {
        this.pFirst = null;
        this.pLast = null;
        this.iN = 0;
    }
    
 /**Indica si la lista esta vacia
 */
    public boolean esVacia() {
        return pFirst == null;
    }
    /**
     * Inserta un elemento al final de la lista.
     * @param dato El objeto a guardar.
     */
    public void insertarFinal(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        if (esVacia()) {
            pFirst = nuevo;
            pLast = nuevo;
        } else {
            pLast.setNext(nuevo);
            pLast = nuevo;
        }
        iN++;
    }
    /**
     * Inserta un nodo al principio de la lista.
     * Sirve para reconstruir el camino de Dijkstra en el orden correcto.
     */
    public void insertarInicio(T dato) {
        Nodo nuevo = new Nodo(dato);
        if (esVacia()) {
            pFirst = nuevo;
        } else {
            nuevo.setNext(pFirst); 
            pFirst = nuevo;             
        }
        iN++;
    }

    /**
     * Obtiene el elemento en la posición especificada.
     * @param indice Posición (0 a iN-1)
     * @return El objeto encontrado o null si el índice es inválido.
     */
    public T acceder(int indice) {
        if (indice < 0 || indice >= iN) return null;
        
        Nodo aux = pFirst;
        for (int i = 0; i < indice; i++) {
            aux = aux.getNext();
        }
        return (T) aux.getDato();
    }
    
    /**
     * Elimina un elemento específico de la lista.
     * @param dato El objeto a eliminar (usa equals).
     */
    public void eliminar(T dato) {
        if (esVacia()) return;

        if (pFirst.getDato().equals(dato)) {
            pFirst = pFirst.getNext();
            iN--;
            return;
        }
        Nodo<T> aux = pFirst;
        while (aux.getNext() != null) {
            if (aux.getNext().getDato().equals(dato)) {
                aux.setNext(aux.getNext().getNext());
                iN--;
                return;
            }
            aux = aux.getNext();
        }
    }
    
    /**
     * Para que la lista enlazada tome comportamiento de cola eliminando solo 
     * desde el inicio.Sirve para implementar BFS.
     * @return 
     */
    public T eliminarInicio(){
        if(esVacia()){
            return null;
        }else{
        T dato = pFirst.getDato();
        pFirst = pFirst.getNext();
        iN--;
        if (pFirst == null) { 
            pLast = null;
        }
        return dato;
        }
    }
    
    public Object buscar(T dato){
        Nodo aux = pFirst;
        while (aux != null) {
          
            if (aux.getDato().equals(dato)) {
                return aux.getDato(); 
            }
            aux = aux.getNext();
        }
        return null;
    }
    

 /**Obtiene el inicio de la lista, primer nodo
 */
    public Nodo<T> getInicio() {
        return pFirst; 
    }
/**Obtiene la cantidad de elementos en la lista.
 */
    public int getSize() {
        return iN;
    }
}
    

