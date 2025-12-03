package net.etfbl.pisio.apigateway.security.oauth2;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.etfbl.pisio.apigateway.dto.JwtResponse;
import net.etfbl.pisio.apigateway.dto.UserDto;
import net.etfbl.pisio.apigateway.security.JwtUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class OAuth2LoginSuccessHandler implements ServerAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public OAuth2LoginSuccessHandler(JwtUtil jwtUtil, WebClient.Builder builder) {
        this.jwtUtil = jwtUtil;
        this.webClient = builder.baseUrl("http://user-service").build();
    }

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange,
                                              Authentication authentication) {
        ServerWebExchange exchange = webFilterExchange.getExchange();

        String email = ((DefaultOAuth2User) authentication.getPrincipal()).getAttribute("email");

        return webClient.post()
                .uri("/internal/users/find-or-create")
                .bodyValue(email)
                .retrieve()
                .bodyToMono(UserDto.class)
                .map(user -> jwtUtil.generateToken(user))
                .flatMap(token -> {
                    JwtResponse jwtResponse = new JwtResponse(token);
                    byte[] bytes;
                    try {
                        bytes = objectMapper.writeValueAsBytes(jwtResponse);
                    } catch (Exception e) {
                        return Mono.error(e);
                    }

                    exchange.getResponse().getHeaders()
                            .setContentType(org.springframework.http.MediaType.APPLICATION_JSON);

                    return exchange.getResponse()
                            .writeWith(Mono.just(exchange.getResponse()
                                    .bufferFactory().wrap(bytes)));
                });
    }
}
