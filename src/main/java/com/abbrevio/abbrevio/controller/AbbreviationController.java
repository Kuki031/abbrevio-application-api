package com.abbrevio.abbrevio.controller;

import com.abbrevio.abbrevio.payload.abbreviation.AbbreviationDTO;
import com.abbrevio.abbrevio.payload.abbreviation.AbbreviationWithUserDTO;
import com.abbrevio.abbrevio.service.AbbreviationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/abbreviations")
public class AbbreviationController {

    private final AbbreviationService abbreviationService;

    public AbbreviationController(AbbreviationService abbreviationService) {
        this.abbreviationService = abbreviationService;
    }

    @PostMapping
    public ResponseEntity<AbbreviationDTO> createAbbreviation(@Valid @RequestBody AbbreviationDTO abbreviationDTO)
    {
        return new ResponseEntity<>(abbreviationService.createAbbreviation(abbreviationDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AbbreviationWithUserDTO>> getAllAbbreviations()
    {
        return ResponseEntity.ok(abbreviationService.getAllAbbreviations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AbbreviationDTO> getAbbreviation(@PathVariable Long id)
    {
        return ResponseEntity.ok(abbreviationService.getAbbreviationById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AbbreviationDTO> updateAbbreviation(@PathVariable Long id, @Valid @RequestBody AbbreviationDTO abbreviationDTO) throws Exception {
        return ResponseEntity.ok(abbreviationService.updateAbbreviation(id, abbreviationDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAbbreviation(@PathVariable Long id) throws Exception {
        abbreviationService.deleteAbbreviation(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("contains/{name}")
    public ResponseEntity<List<AbbreviationWithUserDTO>> getMatchingAbbreviations(@PathVariable String name) {
        return ResponseEntity.ok(abbreviationService.getAllContainingName(name));
    }

    @GetMapping("/my-abbreviations")
    public ResponseEntity<List<AbbreviationWithUserDTO>> getMyAbbreviations()
    {
        return ResponseEntity.ok(abbreviationService.getMyAbbreviations());
    }

    @GetMapping("/recently-searched")
    public ResponseEntity<List<AbbreviationDTO>> getRecentlySearchedAbbreviations()
    {
        return ResponseEntity.ok(abbreviationService.getTopTenSearches());
    }

    @GetMapping("/recently-added")
    public ResponseEntity<List<AbbreviationWithUserDTO>> getRecentlyAddedAbbreviations()
    {
        return ResponseEntity.ok(abbreviationService.getRecentlyAdded());
    }
}
