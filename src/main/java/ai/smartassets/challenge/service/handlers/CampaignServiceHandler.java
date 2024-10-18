package ai.smartassets.challenge.service.handlers;

import ai.smartassets.challenge.entity.CampaignEntity;
import ai.smartassets.challenge.model.Campaign;

public class CampaignServiceHandler {
    private CampaignServiceHandler() {
    }

    public static Campaign convert(CampaignEntity entity) {
        Campaign model = new Campaign();
        model.setCampaignId(entity.getCampaignId());
        model.setDescription(entity.getDescription());
        model.setName(entity.getName());
        return model;
    }

    public static CampaignEntity convert(Campaign model, String brandId) {
        CampaignEntity entity = new CampaignEntity();
        entity.setBrandId(brandId);
        entity.setCampaignId(model.getCampaignId());
        entity.setDescription(model.getDescription());
        entity.setName(model.getName());
        return entity;
    }
}
