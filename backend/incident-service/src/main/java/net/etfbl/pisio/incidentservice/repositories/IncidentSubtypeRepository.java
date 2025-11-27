package net.etfbl.pisio.incidentservice.repositories;

import net.etfbl.pisio.incidentservice.models.IncidentSubtype;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncidentSubtypeRepository extends JpaRepository<IncidentSubtype, Long> {
    List<IncidentSubtype> findByTypeId(Long typeId);
}
