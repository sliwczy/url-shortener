package io.shortcut.controller;

import io.shortcut.service.UrlMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UrlMappingEndpoint {

    private final UrlMappingService urlService;

    @GetMapping
    public Map<String, String> getUrlMappings() {
        return Map.of();
    }

    @PostMapping
    public void createUrl() {
    }
}
