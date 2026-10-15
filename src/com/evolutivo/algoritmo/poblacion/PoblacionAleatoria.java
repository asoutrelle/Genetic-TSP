package com.evolutivo.algoritmo.poblacion;

import com.evolutivo.model.Individuo;
import com.evolutivo.model.Poblacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PoblacionAleatoria implements GeneradorPoblacionInicial {
    private final Random random = new Random();

    @Override
    public Poblacion generar(int tamPoblacion, int cantCiudades) {
        Poblacion poblacion = new Poblacion();
        for (int i = 0; i < tamPoblacion; i++) {
            List<Integer> camino = new ArrayList<>();
            for (int c = 0; c < cantCiudades; c++) {
                camino.add(c);
            }
            Collections.shuffle(camino, random);
            poblacion.add(new Individuo(camino));
        }
        return poblacion;
    }

    @Override
    public String getNombre() {
        return "Aleatoria";
    }
}
