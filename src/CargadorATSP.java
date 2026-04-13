import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CargadorATSP {

    public static int[][] cargar(String rutaArchivo) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(rutaArchivo));
        int dimension = 0;
        int[][] matrizCostos = null;

        while (scanner.hasNextLine()) {
            String linea = scanner.nextLine().trim();

            if (linea.startsWith("DIMENSION")) {
                String[] partes = linea.split(":");
                dimension = Integer.parseInt(partes[1].trim());
                matrizCostos = new int[dimension][dimension];
            }
            else if (linea.equals("EDGE_WEIGHT_SECTION")) {
                break;
            }
        }

        if (matrizCostos != null) {
            for (int i = 0; i < dimension; i++) {
                for (int j = 0; j < dimension; j++) {
                    if (scanner.hasNextInt()) {
                        matrizCostos[i][j] = scanner.nextInt();
                    }
                }
            }
        }

        scanner.close();
        return matrizCostos;
    }
}