package ai.smartassets.challenge.dto;

import ai.smartassets.challenge.model.Creative;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Schema(name = "CreativeRequest")
public class CreativeDto extends Creative {
    @Schema(description = "Brand unique UUID related to the creative")
    private String brandId;
    @Schema(description = "Campaign unique UUID related to the creative")
    private String campaignId;
}
