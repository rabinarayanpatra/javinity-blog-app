package com.javinity.app.security.filter;

import java.io.IOException;
import java.util.Objects;

import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.javinity.app.security.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * A custom filter that extends {@link OncePerRequestFilter} to intercept HTTP requests and perform JWT token
 * validation. It checks the {@code Authorization} header for a JWT token, validates the token, and sets the
 * authentication in the {@link SecurityContextHolder} if the token is valid. This filter ensures that only valid and
 * authenticated requests are processed by the application.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;

  /**
   * Intercepts each request exactly once to check for a valid JWT token in the {@code Authorization} header. If a valid
   * token is found, it authenticates the user, allowing the request to proceed to the controller.
   *
   * @param request     The HTTP request.
   * @param response    The HTTP response.
   * @param filterChain The filter chain allowing the request to proceed to the next filter or resource.
   * @throws ServletException If a servlet-specific error occurs.
   * @throws IOException      If an I/O error occurs during request processing.
   */
  @Override
  protected void doFilterInternal( @NonNull final HttpServletRequest request,
      @NonNull final HttpServletResponse response, @NonNull final FilterChain filterChain )
      throws ServletException, IOException {
    if( request.getServletPath()
               .contains( "/api/v1/auth" ) ) {
      filterChain.doFilter( request, response );
      return;
    }
    if( request.getServletPath()
               .contains( "/v3/api-docs" ) || request.getServletPath()
                                                     .contains( "/swagger-ui" ) ) {
      filterChain.doFilter( request, response );
      return;
    }

    final String authorizationHeader = request.getHeader( HttpHeaders.AUTHORIZATION );
    if( !StringUtils.hasText( authorizationHeader ) || !authorizationHeader.startsWith( "Bearer " ) ) {
      filterChain.doFilter( request, response );
      return;
    }
    final String jwtToken = authorizationHeader.substring( 7 );
    final String userEmail = jwtService.extractUsername( jwtToken );
    if( StringUtils.hasText( userEmail ) && Objects.isNull( SecurityContextHolder.getContext()
                                                                                 .getAuthentication() ) ) {
      final UserDetails userDetails = userDetailsService.loadUserByUsername( userEmail );
      if( jwtService.isValidToken( jwtToken, userDetails ) ) {
        final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.getAuthorities() );
        authenticationToken.setDetails( new WebAuthenticationDetailsSource().buildDetails( request ) );
        SecurityContextHolder.getContext()
                             .setAuthentication( authenticationToken );
      }
    }
    filterChain.doFilter( request, response );
  }
}
