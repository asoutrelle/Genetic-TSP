package com.evolutivo.model;

import java.util.Arrays;
import java.util.List;

public class Individuo {
    private final List<Integer> ruta;
    private double fitness;
    private int costo;

    public Individuo(List<Integer> ruta) {
        this.ruta = ruta;
        this.costo = 0;
        this.fitness = 0.0;
    }


    public double getFitness() { return fitness; }
    public int getCosto() { return costo; }
}