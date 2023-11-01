package utn.backend.grupo128.alquileres.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.backend.grupo128.alquileres.models.Alquiler;
import utn.backend.grupo128.alquileres.repositories.AlquilerRepository;

import java.util.List;

@Service
public class AlquilerService {

    @Autowired
    private final AlquilerRepository repository;

    public AlquilerService(AlquilerRepository repository) {
        this.repository = repository;
    }


    public List<Alquiler> getAll() {
        return repository.findAll();
    }
}
