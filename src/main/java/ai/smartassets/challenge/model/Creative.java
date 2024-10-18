package ai.smartassets.challenge.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class Creative {
    @Schema(description = "Creative unique UUID")
    private String creativeId;
    @Schema(description = "Creative name")
    private String name;
    @Schema(description = "Creative description")
    private String description;
    @Schema(description = "Creative location url")
    private String creativeUrl;
}
