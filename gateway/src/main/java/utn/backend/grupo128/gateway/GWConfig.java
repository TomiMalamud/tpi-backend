package utn.backend.grupo128.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GWConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder,
                                           @Value("${gateway.estaciones.uri}") String uriEstaciones,
                                           @Value("${gateway.alquileres.uri}") String uriAlquileres) {
        return builder.routes()
                // Ruteo al Microservicio de Estaciones
                .route(p -> p.path("/api/estaciones/**").uri(uriEstaciones))
                // Ruteo al Microservicio de Alquileres
                .route(p -> p.path("/api/alquileres/**").uri(uriAlquileres))
                // Ruteo específico para /api/tarifas del Microservicio de Alquileres
                .route(p -> p.path("/api/tarifas/**").uri(uriAlquileres))
                .build();
    }
}
