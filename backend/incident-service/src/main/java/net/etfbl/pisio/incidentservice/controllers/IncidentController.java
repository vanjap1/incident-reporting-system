package net.etfbl.pisio.incidentservice.controllers;

import net.etfbl.pisio.incidentservice.dto.IncidentRequest;
import net.etfbl.pisio.incidentservice.models.Incident;
import net.etfbl.pisio.incidentservice.services.IncidentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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


    @GetMapping("/status/pending")
    public ResponseEntity<?> getAllPendingIncidents() {
        return ResponseEntity.ok(incidentService.getAllPendingIncidents());
    }

    @GetMapping("/status/approved")
    public ResponseEntity<?> getAllApprovedIncidents() {
        return ResponseEntity.ok(incidentService.getAllApprovedIncidents());
    }


    @PutMapping("/{id}/approve")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<?> approveIncident(@PathVariable Long id) {
        Incident approved = incidentService.approveIncident(id);
        return ResponseEntity.ok(approved);
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

    @GetMapping("/filter")
    public ResponseEntity<?> getFilteredIncidents(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String subtype,
            @RequestParam(required = false) Integer days,
            @RequestParam(required = false) Double minLat,
            @RequestParam(required = false) Double maxLat,
            @RequestParam(required = false) Double minLon,
            @RequestParam(required = false) Double maxLon) {

        LocalDateTime fromDate = null;
        if (days != null) {
            fromDate = LocalDateTime.now().minusDays(days);
        }

        List<Incident> results = incidentService.filterIncidents(
                type, subtype, fromDate, minLat, maxLat, minLon, maxLon
        );

        return ResponseEntity.ok(results);
    }


}
