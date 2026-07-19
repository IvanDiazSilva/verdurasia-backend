package com.verdurasia.config;

import org.springframework.context.annotation.Configuration;

/**
 * Configuración MVC general.
 *
 * CORS se gestiona en {@link SecurityConfig#corsConfigurationSource()} para que
 * Spring Security lo aplique antes de los filtros de autenticación.
 */
@Configuration
public class WebConfig {
    // CORS delegado a SecurityConfig.corsConfigurationSource()
}
