package com.evolutivo;


import com.evolutivo.algoritmo.AlgoritmoEvolutivo;
import com.evolutivo.io.ConfigManager;

public class Main {
    public static void main(String[] args) {
        try {
            AlgoritmoEvolutivo algoritmoEvolutivo = getAlgoritmoEvolutivo(args[0]);
            algoritmoEvolutivo.ejecutar("outputResultados.txt");

        } catch (NumberFormatException e) {
            System.err.println("Error de formato: Revisa que los números en tu archivo config.properties sean correctos.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Ocurrió un error al inicializar el algoritmo: " + e.getMessage());
        }
    }

    private static AlgoritmoEvolutivo getAlgoritmoEvolutivo(String ruta) {
        ConfigManager config = new ConfigManager();

        int poblacion = config.getPoblacion();
        float probCruce = config.getProbCruce();
        float probMutacion = config.getProbMutacion();
        int maxGen = config.getMaxGen();

        return new AlgoritmoEvolutivo(
                ruta,
                poblacion,
                probCruce,
                probMutacion,
                maxGen
        );
    }
}