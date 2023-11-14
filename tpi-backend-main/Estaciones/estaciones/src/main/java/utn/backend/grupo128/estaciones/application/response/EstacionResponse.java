package utn.backend.grupo128.estaciones.application.response;


import jdk.jshell.Snippet;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import utn.backend.grupo128.estaciones.models.Estacion;

import java.time.LocalDateTime;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EstacionResponse {
    private Integer id;
    private String nombre;
    private LocalDateTime fechaHoraCreacion;
    private Double latitud;
    private Double longitud;


    public static EstacionResponse from(Estacion aEstacion) {
        return EstacionResponse.builder()
                .id(aEstacion.getId())
                .nombre(aEstacion.getNombre().getNombre())
                .fechaHoraCreacion(aEstacion.getFechaHoraCreacion())
                .latitud(aEstacion.getCoordenada().getLatitud())
                .longitud(aEstacion.getCoordenada().getLongitud())
                .build();
    }


}
