package com.abbrevio.abbrevio.controller;

import com.abbrevio.abbrevio.dto.AbbreviationDTO;
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
    public ResponseEntity<List<AbbreviationDTO>> getAllAbbreviations()
    {
        return ResponseEntity.ok(abbreviationService.getAllAbbreviations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AbbreviationDTO> getAbbreviation(@PathVariable Long id)
    {
        return ResponseEntity.ok(abbreviationService.getAbbreviationById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AbbreviationDTO> updateAbbreviation(@PathVariable Long id, @Valid @RequestBody AbbreviationDTO abbreviationDTO)
    {
        return ResponseEntity.ok(abbreviationService.updateAbbreviation(id, abbreviationDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAbbreviation(@PathVariable Long id)
    {
        abbreviationService.deleteAbbreviation(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
