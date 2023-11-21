package utn.backend.grupo128.alquileres.application.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CrearAlquilerRequest {

    @NotBlank
    @Size(min = 2, max = 50, message = "Debe ser entre 2 y 50 caracteres.")
    private String idCliente;

    @NotNull
        private Integer estacionRetiroId;

}
