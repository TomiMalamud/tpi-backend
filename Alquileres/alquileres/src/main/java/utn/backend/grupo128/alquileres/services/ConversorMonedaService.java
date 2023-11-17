package utn.backend.grupo128.alquileres.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ConversorMonedaService {

    private final RestTemplate restTemplate;

    public ConversorMonedaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Float convertirMoneda(String monedaDestino, Float importe) {
        String url = "http://34.82.105.125:8080/convertir";
        String requestJson = String.format("{\"moneda_destino\":\"%s\",\"importe\":%f}", monedaDestino, importe);

        ConversionResponse response = restTemplate.postForObject(url, requestJson, ConversionResponse.class);
        return response != null ? response.getImporte() : null;
    }

    private static class ConversionResponse {
        private Float importe;

        public Float getImporte() {
            return importe;
        }

        public void setImporte(Float importe) {
            this.importe = importe;
        }
    }
}
