import java.io.FileNotFoundException;
import java.util.Random;

public class Main {
    public static void imprimir(int[][] m) {
        int limit = m.length;
        for (int[] ints : m) {
            for (int j = 0; j < limit; j++) {
                System.out.print(ints[j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        int [][] m = CargadorATSP.cargar("assets/br17.atsp");
        int cant_ciudades = m.length;
        int[] camino = new int[cant_ciudades];
        Random r = new Random();
        for (int i = 0; i < cant_ciudades; i++) {
            camino[i] = r.nextInt(cant_ciudades);
        }
        Individuo individuo = new Individuo(camino);
        individuo.evaluar(m);
        System.out.println("el costo es: "+individuo.getCosto());
        System.out.println("el fitness es: "+individuo.getFitness());
    }
}