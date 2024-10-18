package ai.smartassets.challenge.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.querydsl.core.types.Predicate;

import ai.smartassets.challenge.entity.CampaignEntity;
import ai.smartassets.challenge.exception.NotFoundFieldException;
import ai.smartassets.challenge.model.Campaign;
import ai.smartassets.challenge.repository.BrandRepository;
import ai.smartassets.challenge.repository.CampaignRepository;

@ExtendWith(MockitoExtension.class)
class CampaignServiceTest {
    @Mock
    private BrandRepository brandRepository;
    @Mock
    private CampaignRepository campaignRepository;
    @InjectMocks
    private CampaignService testService;

    @Test
    void whenCreateCampaignAndOk_thenCreateCampaign() {
        String brandId = "brandId";
        Campaign param = new Campaign();
        param.setName("CampaignName");
        param.setDescription("CampaignDesc");
        CampaignEntity entity = new CampaignEntity();
        entity.setCampaignId("CampaignId");
        entity.setBrandId(brandId);
        entity.setDescription(param.getDescription());
        entity.setName(param.getName());

        Mockito.when(brandRepository.existsById(brandId)).thenReturn(true);
        Mockito.when(campaignRepository.save(any())).thenReturn(entity);
        Campaign response = testService.createCampaign(param, brandId);
        assertNotNull(response);
        assertEquals("CampaignName", response.getName());
    }

    @Test
    void whenCreateCampaignAndBrandNotExists_thenThrowError() {
        String brandId = "brandId";
        Campaign param = new Campaign();
        param.setName("CampaignName");
        param.setDescription("CampaignDesc");

        Mockito.when(brandRepository.existsById(brandId)).thenReturn(false);
        NotFoundFieldException exception = assertThrows(NotFoundFieldException.class,
                () -> testService.createCampaign(param, brandId));
        assertEquals("Brand not exists", exception.getMessage());
    }

    @Test
    void whenListCampaignsAndOk_thenListCampaigns() {
        String brandId = "brandId";
        Mockito.when(campaignRepository.findAll(any(Predicate.class), eq(PageRequest.of(0, 50))))
                .thenReturn(new PageImpl<CampaignEntity>(List.of(new CampaignEntity(), new CampaignEntity())));

        Mockito.when(brandRepository.existsById(brandId)).thenReturn(true);
        List<Campaign> response = testService.listCampaignsByBrand(brandId, 0, 100);
        assertNotNull(response);
        assertEquals(2, response.size());
    }

    @Test
    void whenListCampaignsAndBrandNotExists_thenThrowError() {
        String brandId = "brandId";

        Mockito.when(brandRepository.existsById(brandId)).thenReturn(false);
        NotFoundFieldException exception = assertThrows(NotFoundFieldException.class,
                () -> testService.listCampaignsByBrand(brandId, 0, 100));
        assertEquals("Brand not exists", exception.getMessage());
    }
}
