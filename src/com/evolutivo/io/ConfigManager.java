package com.evolutivo.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    public Properties propiedades;
    public ConfigManager() {
        propiedades = new Properties();
        try (InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream("config.properties")) {

            if (input == null) {
                System.out.println("Lo siento, no se pudo encontrar el archivo config.properties");
                return;
            }
            propiedades.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public String getConfig(String key) {
        return propiedades.getProperty(key);
    }
}
