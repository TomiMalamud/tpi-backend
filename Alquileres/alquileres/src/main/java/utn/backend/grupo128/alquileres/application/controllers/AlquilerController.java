package utn.backend.grupo128.alquileres.application.controllers;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.backend.grupo128.alquileres.application.ResponseHandler;
import utn.backend.grupo128.alquileres.application.request.CrearAlquilerRequest;
import utn.backend.grupo128.alquileres.application.response.AlquilerResponse;
import utn.backend.grupo128.alquileres.models.Alquiler;
import utn.backend.grupo128.alquileres.services.AlquilerService;

import java.util.List;

@RestController
@RequestMapping("/api/alquileres")
public class AlquilerController {

    private final AlquilerService service;

    @Autowired
    public AlquilerController(AlquilerService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Alquiler>> getAllAlquiler() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping("/iniciar")
    public ResponseEntity<Alquiler> iniciarAlquiler(@RequestBody CrearAlquilerRequest alquilerRequest) {
        Alquiler alquiler = service.iniciarAlquiler(alquilerRequest);
        return ResponseEntity.ok(alquiler);
    }

    @GetMapping(params = {"idAlquiler"})
    public ResponseEntity<Object> terminarAlquiler(@RequestParam Integer idAlquiler, Integer idEstacionDevolucion) {
        Alquiler alquiler = service.finalizarAlquiler(idAlquiler, idEstacionDevolucion);
        return ResponseEntity.ok(alquiler);
    }

    @GetMapping(params = {"minId","maxId"})
    public ResponseEntity<Object> alquilerCercano (@RequestParam Integer minId, @RequestParam Integer maxId) {
        try {
            List<Alquiler> listaAlquiler = service.findByAlquilerCercano(minId, maxId);

            List<AlquilerResponse> alquilerResponse = listaAlquiler.stream().map(alquiler -> AlquilerResponse.from(alquiler)).toList();

            return ResponseHandler.success(alquilerResponse);
        } catch (IllegalArgumentException e) {
            return ResponseHandler.badRequest(e.getMessage());
        } catch (Exception e) {
            return ResponseHandler.internalError();
        }
    }
}
