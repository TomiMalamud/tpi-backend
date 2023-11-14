package utn.backend.grupo128.estaciones.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import utn.backend.grupo128.estaciones.models.Estacion;

import java.util.List;

@Repository
public interface EstacionRepository extends JpaRepository<Estacion, Long> {

@Query("Select e From Estaciones e where e.coordenada.latitud = ?1 and e.coordenada.longitud=?2")
    List<Estacion> findByCoordenada(Double latitud, Double longitud);

}
