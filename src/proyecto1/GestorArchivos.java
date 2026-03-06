/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileWriter;
/**
 *
 * @author valen
 */

/**
 * Gestiona la lectura y escritura del archivo de la red de proteínas.
 * Formato esperado del archivo (CSV/TXT):ProteinaA,ProteinaB,Resistencia
 * La red se interpreta como no dirigida, una interacción conecta ambas proteínas.
 * Permite cargar una red desde archivo, guardar el grafo actual
 * y exportar reportes de análisis generados por la aplicación.
 */
public class GestorArchivos {

    /**
     * Carga una red desde un archivo CSV/TXT y construye el grafo de interacción.
     * Por cada línea válida, extrae: proteína origen, proteína destino, resistencia.
     * Se asegura de que ambas proteínas existan en el grafo (addProteina).
     * Agrega la interacción correspondiente al grafo (addInteraccion, el cual crea la adyacencia 
     * en ambos sentidos).
     * Si una línea no puede convertirse correctamente (por ejemplo, 
     * encabezado o resistencia no numérica), se ignora.
     *
     * @param archivo archivo CSV/TXT de entrada.
     * @param miGrafo grafo a poblar con informacion del archivo
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
     * Escribe encabezado y ProteinaA,ProteinaB,Resistencia.
     * Recorre todas las proteínas y sus listas de adyacencia.
     * Para evitar duplicados solo guarda cuando {@code idA.compareTo(idB) < 0}.
     * cada arista se guarda solo una vez utilizando el orden alfabético de los id
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
    /**
     * Exporta el resultado del análisis actual de una red a un archivo de texto
     * El contenido se agrega al final del archivo para permitir almacenar múltiples reportes 
     * en el mismo documento si se selecciona el mismo al momento de exportar.
     * Cada reporte incluye el nombre de la red analizada y la fecha de exportación.
     * @param archivo archivo TXT de salida elegido por el usuario.
     * @param contenidoAnalisis el texto extraído del panel de resultados.
     * @param nombreOrigen nombre del archivo o red analizada
     * @throws Exception si ocurre un error de escritura.
     */
    public void exportarReporte(File archivo, String contenidoAnalisis, String nombreOrigen) throws Exception {
        try (FileWriter fw = new FileWriter(archivo,true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write("\n");
            bw.write("      REPORTE DE ANÁLISIS - " + nombreOrigen + " \n");
            bw.write("      Fecha de exportación: " + new java.util.Date() + "\n");
            bw.write(contenidoAnalisis);
            bw.write("-------------------------------------------\n");
           
        }
    }
}
    

