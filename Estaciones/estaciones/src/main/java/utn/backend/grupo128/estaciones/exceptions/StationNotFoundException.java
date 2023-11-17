package utn.backend.grupo128.estaciones.exceptions;

public class StationNotFoundException extends RuntimeException {
    public StationNotFoundException(String message) {
        super(message);
    }
}