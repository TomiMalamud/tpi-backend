package utn.backend.grupo128.estaciones.services;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import utn.backend.grupo128.estaciones.exceptions.StationNotFoundException;
import utn.backend.grupo128.estaciones.models.Coordenada;
import utn.backend.grupo128.estaciones.models.Estacion;
import utn.backend.grupo128.estaciones.models.NombreEstacion;
import utn.backend.grupo128.estaciones.repositories.EstacionRepository;
import utn.backend.grupo128.estaciones.util.CalculadorDistancia;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class EstacionService {

    private final EstacionRepository estacionRepository;

    public EstacionService(EstacionRepository repository) {
        this.estacionRepository = repository;
    }


    public List<Estacion> getAll() {
        return estacionRepository.findAll();
    }


    public Estacion findByEstacionCercana(Double latitud, Double longitud) {
        List<Estacion> listaEstaciones = getAll();
        Estacion estacionCercana = null;
        double distanciaCercana = Double.MAX_VALUE;

        for (Estacion estacion : listaEstaciones) {
            double distancia = CalculadorDistancia.calcularDistanciaEuclidea(
                    latitud, longitud,
                    estacion.getCoordenada().getLatitud(), estacion.getCoordenada().getLongitud());

            if (distancia < distanciaCercana) {
                distanciaCercana = distancia;
                estacionCercana = estacion;
            }
        }

        if (estacionCercana == null) {
            throw new StationNotFoundException("No se encontraron estaciones.");
        }

        return estacionCercana;
    }


    @Transactional
    public Estacion create(NombreEstacion nombre, LocalDateTime fechaHoraCreacion, Coordenada coordenada) {
        Estacion estacion = new Estacion(nombre, fechaHoraCreacion, coordenada);
        return estacionRepository.save(estacion);
    }

    public double getDistanciaEntreEstaciones(long estacionId1, long estacionId2) {
        Estacion station1 = estacionRepository.findById(estacionId1).orElseThrow(() -> new StationNotFoundException("Estación " + estacionId1 + " no encontrada"));
        Estacion station2 = estacionRepository.findById(estacionId2).orElseThrow(() -> new StationNotFoundException("Estación " + estacionId2 + " no encontrada"));

        return CalculadorDistancia.calcularDistanciaEuclidea(
                station1.getCoordenada().getLatitud(), station1.getCoordenada().getLongitud(),
                station2.getCoordenada().getLatitud(), station2.getCoordenada().getLongitud()
        );
    }

}
