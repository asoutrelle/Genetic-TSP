package com.evolutivo.algoritmo.cruce;

import com.evolutivo.model.Individuo;

public interface OperadorCruce {
    Individuo[] cruzar(Individuo padre1, Individuo padre2, float probCruce);
    String getNombre();
}
