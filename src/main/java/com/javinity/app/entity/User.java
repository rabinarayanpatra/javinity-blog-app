package com.javinity.app.entity;

import java.time.LocalDateTime;

import com.javinity.app.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
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
@Entity
@Getter
@Setter
public class User extends BaseEntity {

  @Column( nullable = false, length = 70 )
  private String name;

  @Column( nullable = false, unique = true, length = 100 )
  private String email;

  @Column( length = 255 )
  private String bio;

  @Column( length = 500 )
  private String profilePicUrl;

  @Column( nullable = false )
  private Boolean active = false;

  @Column( nullable = false )
  private Boolean emailVerified;

  private LocalDateTime lastLogin;

  @Enumerated( EnumType.STRING )
  @Column( nullable = false )
  private Role role;
}