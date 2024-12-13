package com.javinity.app.auth.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO for the authentication response data. This class encapsulates the authentication token (e.g., JWT) provided to
 * the client upon successful authentication, along with its expiration time. It uses Lombok annotations to
 * automatically generate getters, setters, a no-argument constructor, and an all-argument constructor, as well as
 * supporting the Builder pattern.
 */
@Getter // Generates getter methods for all fields.
@Setter // Generates setter methods for all fields.
@Builder // Enables the Builder pattern for this class.
@NoArgsConstructor // Generates a no-argument constructor.
@AllArgsConstructor // Generates a constructor that accepts arguments for all fields.
public class AuthenticationResponse {
  /**
   * The JWT access token issued to the client upon successful authentication. This token allows the client to make
   * authenticated requests by including it in the request headers.
   */
  private String accessToken;

  /**
   * The JWT refresh token that can be used to obtain a new access token once the current access token expires. Refresh
   * tokens have a longer validity period than access tokens.
   */
  private String refreshToken;

  /**
   * The expiration time of the access token. This informs the client about when the access token will expire and a new
   * token (either by re-authenticating or using the refresh token) will be required.
   */
  private LocalDateTime accessTokenExpiresAt;

  /**
   * The expiration time of the refresh token. This indicates when the refresh token itself will expire, necessitating a
   * full re-authentication process.
   */
  private LocalDateTime refreshTokenExpiresAt;
}
