package net.etfbl.pisio.apigateway.security.oauth2;

import net.etfbl.pisio.apigateway.dto.UserDto;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.ReactiveOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class DomainRestrictingOAuth2UserService
        implements ReactiveOAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final WebClient webClient;

    public DomainRestrictingOAuth2UserService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://user-service").build();
    }

    @Override
    public Mono<OAuth2User> loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // Delegate to default reactive loader
        ReactiveOAuth2UserService<OAuth2UserRequest, OAuth2User> delegate =
                new org.springframework.security.oauth2.client.userinfo.DefaultReactiveOAuth2UserService();

        return delegate.loadUser(userRequest)
                .flatMap(oAuth2User -> {
                    String email = oAuth2User.getAttribute("email");
                    if (email == null || !email.endsWith("etf.unibl.org")) {
                        return Mono.error(new OAuth2AuthenticationException(
                                new OAuth2Error("invalid_domain", "Only etf.unibl.org accounts are allowed", null)
                        ));
                    }

                    // Call user-service reactively
                    return webClient.post()
                            .uri("/internal/users/find-or-create")
                            .bodyValue(email)
                            .retrieve()
                            .bodyToMono(UserDto.class)
                            .map(user -> {
                                SimpleGrantedAuthority authority =
                                        new SimpleGrantedAuthority("ROLE_" + user.getRole());

                                return new DefaultOAuth2User(
                                        List.of(authority),
                                        oAuth2User.getAttributes(),
                                        "email"
                                );
                            });
                });
    }
}
