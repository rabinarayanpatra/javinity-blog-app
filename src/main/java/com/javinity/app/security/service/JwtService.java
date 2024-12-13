package com.javinity.app.security.service;

import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * Service component responsible for generating and validating JSON Web Tokens (JWT). It provides functionality to
 * generate a new token for a user, validate an existing token, and extract information like username and expiration
 * date from the token.
 */
@Component
public class JwtService {

  private static final long MILLIS_TO_SECOND = 1000L;
  @Value( "${application.security.jwt.secret-key}" )
  private String secretKey;
  @Value( "${application.security.jwt.expiry-seconds.access-token}" )
  private long accessTokenExpirySeconds;
  @Value( "${application.security.jwt.expiry-seconds.refresh-token}" )
  private long refreshTokenExpirySeconds;

  /**
   * Extracts the username (subject) from the given JWT token.
   *
   * @param token The JWT token from which to extract the username.
   * @return The username extracted from the token.
   */
  public String extractUsername( final CharSequence token ) {
    return extractClaim( token, Claims::getSubject );
  }

  /**
   * Generates a new JWT token for a user without any additional claims.
   *
   * @param userDetails The user details for whom the token is being generated.
   * @return A new JWT token.
   */
  public String generateToken( final UserDetails userDetails ) {
    return generateToken( Collections.emptyMap(), userDetails, accessTokenExpirySeconds );
  }

  /**
   * Generates a new JWT refresh token for a user. This token is used to allow the user to obtain a new access token
   * once the current access token is expired, without needing to log in again.
   *
   * @param userDetails The user details for whom the refresh token is being generated.
   * @return A new JWT refresh token.
   */
  public String generateRefreshToken( final UserDetails userDetails ) {
    return generateToken( Collections.emptyMap(), userDetails, refreshTokenExpirySeconds );
  }

  /**
   * Validates a JWT token against a user's details.
   *
   * @param token       The JWT token to validate.
   * @param userDetails The user details to validate against the token.
   * @return {@code true} if the token is valid and corresponds to the given user details, otherwise {@code false}.
   */
  public boolean isValidToken( final CharSequence token, final UserDetails userDetails ) {
    final String username = extractUsername( token );
    return username.equals( userDetails.getUsername() ) && !isTokenExpired( token );
  }

  /**
   * Extracts the expiration date from the given JWT token.
   *
   * @param token The JWT token from which to extract the expiration date.
   * @return The expiration date of the token.
   */
  public Date extractExpiration( final CharSequence token ) {
    return extractClaim( token, Claims::getExpiration );
  }

  /**
   * Generates a new JWT token for a user with additional claims.
   *
   * @param extraClaims        Additional claims to be included in the token.
   * @param userDetails        The user details for whom the token is being generated.
   * @param tokenExpirySeconds The token expiry time in seconds.
   * @return A new JWT token.
   */
  private String generateToken( final Map<String, Object> extraClaims, final UserDetails userDetails,
      final long tokenExpirySeconds ) {
    return Jwts.builder()
               .claims( extraClaims )
               .subject( userDetails.getUsername() )
               .issuedAt( new Date() )
               .expiration( new Date( System.currentTimeMillis() + tokenExpirySeconds * MILLIS_TO_SECOND ) )
               .signWith( getSignInKey() )
               .compact();
  }

  /**
   * Checks if the JWT token has expired.
   *
   * @param token The JWT token to check.
   * @return {@code true} if the token has expired, otherwise {@code false}.
   */
  private boolean isTokenExpired( final CharSequence token ) {
    return extractExpiration( token ).before( new Date() );
  }

  /**
   * Extracts a specific claim from the given JWT token.
   *
   * @param token          The JWT token from which to extract the claim.
   * @param claimsResolver The function to apply to the claims for extracting the desired information.
   * @param <T>            The type of the information to extract.
   * @return The extracted information.
   */
  private <T> T extractClaim( final CharSequence token, final Function<Claims, T> claimsResolver ) {
    final Claims claims = extractAllClaims( token );
    return claimsResolver.apply( claims );
  }

  /**
   * Extracts all claims from the given JWT token.
   *
   * @param token The JWT token from which to extract the claims.
   * @return The claims extracted from the token.
   */
  private Claims extractAllClaims( final CharSequence token ) {
    return Jwts.parser()
               .verifyWith( getSignInKey() )
               .build()
               .parseSignedClaims( token )
               .getPayload();
  }

  /**
   * Retrieves the signing key used for verifying the JWT token, derived from the base64-encoded secret key.
   *
   * @return The secret key used for signing JWT tokens.
   */
  private SecretKey getSignInKey() {
    final byte[] secretKeyBytes = Decoders.BASE64.decode( secretKey );
    return Keys.hmacShaKeyFor( secretKeyBytes );
  }
}
