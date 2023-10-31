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

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "FECHA_HORA_CREACION")
    private LocalDateTime fechaHoraCreacion;

    @Column(name = "LATITUD")
    private Float latitud;

    @Column(name = "LONGITUD")
    private Float longitud;




}
