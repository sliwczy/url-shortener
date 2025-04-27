package io.shortcut.controller;

import io.shortcut.service.UrlMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.MissingResourceException;

@RestController
@RequiredArgsConstructor
@RequestMapping("s")
public class RedirectEndpoint {

    private final UrlMappingService urlService;

    @GetMapping("/{urlHash}")
    public ResponseEntity<String> redirect(@PathVariable String urlHash) {
        try {
            var redirectUrl = urlService.getUrlForAHashMapping(urlHash);

            return ResponseEntity
                    .status(HttpStatus.FOUND)
                    .header("Location", redirectUrl)
                    .build();
        } catch (MissingResourceException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
