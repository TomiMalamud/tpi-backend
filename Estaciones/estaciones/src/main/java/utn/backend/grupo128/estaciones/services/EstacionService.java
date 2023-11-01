package utn.backend.grupo128.estaciones.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.backend.grupo128.estaciones.models.Coordenada;
import utn.backend.grupo128.estaciones.models.Estacion;
import utn.backend.grupo128.estaciones.repositories.EstacionRepository;

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
}
