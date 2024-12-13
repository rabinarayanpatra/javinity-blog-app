package com.javinity.app.entity;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.javinity.app.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a user within the application.
 * <p>
 * This entity contains user-specific details such as name, email, role, and other profile-related attributes. It
 * extends {@link BaseEntity}.
 * </p>
 *
 * @see BaseEntity
 * @see Role
 */
@Entity( name = "users" )
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity implements UserDetails {

  @Column( nullable = false, length = 70 )
  private String name;

  @Column( nullable = false, unique = true, length = 100 )
  private String email;

  private String password;

  @Column
  private String bio;

  @Column( length = 500 )
  private String profilePicUrl;

  private Boolean emailVerified;
  private Instant lastLogin;

  @Enumerated( EnumType.STRING )
  @Column( nullable = false )
  private Role role;
  private Boolean accountExpired;
  private Boolean accountLocked;
  private Boolean credentialsExpired;
  private Boolean enabled;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of( new SimpleGrantedAuthority( role.toString() ) );
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }
}