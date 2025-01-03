package utn.backend.grupo128.estaciones.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity(name = "Estaciones")
@Data
public class Estacion {

    @Id
    @GeneratedValue(generator = "estacion_sequence")
    @TableGenerator(name = "estacion_sequence",
    table = "sqlite_sequence",
    pkColumnName = "name",
    valueColumnName = "seq",
    pkColumnValue = "ESTACIONES",
    allocationSize = 1
    )
    private Integer id;

    @Embedded
    private NombreEstacion nombre;

    @Column(name = "FECHA_HORA_CREACION")
    private LocalDateTime fechaHoraCreacion;

    @Embedded
    private Coordenada coordenada;

    public Estacion(){

    }

    public Estacion(NombreEstacion nombre, LocalDateTime fechaHoraCreacion, Coordenada coordenada) {
        this.nombre = nombre;
        this.fechaHoraCreacion = fechaHoraCreacion;
        this.coordenada = coordenada;
    }



}
