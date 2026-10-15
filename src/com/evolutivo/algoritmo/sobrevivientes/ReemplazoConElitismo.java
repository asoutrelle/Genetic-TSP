package com.evolutivo.algoritmo.sobrevivientes;

import com.evolutivo.model.Individuo;
import com.evolutivo.model.Poblacion;

import java.util.List;

public class ReemplazoConElitismo implements SeleccionSobrevivientes {
    private final int cantElite;

    public ReemplazoConElitismo(int cantElite) {
        this.cantElite = cantElite;
    }

    @Override
    public Poblacion seleccionar(Poblacion poblacionAnterior, Poblacion nuevaPoblacion, int tamPoblacion, int[][] matrix) {
        nuevaPoblacion.evaluarPoblacion(matrix);

        List<Individuo> elite = poblacionAnterior.getIndividuos().subList(0, Math.min(cantElite, poblacionAnterior.getIndividuos().size()));

        Poblacion resultado = new Poblacion();

        for (Individuo ind : elite) {
            resultado.add(ind);
        }

        List<Individuo> hijos = nuevaPoblacion.getIndividuos();
        for (int i = 0; i < hijos.size() && resultado.getIndividuos().size() < tamPoblacion; i++) {
            resultado.add(hijos.get(i));
        }

        resultado.evaluarPoblacion(matrix);
        return resultado;
    }

    @Override
    public String getNombre() {
        return "Con elitismo (top " + cantElite + ")";
    }
}
