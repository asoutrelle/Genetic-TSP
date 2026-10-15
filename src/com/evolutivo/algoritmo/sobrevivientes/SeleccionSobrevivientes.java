package com.evolutivo.algoritmo.sobrevivientes;

import com.evolutivo.model.Poblacion;

public interface SeleccionSobrevivientes {
    Poblacion seleccionar(Poblacion poblacionAnterior, Poblacion nuevaPoblacion, int tamPoblacion, int[][] matrix);
    String getNombre();
}
