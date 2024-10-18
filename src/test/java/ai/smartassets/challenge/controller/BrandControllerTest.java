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

import ai.smartassets.challenge.dto.BrandDto;
import ai.smartassets.challenge.model.Brand;
import ai.smartassets.challenge.service.BrandService;
import ai.smartassets.challenge.utils.JsonConverter;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(value = BrandController.class)
class BrandControllerTest {
    private final String API_URL = "/brands";
    private final UriComponentsBuilder URI = UriComponentsBuilder.newInstance().path(API_URL);
    @Autowired
    private MockMvc mvc;
    @MockBean
    private BrandService brandService;

    @Test
    void whenListBrandsOk_ThenStatus200() throws Exception {
        given(brandService.listBrands(0, 10)).willReturn(getBrands());

        mvc.perform(get(URI.build().toUri()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))));
    }

    @Test
    void whenListBrandsAndThrowError_ThenStatus500() throws Exception {
        given(brandService.listBrands(0, 10)).willThrow(new RuntimeException("Error"));

        mvc.perform(get(URI.build().toUri()))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void whenCreateBrandAndOk_ThenStatus201() throws Exception {
        BrandDto dto = getBrandDto();
        Brand brand = dto;
        given(brandService.createBrand(dto)).willReturn(brand);

        mvc.perform(post(URI.build().toUri())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConverter.toJson(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("BrandName1")));
    }

    private List<Brand> getBrands() {
        Brand brand1 = new Brand();
        brand1.setBrandId("BrandID1");
        brand1.setName("BrandName1");
        brand1.setDescription("BrandDesc1");
        Brand brand2 = new Brand();
        brand1.setBrandId("BrandID2");
        brand1.setName("BrandName2");
        brand1.setDescription("BrandDesc2");
        return List.of(brand1, brand2);
    }

    private BrandDto getBrandDto() {
        BrandDto brand1 = new BrandDto();
        brand1.setBrandId("BrandID1");
        brand1.setName("BrandName1");
        brand1.setDescription("BrandDesc1");
        return brand1;
    }
}
