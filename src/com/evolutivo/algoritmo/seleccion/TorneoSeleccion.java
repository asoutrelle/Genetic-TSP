package com.evolutivo.algoritmo.seleccion;

import com.evolutivo.model.Individuo;
import com.evolutivo.model.Poblacion;

import java.util.List;
import java.util.Random;

public class TorneoSeleccion implements SeleccionPadres {
    private final int k;
    private final Random random = new Random();

    public TorneoSeleccion(int k) {
        this.k = k;
    }

    @Override
    public Individuo seleccionar(Poblacion poblacion) {
        List<Individuo> individuos = poblacion.getIndividuos();
        Individuo mejor = null;
        for (int i = 0; i < k; i++) {
            int idx = random.nextInt(individuos.size());
            Individuo competidor = individuos.get(idx);
            if (mejor == null || competidor.getFitness() > mejor.getFitness()) {
                mejor = competidor;
            }
        }
        return mejor;
    }

    @Override
    public String getNombre() {
        return "Torneo (k=" + k + ")";
    }
}
