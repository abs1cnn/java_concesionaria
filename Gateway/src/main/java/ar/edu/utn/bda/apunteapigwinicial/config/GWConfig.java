package ar.edu.utn.bda.apunteapigwinicial.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;

@Configuration
@EnableWebFluxSecurity
public class GWConfig {
    @Bean
    public RouteLocator configurarRutas(RouteLocatorBuilder builder,
                                        @Value("${url-microservicio-cliente}") String uriCliente,
                                        @Value("${url-microservicio-empleado}") String uriEmpleado,
                                        @Value("${url-microservicio-vehiculo}") String uriVehiculo,
                                        @Value("${url-microservicio-prueba}") String uriPrueba) {
        return builder.routes()
                // Ruteo al Microservicio de cliente
                .route(p -> p.path("/cliente/**").uri(uriCliente))
                // Ruteo al Microservicio de empleado
                .route(p -> p.path("/empleado/**").uri(uriEmpleado))
                // Ruteo al Microservicio de vehículos
                .route(p -> p.path("/vehiculo/**").uri(uriVehiculo))
                // Ruteo al Microservicio de prueba
                .route(p -> p.path("/prueba/**").uri(uriPrueba))
                // Ruteo al Microservicio de posición
                .route(p -> p.path("/posicion/**").uri(uriPrueba))
                .build();
    }
}


    /*@Value("${apunte-api-gw-kempes.url-microservicio-cliente}")
    private String uriCliente;

    @Value("${apunte-api-gw-kempes.url-microservicio-empleado}")
    private String uriEmpleado;

    @Value("${apunte-api-gw-kempes.url-microservicio-vehiculo}")
    private String uriVehiculo;

    @Value("${apunte-api-gw-kempes.url-microservicio-prueba}")
    private String uriPrueba;

    @Bean
    public RouteLocator configurarRutas(RouteLocatorBuilder builder) {
        return builder.routes()
                // Ruteo al Microservicio de cliente
                .route(p -> p.path("/api/cliente/**").uri(uriCliente))
                // Ruteo al Microservicio de empleado
                .route(p -> p.path("/api/empleado/**").uri(uriEmpleado))
                // Ruteo al Microservicio de vehículos
                .route(p -> p.path("/api/vehiculo/**").uri(uriVehiculo))
                // Ruteo al Microservicio de prueba
                .route(p -> p.path("/api/prueba/**").uri(uriPrueba))
                .build();
    }*/
