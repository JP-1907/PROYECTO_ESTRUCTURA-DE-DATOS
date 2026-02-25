/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.File;
/**
 *
 * @author valen
 */

/**
 * Gestiona la lectura y escritura del repositorio (archivo) de la red de proteínas.
 * Formato esperado del archivo (CSV/TXT):ProteinaA,ProteinaB,Resistencia
 * La red se interpreta como nO dirigida, una interacción conecta ambas proteínas.
 */
public class GestorArchivos {

    /**
     * Carga una red desde un archivo CSV/TXT y la inserta en el grafo no dirigido 
     * recibido.
     * Por cada línea válida, extrae: proteína origen, proteína destino, resistencia.
     * Se asegura de que ambas proteínas existan en el grafo (addProteina).
     * Agrega la interacción al grafo (addInteraccion, el cual crea la adyacencia 
     * en ambos sentidos).
     *
     * @param archivo archivo CSV/TXT de entrada.
     * @param miGrafo grafo a poblar (se modifica en memoria).
     * @throws Exception si ocurre error de lectura o parseo numérico.
     */
    public void cargarRed(File archivo, GrafoBio miGrafo) throws Exception {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;

                String[] datos = linea.split(",");
                if (datos.length < 3) continue;

                String origen = datos[0].trim();
                String destino = datos[1].trim();
                String resistenciaStr = datos[2].trim();
                double resistencia;

                if (origen.isEmpty() || destino.isEmpty() || resistenciaStr.isEmpty()) continue;

                try {
                resistencia = Double.parseDouble(resistenciaStr);
                } catch (NumberFormatException e) {
                    /**Por si la primera linea es titulo, la ignoro*/
                    continue;
                }

                miGrafo.addProteina(origen);
                miGrafo.addProteina(destino);
                miGrafo.addInteraccion(origen, destino, resistencia);
            }
        }
    }

    /**
     * Guarda el grafo actual a un archivo CSV.
     * Escribe encabezado: {@code ProteinaA,ProteinaB,Resistencia}.
     * Recorre todas las proteínas y sus listas de adyacencia.
     * Para evitar duplicados solo guarda cuando {@code idA.compareTo(idB) < 0}.
     *
     * @param archivoDestino archivo de salida.
     * @param miGrafo grafo actual en memoria.
     * @throws Exception si ocurre un error de escritura.
     */
    public void guardarRed(File archivoDestino, GrafoBio miGrafo) throws Exception {
        try (PrintWriter writer = new PrintWriter(archivoDestino)) {

            writer.println("ProteinaA,ProteinaB,Resistencia");

            Nodo<Proteina> auxProt = miGrafo.getListaProteinas().getInicio();
            while (auxProt != null) {
                Proteina p = auxProt.getDato();
                String idA = p.getID();

                Nodo<Interaccion> auxInt = p.getAdyacentes().getInicio();
                while (auxInt != null) {
                    Interaccion inter = auxInt.getDato();
                    String idB = inter.getPB().getID();

                    // Guarda una sola vez por arista para evitar duplicados
                    if (idA.compareTo(idB) < 0) {
                        writer.println(idA + "," + idB + "," + inter.getResistencia());
                    }
                    auxInt = auxInt.getNext();
                }

                auxProt = auxProt.getNext();
            }
        }
    }
}
    

