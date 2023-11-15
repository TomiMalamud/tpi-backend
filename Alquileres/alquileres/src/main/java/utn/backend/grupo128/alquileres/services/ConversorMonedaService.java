package utn.backend.grupo128.alquileres.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ConversorMonedaService {

    private final WebClient.Builder webClientBuilder;

    @Autowired
    public ConversorMonedaService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

     public Mono<Float> convertirMoneda(Float monto, String monedaDestino) {
        return Mono.just(monto);
        /*
        return webClientBuilder.build().post()
                .uri("http://34.82.105.125:8080/convertir")
                .bodyValue(new ConversionRequest(monto, monedaDestino))
                .retrieve()
                .bodyToMono(ConversionResponse.class)
                .map(response -> response.getImporte());
    */
    }

    static class ConversionRequest {
        // ... campos y métodos ...
    }

    static class ConversionResponse {
        // ... campos y métodos ...
    }
}

