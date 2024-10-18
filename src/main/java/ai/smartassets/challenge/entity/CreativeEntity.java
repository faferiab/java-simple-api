package ai.smartassets.challenge.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.querydsl.core.annotations.QueryEntity;

import ai.smartassets.challenge.model.Creative;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Document(collection = "creatives")
@QueryEntity
public class CreativeEntity extends Creative {
    @Id
    private String creativeId;
    private String brandId;
    private String campaignId;
}
