package utn.backend.grupo128.alquileres.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity(name = "Alquileres")
@Data
public class Alquiler {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;

    @Column(name = "ID_CLIENTE")
    private Integer idCliente;

    @Column(name = "ESTADO")
    private Integer estado;

    @Column(name = "ESTACION_RETIRO")
    private Integer estacionRetiro;

    @Column(name = "ESTACION_DEVOLUCION")
    private Integer estacionDevolucion;

    @Column(name = "FECHA_HORA_RETIRO")
    private LocalDateTime fechaHoraRetiro;

    @Column(name = "FECHA_HORA_DEVOLUCION")
    private LocalDateTime fechaHoraDevolucion;

    @Column(name = "MONTO")
    private Float monto;

    @Column(name = "ID_TARIFA")
    private Integer idTarifa;


}
