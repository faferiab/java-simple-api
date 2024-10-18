package ai.smartassets.challenge.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import ai.smartassets.challenge.entity.BrandEntity;
import ai.smartassets.challenge.model.Brand;
import ai.smartassets.challenge.repository.BrandRepository;
import ai.smartassets.challenge.service.handlers.BrandServiceHandler;

@Service
public class BrandService {
    private final int MIN_SIZE_PAGE = 10;
    private final int MAX_SIZE_PAGE = 50;
    private BrandRepository repository;

    public BrandService(BrandRepository brandRepository) {
        this.repository = brandRepository;
    }

    public Brand createBrand(Brand brand) {
        brand.setBrandId(UUID.randomUUID().toString());
        BrandEntity entity = BrandServiceHandler.convert(brand);
        entity = repository.save(entity);
        return BrandServiceHandler.convert(entity);
    }

    public List<Brand> listBrands(int page, int size) {
        int actualSize = Math.min(MAX_SIZE_PAGE, Math.max(size, MIN_SIZE_PAGE));
        List<Brand> response = repository
                .findAll(PageRequest.of(page, actualSize))
                .map(BrandServiceHandler::convert)
                .toList();
        return response;
    }
}
