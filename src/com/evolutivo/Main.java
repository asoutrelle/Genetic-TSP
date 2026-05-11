package com.evolutivo;


import com.evolutivo.algoritmo.AlgoritmoEvolutivo;
import com.evolutivo.io.ConfigManager;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            ConfigManager config = new ConfigManager();

            // 1. Extraer y convertir los valores primero
            String archivoTSP = "assets/br17.atsp";
            int poblacion = Integer.parseInt(config.getConfig("poblacion"));
            float probCruce = Float.parseFloat(config.getConfig("probCruce"));
            float probMutacion = Float.parseFloat(config.getConfig("probMutacion"));
            int maxGen = Integer.parseInt(config.getConfig("maxGen"));

            AlgoritmoEvolutivo algoritmoEvolutivo = new AlgoritmoEvolutivo(
                    archivoTSP,
                    poblacion,
                    probCruce,
                    probMutacion,
                    maxGen
            );
            algoritmoEvolutivo.ejecutar();

        } catch (NumberFormatException e) {
            System.err.println("Error de formato: Revisa que los números en tu archivo config.properties sean correctos.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Ocurrió un error al inicializar el algoritmo: " + e.getMessage());
        }
    }

}