package com.javinity.app.security.configuration;

import java.security.SecureRandom;
import java.util.Optional;
import java.util.random.RandomGenerator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.javinity.app.entity.User;
import com.javinity.app.enums.Role;
import com.javinity.app.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Configuration class responsible for setting up a default admin user upon application startup. It utilizes Spring's
 * {@link Configuration} to denote that the class provides bean definitions to the application context. Additionally,
 * this class uses a {@link CommandLineRunner} bean to execute the admin user creation or update logic right after the
 * application starts. It checks for an existing admin user by email; if not found, it creates a new admin user with a
 * generated random password. If the admin user already exists, it updates the user's password.
 */
@Configuration
@Slf4j
public class DefaultAdminUserConfig {
  private static final RandomGenerator secureRandom = new SecureRandom();
  @Value( "${application.config.default.admin-email}" )
  private String adminEmail;
  @Value( "${application.config.default.admin-password-length}" )
  private int adminPasswordLength;
  @Value( "${application.config.default.password-char-pool}" )
  private String passwordCharPool;

  /**
   * Provides a {@link CommandLineRunner} bean that ensures the existence of a default admin user in the database at
   * application startup. If the admin user does not exist, it creates one with a random password. If the admin user
   * already exists, it updates the password. The admin user details, including the generated random password, are
   * logged for administrative purposes.
   *
   * @param userRepository  The repository for accessing and modifying {@link User} entities.
   * @param passwordEncoder The encoder for hashing user passwords.
   * @return A {@link CommandLineRunner} that checks for the admin user's existence and creates or updates it as
   * necessary.
   */
  @Bean
  CommandLineRunner createDefaultAdminUser( final UserRepository userRepository,
      final PasswordEncoder passwordEncoder ) {
    return args -> {
      final Optional<User> adminUser = userRepository.findByEmail( adminEmail );
      final String randomPassword = generateRandomPassword( adminPasswordLength );

      if( adminUser.isEmpty() ) {
        final User user = User.builder()
                              .name( "Administrator" )
                              .email( adminEmail )
                              .password( passwordEncoder.encode( randomPassword ) )
                              .role( Role.ADMIN )
                              .accountExpired( Boolean.FALSE )
                              .accountLocked( Boolean.FALSE )
                              .credentialsExpired( Boolean.FALSE )
                              .enabled( Boolean.TRUE )
                              .build();

        userRepository.save( user );
        log.info( "Default admin user created with email: {} and password: {}", adminEmail, randomPassword );
      } else {
        // Update existing user's password
        final User existingUser = adminUser.get();
        existingUser.setPassword( passwordEncoder.encode( randomPassword ) );
        userRepository.save( existingUser );
        log.info( "Admin user already exists. Password updated with: {}", randomPassword );
      }
    };
  }

  /**
   * Generates a random password using the specified character pool and length. This private helper method is used
   * during the creation or updating of the default admin user's password.
   *
   * @param length The desired length of the generated password.
   * @return A {@link String} containing the randomly generated password.
   * @throws IllegalArgumentException If the specified length is less than or equal to zero.
   */
  private String generateRandomPassword( final int length ) {
    if( length <= 0 ) {
      throw new IllegalArgumentException( "Length must be positive" );
    }
    final StringBuilder password = new StringBuilder( length );
    for( int i = 0; i < length; i++ ) {
      final int index = secureRandom.nextInt( passwordCharPool.length() );
      password.append( passwordCharPool.charAt( index ) );
    }
    return password.toString();
  }
}
