package ai.smartassets.challenge.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.querydsl.core.annotations.QueryEntity;

import ai.smartassets.challenge.model.Brand;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Document(collection = "brands")
@QueryEntity
public class BrandEntity extends Brand {
    @Id
    private String brandId;
}
