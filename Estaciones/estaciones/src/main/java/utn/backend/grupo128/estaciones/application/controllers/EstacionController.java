package utn.backend.grupo128.estaciones.application.controllers;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.backend.grupo128.estaciones.application.ResponseHandler;
import utn.backend.grupo128.estaciones.application.response.EstacionResponse;
import utn.backend.grupo128.estaciones.services.EstacionService;

import java.util.List;

@RestController
@RequestMapping("/api/estaciones")
public class EstacionController {

    @Autowired
    private final EstacionService service;


    public EstacionController(EstacionService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<EstacionResponse>> getAllEstacion() {
        return ResponseEntity.ok(service.getAll().stream().map(EstacionResponse::from).toList());
    }

    @GetMapping(params = { "latitud", "longitud" })
    public ResponseEntity<Object> estacionCercana(@RequestParam Double latitud, @RequestParam Double longitud) {

        try {
            val estacion = service.findByEstacionCercana(latitud, longitud);

            EstacionResponse estacionResponse = EstacionResponse.from(estacion);

            return ResponseHandler.success(estacionResponse);
        } catch (IllegalArgumentException e) {
            return ResponseHandler.badRequest(e.getMessage());
        } catch (Exception e) {
            return ResponseHandler.internalError();
        }
    }



}
