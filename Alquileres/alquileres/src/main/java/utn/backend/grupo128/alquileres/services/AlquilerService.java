package utn.backend.grupo128.alquileres.services;


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


@Service
public class AlquilerService {

    @Autowired
    private final AlquilerRepository repository;
    private final TarifaRepository repositoryTarifa;

    public AlquilerService(AlquilerRepository repository, TarifaRepository tarifaRepository, TarifaRepository repositoryTarifa) {
        this.repository = repository;
        this.repositoryTarifa = repositoryTarifa;
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

    public Alquiler finalizarAlquiler(Integer idAlquiler, Integer idEstacionDevolucion) {
        List<Alquiler> alquileres = getAll();
        for (Alquiler alquiler: alquileres) {
            if (alquiler.getId() == idAlquiler) {

                //SET DEVOLUCION
                alquiler.setEstado(2);
                alquiler.setEstacionDevolucion(idEstacionDevolucion);
                alquiler.setFechaHoraDevolucion(LocalDateTime.now());

                var diaSemana = alquiler.getFechaHoraDevolucion().getDayOfWeek().getValue();
                var diaMes = alquiler.getFechaHoraDevolucion().getDayOfMonth();
                var mes = alquiler.getFechaHoraDevolucion().getMonthValue();
                var anio = alquiler.getFechaHoraDevolucion().getYear();
                Tarifa tarifaAplicada = null;
                Float montoAlquiler = 0F;

                //SET TARIFA
                List<Tarifa> tarifas = repositoryTarifa.findAll();
                for (Tarifa tarifa: tarifas) {
                    if (tarifa.getDiaSemana() == 0 && tarifa.getDiaMes() == diaMes && tarifa.getMes() == mes && tarifa.getAnio() == anio) {
                        alquiler.setIdTarifa(tarifa.getId());
                        tarifaAplicada = tarifa;
                    } else if (tarifa.getDiaSemana() == diaSemana) {
                        alquiler.setIdTarifa(tarifa.getId());
                        tarifaAplicada = tarifa;
                    }
                }

                //CALCULAR MONTO
                var tiempoDeAlquiler = Duration.between(alquiler.getFechaHoraRetiro(), alquiler.getFechaHoraDevolucion());
                //1 - Calcular monto tiempo
                if (tiempoDeAlquiler.toMinutes() > 31) {
                    //Calculo fraccionado
                    montoAlquiler = tarifaAplicada.getMontoFijoAlquiler() + tiempoDeAlquiler.toMinutes() * tarifaAplicada.getMontoMinutoFraccion();
                } else {
                    //Calculo no fraccionado
                    montoAlquiler = tarifaAplicada.getMontoFijoAlquiler() + tiempoDeAlquiler.toHours() * tarifaAplicada.getMontoMinutoFraccion();
                }

                alquiler.setMonto(montoAlquiler);

                return alquiler;
            }
        }
        return new Alquiler();
    }

    public List<Alquiler> findByAlquilerCercano(Integer minId, Integer maxId) {
        List<Alquiler> listaAlquileres = getAll().stream().filter(alquiler -> alquiler.getId() >= minId
        && alquiler.getId() <= maxId).toList();

       return listaAlquileres;
    }
}

