package utn.backend.grupo128.alquileres.application.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utn.backend.grupo128.alquileres.application.response.TarifaResponse;
import utn.backend.grupo128.alquileres.models.Tarifa;
import utn.backend.grupo128.alquileres.services.TarifaService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tarifas")
public class TarifaController {

    private final TarifaService service;

    @Autowired
    public TarifaController(TarifaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<TarifaResponse>> getAllTarifa() {
        List<Tarifa> tarifas = service.getAll();
        List<TarifaResponse> responses = tarifas.stream()
                .map(TarifaResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
}
