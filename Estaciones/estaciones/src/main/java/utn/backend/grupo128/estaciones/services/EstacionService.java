package utn.backend.grupo128.estaciones.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.backend.grupo128.estaciones.models.Estacion;
import utn.backend.grupo128.estaciones.repositories.EstacionRepository;

import java.util.Arrays;
import java.util.List;


@Service
public class EstacionService {

    @Autowired
    private final EstacionRepository repository;

    public EstacionService(EstacionRepository repository) {
        this.repository = repository;
    }


    public Iterable<Estacion> getAll() {
        return repository.findAll();
    }

    public List<Estacion> findByEstacionWithLatitudAndLongitud(Float latitud, Float longitud) {
        return repository.findByLatitudAndLongitud(latitud,longitud);
    }
}
