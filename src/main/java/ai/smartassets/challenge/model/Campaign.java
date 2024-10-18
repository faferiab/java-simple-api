package ai.smartassets.challenge.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class Campaign {
    @Schema(description = "Campaign unique UUID")
    private String campaignId;
    @Schema(description = "Campaign name")
    private String name;
    @Schema(description = "Campaign description")
    private String description;
}
