package com.javinity.app.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a category to which posts can belong.
 * <p>
 * Each category has a unique name.
 * </p>
 */
@Entity
@Getter
@Setter
public class Category extends BaseEntity {
  /**
   * The name of the category.
   */
  private String name;
}
