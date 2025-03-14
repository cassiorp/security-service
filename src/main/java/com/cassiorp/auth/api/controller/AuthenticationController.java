package com.cassiorp.auth.api.controller;


import com.cassiorp.auth.api.dto.*;
import com.cassiorp.auth.entity.User;
import com.cassiorp.auth.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.cassiorp.auth.api.converter.UserConverter.toDTO;
import static com.cassiorp.auth.api.converter.UserConverter.toEntity;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {

  private final AuthenticationService authenticationService;

  public AuthenticationController(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  @PostMapping("/signup")
  public ResponseEntity<UserResponseDTO> signup(@RequestBody UserRequestDTO userRequestDTO) {
    UserResponseDTO user = authenticationService.signup(userRequestDTO);
    return new ResponseEntity<>(user, HttpStatus.CREATED);
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
    LoginResponseDTO loginResponseDTO = authenticationService.authenticateUser(loginRequestDTO);
    return ResponseEntity.ok(loginResponseDTO);
  }

  @PostMapping("/validate")
  public void validate(@RequestBody String token) {
    authenticationService.validateToken(token);
  }
}