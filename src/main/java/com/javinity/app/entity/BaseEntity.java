package com.javinity.app.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.javinity.app.util.DateTimeUtils;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

/**
 * Abstract base class for entities, providing common fields and lifecycle behavior.
 *
 * <p>Includes a universally unique identifier (UUID) as the primary key and
 * timestamp fields for creation and update tracking. Automatically manages these timestamps during persistence
 * events.</p>
 *
 * <p>Annotations:</p>
 * <ul>
 *   <li>{@link MappedSuperclass}: Marks this class as a base for entity inheritance.</li>
 *   <li>{@link Id}: Indicates the {@code id} field as the primary key.</li>
 *   <li>{@link PrePersist} and {@link PreUpdate}: Hook into JPA lifecycle events
 *       to automatically update timestamps.</li>
 * </ul>
 */
@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {
  @Id
  @GeneratedValue( strategy = GenerationType.UUID )
  protected UUID id;

  @Column( nullable = false, updatable = false )
  protected LocalDateTime createdAt;

  @Column( nullable = false )
  protected LocalDateTime updatedAt;

  /**
   * Lifecycle method triggered before the entity is persisted.
   *
   * <p>Sets the {@code createdAt} and {@code updatedAt} timestamps to the
   * current date and time.</p>
   */
  @PrePersist
  protected void onCreate() {
    this.createdAt = DateTimeUtils.getCurrentDateTime();
    this.updatedAt = DateTimeUtils.getCurrentDateTime();
  }

  /**
   * Lifecycle method triggered before the entity is updated.
   *
   * <p>Updates the {@code updatedAt} timestamp to the current date and time.</p>
   */
  @PreUpdate
  protected void onUpdate() {
    this.updatedAt = DateTimeUtils.getCurrentDateTime();
  }
}
