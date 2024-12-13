package com.javinity.app.security.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.javinity.app.enums.Role;
import com.javinity.app.security.filter.JwtAuthFilter;

import lombok.RequiredArgsConstructor;

/**
 * SecurityConfiguration configures the Spring Security to enable web security support and provide HTTP security
 * configurations. It defines a security filter chain that incorporates JWT authentication and specifies which endpoints
 * are public.
 *
 * <p>This configuration disables CSRF protection for simplicity and statelessness, and
 * configures session management to be stateless, indicating that no session will be created or used by Spring
 * Security.</p>
 *
 * <p>It also configures a custom JWT authentication filter to be added before Spring Security's
 * default UsernamePasswordAuthenticationFilter to support JWT based authentication.</p>
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

  /**
   * URLs that are publicly accessible without authentication.
   */
  private static final String[] publicUrls = { "/api/v1/auth/**", "/api/v1/health-check", "/v3/api-docs/**",
      "/swagger-ui/**" };

  /**
   * Custom JWT Authentication Filter.
   */
  private final JwtAuthFilter jwtAuthFilter;

  /**
   * Authentication provider for custom authentication logic.
   */
  private final AuthenticationProvider authenticationProvider;

  /**
   * Configures the {@link HttpSecurity} to set up Spring Security's filter chain. This configuration ensures that
   * requests to public URLs are permitted without authentication, while all other requests must be authenticated. It
   * also configures the session management to prevent session creation, ensuring the API is stateless, and adds the
   * custom JWT Authentication Filter to the security filter chain.
   *
   * @param httpSecurity the {@link HttpSecurity} to modify
   * @return the {@link SecurityFilterChain} that has been configured
   * @throws Exception if an error occurs during configuration
   */

  @Bean
  public SecurityFilterChain securityFilterChain( final HttpSecurity httpSecurity ) throws Exception {
    httpSecurity.csrf( AbstractHttpConfigurer::disable )
                .authorizeHttpRequests( authorizeHttpRequests -> authorizeHttpRequests.requestMatchers( publicUrls )
                                                                                      .permitAll()
                                                                                      .requestMatchers(
                                                                                          "/api/v1/admin/**" )
                                                                                      .hasAuthority( Role.ADMIN.name() )
                                                                                      .anyRequest()
                                                                                      .authenticated() )
                .sessionManagement( sm -> sm.sessionCreationPolicy( SessionCreationPolicy.STATELESS ) )
                .authenticationProvider( authenticationProvider )
                .addFilterBefore( jwtAuthFilter, UsernamePasswordAuthenticationFilter.class );
    return httpSecurity.build();
  }
}
