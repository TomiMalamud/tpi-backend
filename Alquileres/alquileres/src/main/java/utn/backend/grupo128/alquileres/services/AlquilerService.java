package utn.backend.grupo128.alquileres.services;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import utn.backend.grupo128.alquileres.application.request.CrearAlquilerRequest;
import utn.backend.grupo128.alquileres.models.Alquiler;
import utn.backend.grupo128.alquileres.models.Tarifa;
import utn.backend.grupo128.alquileres.repositories.AlquilerRepository;
import utn.backend.grupo128.alquileres.repositories.TarifaRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class AlquilerService {

    private final AlquilerRepository alquilerRepository;
    private final TarifaRepository tarifaRepository;
    private final RestTemplate restTemplate;
    @Value("${gateway.base-url}")
    private String gatewayBaseUrl;
    private final ConversorMonedaService conversorMonedaService;

    public AlquilerService(AlquilerRepository alquilerRepository,
                           TarifaRepository tarifaRepository,
                           RestTemplate restTemplate, ConversorMonedaService conversorMonedaService) {
        this.alquilerRepository = alquilerRepository;
        this.tarifaRepository = tarifaRepository;
        this.restTemplate = restTemplate;
        this.conversorMonedaService=conversorMonedaService;
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
    public Alquiler finalizarAlquiler(Integer idAlquiler, Integer idEstacionDevolucion, String monedaDestino) {
        Alquiler alquiler = alquilerRepository.findById(idAlquiler)
                .orElseThrow(() -> new EntityNotFoundException("Alquiler no encontrado con ID: " + idAlquiler));

        // SET DEVOLUCION
        alquiler.setEstado(2); // 2 representa el estado 'finalizado'
        alquiler.setEstacionDevolucion(idEstacionDevolucion);
        alquiler.setFechaHoraDevolucion(LocalDateTime.now());

        // Encuentra la tarifa aplicable
        Tarifa tarifaAplicada = encontrarTarifaAplicable(alquiler);
        alquiler.setIdTarifa(tarifaAplicada.getId());

        // Obtener distancia euclídea entre estaciones
        double distancia = obtenerDistanciaDeEstacionService(alquiler.getEstacionRetiro(), idEstacionDevolucion);

        // CALCULAR MONTO
        Float montoAlquiler = calcularMontoAlquiler(alquiler, tarifaAplicada, distancia);
        alquiler.setMonto(montoAlquiler);

        if (monedaDestino != null && !monedaDestino.isEmpty()) {
            Float montoConvertido = conversorMonedaService.convertirMoneda(monedaDestino, montoAlquiler);
            alquiler.setMonto(montoConvertido);
        }

        // Guarda el alquiler actualizado en la base de datos
        return alquilerRepository.save(alquiler);
    }


    private double obtenerDistanciaDeEstacionService(long estacionId1, long estacionId2) {
        String url = gatewayBaseUrl + "/api/estaciones/distancia?estacionId1={id1}&estacionId2={id2}";
        try {
            Double distancia = restTemplate.getForObject(url, Double.class, estacionId1, estacionId2);
            if (distancia == null) {
                throw new RuntimeException("La respuesta del servicio de EstacionService fue null");
            }
            return distancia;
        } catch (HttpClientErrorException ex) {
            throw new RuntimeException("Error al obtener distancia desde EstacionService", ex);
        }
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

    private Float calcularMontoAlquiler(Alquiler alquiler, Tarifa tarifaAplicada, double distancia) {
        Duration tiempoDeAlquiler = Duration.between(alquiler.getFechaHoraRetiro(), alquiler.getFechaHoraDevolucion());
        long minutosTotales = tiempoDeAlquiler.toMinutes();
        float montoBase;

        if (minutosTotales <= 30) {
            // Cálculo para 30 minutos o menos
            montoBase = tarifaAplicada.getMontoFijoAlquiler() + tarifaAplicada.getMontoMinutoFraccion() * minutosTotales;
        } else {
            // Cálculo para más de 30 minutos, redondeando hacia arriba las horas
            long horasTotales = (minutosTotales + 59) / 60; // Redondeo hacia arriba
            montoBase = tarifaAplicada.getMontoFijoAlquiler() + tarifaAplicada.getMontoHora() * horasTotales;
        }

        // Costo adicional por km
        float costoAdicionalPorKm = tarifaAplicada.getMontoKm() * (float) distancia;
        return montoBase + costoAdicionalPorKm;
    }

    public List<Alquiler> findByAlquilerCercano(Integer minId, Integer maxId) {

        return getAll().stream().filter(alquiler -> alquiler.getId() >= minId
                && alquiler.getId() <= maxId).toList();
    }
}

