package com.evolutivo.model;

import java.util.List;

public record ResultadoEjecucion(
        int costoFinal,
        List<Integer> ruta,
        long tiempoMs,
        List<Integer> historicoCostos,
        int generacionesEjecutadas,
        String nombreGenerador,
        String nombreSeleccion,
        String nombreCruce,
        String nombreMutacion,
        String nombreSobrevivientes,
        String nombreCorte
) {
}
