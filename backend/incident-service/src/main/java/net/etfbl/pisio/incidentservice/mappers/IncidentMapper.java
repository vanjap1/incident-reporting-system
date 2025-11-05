package net.etfbl.pisio.incidentservice.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.etfbl.pisio.incidentservice.dto.IncidentRequest;
import net.etfbl.pisio.incidentservice.models.Incident;
import org.springframework.stereotype.Component;

@Component
public class IncidentMapper {

    private final ObjectMapper objectMapper;

    public IncidentMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Incident toEntity(IncidentRequest request) {
        return objectMapper.convertValue(request, Incident.class);
    }

}
