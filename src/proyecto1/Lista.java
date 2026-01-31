package proyecto1;



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author valen
 */
public class Lista {
    /**
 * Clase Lista (para lista enlazada simple)
 * Implementación dinámica para almacenar elementos 
 * y tenern estructura dinámica y escalable.
 */
    private Nodo pFirst; // Primer elemento
    private int iN;  // Contador de elementos para acceso rápido O(1)

    public Lista() {
        this.pFirst = null;
        this.iN = 0;
    }
    
    public boolean esVacia() {
        return pFirst == null;
    }
    /**
     * Inserta un elemento al final de la lista.
     * @param dato El objeto a guardar.
     */
    public void insertar(Object dato) {
        Nodo nuevo = new Nodo(dato);
        if (esVacia()) {
            pFirst = nuevo;
        } else {
            Nodo aux = pFirst;
            while (aux.getNext() != null) {
                aux = aux.getNext();
            }
            aux.setNext(nuevo);
        }
        iN++;
    }

    /**
     * Obtiene el elemento en la posición especificada.
     * @param indice Posición (0 a iN-1)
     * @return El objeto encontrado o null si el índice es inválido.
     */
    public Object acceder(int indice) {
        if (indice < 0 || indice >= iN) return null;
        
        Nodo aux = pFirst;
        for (int i = 0; i < indice; i++) {
            aux = aux.getNext();
        }
        return aux.getDato();
    }
    
    /**
     * Elimina un elemento específico de la lista.
     * @param dato El objeto a eliminar (usa equals).
     */
    public void eliminar(Object dato) {
        if (esVacia()) return;

        if (pFirst.getDato().equals(dato)) {
            pFirst = pFirst.getNext();
            iN--;
            return;
        }
        Nodo aux = pFirst;
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
     * Busca un elemento en la lista.
     * @return El Nodo que contiene el dato, o null si no existe.
     */
    public Nodo buscar(Object datoBuscado) {
        Nodo p = pFirst;        
        boolean encontrado = false; // Lógico encontrado <- falso

        // Mientras no lleguemos al final Y no lo hayamos encontrado
        while (p != null && !encontrado) { 
            // .equals porque son Objetos
            if (p.getDato().equals(datoBuscado)) { 
                encontrado = true; // ¡Lo encontramos! El while se detendrá.
            } else {
                // Si no es igual, avanzamos al siguiente
                p = p.getNext();   // P <- Próximo(P)
            }
        }
        // Si salió porque lo encontró, P apunta al nodo correcto.
        // Si salió porque terminó la lista, P será null (NIL).
        return p;
    }

    public Nodo getInicio() {
        return pFirst; 
    }
    public int getSize() {
        return iN;
    }
}
    

