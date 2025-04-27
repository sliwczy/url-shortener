package io.shortcut.controller;

import io.shortcut.domain.UserAccount;
import io.shortcut.dto.UserAccountDTO;
import io.shortcut.repository.UserAccountRepository;
import io.shortcut.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserAccountEndpoint {

    private final JwtService jwtService;
    private final UserAccountRepository userAccountRepository;

    @PostMapping("/auth")
    public String createAuthenticationToken(@RequestBody UserAccountDTO userAccountDTO) throws Exception {

        UserAccount userAccount = userAccountRepository.getUserByEmail(userAccountDTO.getEmail());
        //todo: passwords in db should be encrypted and salted but not doing it because this is MVP POC/MVP
        if (userAccount.getPassword().equals(userAccountDTO.getPassword())) {
            return jwtService.generateToken(userAccountDTO.getEmail());
        } else {
            //or return response entity ?
            throw new Exception("Invalid credentials");
        }
    }

    @PostMapping("/register")
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
