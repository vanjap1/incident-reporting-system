package net.etfbl.pisio.incidentservice.repositories;

import net.etfbl.pisio.incidentservice.models.Incident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, Long> {


    // Time filter
    List<Incident> findByCreatedAtAfter(LocalDateTime fromDate);

    // Type filter
    List<Incident> findByType_Name(String typeName);

    // Subtype filter
    List<Incident> findBySubtype_Name(String subtypeName);

    // Combined type + subtype
    List<Incident> findByType_NameAndSubtype_Name(String typeName, String subtypeName);

    // Location bounding box
    List<Incident> findByLatitudeBetweenAndLongitudeBetween(
            double minLat, double maxLat,
            double minLon, double maxLon);

    List<Incident> findByStatus(Incident.Status status);
}
