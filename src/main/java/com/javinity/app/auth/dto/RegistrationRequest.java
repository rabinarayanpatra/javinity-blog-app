package com.javinity.app.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO for capturing registration request data from the client. This class is used to encapsulate user information
 * provided during the registration process, including the user's name, email, and password. It leverages Lombok
 * annotations to reduce boilerplate code by automatically generating getters, setters, constructors, and supporting the
 * Builder pattern.
 */
@Getter // Generates getter methods for all fields.
@Setter // Generates setter methods for all fields.
@NoArgsConstructor // Generates a no-argument constructor.
@AllArgsConstructor // Generates a constructor with parameters for all fields.
@Builder // Provides the Builder pattern for constructing instances.
public class RegistrationRequest {
  /**
   * The name of the user attempting to register. This is typically used for identification within the application and
   * may not be unique.
   */
  private String name;

  /**
   * The email address of the user attempting to register. This acts as a unique identifier for the user within the
   * application and is used during the login process.
   */
  private String email;

  /**
   * The password chosen by the user during the registration process. This will be stored in an encoded format within
   * the application's database for security purposes.
   */
  private String password;
}
