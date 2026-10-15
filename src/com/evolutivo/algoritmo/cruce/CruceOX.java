package com.evolutivo.algoritmo.cruce;

import com.evolutivo.model.Individuo;

import java.util.*;

public class CruceOX implements OperadorCruce {
    private final Random random = new Random();

    @Override
    public Individuo[] cruzar(Individuo padre1, Individuo padre2, float probCruce) {
        Individuo[] hijos = new Individuo[2];
        if (random.nextFloat() <= probCruce) {
            hijos[0] = ox(padre1, padre2);
            hijos[1] = ox(padre2, padre1);
        } else {
            hijos[0] = new Individuo(padre1.getRuta());
            hijos[1] = new Individuo(padre2.getRuta());
        }
        return hijos;
    }

    private Individuo ox(Individuo p1, Individuo p2) {
        List<Integer> ruta1 = p1.getRuta();
        List<Integer> ruta2 = p2.getRuta();
        int n = ruta1.size();

        int corte1 = random.nextInt(n);
        int corte2 = random.nextInt(n);
        int inicio = Math.min(corte1, corte2);
        int fin = Math.max(corte1, corte2);

        Integer[] hijoArr = new Integer[n];
        Set<Integer> usadas = new HashSet<>();
        for (int i = inicio; i <= fin; i++) {
            hijoArr[i] = ruta1.get(i);
            usadas.add(ruta1.get(i));
        }

        int posHijo = (fin + 1) % n;
        for (int i = 0; i < n; i++) {
            int ciudad = ruta2.get((fin + 1 + i) % n);
            if (!usadas.contains(ciudad)) {
                hijoArr[posHijo] = ciudad;
                usadas.add(ciudad);
                posHijo = (posHijo + 1) % n;
            }
        }

        return new Individuo(Arrays.asList(hijoArr));
    }

    @Override
    public String getNombre() {
        return "OX (Order Crossover)";
    }
}
