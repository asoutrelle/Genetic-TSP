import java.util.Arrays;

public class Individuo {
    private final int[] ruta;
    private double fitness;
    private int costo;

    // Constructor para inicializar una ruta aleatoria o predefinida
    public Individuo(int[] ruta) {
        this.ruta = Arrays.copyOf(ruta, ruta.length);
        this.costo = 0;
        this.fitness = 0.0;
    }

    // Calcula el costo total y el fitness
    public void evaluar(int[][] matrizCostos) {
        this.costo = 0;
        int n = ruta.length;

        // Sumar el costo de ir de ciudad en ciudad
        for (int i = 0; i < n - 1; i++) {
            int origen = ruta[i] - 1;  // Restamos 1 porque los índices en Java empiezan en 0
            int destino = ruta[i + 1] - 1;
            this.costo += matrizCostos[origen][destino];
        }

        // Sumar el costo de volver a la ciudad de partida
        int ultimo = ruta[n - 1] - 1;
        int primero = ruta[0] - 1;
        this.costo += matrizCostos[ultimo][primero];

        // Calcular fitness (inverso del costo)
        this.fitness = 1.0 / this.costo;
    }

    // Getters y Setters...
    public double getFitness() { return fitness; }
    public int getCosto() { return costo; }
    public int[] getRuta() { return ruta; }
}