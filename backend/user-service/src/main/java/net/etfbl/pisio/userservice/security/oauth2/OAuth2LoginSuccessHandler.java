package net.etfbl.pisio.userservice.security.oauth2;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.etfbl.pisio.userservice.dto.JwtResponse;
import net.etfbl.pisio.userservice.exceptions.ResourceNotFoundException;
import net.etfbl.pisio.userservice.models.User;
import net.etfbl.pisio.userservice.repositories.UserRepository;
import net.etfbl.pisio.userservice.security.JwtUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public OAuth2LoginSuccessHandler(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        String email = ((DefaultOAuth2User) authentication.getPrincipal()).getAttribute("email");
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Such user doesn't exist"));

        String token = jwtUtil.generateToken(user);

        // Option 1: redirect frontend with token in query param
        //response.sendRedirect("http://host.docker.internal:3000?token=" + token);

        // Option 2: return JSON directly
        response.setContentType("application/json");

        response.getWriter().write(new ObjectMapper().
                writeValueAsString(new JwtResponse(token)));
    }
}
