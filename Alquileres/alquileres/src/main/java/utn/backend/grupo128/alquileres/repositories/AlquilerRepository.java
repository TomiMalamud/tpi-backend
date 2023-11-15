package utn.backend.grupo128.alquileres.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import utn.backend.grupo128.alquileres.models.Alquiler;

public interface AlquilerRepository extends JpaRepository<Alquiler, Integer> {
}
