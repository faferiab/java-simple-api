package ai.smartassets.challenge.dto;

import ai.smartassets.challenge.model.Brand;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Schema(name = "BrandRequest")
public class BrandDto extends Brand {
}
