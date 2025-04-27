package io.shortcut.service;

import io.shortcut.domain.UrlMapping;
import io.shortcut.dto.UrlMappingResponseDTO;
import io.shortcut.repository.UrlMappingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.MissingResourceException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UrlMappingService {

    private final UrlMappingRepository urlMappingRepository;
    private final HashingService hashingService;

    public String createIfNotExists(String email, String url) {
        Optional<UrlMapping> urlMapping = urlMappingRepository.getUrlMappingByUserEmailAndUrl(email, url);
        if (urlMapping.isPresent()) {
            log.info("mapping already exists! url: {} hash: {}", url, urlMapping.get().getShortenedUrl());
            return urlMapping.get().getShortenedUrl();
        } else {
            createUrlMapping(email, url);
            return urlMappingRepository.getUrlMappingByUserEmailAndUrl(email, url).get().getShortenedUrl();
        }
    }

    public String getUrlForAHashMapping(String hashCode) {
        return urlMappingRepository.getUrlMappingByShortenedUrl(hashCode)
                .map(UrlMapping::getUrl)
                .orElseThrow(() -> new MissingResourceException("No url with given hash found: " + hashCode, UrlMapping.class.getName(), hashCode));
    }

    public Optional<UrlMapping> getUrlMappingByUuid(String uuid) {
        return urlMappingRepository.getUrlMappingByUuid(uuid);
//                .orElseThrow(() -> new MissingResourceException("No mapping found for uuid: " + uuid, UrlMapping.class.getName(), uuid));
    }

    private void createUrlMapping(String email, String url) {
        var mapping = new UrlMapping();
        mapping.setUserEmail(email);
        mapping.setUrl(url);
        mapping.setShortenedUrl(hashingService.getShortenedUrlHash());
        urlMappingRepository.save(mapping);
    }

    public List<UrlMappingResponseDTO> getAllMappingsForUser(String email) {
        return urlMappingRepository.findAllByUserEmail(email)
                .stream()
                .map(entity -> UrlMappingResponseDTO.builder()
                        .uuid(entity.getUuid())
                        .url(entity.getUrl())
                        .shortenedUrl(entity.getShortenedUrl())
                        .createdBy(entity.getUserEmail())
                        .build()
                )
                .toList();
    }
}
