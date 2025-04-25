package io.shortcut.controller;

import io.shortcut.service.UrlMappingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UrlEndpoint {

    private final UrlMappingService urlService;

    @GetMapping
    public String getUrlMappings() {

    }

    @PostMapping
    public String createUrl() {

    }
}
