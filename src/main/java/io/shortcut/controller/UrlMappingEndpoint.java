package io.shortcut.controller;

import io.shortcut.domain.UrlMapping;
import io.shortcut.service.UrlMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UrlMappingEndpoint {

    private final UrlMappingService urlService;
    private final ValidationService validationService;

    //todo not following REST API pattern purposely - to have shortest url possible
    @GetMapping
    public ResponseEntity<String> HttpRequest(String urlHash) {
        String redirectUrl = urlService.getUrlForAHashMapping(urlHash);

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .header("Location", redirectUrl)
                .build();
    }

    @GetMapping("/mappings/{uuid}")
    public ResponseEntity<UrlMapping> getUrlMapping(@PathVariable String uuid) {
        return ResponseEntity.of(urlService.getUrlMappingByUuid(uuid));
    }

    @GetMapping("/mappings")
    public Map<String, String> getUrlMappings() {
        //todo: get email from a security context
        return urlService.getAllMappingsForUser("email");
    }

    @PutMapping("/mappings")
    public ResponseEntity<String> createUrl(@RequestBody UrlMapping urlMapping) {
        validationService.validateUrl(urlMapping.getUrl());
        String hash = urlService.createIfNotExists(urlMapping.getUserEmail(), urlMapping.getUrl());
        return ResponseEntity.of(Optional.of(hash));
    }
}
