package ai.smartassets.challenge.service.handlers;

import ai.smartassets.challenge.entity.BrandEntity;
import ai.smartassets.challenge.model.Brand;

public class BrandServiceHandler {
    private BrandServiceHandler() {
    }

    public static Brand convert(BrandEntity entity) {
        Brand model = new Brand();
        model.setBrandId(entity.getBrandId());
        model.setDescription(entity.getDescription());
        model.setName(entity.getName());
        return model;
    }

    public static BrandEntity convert(Brand model) {
        BrandEntity entity = new BrandEntity();
        entity.setBrandId(model.getBrandId());
        entity.setDescription(model.getDescription());
        entity.setName(model.getName());
        return entity;
    }
}
