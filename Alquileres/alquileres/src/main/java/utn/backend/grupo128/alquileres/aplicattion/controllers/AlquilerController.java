package utn.backend.grupo128.alquileres.aplicattion.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.backend.grupo128.alquileres.models.Alquiler;
import utn.backend.grupo128.alquileres.services.AlquilerService;

import java.util.List;

@RestController
@RequestMapping("/api/alquileres")
public class AlquilerController {

    @Autowired

    private final AlquilerService service;


    public AlquilerController(AlquilerService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Alquiler>> getAllAlquiler() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping
    public ResponseEntity<Alquiler> createAlquiler(@RequestBody Alquiler alquiler) {
        return ResponseEntity.ok(service.create(alquiler));
    }


}
