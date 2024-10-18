package ai.smartassets.challenge;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.util.UriComponentsBuilder;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import ai.smartassets.challenge.dto.BrandDto;
import ai.smartassets.challenge.dto.CampaignDto;
import ai.smartassets.challenge.dto.CreativeDto;
import ai.smartassets.challenge.model.Brand;
import ai.smartassets.challenge.model.Campaign;
import ai.smartassets.challenge.utils.JsonConverter;

@Testcontainers
@AutoConfigureMockMvc
@SpringBootTest
class ChallengeApplicationTests {
	private final String BRAND_API_URL = "/brands";
	private final UriComponentsBuilder BRAND_URI = UriComponentsBuilder
			.newInstance().path(BRAND_API_URL);
	private final String CAMPAIGN_API_URL = "/campaigns";
	private final UriComponentsBuilder CAMPAIGN_URI = UriComponentsBuilder
			.newInstance().path(CAMPAIGN_API_URL);
	private final String CREATIVE_API_URL = "/creatives";
	private final UriComponentsBuilder CREATIVE_URI = UriComponentsBuilder
			.newInstance().path(CREATIVE_API_URL);

	@Container
	@ServiceConnection
	private static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:8.0.0");

	@Autowired
	private MockMvc mvc;

	@Test
	void createBrandAndListBand() throws Exception {
		BrandDto dto = new BrandDto();
		dto.setName("BrandId1");
		dto.setDescription("BrandDesc1");
		createBrand(dto)
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.brandId", notNullValue()));
		dto = new BrandDto();
		dto.setName("BrandId2");
		dto.setDescription("BrandDesc2");
		createBrand(dto)
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.brandId", notNullValue()));
		listBrands()
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(greaterThan(2))));
	}

	@Test
	void createCampaignAndListCampaign() throws Exception {
		BrandDto brandDto = new BrandDto();
		brandDto.setName("BrandId1");
		brandDto.setDescription("BrandDesc1");
		MockHttpServletResponse response = createBrand(brandDto)
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.brandId", notNullValue()))
				.andReturn()
				.getResponse();
		Brand brand = JsonConverter.fromJson(response.getContentAsString(), Brand.class);
		CampaignDto dto = new CampaignDto();
		dto.setBrandId(brand.getBrandId());
		dto.setName("CampaignName1");
		dto.setDescription("CampaignDesc1");
		createCampaign(dto)
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.campaignId", notNullValue()));
		dto = new CampaignDto();
		dto.setBrandId(brand.getBrandId());
		dto.setName("CampaignName2");
		dto.setDescription("CampaignDesc2");
		createCampaign(dto)
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.campaignId", notNullValue()));
		dto = new CampaignDto();
		dto.setBrandId(brand.getBrandId());
		dto.setName("CampaignName3");
		dto.setDescription("CampaignDesc3");
		createCampaign(dto)
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.campaignId", notNullValue()));
		listCampaigns(brand.getBrandId())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(equalTo(3))));
	}

	@Test
	void createCampaignAndThrowErrorBrandNotExists() throws Exception {
		CampaignDto dto = new CampaignDto();
		dto.setBrandId("FAKE_BRAND_ID");
		dto.setName("CampaignName1");
		dto.setDescription("CampaignDesc1");
		createCampaign(dto)
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error", is("Brand not exists")));
	}

	@Test
	void createCreativeAndListCreative() throws Exception {
		BrandDto brandDto = new BrandDto();
		brandDto.setName("BrandId1");
		brandDto.setDescription("BrandDesc1");
		MockHttpServletResponse response = createBrand(brandDto)
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.brandId", notNullValue()))
				.andReturn()
				.getResponse();
		Brand brand = JsonConverter.fromJson(response.getContentAsString(), Brand.class);
		CampaignDto campaignDto = new CampaignDto();
		campaignDto.setBrandId(brand.getBrandId());
		campaignDto.setName("CampaignName1");
		campaignDto.setDescription("CampaignDesc1");
		response = createCampaign(campaignDto)
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.campaignId", notNullValue()))
				.andReturn()
				.getResponse();
		Campaign campaign = JsonConverter.fromJson(response.getContentAsString(), Campaign.class);

		CreativeDto dto = new CreativeDto();
		dto.setBrandId(brand.getBrandId());
		dto.setCampaignId(campaign.getCampaignId());
		dto.setName("CreativeName1");
		dto.setDescription("CreativeDesc1");
		dto.setCreativeUrl("CreativeUrl1");
		createCreative(dto)
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.creativeId", notNullValue()));
		dto = new CreativeDto();
		dto.setBrandId(brand.getBrandId());
		dto.setCampaignId(campaign.getCampaignId());
		dto.setName("CreativeName2");
		dto.setDescription("CreativeDesc2");
		dto.setCreativeUrl("CreativeUrl2");
		createCreative(dto)
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.creativeId", notNullValue()));
		listCreatives(brand.getBrandId(), campaign.getCampaignId())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(equalTo(2))));
	}

	@Test
	void createCreativeAndThrowErrorBrandNotExists() throws Exception {
		CreativeDto dto = new CreativeDto();
		dto.setBrandId("FAKE_BRAND");
		dto.setCampaignId("FAKE_CAMPAIGN");
		dto.setName("CreativeName1");
		dto.setDescription("CreativeDesc1");
		dto.setCreativeUrl("CreativeUrl1");
		createCreative(dto)
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error", is("Brand not exists")));
	}

	private ResultActions createBrand(BrandDto dto) throws Exception {
		return mvc.perform(post(BRAND_URI.build().toUri())
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonConverter.toJson(dto)));
	}

	private ResultActions listBrands() throws Exception {
		return mvc.perform(get(BRAND_URI.build().toUri()));
	}

	private ResultActions createCampaign(CampaignDto dto) throws Exception {
		return mvc.perform(post(CAMPAIGN_URI.build().toUri())
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonConverter.toJson(dto)));
	}

	private ResultActions listCampaigns(String brandId) throws Exception {
		return mvc.perform(get(CAMPAIGN_URI.queryParam("brandId", brandId).build().toUri()));
	}

	private ResultActions createCreative(CreativeDto dto) throws Exception {
		return mvc.perform(post(CREATIVE_URI.build().toUri())
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonConverter.toJson(dto)));
	}

	private ResultActions listCreatives(String brandId, String campaignId) throws Exception {
		return mvc.perform(get(CREATIVE_URI.queryParam("brandId", brandId)
				.queryParam("campaignId", campaignId).build().toUri()));
	}
}
