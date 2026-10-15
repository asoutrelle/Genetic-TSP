package com.evolutivo.experimento;

public class ExperimentoConfig {
    public final String nombre;
    public final int poblacion;
    public final float probCruce;
    public final float probMutacion;
    public final String generadorPoblacion;
    public final String seleccionPadres;
    public final int tamTorneo;
    public final String cruce;
    public final String mutacion;
    public final String sobrevivientes;
    public final int cantElite;
    public final String condicionCorte;
    public final int maxGen;
    public final int generacionesSinMejora;

    private ExperimentoConfig(Builder b) {
        this.nombre = b.nombre;
        this.poblacion = b.poblacion;
        this.probCruce = b.probCruce;
        this.probMutacion = b.probMutacion;
        this.generadorPoblacion = b.generadorPoblacion;
        this.seleccionPadres = b.seleccionPadres;
        this.tamTorneo = b.tamTorneo;
        this.cruce = b.cruce;
        this.mutacion = b.mutacion;
        this.sobrevivientes = b.sobrevivientes;
        this.cantElite = b.cantElite;
        this.condicionCorte = b.condicionCorte;
        this.maxGen = b.maxGen;
        this.generacionesSinMejora = b.generacionesSinMejora;
    }

    public static Builder builder(String nombre) {
        return new Builder(nombre);
    }

    /** Los valores por defecto son el baseline actual del proyecto (config.properties). */
    public static class Builder {
        private final String nombre;
        private int poblacion = 100;
        private float probCruce = 0.95f;
        private float probMutacion = 0.10f;
        private String generadorPoblacion = "aleatoria";
        private String seleccionPadres = "torneo";
        private int tamTorneo = 3;
        private String cruce = "erx";
        private String mutacion = "swap";
        private String sobrevivientes = "elitismo";
        private int cantElite = 4;
        private String condicionCorte = "generaciones";
        private int maxGen = 5000;
        private int generacionesSinMejora = 500;

        private Builder(String nombre) {
            this.nombre = nombre;
        }

        public Builder poblacion(int v) { this.poblacion = v; return this; }
        public Builder probCruce(float v) { this.probCruce = v; return this; }
        public Builder probMutacion(float v) { this.probMutacion = v; return this; }
        public Builder generadorPoblacion(String v) { this.generadorPoblacion = v; return this; }
        public Builder seleccionPadres(String v) { this.seleccionPadres = v; return this; }
        public Builder tamTorneo(int v) { this.tamTorneo = v; return this; }
        public Builder cruce(String v) { this.cruce = v; return this; }
        public Builder mutacion(String v) { this.mutacion = v; return this; }
        public Builder sobrevivientes(String v) { this.sobrevivientes = v; return this; }
        public Builder cantElite(int v) { this.cantElite = v; return this; }
        public Builder condicionCorte(String v) { this.condicionCorte = v; return this; }
        public Builder maxGen(int v) { this.maxGen = v; return this; }
        public Builder generacionesSinMejora(int v) { this.generacionesSinMejora = v; return this; }

        public ExperimentoConfig build() {
            return new ExperimentoConfig(this);
        }
    }
}
