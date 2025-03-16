package com.cassiorp.auth.service;

import com.cassiorp.auth.entity.User;
import com.cassiorp.auth.exception.ConflictException;
import com.cassiorp.auth.exception.EntityNotFoundException;
import com.cassiorp.auth.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  public static final String EMAIL_ALREADY_EXISTS = "Email already exists!";
  private static final String USER_NOT_FOUND_ERROR = "User not found";

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public User createUser(User user) {
    validateIfEmailAlreadyExists(user.getEmail());
    encondePassword(user);
    return userRepository.save(user);
  }

  private void encondePassword(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
  }

  private void validateIfEmailAlreadyExists(String email) {
    if (userRepository.findByEmail(email).isPresent()) {
      logger.error(EMAIL_ALREADY_EXISTS);
      throw new ConflictException(EMAIL_ALREADY_EXISTS);
    }
  }

  public User findUserByEmail(String email) {
    return userRepository
        .findByEmail(email)
        .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_ERROR));
  }

  public User findUserById(String id) {
    return userRepository
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_ERROR));
  }
}