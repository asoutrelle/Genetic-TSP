package com.evolutivo.algoritmo.corte;

public class PorEstancamiento implements CondicionCorte {
    private final int generacionesSinMejoraLimite;
    private final int maxGen;
    private int mejorCostoVisto = Integer.MAX_VALUE;
    private int generacionesSinMejora = 0;

    public PorEstancamiento(int generacionesSinMejoraLimite, int maxGen) {
        this.generacionesSinMejoraLimite = generacionesSinMejoraLimite;
        this.maxGen = maxGen;
    }

    @Override
    public boolean debeDetener(int generacionActual, int mejorCostoActual) {
        if (mejorCostoActual < mejorCostoVisto) {
            mejorCostoVisto = mejorCostoActual;
            generacionesSinMejora = 0;
        } else {
            generacionesSinMejora++;
        }
        return generacionesSinMejora >= generacionesSinMejoraLimite || generacionActual >= maxGen;
    }

    @Override
    public String getNombre() {
        return "Por estancamiento (" + generacionesSinMejoraLimite + " gen. sin mejora, tope=" + maxGen + ")";
    }
}
