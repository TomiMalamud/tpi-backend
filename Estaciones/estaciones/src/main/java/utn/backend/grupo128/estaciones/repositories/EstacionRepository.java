package utn.backend.grupo128.estaciones.repositories;

import org.springframework.data.repository.CrudRepository;
import utn.backend.grupo128.estaciones.models.Estacion;

import java.util.List;

public interface EstacionRepository extends CrudRepository<Estacion, Long> {

    List<Estacion> findByLatitudAndLongitud(Float latitud, Float longitud);

}
