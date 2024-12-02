package com.openclassrooms.P3_Full_Stack_portail_locataire.configs;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfiguration(
            JwtAuthenticationFilter jwtAuthenticationFilter,
            AuthenticationProvider authenticationProvider
    ) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Désactiver CSRF (inutile pour une API REST avec JWT)
                .csrf(csrf -> csrf.disable())

                // Définir les autorisations d'accès
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("auth/login","rental/list","/api/rental/detail/**").permitAll()
                        // Autoriser les endpoints sous "/auth/**" sans authentification
                        .anyRequest().authenticated()           // Toutes les autres requêtes nécessitent une authentification
                )

                // Gestion des sessions : API sans état (stateless)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Ajouter le AuthenticationProvider configuré
                .authenticationProvider(authenticationProvider)

                // Ajouter le filtre JWT avant UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")               // URL à appeler pour se déconnecter
                        .logoutSuccessHandler((request, response, authentication) -> {
                            // Configurer la réponse HTTP
                            response.setContentType("application/json");
                            response.setCharacterEncoding("UTF-8");
                            response.setStatus(HttpServletResponse.SC_OK);

                            // Écrire un message JSON dans le corps de la réponse
                            response.getWriter().write("{\"message\": \"Vous êtes bien déconnecté.\"}");
                            response.getWriter().flush();
                        })
                        .invalidateHttpSession(true)            // Invalider la session
                        .clearAuthentication(true)             // Effacer l'authentification actuelle
                        .deleteCookies("JSESSIONID")           // Supprimer les cookies de session, si nécessaires
                );
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:8090"));
        configuration.setAllowedMethods(List.of("GET","POST"));
        configuration.setAllowedHeaders(List.of("Authorization","Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**",configuration);

        return source;
    }
}
