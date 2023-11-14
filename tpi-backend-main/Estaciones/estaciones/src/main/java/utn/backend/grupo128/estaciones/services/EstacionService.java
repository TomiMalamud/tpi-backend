package utn.backend.grupo128.estaciones.services;

import jakarta.transaction.Transactional;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.backend.grupo128.estaciones.models.Coordenada;
import utn.backend.grupo128.estaciones.models.Estacion;
import utn.backend.grupo128.estaciones.models.NombreEstacion;
import utn.backend.grupo128.estaciones.repositories.EstacionRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;


@Service
public class EstacionService {

    private final EstacionRepository repository;

    public EstacionService(EstacionRepository repository) {
        this.repository = repository;
    }


    public List<Estacion> getAll() {
        return repository.findAll();
    }


    public Estacion findByEstacionCercana(Double latitud, Double longitud) {
        Iterable<Estacion> listaEstaciones = getAll();

        Coordenada coordenadaOrigen = new Coordenada(latitud, longitud);

        Estacion estacionCercana = new Estacion();
        var distanciaCercana = -1.0;

        for (Estacion estacion : listaEstaciones) {

            var distancia = estacion.getCoordenada().calularDistancia(coordenadaOrigen);

            if (distanciaCercana == -1.0) {
                distanciaCercana = distancia;
                estacionCercana = estacion;
            } else if (distancia < distanciaCercana) {
                distanciaCercana = distancia;
                estacionCercana = estacion;
            }
        }

        return estacionCercana;
    }

    @Transactional
    public Estacion create(NombreEstacion nombre, LocalDateTime fechaHoraCreacion, Coordenada coordenada) {
        Estacion estacion = new Estacion(nombre, fechaHoraCreacion, coordenada);
        return repository.save(estacion);
    }
}