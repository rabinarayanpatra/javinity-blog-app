package com.javinity.app.auth.service;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.javinity.app.auth.dto.AuthenticationRequest;
import com.javinity.app.auth.dto.AuthenticationResponse;
import com.javinity.app.auth.dto.RefreshTokenRequest;
import com.javinity.app.auth.dto.RegistrationRequest;
import com.javinity.app.entity.User;
import com.javinity.app.enums.Role;
import com.javinity.app.repository.UserRepository;
import com.javinity.app.security.service.JwtService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service providing authentication operations such as signup and login. It integrates the application's security
 * mechanisms, including JWT token generation and user authentication, leveraging Spring Security's
 * {@link AuthenticationManager} and custom user details management.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  /**
   * Registers a new user with the given registration details and generates a JWT token for them. It encodes the user's
   * password and assigns them a default role before saving to the database.
   *
   * @param request The registration request containing the user's information.
   * @return An {@link AuthenticationResponse} containing the JWT token and its expiration time.
   */
  public AuthenticationResponse signup( final RegistrationRequest request ) {
    final User user = User.builder()
                          .name( request.getName() )
                          .email( request.getEmail() )
                          .password( passwordEncoder.encode( request.getPassword() ) )
                          .accountLocked( Boolean.FALSE )
                          .credentialsExpired( Boolean.FALSE )
                          .accountExpired( Boolean.FALSE )
                          .enabled( Boolean.TRUE )
                          .role( Role.USER )
                          .build();
    final User savedUser = userRepository.save( user );
    final String token = jwtService.generateToken( savedUser );
    final String refreshToken = jwtService.generateRefreshToken( savedUser );
    return getAuthenticationResponse( token, refreshToken );
  }

  /**
   * Authenticates a user with the provided login credentials. It throws {@link UsernameNotFoundException} if the user
   * does not exist. Upon successful authentication, a JWT token is generated for the user.
   *
   * @param request The authentication request containing the user's login credentials.
   * @return An {@link AuthenticationResponse} containing the JWT token and its expiration time.
   */
  public AuthenticationResponse login( final AuthenticationRequest request ) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken( request.getEmail(), request.getPassword() ) );
    final User user = userRepository.findByEmail( request.getEmail() )
                                    .orElseThrow( () -> new UsernameNotFoundException( "User not found" ) );
    final String token = jwtService.generateToken( user );
    final String refreshToken = jwtService.generateRefreshToken( user );
    return getAuthenticationResponse( token, refreshToken );
  }

  /**
   * Retrieves the currently authenticated principal user from the Spring Security Context. This method casts the
   * authenticated principal to a {@link User} object. It is primarily used within the service layer to obtain the user
   * details of the currently logged-in user.
   *
   * @return The {@link User} representing the currently authenticated principal.
   * @throws ClassCastException if the authenticated principal cannot be cast to a {@link User}.
   */
  public User getCurrentPrincipalUser() {
    return (User) SecurityContextHolder.getContext()
                                       .getAuthentication()
                                       .getPrincipal();
  }

  /**
   * Processes a refresh token request to generate a new access token. This method extracts the user's email from the
   * provided refresh token, retrieves the corresponding user details, and validates the refresh token. If the
   * validation is successful, a new access token is generated for the user. This approach ensures that users can
   * continue their session without needing to re-authenticate, providing a seamless user experience.
   * <p>
   * The method assumes the refresh token is valid and associated with an existing user. If the refresh token is invalid
   * or does not correspond to an existing user, a security exception is thrown, indicating the authentication failure.
   *
   * @param request The refresh token request containing the refresh token to be validated and used for generating a new
   *                access token.
   * @return An {@link AuthenticationResponse} object containing the new access token and associated data, including the
   * original refresh token.
   * @throws BadCredentialsException   if the refresh token is invalid, indicating a problem with the token's integrity
   *                                   or if it's expired.
   * @throws UsernameNotFoundException if no user is associated with the email extracted from the refresh token,
   *                                   indicating the user does not exist.
   */
  public AuthenticationResponse refreshToken( @NonNull final RefreshTokenRequest request ) {
    final String refreshToken = request.getRefreshToken();

    SecurityContextHolder.getContext()
                         .setAuthentication( null );

    // Extract user email from the refresh token and validate it
    final String userEmail = jwtService.extractUsername( refreshToken );
    if( !StringUtils.hasText( userEmail ) ) {
      throw new BadCredentialsException( "Invalid refresh token" );
    }

    // Retrieve user details
    final UserDetails userDetails = userRepository.findByEmail( userEmail )
                                                  .orElseThrow( () -> new UsernameNotFoundException(
                                                      "User not found with email: " + userEmail ) );

    // Validate the refresh token
    if( !jwtService.isValidToken( refreshToken, userDetails ) ) {
      throw new BadCredentialsException( "Invalid refresh token" );
    }

    // Generate a new access token
    final String newAccessToken = jwtService.generateToken( userDetails );

    // Get and return the authentication response
    return getAuthenticationResponse( newAccessToken, refreshToken );
  }

  /**
   * Constructs an {@link AuthenticationResponse} object containing the access and refresh tokens along with their
   * respective expiration times. This method utilizes the {@code jwtService} to extract expiration dates from both
   * tokens, converting them into {@link LocalDateTime} format based on the system's default timezone.
   *
   * @param token        The JWT access token issued upon successful authentication.
   * @param refreshToken The JWT refresh token that can be used to obtain a new access token once the current one
   *                     expires.
   * @return An instance of {@link AuthenticationResponse} populated with the access token, refresh token, and their
   * expiration times.
   */
  private AuthenticationResponse getAuthenticationResponse( final String token, final String refreshToken ) {
    return AuthenticationResponse.builder()
                                 .accessToken( token )
                                 .accessTokenExpiresAt( jwtService.extractExpiration( token )
                                                                  .toInstant()
                                                                  .atZone( ZoneId.systemDefault() )
                                                                  .toLocalDateTime() )
                                 .refreshToken( refreshToken )
                                 .refreshTokenExpiresAt( jwtService.extractExpiration( refreshToken )
                                                                   .toInstant()
                                                                   .atZone( ZoneId.systemDefault() )
                                                                   .toLocalDateTime() )
                                 .build();
  }

}
