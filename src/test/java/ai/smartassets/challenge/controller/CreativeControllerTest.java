package ai.smartassets.challenge.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponentsBuilder;

import ai.smartassets.challenge.dto.CreativeDto;
import ai.smartassets.challenge.exception.NotFoundFieldException;
import ai.smartassets.challenge.model.Creative;
import ai.smartassets.challenge.service.CreativeService;
import ai.smartassets.challenge.utils.JsonConverter;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(value = CreativeController.class)
class CreativeControllerTest {
    private final String API_URL = "/creatives";
    private final UriComponentsBuilder URI = UriComponentsBuilder.newInstance().path(API_URL);
    @Autowired
    private MockMvc mvc;
    @MockBean
    private CreativeService creativeService;

    @Test
    void whenListCreativesAndOk_ThenStatus200() throws Exception {
        final String brandId = "brandId";
        final String campaignId = "campaignId";
        given(creativeService.listCreativesByBrandCampaign(brandId, campaignId, 0, 10))
                .willReturn(getCreatives());

        mvc.perform(get(URI.queryParam("brandId", brandId)
                .queryParam("campaignId", campaignId).build().toUri()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))));
    }

    @Test
    void whenListCampaignsAndThrowBrandNotExists_ThenStatus400() throws Exception {
        final String brandId = "brandId";
        final String campaignId = "campaignId";
        given(creativeService.listCreativesByBrandCampaign(brandId, campaignId, 0, 10))
                .willThrow(new NotFoundFieldException("Error"));

        mvc.perform(get(URI.queryParam("brandId", brandId)
                .queryParam("campaignId", campaignId).build().toUri()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenListCampaignsAndThrowError_ThenStatus500() throws Exception {
        final String brandId = "brandId";
        final String campaignId = "campaignId";
        given(creativeService.listCreativesByBrandCampaign(brandId, campaignId, 0, 10))
                .willThrow(new RuntimeException("Error"));

        mvc.perform(get(URI.queryParam("brandId", brandId)
                .queryParam("campaignId", campaignId).build().toUri()))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void whenListCampaignsAndNoBrandIdParam_ThenStatus400() throws Exception {
        final String campaignId = "campaignId";
        mvc.perform(get(URI.queryParam("campaignId", campaignId).build().toUri()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenListCampaignsAndNoCampaignIdParam_ThenStatus400() throws Exception {
        final String brandId = "brandId";
        mvc.perform(get(URI.queryParam("brandId", brandId).build().toUri()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenCreateCampaignOk_ThenStatus201() throws Exception {
        final String brandId = "brandId";
        final String campaignId = "campaignId";
        CreativeDto dto = getCreativeDto();
        dto.setBrandId(brandId);
        dto.setCampaignId(campaignId);
        Creative campaign = dto;

        given(creativeService.createCreative(dto, brandId, campaignId)).willReturn(campaign);

        mvc.perform(post(URI.build().toUri())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConverter.toJson(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("CreativeName")));
    }

    @Test
    void whenCreateCampaignAndThrowError_ThenStatus500() throws Exception {
        final String brandId = "brandId";
        final String campaignId = "campaignId";
        CreativeDto dto = getCreativeDto();
        dto.setBrandId(brandId);
        dto.setCampaignId(campaignId);

        given(creativeService.createCreative(dto, brandId, campaignId))
                .willThrow(new RuntimeException("Error"));

        mvc.perform(post(URI.build().toUri())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConverter.toJson(dto)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void whenCreateCampaignAndNoBody_ThenStatus400() throws Exception {
        mvc.perform(post(URI.build().toUri())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    private List<Creative> getCreatives() {
        return List.of(new Creative(), new Creative());
    }

    private CreativeDto getCreativeDto() {
        CreativeDto dto = new CreativeDto();
        dto.setDescription("CreativeDesc");
        dto.setName("CreativeName");
        dto.setCreativeUrl("CreativeUrl");
        return dto;
    }
}
