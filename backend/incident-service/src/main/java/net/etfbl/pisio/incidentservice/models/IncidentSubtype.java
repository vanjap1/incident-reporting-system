package net.etfbl.pisio.incidentservice.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "incident_subtypes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IncidentSubtype {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private IncidentType type;
}
