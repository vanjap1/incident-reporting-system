package net.etfbl.pisio.incidentservice.repositories;

import net.etfbl.pisio.incidentservice.models.Incident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, Long> {

}
