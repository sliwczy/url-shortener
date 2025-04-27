package io.shortcut.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserAccountDTO {
    String email;
    String password;
}
