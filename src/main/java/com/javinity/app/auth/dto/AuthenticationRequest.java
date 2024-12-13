package com.javinity.app.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO for authentication request data. This class is used to encapsulate the login credentials provided by the client,
 * specifically the user's email and password. It leverages Lombok annotations for boilerplate code reduction in terms
 * of getters, setters, and constructors.
 */
@Getter
@Setter
@NoArgsConstructor  // Generates a no-argument constructor.
@AllArgsConstructor // Generates a constructor with arguments for all fields.
@Builder            // Provides the Builder pattern for constructing instances.
public class AuthenticationRequest {
  /**
   * The email of the user attempting to log in. This serves as the username in the authentication process.
   */
  private String email;

  /**
   * The password of the user attempting to log in. This will be checked against the encoded password stored in the
   * database for authentication.
   */
  private String password;
}
