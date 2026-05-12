package com.evolutivo.io;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class FileManager {

    public static void guardarResultados(String nombreArchivo,
                                         int tamPoblacion, float probCruce, float probMutacion, int maxGen,
                                         long tiempoEjecucion, int mejorCosto, List<Integer> mejorRuta,
                                         List<Integer> historicoCostos) {

        try (PrintWriter pw = new PrintWriter(new FileWriter(nombreArchivo))) {
            pw.println("==================================================");
            pw.println(" REPORTE DE EJECUCIÓN - ALGORITMO EVOLUTIVO TSP ");
            pw.println("==================================================");
            pw.println("\n--- CONFIGURACIÓN UTILIZADA ---");
            pw.println("Tamaño de la población: " + tamPoblacion);
            pw.println("Probabilidad de Cruce: " + (probCruce * 100) + "%");
            pw.println("Probabilidad de Mutación: " + (probMutacion * 100) + "%");
            pw.println("Condición de corte (Generaciones): " + maxGen);

            pw.println("\n--- RESULTADOS FINALES ---");
            pw.println("Mejor Costo Encontrado: " + mejorCosto);
            pw.println("Ruta Óptima: " + mejorRuta);
            pw.println("Tiempo de ejecución: " + tiempoEjecucion + " milisegundos");

            pw.println("\n--- EVOLUCIÓN DEL COSTO POR GENERACIÓN ---");
            pw.println("Gen\tCosto");
            for (int i = 0; i < historicoCostos.size(); i++) {
                pw.println(i + "\t" + historicoCostos.get(i));
            }

            System.out.println("-> Resultados guardados exitosamente en: " + nombreArchivo);

        } catch (IOException e) {
            System.err.println("Error al intentar guardar el archivo: " + e.getMessage());
        }
    }
}