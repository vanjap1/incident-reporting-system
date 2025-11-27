package net.etfbl.pisio.incidentservice.controllers;

import net.etfbl.pisio.incidentservice.models.IncidentSubtype;
import net.etfbl.pisio.incidentservice.services.IncidentSubtypeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/types/{typeId}/subtypes")
public class IncidentSubtypeController {
    private final IncidentSubtypeService service;

    public IncidentSubtypeController(IncidentSubtypeService service) {
        this.service = service;
    }

    @GetMapping
    public List<IncidentSubtype> getSubtypes(@PathVariable Long typeId) {
        return service.findByType(typeId);
    }
}
