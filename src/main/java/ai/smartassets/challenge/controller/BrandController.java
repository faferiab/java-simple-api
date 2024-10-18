package ai.smartassets.challenge.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.smartassets.challenge.dto.BrandDto;
import ai.smartassets.challenge.model.Brand;
import ai.smartassets.challenge.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/brands")
public class BrandController {
    private BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @Operation(summary = "Get all brands")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of all brands", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Brand.class))) }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
    @GetMapping
    public ResponseEntity<?> listBrands(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<?> result = brandService.listBrands(page, size);
        return ResponseEntity.ok().body(result);
    }

    @Operation(summary = "Create brand")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "New brand created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Brand.class)) }),
            @ApiResponse(responseCode = "400", description = "Incorrect body", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
    @PostMapping
    public ResponseEntity<?> createBrand(@RequestBody BrandDto brand) {
        Brand result = brandService.createBrand(brand);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
