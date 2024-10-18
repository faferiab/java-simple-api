package ai.smartassets.challenge.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.dsl.BooleanExpression;

import ai.smartassets.challenge.entity.CreativeEntity;
import ai.smartassets.challenge.entity.QCreativeEntity;
import ai.smartassets.challenge.exception.NotFoundFieldException;
import ai.smartassets.challenge.model.Creative;
import ai.smartassets.challenge.repository.BrandRepository;
import ai.smartassets.challenge.repository.CampaignRepository;
import ai.smartassets.challenge.repository.CreativeRepository;
import ai.smartassets.challenge.service.handlers.CreativeServiceHandler;

@Service
public class CreativeService {
    private final int MIN_SIZE_PAGE = 10;
    private final int MAX_SIZE_PAGE = 50;
    private CreativeRepository repository;
    private CampaignRepository campaignRepository;
    private BrandRepository brandRepository;

    public CreativeService(CreativeRepository creativeRepository, CampaignRepository campaignRepository,
            BrandRepository brandRepository) {
        this.repository = creativeRepository;
        this.campaignRepository = campaignRepository;
        this.brandRepository = brandRepository;
    }

    public Creative createCreative(Creative creative, String brandId, String campaignId) {
        creative.setCreativeId(UUID.randomUUID().toString());
        validateBrandIdCampaignId(brandId, campaignId);
        CreativeEntity entity = CreativeServiceHandler.convert(creative, brandId, campaignId);
        repository.save(entity);
        return creative;
    }

    public List<Creative> listCreativesByBrandCampaign(String brandId, String campaignId, int page, int size) {
        int actualSize = Math.min(MAX_SIZE_PAGE, Math.max(size, MIN_SIZE_PAGE));
        validateBrandIdCampaignId(brandId, campaignId);
        QCreativeEntity creative = QCreativeEntity.creativeEntity;
        BooleanExpression brandCampaignMatches = creative.brandId.eq(brandId)
                .and(creative.campaignId.eq(campaignId));
        List<Creative> response = repository
                .findAll(brandCampaignMatches, PageRequest.of(page, actualSize))
                .map(CreativeServiceHandler::convert)
                .toList();
        return response;
    }

    private void validateBrandIdCampaignId(String brandId, String campaignId) {
        if (!brandRepository.existsById(brandId)) {
            throw new NotFoundFieldException("Brand not exists");
        }
        if (!campaignRepository.existsById(campaignId)) {
            throw new NotFoundFieldException("Campaign not exists");
        }
    }
}
