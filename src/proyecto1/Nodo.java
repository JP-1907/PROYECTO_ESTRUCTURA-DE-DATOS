/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1;

/**
 *
 * @author valen
 */
public class Nodo {
    private Object dato;   // El contenido (Proteina, Interaccion, etc.)
    private Nodo pNext;    // El puntero al siguiente nodo

    // Constructor
    public Nodo(Object dato) {
        this.dato = dato;
        this.pNext = null;
    }

    // Getters y Setters
    public Object getDato() {
        return dato;
    }

    public Nodo getNext() {
        return pNext;
    }

    public void setNext(Nodo pNext) {
        this.pNext = pNext;
    }
    
    public void setDato(Object dato) {
        this.dato = dato;
}
}
    
