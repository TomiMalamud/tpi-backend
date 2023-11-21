package utn.backend.grupo128.alquileres.application.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import utn.backend.grupo128.alquileres.models.Tarifa;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TarifaResponse {

    Integer id;
    Integer tipoTarifa;
    String definicion;
    Integer diaSemana;
    Integer diaMes;
    Integer mes;
    Integer anio;
    Float montoFijoAlquiler;
    Float montoMinutoFraccion;
    Float montoKm;
    Float montoHora;

    public static TarifaResponse from(Tarifa tarifa) {
        return TarifaResponse.builder()
                .id(tarifa.getId())
                .tipoTarifa(tarifa.getTipoTarifa())
                .definicion(tarifa.getDefinicion())
                .diaSemana(tarifa.getDiaSemana())
                .diaMes(tarifa.getDiaMes())
                .mes(tarifa.getMes())
                .anio(tarifa.getAnio())
                .montoFijoAlquiler(tarifa.getMontoFijoAlquiler())
                .montoMinutoFraccion(tarifa.getMontoMinutoFraccion())
                .montoKm(tarifa.getMontoKm())
                .montoHora(tarifa.getMontoHora())
                .build();
    }
}
