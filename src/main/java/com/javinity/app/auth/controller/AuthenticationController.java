package com.javinity.app.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javinity.app.auth.dto.AuthenticationRequest;
import com.javinity.app.auth.dto.AuthenticationResponse;
import com.javinity.app.auth.dto.RefreshTokenRequest;
import com.javinity.app.auth.dto.RegistrationRequest;
import com.javinity.app.auth.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

/**
 * REST controller for handling authentication requests including user signup and login. This controller provides
 * endpoints for registering new users and for authenticating existing users, leveraging the
 * {@link AuthenticationService} for the business logic and interactions with the security layer.
 */
@RestController
@RequestMapping( "/api/v1/auth" )
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService authenticationService;

  /**
   * Endpoint for user registration. Accepts a registration request, processes it through the
   * {@link AuthenticationService}, and returns an authentication response.
   *
   * @param request The {@link RegistrationRequest} containing the user's registration details.
   * @return A {@link ResponseEntity} containing the {@link AuthenticationResponse} with the JWT token and its
   * expiration time, indicating successful registration.
   */
  @PostMapping( "/signup" )
  public ResponseEntity<AuthenticationResponse> signup( @RequestBody final RegistrationRequest request ) {
    return ResponseEntity.ok( authenticationService.signup( request ) );
  }

  /**
   * Endpoint for user login. Accepts a login request, authenticates the user through the {@link AuthenticationService},
   * and returns an authentication response.
   *
   * @param request The {@link AuthenticationRequest} containing the user's login credentials.
   * @return A {@link ResponseEntity} containing the {@link AuthenticationResponse} with the JWT token and its
   * expiration time, indicating successful authentication.
   */
  @PostMapping( "/login" )
  public ResponseEntity<AuthenticationResponse> login( @RequestBody final AuthenticationRequest request ) {
    return ResponseEntity.ok( authenticationService.login( request ) );
  }

  /**
   * Handles a request to refresh an access token using a provided refresh token. Upon successful validation of the
   * refresh token, a new access token is generated and returned along with the original refresh token.
   *
   * @param request Contains the refresh token submitted by the client.
   * @return ResponseEntity containing the new access token and associated data.
   */
  @PostMapping( "/refresh-token" )
  public ResponseEntity<AuthenticationResponse> refreshToken( @RequestBody final RefreshTokenRequest request ) {
    return ResponseEntity.ok( authenticationService.refreshToken( request ) );
  }
}
