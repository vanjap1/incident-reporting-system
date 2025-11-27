package net.etfbl.pisio.incidentservice.models;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "incident_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IncidentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

}
