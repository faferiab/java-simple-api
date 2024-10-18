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

import ai.smartassets.challenge.entity.CreativeEntity;
import ai.smartassets.challenge.exception.NotFoundFieldException;
import ai.smartassets.challenge.model.Creative;
import ai.smartassets.challenge.repository.BrandRepository;
import ai.smartassets.challenge.repository.CampaignRepository;
import ai.smartassets.challenge.repository.CreativeRepository;

@ExtendWith(MockitoExtension.class)
class CreativeServiceTest {
    @Mock
    private BrandRepository brandRepository;
    @Mock
    private CampaignRepository campaignRepository;
    @Mock
    private CreativeRepository creativeRepository;
    @InjectMocks
    private CreativeService testService;

    @Test
    void whenCreateCreativeAndOk_thenCreateCreative() {
        String brandId = "brandId";
        String campaignId = "campaignId";
        Creative param = new Creative();
        param.setName("CreativeName");
        param.setDescription("CreativeDesc");
        param.setCreativeUrl("CreativeUrl");
        CreativeEntity entity = new CreativeEntity();
        entity.setCampaignId("CampaignId");
        entity.setBrandId(brandId);
        entity.setCampaignId(campaignId);
        entity.setDescription(param.getDescription());
        entity.setName(param.getName());
        entity.setCreativeUrl(param.getCreativeUrl());

        Mockito.when(creativeRepository.save(any())).thenReturn(entity);
        Mockito.when(brandRepository.existsById(brandId)).thenReturn(true);
        Mockito.when(campaignRepository.existsById(campaignId)).thenReturn(true);
        Creative response = testService.createCreative(param, brandId, campaignId);
        assertNotNull(response);
        assertEquals("CreativeName", response.getName());
    }

    @Test
    void whenCreateCreativeAndBrandNotExists_thenThrowError() {
        String brandId = "brandId";
        String campaignId = "campaignId";
        Creative param = new Creative();
        param.setName("CreativeName");
        param.setDescription("CreativeDesc");
        param.setCreativeUrl("CreativeUrl");

        Mockito.when(brandRepository.existsById(brandId)).thenReturn(false);
        NotFoundFieldException exception = assertThrows(NotFoundFieldException.class,
                () -> testService.createCreative(param, brandId, campaignId));
        assertEquals("Brand not exists", exception.getMessage());
    }

    @Test
    void whenCreateCreativeAndCampaignNotExists_thenThrowError() {
        String brandId = "brandId";
        String campaignId = "campaignId";
        Creative param = new Creative();
        param.setName("CreativeName");
        param.setDescription("CreativeDesc");
        param.setCreativeUrl("CreativeUrl");

        Mockito.when(brandRepository.existsById(brandId)).thenReturn(true);
        Mockito.when(campaignRepository.existsById(campaignId)).thenReturn(false);
        NotFoundFieldException exception = assertThrows(NotFoundFieldException.class,
                () -> testService.createCreative(param, brandId, campaignId));
        assertEquals("Campaign not exists", exception.getMessage());
    }

    @Test
    void whenListCreativesAndOk_thenListCreatives() {
        String brandId = "brandId";
        String campaignId = "campaignId";

        Mockito.when(creativeRepository.findAll(any(Predicate.class), eq(PageRequest.of(0, 50))))
                .thenReturn(new PageImpl<CreativeEntity>(List.of(new CreativeEntity(), new CreativeEntity())));
        Mockito.when(brandRepository.existsById(brandId)).thenReturn(true);
        Mockito.when(campaignRepository.existsById(campaignId)).thenReturn(true);
        List<Creative> response = testService.listCreativesByBrandCampaign(brandId, campaignId, 0, 100);
        assertNotNull(response);
        assertEquals(2, response.size());
    }

    @Test
    void whenListCreativesAndBrandNotExists_thenThrowError() {
        String brandId = "brandId";
        String campaignId = "campaignId";

        Mockito.when(brandRepository.existsById(brandId)).thenReturn(false);
        NotFoundFieldException exception = assertThrows(NotFoundFieldException.class,
                () -> testService.listCreativesByBrandCampaign(brandId, campaignId, 0, 100));
        assertEquals("Brand not exists", exception.getMessage());
    }

    @Test
    void whenListCreativesAndCampaignNotExists_thenThrowError() {
        String brandId = "brandId";
        String campaignId = "campaignId";

        Mockito.when(brandRepository.existsById(brandId)).thenReturn(true);
        Mockito.when(campaignRepository.existsById(campaignId)).thenReturn(false);
        NotFoundFieldException exception = assertThrows(NotFoundFieldException.class,
                () -> testService.listCreativesByBrandCampaign(brandId, campaignId, 0, 100));
        assertEquals("Campaign not exists", exception.getMessage());
    }

}
