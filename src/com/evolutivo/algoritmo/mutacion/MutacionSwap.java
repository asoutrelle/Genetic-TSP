package com.evolutivo.algoritmo.mutacion;

import com.evolutivo.model.Individuo;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MutacionSwap implements OperadorMutacion {
    private final Random random = new Random();

    @Override
    public void mutar(Individuo individuo, float probMutacion) {
        if (random.nextFloat() <= probMutacion) {
            List<Integer> ruta = individuo.getRuta();
            int n = ruta.size();
            int pos1 = random.nextInt(n);
            int pos2 = random.nextInt(n);
            Collections.swap(ruta, pos1, pos2);
        }
    }

    @Override
    public String getNombre() {
        return "Swap";
    }
}
