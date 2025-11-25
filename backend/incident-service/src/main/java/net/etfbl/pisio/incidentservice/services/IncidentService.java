package net.etfbl.pisio.incidentservice.services;

import jakarta.validation.Valid;
import net.etfbl.pisio.incidentservice.dto.IncidentRequest;
import net.etfbl.pisio.incidentservice.exceptions.DuplicateIncidentException;
import net.etfbl.pisio.incidentservice.exceptions.ResourceNotFoundException;
import net.etfbl.pisio.incidentservice.mappers.IncidentMapper;
import net.etfbl.pisio.incidentservice.models.Incident;
import net.etfbl.pisio.incidentservice.repositories.IncidentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@Service
public class IncidentService {
    private final IncidentRepository incidentRepository;
    private final IncidentMapper incidentMapper;

    public IncidentService(IncidentRepository incidentRepository, IncidentMapper incidentMapper) {
        this.incidentRepository = incidentRepository;
        this.incidentMapper = incidentMapper;
    }


    public List<Incident> getAllIncidents() {
        return incidentRepository.findAll();
    }

    public Incident getIncidentById(Long id) {
        return incidentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Incident not found with id " + id));
    }

    public Incident createIncident(@Valid @RequestBody IncidentRequest incidentRequest) {
        Incident incident = incidentMapper.toEntity(incidentRequest);
        return incidentRepository.save(incident);
    }

    public void deleteIncident(Long id) {
        Incident existing = incidentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Incident not found with id " + id));
        incidentRepository.delete(existing);
    }

    public Incident updateIncident(Long id, IncidentRequest incidentRequest) {
        Incident existing = incidentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Incident not found with id " + id));

        existing.setType(incidentRequest.getType());
        existing.setSubtype(incidentRequest.getSubtype());
        existing.setLatitude(incidentRequest.getLatitude());
        existing.setLongitude(incidentRequest.getLongitude());
        existing.setDescription(incidentRequest.getDescription());
        existing.setImageUrl(incidentRequest.getImageUrl());

        return incidentRepository.save(existing);
    }
}
