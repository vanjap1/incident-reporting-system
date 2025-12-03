package net.etfbl.pisio.incidentservice.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.etfbl.pisio.incidentservice.dto.IncidentRequest;
import net.etfbl.pisio.incidentservice.models.Incident;
import net.etfbl.pisio.incidentservice.models.IncidentSubtype;
import net.etfbl.pisio.incidentservice.models.IncidentType;
import net.etfbl.pisio.incidentservice.repositories.IncidentSubtypeRepository;
import net.etfbl.pisio.incidentservice.repositories.IncidentTypeRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class IncidentMapper {

    private final IncidentTypeRepository typeRepo;
    private final IncidentSubtypeRepository subtypeRepo;

    public IncidentMapper(IncidentTypeRepository typeRepo, IncidentSubtypeRepository subtypeRepo) {
        this.typeRepo = typeRepo;
        this.subtypeRepo = subtypeRepo;
    }

    public Incident toEntity(IncidentRequest request) {
        IncidentType type = typeRepo.findById(request.getTypeId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid type ID"));

        IncidentSubtype subtype = null;
        if (request.getSubtypeId() != null) {
            subtype = subtypeRepo.findById(request.getSubtypeId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid subtype ID"));
        }

        return Incident.builder()
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .type(type)
                .subtype(subtype)
                .description(request.getDescription())
                .imageUrl(request.getImageUrl())
                .status(Incident.Status.PENDING)
                .createdAt(LocalDateTime.now())
                .build();
    }
}

