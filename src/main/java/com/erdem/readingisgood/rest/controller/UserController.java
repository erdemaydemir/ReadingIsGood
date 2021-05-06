package com.erdem.readingisgood.rest.controller;

import com.erdem.readingisgood.rest.model.common.Response;
import com.erdem.readingisgood.rest.model.request.LoginRequest;
import com.erdem.readingisgood.rest.model.response.LoginResponse;
import com.erdem.readingisgood.rest.util.ResponseDispatcher;
import com.erdem.readingisgood.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public Response<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        return ResponseDispatcher.successResponse(userService.login(loginRequest), HttpStatus.OK);
    }
}
