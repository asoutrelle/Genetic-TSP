package com.evolutivo.algoritmo.corte;

public interface CondicionCorte {
    boolean debeDetener(int generacionActual, int mejorCostoActual);
    String getNombre();
}
