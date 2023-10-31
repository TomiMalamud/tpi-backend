package utn.backend.grupo128.estaciones.application.response;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import utn.backend.grupo128.estaciones.models.Estacion;

import java.time.LocalDateTime;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EstacionWithLatitudAndLongitudResponse {
    private Integer id;
    private String nombre;
    private LocalDateTime fechaHoraCreacion;
    private Float latitud;
    private Float longitud;

    public static EstacionWithLatitudAndLongitudResponse from(Estacion aEstacion) {
        return EstacionWithLatitudAndLongitudResponse.builder()
                .id(aEstacion.getId())
                .nombre(aEstacion.getNombre())
                .fechaHoraCreacion(aEstacion.getFechaHoraCreacion())
                .latitud(aEstacion.getLatitud())
                .longitud(aEstacion.getLongitud())
                .build();
    }





}
