package com.example.content_generator.aiservice.controller;

import com.example.content_generator.aiservice.model.ContentRequest;
import com.example.content_generator.aiservice.model.EmbeddingReq;
import com.example.content_generator.aiservice.service.OpenAiService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/generate")
public class ContentController {

    private final OpenAiService openAIService;

    public ContentController(OpenAiService openAIService) {
        this.openAIService = openAIService;
    }

    @CrossOrigin
    @PostMapping("/content")
    public ResponseEntity<Object> generateContent(@RequestBody ContentRequest request) {
        // Set response headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            return new ResponseEntity<>(openAIService.generateContent(request), headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.toString(), headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin
    @PostMapping("/embeddings")
    public ResponseEntity<Object> generateEmbedding(@RequestBody EmbeddingReq request) {

        // Set response headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            return new ResponseEntity<>(openAIService.generateEmbeddings(request.getInput().toString()), headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.toString(), headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

