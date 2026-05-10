package com.evolutivo.model;

import java.util.ArrayList;

public class Poblacion {
    private ArrayList<Individuo> individuos;
    private int tam;

    public Poblacion() {
        individuos = new ArrayList<>();
    }
    public void add(Individuo ind) {
        individuos.add(ind);
    }
}
