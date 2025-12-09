package net.etfbl.pisio.incidentservice.services;

import jakarta.validation.Valid;
import net.etfbl.pisio.incidentservice.dto.IncidentRequest;
import net.etfbl.pisio.incidentservice.exceptions.DuplicateIncidentException;
import net.etfbl.pisio.incidentservice.exceptions.ResourceNotFoundException;
import net.etfbl.pisio.incidentservice.mappers.IncidentMapper;
import net.etfbl.pisio.incidentservice.models.Incident;
import net.etfbl.pisio.incidentservice.repositories.IncidentRepository;
import net.etfbl.pisio.incidentservice.repositories.IncidentSubtypeRepository;
import net.etfbl.pisio.incidentservice.repositories.IncidentTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class IncidentService {
    private final IncidentRepository incidentRepository;
    private final IncidentMapper incidentMapper;
    private final IncidentTypeRepository incidentTypeRepository;
    private final IncidentSubtypeRepository incidentSubtypeRepository;

    public IncidentService(IncidentRepository incidentRepository, IncidentMapper incidentMapper, IncidentTypeRepository incidentTypeRepository, IncidentSubtypeRepository incidentSubtypeRepository) {
        this.incidentRepository = incidentRepository;
        this.incidentMapper = incidentMapper;
        this.incidentTypeRepository = incidentTypeRepository;
        this.incidentSubtypeRepository = incidentSubtypeRepository;
    }

    public List<Incident> getAllIncidents() {
        return incidentRepository.findAll();
    }

    public List<Incident> getAllPendingIncidents() {
        return incidentRepository.findByStatus(Incident.Status.PENDING);
    }


    public List<Incident> getAllApprovedIncidents() {
        return incidentRepository.findByStatus(Incident.Status.APPROVED);
    }

    public Incident approveIncident(Long id) {
        Incident incident = incidentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Incident not found with id " + id));
        incident.setStatus(Incident.Status.APPROVED);
        return incidentRepository.save(incident);
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


        existing.setType(incidentTypeRepository.findById(incidentRequest.getTypeId()).orElseThrow(() -> new ResourceNotFoundException("Incident type not found with id " + incidentRequest.getTypeId())));
        existing.setSubtype(incidentSubtypeRepository.findById(incidentRequest.getSubtypeId()).orElse(null));
        existing.setLatitude(incidentRequest.getLatitude());
        existing.setLongitude(incidentRequest.getLongitude());
        existing.setDescription(incidentRequest.getDescription());
        existing.setImageUrl(incidentRequest.getImageUrl());

        return incidentRepository.save(existing);
    }

    public List<Incident> filterIncidents(String type,
                                          String subtype,
                                          LocalDateTime fromDate,
                                          Double minLat, Double maxLat,
                                          Double minLon, Double maxLon) {

        return incidentRepository.findAll().stream()
                // filter by type if provided
                .filter(i -> type == null ||
                        (i.getType() != null && i.getType().getName().equalsIgnoreCase(type)))
                // filter by subtype if provided
                .filter(i -> subtype == null ||
                        (i.getSubtype() != null && i.getSubtype().getName().equalsIgnoreCase(subtype)))
                // filter by time if provided
                .filter(i -> fromDate == null || i.getCreatedAt().isAfter(fromDate))
                // filter by location bounding box if provided
                .filter(i -> minLat == null || maxLat == null || minLon == null || maxLon == null ||
                        (i.getLatitude() >= minLat && i.getLatitude() <= maxLat &&
                                i.getLongitude() >= minLon && i.getLongitude() <= maxLon))
                .toList();
    }


}
