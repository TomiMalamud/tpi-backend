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

    Integer id;
    String idCliente;
    Integer estado;
    Integer estacionRetiro;
    Integer estacionDevolucion;
    LocalDateTime fechaHoraRetiro;
    LocalDateTime fechaHoraDevolucion;
    Float monto;
    Integer idTarifa;

    public static AlquilerResponse from(Alquiler unAlquiler) {
        return AlquilerResponse.builder()
                .id(unAlquiler.getId())
                .idCliente(unAlquiler.getIdCliente())
                .estado(unAlquiler.getEstado())
                .estacionRetiro(unAlquiler.getEstacionRetiro())
                .estacionDevolucion(unAlquiler.getEstacionDevolucion())
                .fechaHoraRetiro(unAlquiler.getFechaHoraRetiro())
                .fechaHoraDevolucion(unAlquiler.getFechaHoraDevolucion())
                .monto(unAlquiler.getMonto())
                .idTarifa(unAlquiler.getIdTarifa())
                .build();
    }

}
