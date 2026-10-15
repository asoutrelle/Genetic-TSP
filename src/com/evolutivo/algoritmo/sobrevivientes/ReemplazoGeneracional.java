package com.evolutivo.algoritmo.sobrevivientes;

import com.evolutivo.model.Poblacion;

public class ReemplazoGeneracional implements SeleccionSobrevivientes {
    @Override
    public Poblacion seleccionar(Poblacion poblacionAnterior, Poblacion nuevaPoblacion, int tamPoblacion, int[][] matrix) {
        nuevaPoblacion.evaluarPoblacion(matrix);
        return nuevaPoblacion;
    }

    @Override
    public String getNombre() {
        return "Generacional completo";
    }
}
