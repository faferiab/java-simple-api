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

import ai.smartassets.challenge.dto.CampaignDto;
import ai.smartassets.challenge.exception.NotFoundFieldException;
import ai.smartassets.challenge.model.Campaign;
import ai.smartassets.challenge.service.CampaignService;
import ai.smartassets.challenge.utils.JsonConverter;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(value = CampaignController.class)
class CampaignControllerTest {
    private final String API_URL = "/campaigns";
    private final UriComponentsBuilder URI = UriComponentsBuilder.newInstance().path(API_URL);
    @Autowired
    private MockMvc mvc;
    @MockBean
    private CampaignService campaignService;

    @Test
    void whenListCampaignsOk_ThenStatus200() throws Exception {
        final String brandId = "brandId";
        given(campaignService.listCampaignsByBrand(brandId, 0, 10)).willReturn(getCampaigns());

        mvc.perform(get(URI.queryParam("brandId", brandId).build().toUri()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))));
    }

    @Test
    void whenListCampaignsAndThrowBrandNotExists_ThenStatus400() throws Exception {
        final String brandId = "brandId";
        given(campaignService.listCampaignsByBrand(brandId, 0, 10))
                .willThrow(new NotFoundFieldException("Error"));

        mvc.perform(get(URI.queryParam("brandId", brandId).build().toUri()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenListCampaignsAndThrowError_ThenStatus500() throws Exception {
        final String brandId = "brandId";
        given(campaignService.listCampaignsByBrand(brandId, 0, 10))
                .willThrow(new RuntimeException("Error"));

        mvc.perform(get(URI.queryParam("brandId", brandId).build().toUri()))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void whenListCampaignsAndNoBrandIdParam_ThenStatus400() throws Exception {
        mvc.perform(get(URI.build().toUri()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenCreateCampaignOk_ThenStatus201() throws Exception {
        final String brandId = "brandId";
        CampaignDto dto = getCampaignDto();
        dto.setBrandId(brandId);
        Campaign campaign = dto;

        given(campaignService.createCampaign(dto, brandId)).willReturn(campaign);

        mvc.perform(post(URI.build().toUri())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConverter.toJson(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("CampaignName")));
    }

    @Test
    void whenCreateCampaignAndThrowError_ThenStatus500() throws Exception {
        final String brandId = "brandId";
        CampaignDto dto = getCampaignDto();
        dto.setBrandId(brandId);

        given(campaignService.createCampaign(dto, brandId))
                .willThrow(new RuntimeException("Error"));

        mvc.perform(post(URI.build().toUri())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConverter.toJson(dto)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void whenCreateCampaignAndNoBody_ThenStatus400() throws Exception {
        final String brandId = "brandId";
        CampaignDto dto = getCampaignDto();
        dto.setBrandId(brandId);

        mvc.perform(post(URI.build().toUri())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    private List<Campaign> getCampaigns() {
        return List.of(new Campaign(), new Campaign());
    }

    private CampaignDto getCampaignDto() {
        CampaignDto dto = new CampaignDto();
        dto.setCampaignId("CampaignId");
        dto.setDescription("CampaignDesc");
        dto.setName("CampaignName");
        return dto;
    }
}
