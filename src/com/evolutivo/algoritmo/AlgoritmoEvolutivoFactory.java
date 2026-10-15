package com.evolutivo.algoritmo;

import com.evolutivo.algoritmo.corte.CondicionCorte;
import com.evolutivo.algoritmo.corte.PorEstancamiento;
import com.evolutivo.algoritmo.corte.PorGeneraciones;
import com.evolutivo.algoritmo.cruce.CruceERX;
import com.evolutivo.algoritmo.cruce.CruceOX;
import com.evolutivo.algoritmo.cruce.OperadorCruce;
import com.evolutivo.algoritmo.mutacion.MutacionInversion;
import com.evolutivo.algoritmo.mutacion.MutacionSwap;
import com.evolutivo.algoritmo.mutacion.OperadorMutacion;
import com.evolutivo.algoritmo.poblacion.GeneradorPoblacionInicial;
import com.evolutivo.algoritmo.poblacion.PoblacionAleatoria;
import com.evolutivo.algoritmo.seleccion.RuletaSeleccion;
import com.evolutivo.algoritmo.seleccion.SeleccionPadres;
import com.evolutivo.algoritmo.seleccion.TorneoSeleccion;
import com.evolutivo.algoritmo.sobrevivientes.ReemplazoConElitismo;
import com.evolutivo.algoritmo.sobrevivientes.ReemplazoGeneracional;
import com.evolutivo.algoritmo.sobrevivientes.SeleccionSobrevivientes;
import com.evolutivo.io.ConfigManager;

public class AlgoritmoEvolutivoFactory {

    public static AlgoritmoEvolutivo crear(ConfigManager config, String rutaInstancia) {
        return crear(
                rutaInstancia,
                config.getPoblacion(),
                config.getProbCruce(),
                config.getProbMutacion(),
                config.getGeneradorPoblacion(),
                config.getSeleccionPadres(),
                config.getTamTorneo(),
                config.getCruce(),
                config.getMutacion(),
                config.getSobrevivientes(),
                config.getCantElite(),
                config.getCondicionCorte(),
                config.getMaxGen(),
                config.getGeneracionesSinMejora()
        );
    }

    public static AlgoritmoEvolutivo crear(String rutaInstancia, int poblacion, float probCruce, float probMutacion,
                                            String nombreGenerador, String nombreSeleccion, int tamTorneo,
                                            String nombreCruce, String nombreMutacion, String nombreSobrevivientes,
                                            int cantElite, String nombreCorte, int maxGen, int generacionesSinMejora) {

        GeneradorPoblacionInicial generador = crearGenerador(nombreGenerador);
        SeleccionPadres seleccion = crearSeleccionPadres(nombreSeleccion, tamTorneo);
        OperadorCruce cruce = crearCruce(nombreCruce);
        OperadorMutacion mutacion = crearMutacion(nombreMutacion);
        SeleccionSobrevivientes sobrevivientes = crearSobrevivientes(nombreSobrevivientes, cantElite);
        CondicionCorte corte = crearCondicionCorte(nombreCorte, maxGen, generacionesSinMejora);

        return new AlgoritmoEvolutivo(
                rutaInstancia, poblacion, probCruce, probMutacion,
                generador, seleccion, cruce, mutacion, sobrevivientes, corte
        );
    }

    private static GeneradorPoblacionInicial crearGenerador(String nombre) {
        return new PoblacionAleatoria();
    }

    private static SeleccionPadres crearSeleccionPadres(String nombre, int tamTorneo) {
        return switch (nombre.toLowerCase()) {
            case "ruleta" -> new RuletaSeleccion();
            case "torneo" -> new TorneoSeleccion(tamTorneo);
            default -> throw new IllegalArgumentException("seleccionPadres desconocida: " + nombre);
        };
    }

    private static OperadorCruce crearCruce(String nombre) {
        return switch (nombre.toLowerCase()) {
            case "erx" -> new CruceERX();
            case "ox" -> new CruceOX();
            default -> throw new IllegalArgumentException("cruce desconocido: " + nombre);
        };
    }

    private static OperadorMutacion crearMutacion(String nombre) {
        return switch (nombre.toLowerCase()) {
            case "swap" -> new MutacionSwap();
            case "inversion" -> new MutacionInversion();
            default -> throw new IllegalArgumentException("mutacion desconocida: " + nombre);
        };
    }

    private static SeleccionSobrevivientes crearSobrevivientes(String nombre, int cantElite) {
        return switch (nombre.toLowerCase()) {
            case "generacional" -> new ReemplazoGeneracional();
            case "elitismo" -> new ReemplazoConElitismo(cantElite);
            default -> throw new IllegalArgumentException("sobrevivientes desconocido: " + nombre);
        };
    }

    private static CondicionCorte crearCondicionCorte(String nombre, int maxGen, int generacionesSinMejora) {
        return switch (nombre.toLowerCase()) {
            case "generaciones" -> new PorGeneraciones(maxGen);
            case "estancamiento" -> new PorEstancamiento(generacionesSinMejora, maxGen);
            default -> throw new IllegalArgumentException("condicionCorte desconocida: " + nombre);
        };
    }
}
