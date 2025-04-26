package io.shortcut.controller;

import io.shortcut.service.UrlMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UrlMappingEndpoint {

    private final UrlMappingService urlService;

    @GetMapping
    public ResponseEntity<String> HttpRequest(String urlHash) {
        String redirectUrl = urlService.getUrlForAHashMapping(urlHash);

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .header("Location", redirectUrl)
                .build();
    }

    @GetMapping("/mapping/get-all")
    public Map<String, String> getUrlMappings() {
        return Map.of();
    }

    @PostMapping
    public void createUrl() {
    }
}
