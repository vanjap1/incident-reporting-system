package net.etfbl.pisio.userservice.security.oauth2;

import net.etfbl.pisio.userservice.models.User;
import net.etfbl.pisio.userservice.repositories.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DomainRestrictingOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public DomainRestrictingOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");
        if (email == null || !email.endsWith("etf.unibl.org")) {
            OAuth2Error error = new OAuth2Error("invalid_domain", "Only etf.unibl.org accounts are allowed", null);
            throw new OAuth2AuthenticationException(error);
        }

        // Check local DB
        User user = userRepository.findByEmail(email)
                .orElseGet(() -> userRepository.save(
                        new User(null,null, email, User.Role.USER,User.Provider.GOOGLE) // default role USER
                ));

        // Map DB role to authority
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole().name());

        return new DefaultOAuth2User(
                List.of(authority),
                oAuth2User.getAttributes(),
                "email" // use email as the username attribute
        );
    }
}
