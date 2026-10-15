package com.evolutivo;

import com.evolutivo.algoritmo.AlgoritmoEvolutivo;
import com.evolutivo.algoritmo.AlgoritmoEvolutivoFactory;
import com.evolutivo.io.ConfigManager;
import com.evolutivo.io.FileManager;
import com.evolutivo.model.ResultadoEjecucion;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
        try {
            ConfigManager config = new ConfigManager();
            AlgoritmoEvolutivo algoritmoEvolutivo = AlgoritmoEvolutivoFactory.crear(config, args[0]);
            ResultadoEjecucion resultado = algoritmoEvolutivo.ejecutar();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
            String fechaHora = LocalDateTime.now().format(formatter);
            FileManager.guardarResultados("resultados/result-" + fechaHora + ".txt",
                    config.getPoblacion(), config.getProbCruce(), config.getProbMutacion(),
                    resultado.generacionesEjecutadas(), resultado.tiempoMs(), resultado.costoFinal(),
                    resultado.ruta(), resultado.historicoCostos(),
                    resultado.nombreGenerador(), resultado.nombreSeleccion(), resultado.nombreCruce(),
                    resultado.nombreMutacion(), resultado.nombreSobrevivientes(), resultado.nombreCorte());

        } catch (NumberFormatException e) {
            System.err.println("Error de formato: Revisa que los numeros en tu archivo config.properties sean correctos.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Ocurrio un error al inicializar el algoritmo: " + e.getMessage());
        }
    }
}
