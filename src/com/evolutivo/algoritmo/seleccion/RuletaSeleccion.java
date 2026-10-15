package com.evolutivo.algoritmo.seleccion;

import com.evolutivo.model.Individuo;
import com.evolutivo.model.Poblacion;

import java.util.List;
import java.util.Random;

public class RuletaSeleccion implements SeleccionPadres {
    private final Random random = new Random();

    @Override
    public Individuo seleccionar(Poblacion poblacion) {
        List<Individuo> individuos = poblacion.getIndividuos();
        double sumaFitness = 0.0;
        for (Individuo ind : individuos) {
            sumaFitness += ind.getFitness();
        }

        double punto = random.nextDouble() * sumaFitness;
        double acumulado = 0.0;
        for (Individuo ind : individuos) {
            acumulado += ind.getFitness();
            if (acumulado >= punto) {
                return ind;
            }
        }
        // fallback numérico por redondeo de punto flotante
        return individuos.get(individuos.size() - 1);
    }

    @Override
    public String getNombre() {
        return "Ruleta";
    }
}
