package utn.backend.grupo128.estaciones.models;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public final class Coordenada {

    @Column(name = "LATITUD")
    private Double latitud;


    @Column(name = "LONGITUD")
    private Double longitud;

    public Coordenada() {

    }

    public Coordenada(Double latitud, Double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Double getLatitud() {
        return latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public Double calularDistancia(Coordenada otraCoordenada) {
        return Math.sqrt(Math.pow(getLatitud() - otraCoordenada.getLatitud(), 2) + Math.pow(getLongitud() - otraCoordenada.getLongitud(), 2));
    }
}
