package com.evolutivo.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    public Properties propiedades;

    public ConfigManager() {
        propiedades = new Properties();
        File archivo = new File("config.properties");

        try (InputStream input = new FileInputStream(archivo)) {
            propiedades.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo de configuracion", e);
        }
    }

    public int getPoblacion() {
        return Integer.parseInt(propiedades.getProperty("poblacion"));
    }

    public float getProbCruce() {
        return Float.parseFloat(propiedades.getProperty("probCruce"));
    }

    public int getMaxGen() {
        return Integer.parseInt(propiedades.getProperty("maxGen"));
    }

    public float getProbMutacion() {
        return Float.parseFloat(propiedades.getProperty("probMutacion"));
    }

    // --- Nuevos: selección de componentes ---
    public String getGeneradorPoblacion() {
        return propiedades.getProperty("generadorPoblacion", "aleatoria");
    }

    public String getSeleccionPadres() {
        return propiedades.getProperty("seleccionPadres", "torneo");
    }

    public int getTamTorneo() {
        return Integer.parseInt(propiedades.getProperty("tamTorneo", "3"));
    }

    public String getCruce() {
        return propiedades.getProperty("cruce", "erx");
    }

    public String getMutacion() {
        return propiedades.getProperty("mutacion", "swap");
    }

    public String getSobrevivientes() {
        return propiedades.getProperty("sobrevivientes", "generacional");
    }

    public int getCantElite() {
        return Integer.parseInt(propiedades.getProperty("cantElite", "2"));
    }

    public String getCondicionCorte() {
        return propiedades.getProperty("condicionCorte", "generaciones");
    }

    public int getGeneracionesSinMejora() {
        return Integer.parseInt(propiedades.getProperty("generacionesSinMejora", "50"));
    }
}
