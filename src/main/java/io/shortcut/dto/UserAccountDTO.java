package io.shortcut.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
@Builder
public class UserAccountDTO {
    @NonNull
    String email;
    @NonNull
    String password;
}
