package com.erdem.readingisgood.service;

import com.erdem.readingisgood.config.security.TokenFactory;
import com.erdem.readingisgood.rest.model.request.LoginRequest;
import com.erdem.readingisgood.rest.model.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class UserService {

    private final AuthenticationManager authenticationManager;

    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword(),
                new ArrayList<>()));
        return LoginResponse.builder()
                .token(TokenFactory.createToken(authenticate))
                .build();
    }
}
