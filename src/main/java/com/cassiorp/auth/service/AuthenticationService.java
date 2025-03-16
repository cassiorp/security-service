package com.cassiorp.auth.service;


import com.cassiorp.auth.api.dto.*;
import com.cassiorp.auth.entity.User;
import com.cassiorp.auth.exception.BadCredentialsException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static com.cassiorp.auth.api.converter.UserConverter.toDTO;
import static com.cassiorp.auth.api.converter.UserConverter.toEntity;

@Service
public class AuthenticationService {

  private final UserService userService;
  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;

  public AuthenticationService(UserService userService, AuthenticationManager authenticationManager, JwtService jwtService) {
    this.userService = userService;
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
  }

  public UserResponseDTO signup(UserRequestDTO userRequestDTO) {
    User user = toEntity(userRequestDTO);
    user = userService.createUser(user);
    return toDTO(user);
  }

  public LoginResponseDTO authenticateUser(LoginRequestDTO loginRequestDTO) {
    User authenticatedUser = this.authenticate(loginRequestDTO);
    String jwtToken = jwtService.generateToken(authenticatedUser);
    return buildLoginResponseDTO(jwtToken);
  }

  private User authenticate(LoginRequestDTO loginRequestDTO) {
    User user = userService.findUserById(loginRequestDTO.email());
    UsernamePasswordAuthenticationToken authentication = buildUsernamePasswordAuthenticationToken(loginRequestDTO);
    authenticate(authentication);
    return user;
  }

  private void authenticate(UsernamePasswordAuthenticationToken authentication) {
    try {
      authenticationManager.authenticate(authentication);
    } catch (Exception ex) {
      throw new BadCredentialsException("Password incorrect");
    }
  }

  private static UsernamePasswordAuthenticationToken buildUsernamePasswordAuthenticationToken(LoginRequestDTO loginRequestDTO) {
    return new UsernamePasswordAuthenticationToken(
        loginRequestDTO.email(),
        loginRequestDTO.password()
    );
  }

  private LoginResponseDTO buildLoginResponseDTO(String jwtToken) {
    return LoginResponseDTO.builder()
        .token(jwtToken)
        .expiresAt(buildExpirationTime())
        .build();
  }

  private LocalDateTime buildExpirationTime() {
    return LocalDateTime.now()
        .atZone(ZoneId.systemDefault())
        .toInstant()
        .plusMillis(jwtService.getExpirationTime())
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime();
  }

  public void authorization(AuthorizationRequestDTO authorizationRequestDTO) {
    jwtService.valitadeToken(authorizationRequestDTO.token());
  }

}