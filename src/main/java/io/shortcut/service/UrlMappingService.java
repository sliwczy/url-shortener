package io.shortcut.service;

import io.shortcut.domain.UrlMapping;
import io.shortcut.repository.UrlMappingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UrlMappingService {

    private final UrlMappingRepository urlMappingRepository;
    private final HashingService hashingService;

    private String createIfNotExists(String email, String url) {
        Optional<UrlMapping> urlMapping = urlMappingRepository.getUrlMappingByEmailAndUrl(email, url);
        if (urlMapping.isPresent()) {
            log.info("mapping already exists! url: {} hash: {}", url, urlMapping.get().getHashCode());
            return urlMapping.get().getHashCode();
        } else {
            createUrlMapping(email, url);
            return urlMappingRepository.getUrlMappingByEmailAndUrl(email, url).get().getHashCode();
        }
    }

    public String getUrlForAHashMapping(String hashCode) {
        return urlMappingRepository.getUrlMappingByHashCode(hashCode)
                .map(UrlMapping::getUrl)
                .orElseThrow(() -> new MissingResourceException("No url with given hash found: " + hashCode, UrlMapping.class.getName(), hashCode));
    }

    private void createUrlMapping(String email, String url) {
        var mapping = new UrlMapping();
        mapping.setEmail(email);
        mapping.setUrl(url);
        mapping.setHashCode(generateRandomHash());
        urlMappingRepository.save(mapping);
    }

    public Map<String, String> getAllMappingsForUser(String email) {
        return urlMappingRepository.findAllByEmail(email)
                .stream()
                .collect(Collectors.toMap(UrlMapping::getUrl, UrlMapping::getHashCode));
    }

    private String generateRandomHash() {
        return hashingService.getUuid();
    }
}
