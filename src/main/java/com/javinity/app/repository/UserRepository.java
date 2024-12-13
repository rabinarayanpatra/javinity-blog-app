package com.javinity.app.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javinity.app.entity.User;

/**
 * Repository interface for performing CRUD operations on {@link User} entities.
 * <p>
 * Extends {@link JpaRepository} to provide basic JPA functionalities and custom query methods.
 * </p>
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

  /**
   * Retrieves a {@link User} entity by its email address.
   *
   * @param email the email address to search for.
   * @return an {@link Optional} containing the found {@link User}, or empty if no user is found.
   */
  Optional<User> findByEmail( String email );
}