package com.evolutivo.algoritmo;

import com.evolutivo.io.CargadorATSP;
import com.evolutivo.model.Individuo;
import com.evolutivo.model.Poblacion;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AlgoritmoEvolutivo {
    private final int[][] matrix;
    private int tamPoblacion;
    private int maxGen;
    private float probCruce;
    private float probMutacion;
    private Poblacion poblacionActual;

    public AlgoritmoEvolutivo(String ruta, int tamPoblacion, float probCruce, float probMutacion, int maxGen) {
        try {
            this.matrix = CargadorATSP.cargar(ruta);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Error crítico: No se encontró el archivo de la matriz.", e);
        }
        this.tamPoblacion = tamPoblacion;
        this.maxGen = maxGen;
        this.probCruce = probCruce;
        this.probMutacion = probMutacion;
        this.poblacionActual = new Poblacion();
    }

    public void ejecutar() {
        // 1. Inicialización de la Población (Gen 0)
        iniciarPoblacion();

        // 2. Evaluación inicial
        poblacionActual.evaluarPoblacion(matrix);

        // Guardamos al mejor de todos los tiempos para el reporte final (Requisito A.4)
        Individuo mejorGlobal = poblacionActual.getMejorIndividuo();
        System.out.println("Generación 0 - Mejor Costo Inicial: " + mejorGlobal.getCosto());

        // 3. Bucle de Generaciones (Condición de parada: maxGen)
        for (int g = 1; g <= maxGen; g++) {
            Poblacion nuevaPoblacion = new Poblacion();

            // Llenamos la nueva población hasta alcanzar el tamaño definido (tamPoblacion)
            while (nuevaPoblacion.getIndividuos().size() < tamPoblacion) {

                // a. Selección de Padres (Selección por Torneo)
                Individuo padre1 = seleccionarPadreTorneo(poblacionActual);
                Individuo padre2 = seleccionarPadreTorneo(poblacionActual);

                // b. Recombinación / Cruce
                // Este método devolverá un array de 2 hijos (cumpliendo con la consigna)
                Individuo[] hijos = cruzar(padre1, padre2);

                // c. Mutación (Swap Mutation)
                mutar(hijos[0]);
                mutar(hijos[1]);

                // d. Evaluación de los nuevos hijos
                hijos[0].evaluarFitness(matrix);
                hijos[1].evaluarFitness(matrix);

                // e. Selección de Sobrevivientes (Añadir a la nueva población)
                // Modelo Generacional: los hijos reemplazan a los padres
                nuevaPoblacion.add(hijos[0]);
                // Verificamos no pasarnos del tamaño si tamPoblacion es impar
                if (nuevaPoblacion.getIndividuos().size() < tamPoblacion) {
                    nuevaPoblacion.add(hijos[1]);
                }
            }

            // Reemplazo total: la población nueva pasa a ser la actual
            poblacionActual = nuevaPoblacion;

            // Registro del mejor de la generación actual
            Individuo mejorDeGeneracion = poblacionActual.getMejorIndividuo();
            if (mejorDeGeneracion.getFitness() > mejorGlobal.getFitness()) {
                mejorGlobal = mejorDeGeneracion; // Actualizamos el récord histórico
            }

            // Opcional: Mostrar progreso cada 100 generaciones
            if (g % 100 == 0) {
                System.out.println("Generación " + g + " - Mejor Costo Actual: " + mejorGlobal.getCosto());
            }
        }

        // 4. Resultados finales (Requisito A.4)
        System.out.println("\n========================================");
        System.out.println("FINALIZADO: MEJOR SOLUCIÓN ENCONTRADA");
        System.out.println("Costo total: " + mejorGlobal.getCosto());
        System.out.println("Ruta: " + mejorGlobal.getRuta());
        System.out.println("========================================");
    }

    private void iniciarPoblacion(){
        for(int i = 0; i < tamPoblacion; i++){
            poblacionActual.add(crearIndividuo());
        }
    }
    private Individuo seleccionarPadreTorneo(Poblacion poblacionActual) {
        int k = 3;
        Random r = new Random();
        Individuo mejor = null;
        List<Individuo> individuos = poblacionActual.getIndividuos();

        for (int i = 0; i < k; i++) {
            int randomIndex = r.nextInt(individuos.size());
            Individuo competidor = individuos.get(randomIndex);

            if (mejor == null || competidor.getFitness() > mejor.getFitness()) {
                mejor = competidor;
            }
        }
        return mejor;
    }

    private void mutar(Individuo individuo) {
        Random r = new Random();
        // Tiramos los dados a ver si este individuo muta
        if (r.nextFloat() <= probMutacion) {
            List<Integer> ruta = individuo.getRuta();
            int n = ruta.size();

            // Elegimos dos posiciones distintas al azar
            int pos1 = r.nextInt(n);
            int pos2 = r.nextInt(n);

            // Intercambiamos los valores (swap)
            Collections.swap(ruta, pos1, pos2);

            // Ojo: no evaluamos acá, de eso se encarga el método ejecutar() luego
        }
    }

    private Individuo[] cruzar(Individuo padre1, Individuo padre2) {
        Random r = new Random();
        Individuo[] hijos = new Individuo[2];

        // Verificamos si ocurre el cruce según la probabilidad
        if (r.nextFloat() <= probCruce) {
            // SOLUCIÓN AL COMENTARIO DE LA PROFESORA:
            // Generamos el hijo 1 pasando (padre1, padre2)
            hijos[0] = cruceBasadoEnArcos(padre1, padre2);
            // Generamos el hijo 2 invirtiendo los roles (padre2, padre1)
            hijos[1] = cruceBasadoEnArcos(padre2, padre1);
        } else {
            // Si no hay cruce, los hijos son copias exactas (clones) de los padres
            hijos[0] = new Individuo(padre1.getRuta());
            hijos[1] = new Individuo(padre2.getRuta());
        }

        return hijos;
    }

    private Individuo cruceBasadoEnArcos(Individuo p1, Individuo p2) {
        // Acá irá toda la lógica compleja de construir la tabla de adyacencia
        // Por ahora, para que no dé error de compilación, devolvemos un clon de p1
        return new Individuo(p1.getRuta());
    }

    private Individuo crearIndividuo(){
        List<Integer> camino = new ArrayList<>();
        int cant_ciudades = matrix.length;
        for (int i = 0; i < cant_ciudades; i++) {
            camino.add(i);
        }
        Collections.shuffle(camino, new Random());
        return new Individuo(camino);
    }
}