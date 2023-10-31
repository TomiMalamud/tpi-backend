package utn.backend.grupo128.alquileres.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity(name = "Tarifas")
@Data
public class Tarifa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;

    @Column(name = "TIPO_TARIFA")
    private Integer tipoTarifa;

    @Column(name = "DEFINICION")
    private String definicion;

    @Column(name = "DIA_SEMANA")
    private Integer diaSemana;

    @Column(name = "DIA_MES")
    private Integer diaMes;

    @Column(name = "MES")
    private Integer mes;

    @Column(name = "ANIO")
    private Integer anio;

    @Column(name = "MONTO_FIJO_ALQUILER")
    private Float montoFijoAlquiler;

    @Column(name = "MONTO_MINUTO_FRACCION")
    private Float montoMinutoFraccion;

    @Column(name = "MONTO_KM")
    private Float montoKm;

    @Column(name = "MONTO_HORA")
    private Float montoHora;

}
