package ai.smartassets.challenge.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class Brand {
    @Schema(description = "Brand unique UUID")
    private String brandId;
    @Schema(description = "Brand name")
    private String name;
    @Schema(description = "Brand description")
    private String description;
}
