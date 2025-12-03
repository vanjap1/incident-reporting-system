package net.etfbl.pisio.incidentservice.controllers;

import net.etfbl.pisio.incidentservice.models.IncidentType;
import net.etfbl.pisio.incidentservice.services.IncidentTypeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/types")
public class IncidentTypeController {
    private final IncidentTypeService service;

    public IncidentTypeController(IncidentTypeService service) {
        this.service = service;
    }

    @GetMapping
    public List<IncidentType> getAllTypes() {
        return service.findAll();
    }
}
