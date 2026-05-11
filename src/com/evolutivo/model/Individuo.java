package com.evolutivo.model;

import java.util.ArrayList;
import java.util.List;

public class Individuo implements Comparable<Individuo>{
    private final List<Integer> ruta;
    private double fitness;
    private int costo;

    public Individuo(List<Integer> ruta) {
        this.ruta = new ArrayList<>(ruta);
        this.costo = 0;
        this.fitness = 0.0;
    }

    public void evaluarFitness(int[][] matrix){
        int costoTotal = 0;
        int n = ruta.size();
        for (int i = 0; i < n-1; i++) {
            costoTotal += matrix[ruta.get(i)][ruta.get(i+1)];
        }
        costoTotal += matrix[ruta.get(n-1)][ruta.getFirst()];
        this.costo = costoTotal;
        this.fitness = 1.0d / costoTotal;
    }
    public double getFitness() {
        return fitness;
    }
    public int getCosto() {
        return costo;
    }
    public List<Integer> getRuta() {
        return ruta;
    }

    @Override
    public int compareTo(Individuo o) {
        return Double.compare(o.fitness, this.fitness);
    }
}