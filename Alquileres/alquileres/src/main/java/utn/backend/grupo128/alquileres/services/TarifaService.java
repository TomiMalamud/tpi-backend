package utn.backend.grupo128.alquileres.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.backend.grupo128.alquileres.models.Tarifa;
import utn.backend.grupo128.alquileres.repositories.TarifaRepository;

import java.util.List;

@Service
public class TarifaService {

    @Autowired
    private final TarifaRepository repository;

    public TarifaService(TarifaRepository repository) {
        this.repository = repository;
    }

    public List<Tarifa> getAll() {
        return repository.findAll();
    }
}
