package com.evolutivo.algoritmo.corte;

public class PorGeneraciones implements CondicionCorte {
    private final int maxGen;

    public PorGeneraciones(int maxGen) {
        this.maxGen = maxGen;
    }

    @Override
    public boolean debeDetener(int generacionActual, int mejorCostoActual) {
        return generacionActual >= maxGen;
    }

    @Override
    public String getNombre() {
        return "Por generaciones (max=" + maxGen + ")";
    }
}
