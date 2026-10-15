package com.evolutivo.experimento;

import com.evolutivo.algoritmo.AlgoritmoEvolutivo;
import com.evolutivo.algoritmo.AlgoritmoEvolutivoFactory;
import com.evolutivo.io.FileManager;
import com.evolutivo.model.ResultadoEjecucion;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Ejecuta las 6 configuraciones del trabajo, repitiendo cada una n veces,
 * guarda cada corrida individual (igual que Main) dentro de
 * resultados/&lt;config&gt;/, y arma dos CSV consolidados:
 *
 *   resultados/detalle_corridas.csv        -> una fila por corrida
 *   resultados/resumen_configuraciones.csv -> una fila por configuracion
 *
 * Uso: java -cp out com.evolutivo.experimento.ExperimentoRunner rutaInstancia [repeticiones]
 */
public class ExperimentoRunner {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Uso: java ExperimentoRunner <rutaInstancia> [repeticiones]");
            return;
        }
        String rutaInstancia = args[0];
        int repeticiones = args.length >= 2 ? Integer.parseInt(args[1]) : 5;

        List<ExperimentoConfig> configuraciones = definirConfiguraciones();
        new File("resultados").mkdirs();

        try (PrintWriter detalle = new PrintWriter(new FileWriter("resultados/detalle_corridas.csv"))) {
            detalle.println("configuracion,corrida,costoFinal,generacionesEjecutadas,tiempoMs");

            List<String[]> filasResumen = new ArrayList<>();

            for (ExperimentoConfig cfg : configuraciones) {
                List<Integer> costos = new ArrayList<>();
                List<Long> tiempos = new ArrayList<>();
                List<Integer> generaciones = new ArrayList<>();

                String carpetaConfig = "resultados/" + cfg.nombre;
                new File(carpetaConfig).mkdirs();

                System.out.println("\n=== Configuracion: " + cfg.nombre + " ===");

                for (int corrida = 1; corrida <= repeticiones; corrida++) {
                    AlgoritmoEvolutivo algoritmo = AlgoritmoEvolutivoFactory.crear(
                            rutaInstancia, cfg.poblacion, cfg.probCruce, cfg.probMutacion,
                            cfg.generadorPoblacion, cfg.seleccionPadres, cfg.tamTorneo,
                            cfg.cruce, cfg.mutacion, cfg.sobrevivientes, cfg.cantElite,
                            cfg.condicionCorte, cfg.maxGen, cfg.generacionesSinMejora
                    );

                    ResultadoEjecucion resultado = algoritmo.ejecutar();

                    FileManager.guardarResultados(carpetaConfig + "/result-" + corrida + ".txt",
                            cfg.poblacion, cfg.probCruce, cfg.probMutacion,
                            resultado.generacionesEjecutadas(), resultado.tiempoMs(), resultado.costoFinal(),
                            resultado.ruta(), resultado.historicoCostos(),
                            resultado.nombreGenerador(), resultado.nombreSeleccion(), resultado.nombreCruce(),
                            resultado.nombreMutacion(), resultado.nombreSobrevivientes(), resultado.nombreCorte());

                    costos.add(resultado.costoFinal());
                    tiempos.add(resultado.tiempoMs());
                    generaciones.add(resultado.generacionesEjecutadas());

                    detalle.println(cfg.nombre + "," + corrida + "," + resultado.costoFinal() + ","
                            + resultado.generacionesEjecutadas() + "," + resultado.tiempoMs());
                    detalle.flush();

                    System.out.println("  Corrida " + corrida + "/" + repeticiones
                            + " -> costo=" + resultado.costoFinal() + " tiempo=" + resultado.tiempoMs() + "ms");
                }

                filasResumen.add(new String[]{
                        cfg.nombre,
                        String.valueOf(EstadisticasUtils.promedio(costos)),
                        String.valueOf(EstadisticasUtils.minimo(costos)),
                        String.valueOf(EstadisticasUtils.desvioEstandar(costos)),
                        String.valueOf(EstadisticasUtils.promedioLong(tiempos)),
                        String.valueOf(EstadisticasUtils.promedio(generaciones))
                });
            }

            try (PrintWriter resumen = new PrintWriter(new FileWriter("resultados/resumen_configuraciones.csv"))) {
                resumen.println("configuracion,costoPromedio,costoMejor,costoDesvioEstandar,tiempoPromedioMs,generacionesPromedio");
                for (String[] fila : filasResumen) {
                    resumen.println(String.join(",", fila));
                }
            }

            System.out.println("\nListo -> resultados/detalle_corridas.csv y resultados/resumen_configuraciones.csv");

        } catch (IOException e) {
            System.err.println("Error al escribir los archivos de resultados: " + e.getMessage());
        }
    }

    /** Nombres de carpeta iguales a los que ya venis usando manualmente. */
    private static List<ExperimentoConfig> definirConfiguraciones() {
        return List.of(
                ExperimentoConfig.builder("baseline_torneo_erx_swap_elitismo").build(),
                ExperimentoConfig.builder("cruce_ox").cruce("ox").build(),
                ExperimentoConfig.builder("mutacion_inversion").mutacion("inversion").build(),
                ExperimentoConfig.builder("seleccion_ruleta").seleccionPadres("ruleta").build(),
                ExperimentoConfig.builder("sin_elitismo").sobrevivientes("generacional").build(),
                ExperimentoConfig.builder("corte_estancamiento").condicionCorte("estancamiento").build()
        );
    }
}
