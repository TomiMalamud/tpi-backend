package utn.backend.grupo128.alquileres.services;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.backend.grupo128.alquileres.application.request.CrearAlquilerRequest;
import utn.backend.grupo128.alquileres.models.Alquiler;
import utn.backend.grupo128.alquileres.repositories.AlquilerRepository;

import java.time.LocalDateTime;
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

    @Transactional
    public Alquiler iniciarAlquiler(CrearAlquilerRequest alquilerRequest) {
        Alquiler alquiler = new Alquiler();
        alquiler.setIdCliente(alquilerRequest.getIdCliente());
        alquiler.setEstacionRetiro(alquilerRequest.getEstacionRetiroId());
        alquiler.setEstado(1);
        alquiler.setFechaHoraRetiro(LocalDateTime.now());

        return repository.save(alquiler);
    }


    public List<Alquiler> findByAlquilerCercano(Integer minId, Integer maxId) {
        List<Alquiler> listaAlquileres = getAll().stream().filter(alquiler -> alquiler.getId() >= minId
        && alquiler.getId() <= maxId).toList();

       return listaAlquileres;
    }
}

