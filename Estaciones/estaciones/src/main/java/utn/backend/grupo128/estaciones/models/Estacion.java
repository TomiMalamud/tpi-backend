package utn.backend.grupo128.estaciones.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity(name = "Estaciones")
@Data
public class Estacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;

    @Embedded
    private NombreEstacion nombre;

    @Column(name = "FECHA_HORA_CREACION")
    private LocalDateTime fechaHoraCreacion;

    @Embedded
    private Coordenada coordenada;


    public Estacion(String nombre, LocalDateTime fechaHoraCreacion, Double latitud, Double longitud) {
        this.nombre = nombre;
        this.fechaHoraCreacion = fechaHoraCreacion;
    }
}
