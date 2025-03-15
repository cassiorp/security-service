package com.cassiorp.auth.service;


import com.cassiorp.auth.api.dto.LoginRequestDTO;
import com.cassiorp.auth.api.dto.LoginResponseDTO;
import com.cassiorp.auth.api.dto.UserRequestDTO;
import com.cassiorp.auth.api.dto.UserResponseDTO;
import com.cassiorp.auth.entity.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

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

  //TODO mensagem de erro caso senha invalida
  private User authenticate(LoginRequestDTO loginRequestDTO) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginRequestDTO.email(),
            loginRequestDTO.password()
        )
    );
    return userService.findUserById(loginRequestDTO);
  }

  public LoginResponseDTO authenticateUser(LoginRequestDTO loginRequestDTO) {
    User authenticatedUser = this.authenticate(loginRequestDTO);
    String jwtToken = jwtService.generateToken(authenticatedUser);
    return buildLoginResponseDTO(jwtToken);
  }

  private LoginResponseDTO buildLoginResponseDTO(String jwtToken) {
    return new LoginResponseDTO()
        .setToken(jwtToken)
        .setExpiresIn(jwtService.getExpirationTime());
  }

  public void validateToken(String token) {
    jwtService.valitadeToken(token);
  }

}