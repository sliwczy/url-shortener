package io.shortcut.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UrlMappingRequestDTO {
    String url;
    String shortenedUrl;
}
