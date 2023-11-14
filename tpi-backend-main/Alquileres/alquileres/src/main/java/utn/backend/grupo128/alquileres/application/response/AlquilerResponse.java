package utn.backend.grupo128.alquileres.application.response;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import utn.backend.grupo128.alquileres.models.Alquiler;

import java.time.LocalDateTime;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AlquilerResponse {

    private Integer id;
    private String idCliente;
    private Integer estado;
    private Integer estacionRetiro;
    private Integer estacionDevolucion;
    private LocalDateTime fechaHoraRetiro;
    private LocalDateTime fechaHoraDevolucion;
    private Float monto;
    private Integer idTarifa;

    public static AlquilerResponse from(Alquiler aAlquiler) {
        return AlquilerResponse.builder()
                .id(aAlquiler.getId())
                .idCliente(aAlquiler.getIdCliente())
                .estado(aAlquiler.getEstado())
                .estacionRetiro(aAlquiler.getEstacionRetiro())
                .estacionDevolucion(aAlquiler.getEstacionDevolucion())
                .fechaHoraRetiro(aAlquiler.getFechaHoraRetiro())
                .fechaHoraDevolucion(aAlquiler.getFechaHoraDevolucion())
                .monto(aAlquiler.getMonto())
                .idTarifa(aAlquiler.getIdTarifa())
                .build();
    }

}
