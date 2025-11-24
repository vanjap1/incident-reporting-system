package net.etfbl.pisio.userservice.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.etfbl.pisio.userservice.dto.UserRequest;
import net.etfbl.pisio.userservice.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private final ObjectMapper objectMapper;

    public UserMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public User toEntity(UserRequest request) {
        return objectMapper.convertValue(request, User.class);
    }
}
