package net.etfbl.pisio.incidentservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import net.etfbl.pisio.incidentservice.models.IncidentSubtype;
import net.etfbl.pisio.incidentservice.models.IncidentType;

@Data
public class IncidentRequest {

    @NotNull(message = "Latitude is required")
    private Double latitude;

    @NotNull(message = "Longitude is required")
    private Double longitude;

    @NotNull(message = "Type ID is required")
    private Long typeId;

    private Long subtypeId;

    @Size(max = 255, message = "Description must be at most 255 characters")
    private String description;

    @Pattern(
            regexp = "^(http|https)://.*$",
            message = "Image URL must be a valid URL starting with http or https"
    )
    private String imageUrl;
}
