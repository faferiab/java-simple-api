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

import ai.smartassets.challenge.dto.CampaignDto;
import ai.smartassets.challenge.model.Campaign;
import ai.smartassets.challenge.service.CampaignService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/campaigns")
public class CampaignController {
    private CampaignService campaignService;

    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @Operation(summary = "Get all campaigns by brand")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of all campaigns", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Campaign.class))) }),
            @ApiResponse(responseCode = "400", description = "Missing brand id param", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
    @GetMapping
    public ResponseEntity<?> listCampaigns(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size, @RequestParam String brandId) {
        List<?> result = campaignService.listCampaignsByBrand(brandId, page, size);
        return ResponseEntity.ok().body(result);
    }

    @Operation(summary = "Create campaign")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "New campaign created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Campaign.class)) }),
            @ApiResponse(responseCode = "400", description = "Incorrect body", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
    @PostMapping
    public ResponseEntity<?> createCampaign(@RequestBody CampaignDto campaign) {
        Campaign result = campaignService.createCampaign(campaign, campaign.getBrandId());
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
