package com.javinity.app.security.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.javinity.app.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * Configuration class for defining beans specifically related to Spring Security. This class is responsible for setting
 * up the authentication provider, password encoder, user details service, and authentication manager. It uses the
 * {@link UserRepository} to fetch user details and configure authentication mechanisms accordingly.
 */
@Configuration
@RequiredArgsConstructor
public class BeansConfiguration {

  private final UserRepository userRepository;

  /**
   * Defines the {@link UserDetailsService} bean used by Spring Security to load user-specific data. It uses the
   * {@link UserRepository} to fetch user details based on the username (in this case, the email).
   *
   * @return A custom {@link UserDetailsService} that fetches user details from the database.
   * @throws UsernameNotFoundException if the user is not found in the database.
   */
  @Bean
  public UserDetailsService userDetailsService() {
    return username -> userRepository.findByEmail( username )
                                     .orElseThrow( () -> new UsernameNotFoundException( "User not found" ) );
  }

  /**
   * Configures the {@link AuthenticationProvider} bean using a {@link DaoAuthenticationProvider}. This provider is set
   * up with the custom {@link UserDetailsService} and {@link PasswordEncoder} to handle authentication processes.
   *
   * @return The configured {@link AuthenticationProvider}.
   */
  @Bean
  public AuthenticationProvider authenticationProvider() {
    final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService( userDetailsService() );
    authenticationProvider.setPasswordEncoder( passwordEncoder() );
    return authenticationProvider;
  }

  /**
   * Defines the {@link PasswordEncoder} bean that Spring Security will use for encoding passwords. In this case, a
   * {@link BCryptPasswordEncoder} is used for its robust hashing functionalities.
   *
   * @return The {@link PasswordEncoder} to be used across the application.
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * Configures the {@link AuthenticationManager} bean to manage authentication within the application. It retrieves the
   * authentication manager from {@link AuthenticationConfiguration}.
   *
   * @param authenticationConfiguration The {@link AuthenticationConfiguration} used by Spring Security.
   * @return The global {@link AuthenticationManager}.
   * @throws Exception if there is an error in creating the authentication manager.
   */
  @Bean
  public AuthenticationManager authenticationManager( final AuthenticationConfiguration authenticationConfiguration )
      throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }
}
