package utn.backend.grupo128.alquileres.application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.backend.grupo128.alquileres.application.ResponseHandler;
import utn.backend.grupo128.alquileres.application.request.CrearAlquilerRequest;
import utn.backend.grupo128.alquileres.application.response.AlquilerResponse;
import utn.backend.grupo128.alquileres.models.Alquiler;
import utn.backend.grupo128.alquileres.services.AlquilerService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/alquileres")
public class AlquilerController {

    private final AlquilerService service;

    @Autowired
    public AlquilerController(AlquilerService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Object> getAllAlquiler() {
        List<Alquiler> alquileres = service.getAll();
        List<AlquilerResponse> responses = alquileres.stream()
                .map(AlquilerResponse::from)
                .collect(Collectors.toList());
        return ResponseHandler.success(responses);
    }

    @PostMapping("/iniciar")
    public ResponseEntity<Object> iniciarAlquiler(@RequestBody CrearAlquilerRequest alquilerRequest) {
        Alquiler alquiler = service.iniciarAlquiler(alquilerRequest);
        AlquilerResponse response = AlquilerResponse.from(alquiler);
        return ResponseHandler.success(response);
    }

    @PutMapping("/{idAlquiler}/terminar")
    public ResponseEntity<Object> terminarAlquiler(@PathVariable Integer idAlquiler,
                                                   @RequestParam Integer idEstacionDevolucion,
                                                   @RequestParam(required = false) String monedaDestino) {
        Alquiler alquiler = service.finalizarAlquiler(idAlquiler, idEstacionDevolucion, monedaDestino);
        AlquilerResponse response = AlquilerResponse.from(alquiler);
        return ResponseHandler.success(response);
    }


    @GetMapping(params = {"minId", "maxId"})
    public ResponseEntity<Object> alquilerCercano(@RequestParam Integer minId, @RequestParam Integer maxId) {
        try {
            List<Alquiler> listaAlquiler = service.findByAlquilerCercano(minId, maxId);
            List<AlquilerResponse> alquilerResponse = listaAlquiler.stream()
                    .map(AlquilerResponse::from)
                    .collect(Collectors.toList());
            return ResponseHandler.success(alquilerResponse);
        } catch (IllegalArgumentException e) {
            return ResponseHandler.badRequest(e.getMessage());
        } catch (Exception e) {
            return ResponseHandler.internalError();
        }
    }

}
