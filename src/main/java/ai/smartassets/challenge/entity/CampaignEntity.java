package ai.smartassets.challenge.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.querydsl.core.annotations.QueryEntity;

import ai.smartassets.challenge.model.Campaign;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Document(collection = "campaigns")
@QueryEntity
public class CampaignEntity extends Campaign {
    @Id
    private String campaignId;
    private String brandId;
}
