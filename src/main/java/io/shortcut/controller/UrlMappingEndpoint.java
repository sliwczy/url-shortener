package io.shortcut.controller;

import io.shortcut.domain.UrlMapping;
import io.shortcut.dto.UrlMappingRequestDTO;
import io.shortcut.dto.UrlMappingResponseDTO;
import io.shortcut.service.UrlMappingService;
import io.shortcut.service.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public List<UrlMappingResponseDTO> getUrlMappings() {
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return urlService.getAllMappingsForUser(email);
    }

    @PutMapping
    public ResponseEntity<String> createUrl(@RequestBody UrlMappingRequestDTO urlMapping) {
        validationService.validateUrl(urlMapping.getUrl());
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        String hash = urlService.createIfNotExists(email, urlMapping.getUrl());
        return ResponseEntity.of(Optional.of(hash));
    }
}
