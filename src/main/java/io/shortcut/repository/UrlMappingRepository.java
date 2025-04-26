package io.shortcut.repository;

import io.shortcut.domain.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UrlMappingRepository extends JpaRepository<UrlMapping, String> {
    List<UrlMapping> findAllByUserEmail(String email);

    Optional<UrlMapping> getUrlMappingByUuid(String uuid);

    Optional<UrlMapping> getUrlMappingByUserEmailAndUrl(String email, String url);

    Optional<UrlMapping> getUrlMappingByShortenedUrl(String hashCode);
}
