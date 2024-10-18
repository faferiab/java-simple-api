package ai.smartassets.challenge.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import ai.smartassets.challenge.entity.BrandEntity;
import ai.smartassets.challenge.model.Brand;
import ai.smartassets.challenge.repository.BrandRepository;

@ExtendWith(MockitoExtension.class)
class BrandServiceTest {
    @Mock
    private BrandRepository brandRepository;
    @InjectMocks
    private BrandService brandService;

    @Test
    void whenCreateBrandAndOk_thenCreateBrand() {
        Brand brand = new Brand();
        brand.setName("BrandName");
        brand.setDescription("BrandDesc");
        BrandEntity entity = new BrandEntity();
        entity.setBrandId(brand.getBrandId());
        entity.setDescription(brand.getDescription());
        entity.setName(brand.getName());

        Mockito.when(brandRepository.save(any())).thenReturn(entity);
        Brand response = brandService.createBrand(brand);
        assertNotNull(response);
        assertEquals("BrandName", response.getName());
    }

    @Test
    void whenListBrandsAndOk_thenListBrands() {
        Mockito.when(brandRepository.findAll(PageRequest.of(0, 50)))
                .thenReturn(new PageImpl<BrandEntity>(List.of(new BrandEntity(), new BrandEntity())));
        List<Brand> response = brandService.listBrands(0, 100);
        assertNotNull(response);
        assertEquals(2, response.size());
    }
}
