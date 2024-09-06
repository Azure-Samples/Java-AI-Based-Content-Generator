package com.example.content_generator.aiservice.controller;

import com.example.content_generator.aiservice.model.ContentRequest;
import com.example.content_generator.aiservice.service.OpenAIService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/generate")
public class ContentController {

    private final OpenAIService openAIService;

    public ContentController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @PostMapping("/content")
    public ResponseEntity<String> generateContent(@RequestBody ContentRequest request) {
        // Set response headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            return new ResponseEntity<>(openAIService.generateContent(request.getMessage(), request.getType()), headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.toString(), headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
