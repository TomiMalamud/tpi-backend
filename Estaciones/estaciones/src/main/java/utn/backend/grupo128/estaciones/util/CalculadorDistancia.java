package utn.backend.grupo128.estaciones.util;

public class CalculadorDistancia {

    private static final double GRADOS_A_KILOMETROS = 110.0;

    public static double calcularDistanciaEuclidea(double lat1, double lon1, double lat2, double lon2) {
        // Convertir grados a metros
        double x1 = lat1 * GRADOS_A_KILOMETROS;
        double y1 = lon1 * GRADOS_A_KILOMETROS;
        double x2 = lat2 * GRADOS_A_KILOMETROS;
        double y2 = lon2 * GRADOS_A_KILOMETROS;

        // Calcular distancia euclídea
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
}
