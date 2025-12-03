package net.etfbl.pisio.incidentservice.services;

import net.etfbl.pisio.incidentservice.models.IncidentSubtype;
import net.etfbl.pisio.incidentservice.repositories.IncidentSubtypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncidentSubtypeService {
    private final IncidentSubtypeRepository repo;

    public IncidentSubtypeService(IncidentSubtypeRepository repo) {
        this.repo = repo;
    }

    public List<IncidentSubtype> findByType(Long typeId) {
        return repo.findByTypeId(typeId);
    }
}
