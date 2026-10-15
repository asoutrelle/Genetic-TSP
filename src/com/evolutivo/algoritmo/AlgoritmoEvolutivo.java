package com.evolutivo.algoritmo;

import com.evolutivo.algoritmo.corte.CondicionCorte;
import com.evolutivo.algoritmo.cruce.OperadorCruce;
import com.evolutivo.algoritmo.mutacion.OperadorMutacion;
import com.evolutivo.algoritmo.poblacion.GeneradorPoblacionInicial;
import com.evolutivo.algoritmo.seleccion.SeleccionPadres;
import com.evolutivo.algoritmo.sobrevivientes.SeleccionSobrevivientes;
import com.evolutivo.io.CargadorATSP;
import com.evolutivo.model.Individuo;
import com.evolutivo.model.Poblacion;
import com.evolutivo.model.ResultadoEjecucion;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class AlgoritmoEvolutivo {
    private final int[][] matrix;
    private final int tamPoblacion;
    private final float probCruce;
    private final float probMutacion;

    private final GeneradorPoblacionInicial generadorPoblacion;
    private final SeleccionPadres seleccionPadres;
    private final OperadorCruce operadorCruce;
    private final OperadorMutacion operadorMutacion;
    private final SeleccionSobrevivientes seleccionSobrevivientes;
    private final CondicionCorte condicionCorte;

    private Poblacion poblacionActual;

    public AlgoritmoEvolutivo(String rutaInstancia, int tamPoblacion, float probCruce, float probMutacion,
                               GeneradorPoblacionInicial generadorPoblacion,
                               SeleccionPadres seleccionPadres,
                               OperadorCruce operadorCruce,
                               OperadorMutacion operadorMutacion,
                               SeleccionSobrevivientes seleccionSobrevivientes,
                               CondicionCorte condicionCorte) {
        try {
            this.matrix = CargadorATSP.cargar(rutaInstancia);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Error critico: No se encontro el archivo de la matriz.", e);
        }
        this.tamPoblacion = tamPoblacion;
        this.probCruce = probCruce;
        this.probMutacion = probMutacion;
        this.generadorPoblacion = generadorPoblacion;
        this.seleccionPadres = seleccionPadres;
        this.operadorCruce = operadorCruce;
        this.operadorMutacion = operadorMutacion;
        this.seleccionSobrevivientes = seleccionSobrevivientes;
        this.condicionCorte = condicionCorte;
    }

    /** Ejecuta el algoritmo completo y devuelve el resultado (no escribe nada a disco). */
    public ResultadoEjecucion ejecutar() {
        long startTime = System.currentTimeMillis();
        List<Integer> historicoCostos = new ArrayList<>();

        poblacionActual = generadorPoblacion.generar(tamPoblacion, matrix.length);
        poblacionActual.evaluarPoblacion(matrix);

        Individuo mejorGlobal = poblacionActual.getMejorIndividuo();
        historicoCostos.add(mejorGlobal.getCosto());
        System.out.println("Generacion 0 - Mejor Costo Inicial: " + mejorGlobal.getCosto());

        int generacion = 0;
        while (!condicionCorte.debeDetener(generacion, mejorGlobal.getCosto())) {
            generacion++;
            Poblacion hijos = new Poblacion();

            while (hijos.getIndividuos().size() < tamPoblacion) {
                Individuo padre1 = seleccionPadres.seleccionar(poblacionActual);
                Individuo padre2 = seleccionPadres.seleccionar(poblacionActual);
                Individuo[] descendencia = operadorCruce.cruzar(padre1, padre2, probCruce);
                operadorMutacion.mutar(descendencia[0], probMutacion);
                operadorMutacion.mutar(descendencia[1], probMutacion);
                hijos.add(descendencia[0]);
                if (hijos.getIndividuos().size() < tamPoblacion) {
                    hijos.add(descendencia[1]);
                }
            }

            poblacionActual = seleccionSobrevivientes.seleccionar(poblacionActual, hijos, tamPoblacion, matrix);

            Individuo mejorDeGeneracion = poblacionActual.getMejorIndividuo();
            if (mejorDeGeneracion.getFitness() > mejorGlobal.getFitness()) {
                mejorGlobal = mejorDeGeneracion;
            }
            historicoCostos.add(mejorDeGeneracion.getCosto());

            if (generacion % 100 == 0) {
                System.out.println("Generacion " + generacion + " - Mejor Costo Actual: " + mejorGlobal.getCosto());
            }
        }

        long tiempoTotal = System.currentTimeMillis() - startTime;

        System.out.println("FINALIZADO -> Costo: " + mejorGlobal.getCosto()
                + " | Generaciones: " + generacion + " | Tiempo: " + tiempoTotal + " ms");

        return new ResultadoEjecucion(
                mejorGlobal.getCosto(),
                mejorGlobal.getRuta(),
                tiempoTotal,
                historicoCostos,
                generacion,
                generadorPoblacion.getNombre(),
                seleccionPadres.getNombre(),
                operadorCruce.getNombre(),
                operadorMutacion.getNombre(),
                seleccionSobrevivientes.getNombre(),
                condicionCorte.getNombre()
        );
    }
}
