package io.shortcut.controller;

import io.shortcut.domain.UrlMapping;
import io.shortcut.service.UrlMappingService;
import io.shortcut.service.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("mappings")
public class UrlMappingEndpoint {

    private final UrlMappingService urlService;
    private final ValidationService validationService;


    @GetMapping("/{uuid}")
    public ResponseEntity<UrlMapping> getUrlMapping(@PathVariable String uuid) {
        return ResponseEntity.of(urlService.getUrlMappingByUuid(uuid));
    }

    @GetMapping
    public Map<String, String> getUrlMappings() {
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return urlService.getAllMappingsForUser(email);
    }

    @PutMapping
    public ResponseEntity<String> createUrl(@RequestBody UrlMapping urlMapping) {
        validationService.validateUrl(urlMapping.getUrl());
        String hash = urlService.createIfNotExists(urlMapping.getUserEmail(), urlMapping.getUrl());
        return ResponseEntity.of(Optional.of(hash));
    }
}
