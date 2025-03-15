package com.cassiorp.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class SecurityServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(SecurityServiceApplication.class, args);
  }

}
