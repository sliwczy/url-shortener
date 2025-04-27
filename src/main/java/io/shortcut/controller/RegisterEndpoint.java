package io.shortcut.controller;

import io.shortcut.domain.UserAccount;
import io.shortcut.dto.UserAccountDTO;
import io.shortcut.repository.UserAccountRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("register")
public class RegisterEndpoint {

    private final UserAccountRepository userAccountRepository;

    public RegisterEndpoint(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @PostMapping
    public ResponseEntity<String> createNewUser(@RequestBody UserAccountDTO userAccountDTO) {
        UserAccount userAccount = new UserAccount();
        userAccount.setEmail(userAccountDTO.getEmail());
        userAccount.setPassword(userAccountDTO.getPassword());//todo: encrypt before saving ! implement it later
        long id = userAccountRepository.save(userAccount).getId();

        //todo: if user exists return 409 collision
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(uri).build();
    }
}
