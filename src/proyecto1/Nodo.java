/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1;

/**
 *
 * @author valen
 * Clase Nodo genérico para una lista enlazada simple
 * @param <T>
 */

public class Nodo <T> {
    private T dato;   /**El contenido del nodo (Proteina, Interaccion, etc.)*/
    private Nodo pNext;    /** El puntero al siguiente nodo*/

/**Constructor del nodo. Crea un nodo con el dato indicado
     * @param dato
 */
    public Nodo(T dato) {
        this.dato = dato;
        this.pNext = null;
    }

    // Getters y Setters
/**Obtiene el dato contenido en el nodo.
     * @return el dato
 */
    public T getDato() {
        return dato;
    }
/**Obtiene el nodo siguiente en la lista.
     * @return 
 */
    public Nodo<T> getNext() {
        return pNext;
    }
/**Establece el nodo siguiente en la lista.
     * @param pNext
 */
    public void setNext(Nodo<T> pNext) {
        this.pNext = pNext;
    }
/**Modifica el valor del nodo con
 * @param dato dado
 */
    public void setDato(T dato) {
        this.dato = dato;
}
}
    
