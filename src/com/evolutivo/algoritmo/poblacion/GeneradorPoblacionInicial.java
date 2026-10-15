package com.evolutivo.algoritmo.poblacion;

import com.evolutivo.model.Poblacion;

public interface GeneradorPoblacionInicial {
    Poblacion generar(int tamPoblacion, int cantCiudades);
    String getNombre();
}
