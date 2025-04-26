package io.shortcut.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class UrlMapping {

    @Id
    String uuid;
    String url;
    String shortenedUrl;
    String userEmail;

    @PrePersist
    public void generateUuid() {
        var complexKey = userEmail + url;
        this.uuid = UUID.nameUUIDFromBytes(complexKey.getBytes()).toString();
    }
}
