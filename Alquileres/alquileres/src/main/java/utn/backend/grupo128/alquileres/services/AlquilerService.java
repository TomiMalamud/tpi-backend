package utn.backend.grupo128.alquileres.services;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.backend.grupo128.alquileres.application.request.CrearAlquilerRequest;
import utn.backend.grupo128.alquileres.models.Alquiler;
import utn.backend.grupo128.alquileres.models.Tarifa;
import utn.backend.grupo128.alquileres.repositories.AlquilerRepository;
import utn.backend.grupo128.alquileres.repositories.TarifaRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class AlquilerService {

    @Autowired
    private final AlquilerRepository alquilerRepository;
    private final TarifaRepository tarifaRepository;

    public AlquilerService(AlquilerRepository repository, TarifaRepository tarifaRepository, TarifaRepository repositoryTarifa) {
        this.alquilerRepository = repository;
        this.tarifaRepository = repositoryTarifa;
    }


    public List<Alquiler> getAll() {
        return alquilerRepository.findAll();
    }

    @Transactional
    public Alquiler iniciarAlquiler(CrearAlquilerRequest alquilerRequest) {
        Alquiler alquiler = new Alquiler();
        alquiler.setIdCliente(alquilerRequest.getIdCliente());
        alquiler.setEstacionRetiro(alquilerRequest.getEstacionRetiroId());
        alquiler.setEstado(1);
        alquiler.setFechaHoraRetiro(LocalDateTime.now());

        return alquilerRepository.save(alquiler);
    }

    @Transactional
    public Alquiler finalizarAlquiler(Integer idAlquiler, Integer idEstacionDevolucion) {
        Optional<Alquiler> alquilerOptional = alquilerRepository.findById(idAlquiler);

        if (alquilerOptional.isPresent()) {
            Alquiler alquiler = alquilerOptional.get();

            // SET DEVOLUCION
            alquiler.setEstado(2); // 2 representa el estado 'finalizado'
            alquiler.setEstacionDevolucion(idEstacionDevolucion);
            alquiler.setFechaHoraDevolucion(LocalDateTime.now());

            // Encuentra la tarifa aplicable
            Tarifa tarifaAplicada = encontrarTarifaAplicable(alquiler);
            alquiler.setIdTarifa(tarifaAplicada.getId());

            // CALCULAR MONTO
            Float montoAlquiler = calcularMontoAlquiler(alquiler, tarifaAplicada);
            alquiler.setMonto(montoAlquiler);

            // Guarda el alquiler actualizado en la base de datos
            return alquilerRepository.save(alquiler);
        }

        throw new EntityNotFoundException("Alquiler no encontrado con ID: " + idAlquiler);
    }

    private Tarifa encontrarTarifaAplicable(Alquiler alquiler) {
        var diaSemana = alquiler.getFechaHoraDevolucion().getDayOfWeek().getValue();
        var diaMes = alquiler.getFechaHoraDevolucion().getDayOfMonth();
        var mes = alquiler.getFechaHoraDevolucion().getMonthValue();
        var anio = alquiler.getFechaHoraDevolucion().getYear();
        Tarifa tarifaAplicada = null;

        // SET TARIFA
        List<Tarifa> tarifas = tarifaRepository.findAll();
        for (Tarifa tarifa : tarifas) {
            if ((tarifa.getDiaSemana() == 0 && tarifa.getDiaMes() == diaMes && tarifa.getMes() == mes && tarifa.getAnio() == anio) ||
                    tarifa.getDiaSemana() == diaSemana) {
                tarifaAplicada = tarifa;
                break;
            }
        }

        if (tarifaAplicada == null) {
            throw new EntityNotFoundException("Tarifa aplicable no encontrada para la fecha de devolución.");
        }

        return tarifaAplicada;
    }

    private Float calcularMontoAlquiler(Alquiler alquiler, Tarifa tarifaAplicada) {
        var tiempoDeAlquiler = Duration.between(alquiler.getFechaHoraRetiro(), alquiler.getFechaHoraDevolucion());

        if (tiempoDeAlquiler.toMinutes() <= 30) {
            // Cálculo para menos de o igual a 30 minutos
            return tarifaAplicada.getMontoFijoAlquiler() + tarifaAplicada.getMontoMinutoFraccion() * tiempoDeAlquiler.toMinutes();
        } else {
            // Cálculo para más de 30 minutos
            return tarifaAplicada.getMontoFijoAlquiler() + tarifaAplicada.getMontoHora() * tiempoDeAlquiler.toHours();
        }
    }

    public List<Alquiler> findByAlquilerCercano(Integer minId, Integer maxId) {
        List<Alquiler> listaAlquileres = getAll().stream().filter(alquiler -> alquiler.getId() >= minId
        && alquiler.getId() <= maxId).toList();

       return listaAlquileres;
    }
}

