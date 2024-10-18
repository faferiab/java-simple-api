package ai.smartassets.challenge.dto;

import ai.smartassets.challenge.model.Campaign;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Schema(name = "CampaignRequest")
public class CampaignDto extends Campaign {
    @Schema(description = "Brand unique UUID related to the campaign")
    private String brandId;
}
