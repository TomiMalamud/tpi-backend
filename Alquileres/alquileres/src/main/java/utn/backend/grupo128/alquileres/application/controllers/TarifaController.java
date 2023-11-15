package utn.backend.grupo128.alquileres.application.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utn.backend.grupo128.alquileres.models.Tarifa;
import utn.backend.grupo128.alquileres.services.TarifaService;

import java.util.List;

@RestController
@RequestMapping("/api/tarifas")
public class TarifaController {

    @Autowired
    private final TarifaService service;

    public TarifaController(TarifaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Tarifa>> getAllTarifa() {
        return ResponseEntity.ok(service.getAll());
    }

}
