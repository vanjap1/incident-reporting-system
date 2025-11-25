package net.etfbl.pisio.incidentservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class IncidentRequest {

    @NotNull(message = "Latitude is required")
    private Double latitude;

    @NotNull(message = "Longitude is required")
    private Double longitude;

    @NotBlank(message = "Type is required")
    @Size(max = 50, message = "Type must be at most 50 characters")
    private String type;

    @Size(max = 50, message = "Subtype must be at most 50 characters")
    private String subtype;

    @Size(max = 255, message = "Description must be at most 255 characters")
    private String description;

    @Pattern(
            regexp = "^(http|https)://.*$",
            message = "Image URL must be a valid URL starting with http or https"
    )
    private String imageUrl;
}
