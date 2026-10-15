package com.evolutivo.algoritmo.cruce;

import com.evolutivo.model.Individuo;

import java.util.*;

public class CruceERX implements OperadorCruce {
    private final Random random = new Random();

    @Override
    public Individuo[] cruzar(Individuo padre1, Individuo padre2, float probCruce) {
        Individuo[] hijos = new Individuo[2];
        if (random.nextFloat() <= probCruce) {
            hijos[0] = cruceBasadoEnArcos(padre1, padre2);
            hijos[1] = cruceBasadoEnArcos(padre2, padre1);
        } else {
            hijos[0] = new Individuo(padre1.getRuta());
            hijos[1] = new Individuo(padre2.getRuta());
        }
        return hijos;
    }

    private Individuo cruceBasadoEnArcos(Individuo p1, Individuo p2) {
        int n = p1.getRuta().size();
        List<Integer> ruta1 = p1.getRuta();
        List<Integer> ruta2 = p2.getRuta();

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
        int ciudadActual = random.nextInt(n);

        while (hijoRuta.size() < n) {
            hijoRuta.add(ciudadActual);
            for (int i = 0; i < n; i++) {
                vecinos.get(i).remove(ciudadActual);
                comunes.get(i).remove(ciudadActual);
            }
            if (hijoRuta.size() == n) break;

            Set<Integer> vComunes = comunes.get(ciudadActual);
            Set<Integer> vTodos = vecinos.get(ciudadActual);
            if (!vComunes.isEmpty()) {
                ciudadActual = obtenerMejorCandidato(vComunes, vecinos);
            } else if (!vTodos.isEmpty()) {
                ciudadActual = obtenerMejorCandidato(vTodos, vecinos);
            } else {
                List<Integer> noVisitadas = new ArrayList<>();
                for (int i = 0; i < n; i++) {
                    if (!hijoRuta.contains(i)) {
                        noVisitadas.add(i);
                    }
                }
                ciudadActual = noVisitadas.get(random.nextInt(noVisitadas.size()));
            }
        }
        return new Individuo(hijoRuta);
    }

    private void identificarVecino(int n, List<Integer> ruta, Map<Integer, Set<Integer>> vecinos,
                                    Map<Integer, Set<Integer>> comunes, int i) {
        int ciudad = ruta.get(i);
        int prev = ruta.get((i - 1 + n) % n);
        int next = ruta.get((i + 1) % n);
        agregarArco(ciudad, prev, vecinos, comunes);
        agregarArco(ciudad, next, vecinos, comunes);
    }

    private void agregarArco(int ciudad, int adyacente, Map<Integer, Set<Integer>> vecinos,
                              Map<Integer, Set<Integer>> comunes) {
        if (vecinos.get(ciudad).contains(adyacente)) {
            comunes.get(ciudad).add(adyacente);
        } else {
            vecinos.get(ciudad).add(adyacente);
        }
    }

    private int obtenerMejorCandidato(Set<Integer> candidatos, Map<Integer, Set<Integer>> vecinos) {
        int minVecinos = Integer.MAX_VALUE;
        List<Integer> mejores = new ArrayList<>();
        for (int c : candidatos) {
            int tam = vecinos.get(c).size();
            if (tam < minVecinos) {
                minVecinos = tam;
                mejores.clear();
                mejores.add(c);
            } else if (tam == minVecinos) {
                mejores.add(c);
            }
        }
        return mejores.get(random.nextInt(mejores.size()));
    }

    @Override
    public String getNombre() {
        return "ERX (Edge Recombination Crossover)";
    }
}
