package com.cassiorp.auth.api.controller;


import com.cassiorp.auth.api.dto.LoginRequestDTO;
import com.cassiorp.auth.api.dto.LoginResponseDTO;
import com.cassiorp.auth.api.dto.UserRequestDTO;
import com.cassiorp.auth.api.dto.UserResponseDTO;
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

  //TODO Status code
  //TODO Validation fields
  @PostMapping("/login")
  public ResponseEntity<LoginResponseDTO> login(@Validated @RequestBody LoginRequestDTO loginRequestDTO) {
    LoginResponseDTO loginResponseDTO = authenticationService.authenticateUser(loginRequestDTO);
    return ResponseEntity.ok(loginResponseDTO);
  }

  //TODO Status code
  @PostMapping("/validate")
  public void validate(@RequestBody String token) {
    authenticationService.validateToken(token);
  }
}