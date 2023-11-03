package utn.backend.grupo128.alquileres.aplicattion.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class CrearAlquilerRequest {

    @NotBlank
    @Size(min = 2, max = 50, message = "Debe ser entre 2 y 50 caracteres.")
    private String idCliente;

    @NotNull
    private Integer estacionRetiroId;



}
