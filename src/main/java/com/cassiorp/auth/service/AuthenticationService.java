package com.cassiorp.auth.service;


import com.cassiorp.auth.api.dto.LoginUserDto;
import com.cassiorp.auth.api.dto.RegisterUserDto;
import com.cassiorp.auth.entity.User;
import com.cassiorp.auth.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;

  public AuthenticationService(
      UserRepository userRepository,
      AuthenticationManager authenticationManager,
      PasswordEncoder passwordEncoder
  ) {
    this.authenticationManager = authenticationManager;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public User signup(RegisterUserDto input) {
    var user = User.builder()
        .fullName(input.getFullName())
        .email(input.getEmail())
        .password(passwordEncoder.encode(input.getPassword()))
        .build();

    return userRepository.save(user);
  }

  public User authenticate(LoginUserDto input) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            input.getEmail(),
            input.getPassword()
        )
    );

    return userRepository.findByEmail(input.getEmail()).orElseThrow();
  }

  public List<User> allUsers() {
    return userRepository.findAll();
  }
}