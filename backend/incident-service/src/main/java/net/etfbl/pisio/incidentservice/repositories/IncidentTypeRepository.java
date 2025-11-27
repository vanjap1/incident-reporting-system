package net.etfbl.pisio.incidentservice.repositories;

import net.etfbl.pisio.incidentservice.models.IncidentType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncidentTypeRepository extends JpaRepository<IncidentType, Long> {
}
