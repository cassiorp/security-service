package com.cassiorp.auth.api.controller;


import com.cassiorp.auth.api.dto.*;
import com.cassiorp.auth.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {

  private final AuthenticationService authenticationService;

  public AuthenticationController(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  @PostMapping("/signup")
  public ResponseEntity<UserResponseDTO> signup(@Validated @RequestBody UserRequestDTO userRequestDTO) {
    UserResponseDTO user = authenticationService.signup(userRequestDTO);
    return new ResponseEntity<>(user, HttpStatus.CREATED);
  }

  @PostMapping("/authenticate")
  public ResponseEntity<LoginResponseDTO> authenticate(@Validated @RequestBody LoginRequestDTO loginRequestDTO) {
    LoginResponseDTO loginResponseDTO = authenticationService.authenticateUser(loginRequestDTO);
    return ResponseEntity.ok(loginResponseDTO);
  }

  //TODO Status code
  // Authorization and req y res
  @PostMapping("/authorization")
  public void authorization(@RequestBody AuthorizationRequestDTO authorizationRequestDTO) {
    authenticationService.authorization(authorizationRequestDTO);
  }
}