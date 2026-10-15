package com.evolutivo.experimento;

import java.util.List;

public class EstadisticasUtils {

    public static double promedio(List<Integer> valores) {
        return valores.stream().mapToInt(Integer::intValue).average().orElse(0.0);
    }

    public static double promedioLong(List<Long> valores) {
        return valores.stream().mapToLong(Long::longValue).average().orElse(0.0);
    }

    public static int minimo(List<Integer> valores) {
        return valores.stream().mapToInt(Integer::intValue).min().orElse(0);
    }

    public static double desvioEstandar(List<Integer> valores) {
        double media = promedio(valores);
        double sumaCuadrados = 0.0;
        for (int v : valores) {
            sumaCuadrados += Math.pow(v - media, 2);
        }
        return Math.sqrt(sumaCuadrados / valores.size());
    }
}
