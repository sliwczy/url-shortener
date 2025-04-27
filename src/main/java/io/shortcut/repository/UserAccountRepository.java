package io.shortcut.repository;

import io.shortcut.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount, String> {
    UserAccount getUserByEmail(String email);
}
