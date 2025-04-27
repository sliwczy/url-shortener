package io.shortcut.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UrlMappingResponseDTO {
    String uuid;
    String url;
    String shortenedUrl;
    String createdBy;
}
