package net.etfbl.pisio.incidentservice.controllers;

import net.etfbl.pisio.incidentservice.dto.IncidentRequest;
import net.etfbl.pisio.incidentservice.exceptions.ResourceNotFoundException;
import net.etfbl.pisio.incidentservice.models.Incident;
import net.etfbl.pisio.incidentservice.services.IncidentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incidents")
public class IncidentController {
    private final IncidentService incidentService;

    public IncidentController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    @GetMapping
    public ResponseEntity<?> getAllIncidents() {
        return ResponseEntity.ok(incidentService.getAllIncidents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getIncidentById(@PathVariable Long id) {
        return ResponseEntity.ok(incidentService.getIncidentById(id));
    }

    @PostMapping
    public ResponseEntity<?> createIncident(@RequestBody IncidentRequest incident) {
        Incident saved = incidentService.createIncident(incident);
        return ResponseEntity.status(201).body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteIncident(@PathVariable Long id) {
        incidentService.deleteIncident(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateIncident(@PathVariable Long id, @RequestBody IncidentRequest incident) {
        Incident updated = incidentService.updateIncident(id, incident);
        return ResponseEntity.ok(updated);
    }

}
