package com.example.content_generator.dataservice.controller;

import com.example.content_generator.dataservice.dto.BrochureDTO;
import com.example.content_generator.dataservice.model.Brochure;
import com.example.content_generator.dataservice.repository.BrochureRepository;
import com.example.content_generator.dataservice.service.BrochureService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/brochures")
public class BrochureController {

    private final BrochureService brochureService;

    private final BrochureRepository brochureRepository; // MongoDB repository

    public BrochureController(BrochureService brochureService, BrochureRepository brochureRepository) {
        this.brochureService = brochureService;
        this.brochureRepository = brochureRepository;
    }

    @PostMapping("/upload")
    public ResponseEntity<BrochureDTO> uploadFile(@RequestParam("productId") String productId,
                                                  @RequestParam("file") MultipartFile file) {
        try {
            String fileUrl = brochureService.uploadFile(file);
            Brochure brochure = new Brochure();
            brochure.setProductId(productId);
            brochure.setUrl(fileUrl);

            Brochure savedBrochure = brochureRepository.save(brochure);
            BrochureDTO brochureDTO = new BrochureDTO();
            brochureDTO.setProductId(savedBrochure.getProductId());
            brochureDTO.setUrl(savedBrochure.getUrl());

            return ResponseEntity.ok(brochureDTO);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{productId}")
    public ResponseEntity<List<BrochureDTO>> getBrochuresByProductId(@PathVariable String productId) {
        List<Brochure> brochures = brochureRepository.findByProductId(productId);
        List<BrochureDTO> brochureDTOs = brochures.stream()
                .map(b -> new BrochureDTO(b.getProductId(), b.getUrl()))
                .toList();
        return ResponseEntity.ok(brochureDTOs);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBrochure(@PathVariable String id) {
        Brochure brochure = brochureRepository.findById(id).orElse(null);
        if (brochure != null) {
            brochureService.deleteFile(brochure.getUrl()); // Delete file from Azure Blob Storage
            brochureRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BrochureDTO> updateBrochure(@PathVariable String id,
                                                      @RequestParam("file") MultipartFile file) {
        try {
            Brochure existingBrochure = brochureRepository.findById(id).orElse(null);
            if (existingBrochure != null) {
                brochureService.deleteFile(existingBrochure.getUrl()); // Delete old file

                String newFileUrl = brochureService.uploadFile(file);
                existingBrochure.setUrl(newFileUrl);

                Brochure updatedBrochure = brochureRepository.save(existingBrochure);
                BrochureDTO brochureDTO = new BrochureDTO();
                brochureDTO.setProductId(updatedBrochure.getProductId());
                brochureDTO.setUrl(updatedBrochure.getUrl());

                return ResponseEntity.ok(brochureDTO);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
