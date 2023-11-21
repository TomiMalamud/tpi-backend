package utn.backend.grupo128.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtGrantedAuthoritiesConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
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

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) throws
            Exception {
        http.authorizeExchange(exchanges -> exchanges

                                .pathMatchers("/api/alquileres/**")
                                .hasRole("CLIENTE")
                                .pathMatchers("/api/estaciones/**")
                                .hasRole("ADMINISTRADOR")
                                .pathMatchers("/api/tarifas/**")
                                .permitAll()
                                .anyExchange()
                                .authenticated()
                ).oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtDecoder(jwtDecoder())))
                .csrf(ServerHttpSecurity.CsrfSpec::disable);
        return http.build();
    }
    @Bean
    public ReactiveJwtAuthenticationConverter jwtAuthenticationConverter() {
        var jwtAuthenticationConverter = new ReactiveJwtAuthenticationConverter();
        var grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");
        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new ReactiveJwtGrantedAuthoritiesConverterAdapter(grantedAuthoritiesConverter));
        return jwtAuthenticationConverter;
    }
    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        String jwkSetUri = "https://labsys.frc.utn.edu.ar/aim/realms/backend-tps/protocol/openid-connect/certs";
        return NimbusReactiveJwtDecoder.withJwkSetUri(jwkSetUri).build();
    }

}
