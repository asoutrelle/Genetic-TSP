package com.evolutivo.algoritmo.mutacion;

import com.evolutivo.model.Individuo;

public interface OperadorMutacion {
    void mutar(Individuo individuo, float probMutacion);
    String getNombre();
}
