package utn.backend.grupo128.estaciones.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;


@Embeddable
public final class NombreEstacion {


    @Column(name = "NOMBRE")
    private String nombre;

    public NombreEstacion() {

    }

    public NombreEstacion(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
