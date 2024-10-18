package ai.smartassets.challenge.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.dsl.BooleanExpression;

import ai.smartassets.challenge.entity.CampaignEntity;
import ai.smartassets.challenge.entity.QCampaignEntity;
import ai.smartassets.challenge.exception.NotFoundFieldException;
import ai.smartassets.challenge.model.Campaign;
import ai.smartassets.challenge.repository.BrandRepository;
import ai.smartassets.challenge.repository.CampaignRepository;
import ai.smartassets.challenge.service.handlers.CampaignServiceHandler;

@Service
public class CampaignService {
    private final int MIN_SIZE_PAGE = 10;
    private final int MAX_SIZE_PAGE = 50;
    private CampaignRepository repository;
    private BrandRepository brandRepository;

    public CampaignService(CampaignRepository campaignRepository, BrandRepository brandRepository) {
        this.repository = campaignRepository;
        this.brandRepository = brandRepository;
    }

    public Campaign createCampaign(Campaign campaign, String brandId) {
        validateBrandIdCampaignId(brandId);
        campaign.setCampaignId(UUID.randomUUID().toString());
        CampaignEntity entity = CampaignServiceHandler.convert(campaign, brandId);
        entity = repository.save(entity);
        return CampaignServiceHandler.convert(entity);
    }

    public List<Campaign> listCampaignsByBrand(String brandId, int page, int size) {
        int actualSize = Math.min(MAX_SIZE_PAGE, Math.max(size, MIN_SIZE_PAGE));
        validateBrandIdCampaignId(brandId);
        QCampaignEntity campaign = QCampaignEntity.campaignEntity;
        BooleanExpression brandMatches = campaign.brandId.eq(brandId);
        List<Campaign> response = repository
                .findAll(brandMatches, PageRequest.of(page, actualSize))
                .map(CampaignServiceHandler::convert)
                .toList();
        return response;
    }

    private void validateBrandIdCampaignId(String brandId) {
        if (!brandRepository.existsById(brandId)) {
            throw new NotFoundFieldException("Brand not exists");
        }
    }
}
