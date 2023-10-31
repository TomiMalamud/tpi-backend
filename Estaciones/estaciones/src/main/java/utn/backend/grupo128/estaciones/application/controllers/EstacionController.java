package utn.backend.grupo128.estaciones.application.controllers;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.backend.grupo128.estaciones.application.ResponseHandler;
import utn.backend.grupo128.estaciones.application.response.EstacionWithLatitudAndLongitudResponse;
import utn.backend.grupo128.estaciones.models.Estacion;
import utn.backend.grupo128.estaciones.services.EstacionService;

@RestController
@RequestMapping("/api/estaciones")
public class EstacionController {

    @Autowired
    private final EstacionService service;


    public EstacionController(EstacionService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Iterable<Estacion>> getAllEstacion() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping(params = { "latitud", "longitud" })
    public ResponseEntity<Object> estacionWithLatitudAndLongitud(@RequestParam Float latitud, @RequestParam Float longitud) {

        try {
            val stores = service.findByEstacionWithLatitudAndLongitud(latitud, longitud)
                    .stream()
                    .map(EstacionWithLatitudAndLongitudResponse::from)
                    .toList();

            return ResponseHandler.success(stores);
        } catch (IllegalArgumentException e) {
            return ResponseHandler.badRequest(e.getMessage());
        } catch (Exception e) {
            return ResponseHandler.internalError();
        }
    }



}
