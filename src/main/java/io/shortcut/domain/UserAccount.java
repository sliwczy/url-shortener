package io.shortcut.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(unique = true)
    String email;
    String password;
}
