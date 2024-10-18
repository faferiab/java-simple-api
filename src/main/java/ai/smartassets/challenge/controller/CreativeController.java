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

import ai.smartassets.challenge.dto.CreativeDto;
import ai.smartassets.challenge.model.Creative;
import ai.smartassets.challenge.service.CreativeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/creatives")
public class CreativeController {
    private CreativeService creativeService;

    public CreativeController(CreativeService creativeService) {
        this.creativeService = creativeService;
    }

    @Operation(summary = "Get all Creatives by brand and campaign")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of all creatives", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Creative.class))) }),
            @ApiResponse(responseCode = "400", description = "Missing brand id or campaign id param", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
    @GetMapping
    public ResponseEntity<?> listCampaigns(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size, @RequestParam String brandId,
            @RequestParam String campaignId) {
        List<?> result = creativeService.listCreativesByBrandCampaign(brandId, campaignId, page, size);
        return ResponseEntity.ok().body(result);
    }

    @Operation(summary = "Create creative")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "New creative created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Creative.class)) }),
            @ApiResponse(responseCode = "400", description = "Incorrect body", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
    @PostMapping
    public ResponseEntity<?> createCampaign(@RequestBody CreativeDto creative) {
        Creative result = creativeService.createCreative(creative, creative.getBrandId(), creative.getCampaignId());
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
