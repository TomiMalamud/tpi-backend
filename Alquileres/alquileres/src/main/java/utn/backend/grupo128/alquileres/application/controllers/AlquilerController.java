package utn.backend.grupo128.alquileres.application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.backend.grupo128.alquileres.application.request.CrearAlquilerRequest;
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
}
