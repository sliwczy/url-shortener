package io.shortcut.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class UrlMapping {

    @Id
    String url;
    String hashCode;
    String email;
}
