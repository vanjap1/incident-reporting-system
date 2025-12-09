package net.etfbl.pisio.apigateway.security;

import net.etfbl.pisio.apigateway.security.oauth2.DomainRestrictingOAuth2UserService;
import net.etfbl.pisio.apigateway.security.oauth2.OAuth2LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final ReactiveOAuth2AuthorizedClientService domainUserService;
    private final OAuth2LoginSuccessHandler loginSuccessHandler;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,
                          ReactiveOAuth2AuthorizedClientService domainUserService,
                          OAuth2LoginSuccessHandler loginSuccessHandler) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.domainUserService = domainUserService;
        this.loginSuccessHandler = loginSuccessHandler;
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        // Public endpoints
                        .pathMatchers("/api/auth/**", "/oauth2/**", "/login/oauth2/**", "/api/incidents/status/approved",
                                "/api/incidents/filter").permitAll()
                        // Everything else requires authentication
                        .anyExchange().authenticated()
                )
                // Configure OAuth2 login only for the explicit login endpoints
                .oauth2Login(oauth2 -> oauth2
                        .authenticationSuccessHandler(loginSuccessHandler)
                        .authorizedClientService(domainUserService)
                )
                // Important: disable default login page redirect
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint((exchange, ex) ->
                                Mono.fromRunnable(() -> {
                                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                                })
                        )
                )
                .addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }


}
