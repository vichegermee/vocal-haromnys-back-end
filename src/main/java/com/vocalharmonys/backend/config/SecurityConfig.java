package com.vocalharmonys.backend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vocalharmonys.backend.exception.ErrorResponse;
import com.vocalharmonys.backend.security.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Wires together everything under {@code security/}: which endpoints need a
 * valid JWT, which don't, and how a request that's missing one gets a clean
 * 401 JSON body instead of Spring's default HTML/redirect.
 *
 * Route rules, in order (first match wins):
 *  - the Swagger UI and the OpenAPI spec it's generated from — no token needed,
 *    same reasoning as the public showcase endpoints below (it only describes
 *    the API, it doesn't expose any data by itself).
 *  - POST /api/auth/login and the public read-only showcase endpoints (choristers,
 *    events, gallery, news, partners, cds, home-banners, about-photos) and public form submissions
 *    (donations/checkout-sessions, cd-orders/checkout-sessions, reservations,
 *    join-applications) — no token needed. Reading back a just-placed order's
 *    summary (GET /api/cd-orders/by-session/**) is also public — it's keyed
 *    by the unguessable Stripe session id, not the order's sequential id.
 *  - POST /api/webhooks/stripe — no JWT either, but not "public" in the same
 *    sense: Stripe authenticates itself via the Stripe-Signature header,
 *    verified in StripeWebhookController, not a bearer token.
 *  - everything else — must carry a valid {@code Authorization: Bearer <token>}
 *    header. That covers the répertoire, "who am I", every write to the showcase
 *    content, and listing submitted forms back (including GET /api/donations and
 *    GET /api/cd-orders, which now also carry each order's real payment status).
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final ObjectMapper objectMapper;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, ObjectMapper objectMapper) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.objectMapper = objectMapper;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CorsConfig corsConfig) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfig.corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        ).permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/choristers/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/events/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/gallery/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/news/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/partners/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/cds/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/home-banners/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/about-photos/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/donations/checkout-sessions").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/reservations").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/join-applications").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/cd-orders/checkout-sessions").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/cd-orders/by-session/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/webhooks/stripe").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(handling -> handling.authenticationEntryPoint(this::onAuthenticationFailure))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    private void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        response.setStatus(401);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(
                ErrorResponse.of(401, "Authentification requise.")));
    }
}
