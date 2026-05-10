package com.evolutivo.algoritmo;

import com.evolutivo.io.CargadorATSP;
import com.evolutivo.model.Individuo;
import com.evolutivo.model.Poblacion;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AlgoritmoEvolutivo {
    private final int[][] matrix;

    public AlgoritmoEvolutivo(String ruta, int tamPoblacion, float probCruce, float probMutacion, int maxGen) {
        try {
            this.matrix = CargadorATSP.cargar(ruta);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Error crítico: No se encontró el archivo de la matriz.", e);
        }
        iniciarPoblacion(tamPoblacion);
    }

    private void iniciarPoblacion(int tam){
        Poblacion poblacione = new Poblacion();
        for(int i = 0; i < tam; i++){
            poblacione.add(crearIndividuo());
        }
    }

    private  Individuo crearIndividuo(){
        List<Integer> camino = new ArrayList<>();
        int cant_ciudades = matrix.length;
        for (int i = 0; i < cant_ciudades; i++) {
            camino.add(i);
        }
        Collections.shuffle(camino, new Random());
        return new Individuo(camino);
    }
}