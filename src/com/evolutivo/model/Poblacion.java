package com.evolutivo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Poblacion {
    private ArrayList<Individuo> individuos;

    public Poblacion() {
        individuos = new ArrayList<>();
    }
    
    public void add(Individuo ind) {
        individuos.add(ind);
    }
    
    public void evaluarPoblacion(int[][] matrix){
        for (Individuo individuo : individuos) {
            individuo.evaluarFitness(matrix);
        }
        Collections.sort(individuos);
    }
    public Individuo getMejorIndividuo(){
        return individuos.getFirst();
    }
    public List<Individuo> getIndividuos() {
        return individuos;
    }
}
