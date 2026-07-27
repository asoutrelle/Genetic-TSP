package com.evolutivo.algoritmo;

import com.evolutivo.io.CargadorATSP;
import com.evolutivo.io.FileManager;
import com.evolutivo.model.Individuo;
import com.evolutivo.model.Poblacion;

import java.io.FileNotFoundException;
import java.util.*;

public class AlgoritmoEvolutivo {
    private final int[][] matrix;
    private final int tamPoblacion;
    private final int maxGen;
    private final float probCruce;
    private final float probMutacion;
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

    public void ejecutar(String nombreArchivoSalida) {
        long startTime = System.currentTimeMillis(); // INICIAMOS EL CRONOMETRO
        List<Integer> historicoCostos = new ArrayList<>(); // PARA GUARDAR LA EVOLUCION

        iniciarPoblacion();
        poblacionActual.evaluarPoblacion(matrix);

        Individuo mejorGlobal = poblacionActual.getMejorIndividuo();
        historicoCostos.add(mejorGlobal.getCosto()); // Guardamos costo Gen 0

        System.out.println("Generación 0 - Mejor Costo Inicial: " + mejorGlobal.getCosto());

        for (int g = 1; g <= maxGen; g++) {
            Poblacion nuevaPoblacion = new Poblacion();

            while (nuevaPoblacion.getIndividuos().size() < tamPoblacion) {
                Individuo padre1 = seleccionarPadreTorneo(poblacionActual);
                Individuo padre2 = seleccionarPadreTorneo(poblacionActual);
                Individuo[] hijos = cruzar(padre1, padre2);
                mutar(hijos[0]);
                mutar(hijos[1]);
                hijos[0].evaluarFitness(matrix);
                hijos[1].evaluarFitness(matrix);
                nuevaPoblacion.add(hijos[0]);
                if (nuevaPoblacion.getIndividuos().size() < tamPoblacion) {
                    nuevaPoblacion.add(hijos[1]);
                }
            }

            poblacionActual = nuevaPoblacion;

            Individuo mejorDeGeneracion = poblacionActual.getMejorIndividuo();
            if (mejorDeGeneracion.getFitness() > mejorGlobal.getFitness()) {
                mejorGlobal = mejorDeGeneracion;
            }

            // GUARDAMOS EL MEJOR COSTO DE ESTA GENERACIÓN EN EL HISTORIAL
            historicoCostos.add(mejorDeGeneracion.getCosto());

            if (g % 100 == 0) {
                System.out.println("Generación " + g + " - Mejor Costo Actual: " + mejorGlobal.getCosto());
            }
        }

        long endTime = System.currentTimeMillis(); // DETENEMOS EL CRONÓMETRO
        long tiempoTotal = endTime - startTime;

        System.out.println("\n========================================");
        System.out.println("FINALIZADO: MEJOR SOLUCION ENCONTRADA");
        System.out.println("Costo total: " + mejorGlobal.getCosto());
        System.out.println("Tiempo de ejecución: " + tiempoTotal + " ms");
        System.out.println("========================================");

        // LLAMAMOS AL GESTOR DE ARCHIVOS PARA EXPORTAR EL TXT
        FileManager.guardarResultados(nombreArchivoSalida, tamPoblacion, probCruce, probMutacion, maxGen, tiempoTotal, mejorGlobal.getCosto(), mejorGlobal.getRuta(), historicoCostos);
    }

    private void iniciarPoblacion() {
        for(int i = 0; i < tamPoblacion; i++) {
            poblacionActual.add(crearIndividuo());
        }
    }
    private Individuo seleccionarPadreTorneo(Poblacion poblacionActual) {
        int k = 3; //tamaño del torneo
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
        int n = p1.getRuta().size();
        List<Integer> ruta1 = p1.getRuta();
        List<Integer> ruta2 = p2.getRuta();
        // 1. Construir la tabla de arcos (Separando los comunes '+')
        Map<Integer, Set<Integer>> vecinos = new HashMap<>();
        Map<Integer, Set<Integer>> comunes = new HashMap<>();
        for (int i = 0; i < n; i++) {
            vecinos.put(i, new HashSet<>());
            comunes.put(i, new HashSet<>());
        }

        for (int i = 0; i < n; i++) {
            identificarVecino(n, ruta1, vecinos, comunes, i);
            identificarVecino(n, ruta2, vecinos, comunes, i);
        }

        List<Integer> hijoRuta = new ArrayList<>();
        Random r = new Random();
        // PASO 1 DE TU TEORÍA: Elegir al azar un elemento y ubicarlo en el hijo
        int ciudadActual = r.nextInt(n);

        while (hijoRuta.size() < n) {
            hijoRuta.add(ciudadActual);
            // PASO 2 DE TU TEORÍA: Remover todas las referencias a este elemento
            for (int i = 0; i < n; i++) {
                vecinos.get(i).remove(ciudadActual);
                comunes.get(i).remove(ciudadActual);
            }
            if (hijoRuta.size() == n) break;
            // PASO 3 DE TU TEORÍA: Examinar la lista de arcos
            Set<Integer> vComunes = comunes.get(ciudadActual);
            Set<Integer> vTodos = vecinos.get(ciudadActual);
            if (!vComunes.isEmpty()) {
                // Si existe un arco común (+), se elige (si hay varios, desempatamos por lista más corta)
                ciudadActual = obtenerMejorCandidato(vComunes, vecinos, r);
            } else if (!vTodos.isEmpty()) {
                // En otro caso, se elige el elemento con la lista de arcos más corta
                ciudadActual = obtenerMejorCandidato(vTodos, vecinos, r);
            } else {
                // PASO 4 DE TU TEORÍA: Lista vacía -> un nuevo elemento es elegido al azar
                List<Integer> noVisitadas = new ArrayList<>();
                for (int i = 0; i < n; i++) {
                    if (!hijoRuta.contains(i)) {
                        noVisitadas.add(i);
                    }
                }
                ciudadActual = noVisitadas.get(r.nextInt(noVisitadas.size()));
            }
        }
        return new Individuo(hijoRuta);
    }

    private void identificarVecino(int n, List<Integer> ruta, Map<Integer, Set<Integer>> vecinos, Map<Integer, Set<Integer>> comunes, int i) {
        int ciudad = ruta.get(i);
        int prev = ruta.get((i - 1 + n) % n);
        int next = ruta.get((i + 1) % n);
        agregarArco(ciudad, prev, vecinos, comunes);
        agregarArco(ciudad, next, vecinos, comunes);
    }

    // Registra el arco. Si ya existía, lo marca como arco común (+)
    private void agregarArco(int ciudad, int adyacente, Map<Integer, Set<Integer>> vecinos, Map<Integer, Set<Integer>> comunes) {
        if (vecinos.get(ciudad).contains(adyacente)) {
            comunes.get(ciudad).add(adyacente);
        } else {
            vecinos.get(ciudad).add(adyacente);
        }
    }


    private int obtenerMejorCandidato(Set<Integer> candidatos, Map<Integer, Set<Integer>> vecinos, Random r) {
        int minVecinos = Integer.MAX_VALUE;
        List<Integer> mejores = new ArrayList<>();

        for (int c : candidatos) {
            int tam = vecinos.get(c).size();
            if (tam < minVecinos) {
                minVecinos = tam;
                mejores.clear();
                mejores.add(c);
            } else if (tam == minVecinos) {
                mejores.add(c); // En caso de empate, lo agregamos a la lista
            }
        }
        // Desempate aleatorio entre los que tengan la lista más corta
        return mejores.get(r.nextInt(mejores.size()));
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