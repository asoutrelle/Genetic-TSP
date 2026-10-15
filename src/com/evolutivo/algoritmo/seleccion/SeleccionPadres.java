package com.evolutivo.algoritmo.seleccion;

import com.evolutivo.model.Individuo;
import com.evolutivo.model.Poblacion;

public interface SeleccionPadres {
    Individuo seleccionar(Poblacion poblacion);
    String getNombre();
}
