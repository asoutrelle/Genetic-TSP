package com.evolutivo.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class FileManager {

    public static void guardarResultados(String nombreArchivo,
                                          int tamPoblacion, float probCruce, float probMutacion, int generacionesEjecutadas,
                                          long tiempoEjecucion, int mejorCosto, List<Integer> mejorRuta,
                                          List<Integer> historicoCostos,
                                          String nombreGenerador, String nombreSeleccion, String nombreCruce,
                                          String nombreMutacion, String nombreSobrevivientes, String nombreCorte) {

        new File(nombreArchivo).getAbsoluteFile().getParentFile().mkdirs();

        try (PrintWriter pw = new PrintWriter(new FileWriter(nombreArchivo))) {
            pw.println("==================================================");
            pw.println(" REPORTE DE EJECUCION - ALGORITMO EVOLUTIVO TSP ");
            pw.println("==================================================");

            pw.println("\n--- CONFIGURACION UTILIZADA ---");
            pw.println("Tamaño de la poblacion: " + tamPoblacion);
            pw.println("Probabilidad de Cruce: " + (probCruce * 100) + "%");
            pw.println("Probabilidad de Mutacion: " + (probMutacion * 100) + "%");
            pw.println("Generacion de poblacion inicial: " + nombreGenerador);
            pw.println("Seleccion de padres: " + nombreSeleccion);
            pw.println("Operador de cruce: " + nombreCruce);
            pw.println("Operador de mutacion: " + nombreMutacion);
            pw.println("Seleccion de sobrevivientes: " + nombreSobrevivientes);
            pw.println("Condicion de corte: " + nombreCorte);
            pw.println("Generaciones ejecutadas: " + generacionesEjecutadas);

            pw.println("\n--- RESULTADOS FINALES ---");
            pw.println("Mejor Costo Encontrado: " + mejorCosto);
            pw.println("Ruta optima: " + mejorRuta);
            pw.println("Tiempo de ejecucion: " + tiempoEjecucion + " milisegundos");

            pw.println("\n--- EVOLUCION DEL COSTO POR GENERACION ---");
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
