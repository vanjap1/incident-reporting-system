package net.etfbl.pisio.incidentservice.services;

import net.etfbl.pisio.incidentservice.models.IncidentType;
import net.etfbl.pisio.incidentservice.repositories.IncidentTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncidentTypeService {
    private final IncidentTypeRepository repo;

    public IncidentTypeService(IncidentTypeRepository repo) {
        this.repo = repo;
    }

    public List<IncidentType> findAll() {
        return repo.findAll();
    }
}
