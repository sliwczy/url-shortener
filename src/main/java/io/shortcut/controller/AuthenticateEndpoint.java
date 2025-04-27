package io.shortcut.controller;

import io.shortcut.domain.UserAccount;
import io.shortcut.dto.UserAccountDTO;
import io.shortcut.repository.UserAccountRepository;
import io.shortcut.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthenticateEndpoint {

    private final JwtService jwtService;
    private final UserAccountRepository userAccountRepository;

    @PostMapping
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
}
