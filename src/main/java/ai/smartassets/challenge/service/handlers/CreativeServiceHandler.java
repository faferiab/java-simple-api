package ai.smartassets.challenge.service.handlers;

import ai.smartassets.challenge.entity.CreativeEntity;
import ai.smartassets.challenge.model.Creative;

public class CreativeServiceHandler {
    private CreativeServiceHandler() {}
    public static Creative convert(CreativeEntity entity){
        Creative creative = new Creative();
        creative.setCreativeId(entity.getCreativeId());
        creative.setCreativeUrl(entity.getCreativeUrl());
        creative.setDescription(entity.getDescription());
        creative.setName(entity.getName());
        return creative;
    }

    public static CreativeEntity convert(Creative model, String brandId, String campaignId){
        CreativeEntity entity = new CreativeEntity();
        entity.setCreativeId(model.getCreativeId());
        entity.setCreativeUrl(model.getCreativeUrl());
        entity.setDescription(model.getDescription());
        entity.setName(model.getName());
        entity.setBrandId(brandId);
        entity.setCampaignId(campaignId);
        return entity;
    }
}
