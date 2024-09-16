package com.abbrevio.abbrevio.controller;

import com.abbrevio.abbrevio.dto.MeaningDTO;
import com.abbrevio.abbrevio.service.MeaningService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/abbreviations")
public class MeaningController {

    private final MeaningService meaningService;

    public MeaningController(MeaningService meaningService) {
        this.meaningService = meaningService;
    }

    @GetMapping("/{id}/meanings")
    public ResponseEntity<Set<MeaningDTO>> getMeaningsForAbbreviation(@PathVariable Long id)
    {
        return ResponseEntity.ok(meaningService.getMeaningsForAbbreviation(id));
    }

    @GetMapping("/{abbrevId}/meanings/{meaningId}")
    public ResponseEntity<MeaningDTO> getMeaningForAbbreviation(@PathVariable Long abbrevId, @PathVariable Long meaningId) throws Exception {
        return ResponseEntity.ok(meaningService.getMeaningForAbbreviation(abbrevId, meaningId));
    }

    @PostMapping("/{id}/meanings")
    public ResponseEntity<MeaningDTO> createMeaningForAbbreviation(@Valid @RequestBody MeaningDTO meaningDTO, @PathVariable Long id)
    {
        return new ResponseEntity<>(meaningService.createMeaningForAbbreviation(meaningDTO, id), HttpStatus.CREATED);
    }

    @PatchMapping("/{abbrevId}/meanings/{meaningId}")
    public ResponseEntity<MeaningDTO> updateMeaningForAbbreviation(@Valid @RequestBody MeaningDTO meaningDTO, @PathVariable Long abbrevId, @PathVariable Long meaningId) throws Exception {
        return ResponseEntity.ok(meaningService.updateMeaningForAbbreviation(abbrevId, meaningId, meaningDTO));
    }

    @DeleteMapping("/{abbrevId}/meanings/{meaningId}")
    public ResponseEntity<HttpStatus> deleteMeaningForAbbreviation(@PathVariable Long abbrevId, @PathVariable Long meaningId) throws Exception {
        meaningService.deleteMeaningForAbbreviation(abbrevId, meaningId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
