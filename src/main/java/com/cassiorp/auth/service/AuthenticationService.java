package com.cassiorp.auth.service;


import com.cassiorp.auth.api.dto.LoginRequestDTO;
import com.cassiorp.auth.api.dto.LoginResponseDTO;
import com.cassiorp.auth.api.dto.UserRequestDTO;
import com.cassiorp.auth.api.dto.UserResponseDTO;
import com.cassiorp.auth.entity.User;
import com.cassiorp.auth.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static com.cassiorp.auth.api.converter.UserConverter.toDTO;
import static com.cassiorp.auth.api.converter.UserConverter.toEntity;

@Service
public class AuthenticationService {

  private static final String USER_NOT_FOUND_ERROR = "User not found";

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;

  public AuthenticationService(
      UserRepository userRepository,
      AuthenticationManager authenticationManager,
      PasswordEncoder passwordEncoder, JwtService jwtService
  ) {
    this.authenticationManager = authenticationManager;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
  }

  //TODO email unico
  public UserResponseDTO signup(UserRequestDTO userRequestDTO) {
    User user = toEntity(userRequestDTO);
    encondePassword(user);
    return toDTO(userRepository.save(user));
  }

  private void encondePassword(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
  }

  //TODO mensagem de erro caso senha invalida
  private User authenticate(LoginRequestDTO loginRequestDTO) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginRequestDTO.getEmail(),
            loginRequestDTO.getPassword()
        )
    );
    return findUserById(loginRequestDTO);
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

  private User findUserById(LoginRequestDTO loginRequestDTO) {
    return userRepository
        .findByEmail(loginRequestDTO.getEmail())
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            USER_NOT_FOUND_ERROR
        ));
  }

  public void validateToken(String token) {
    jwtService.valitadeToken(token);
  }

}