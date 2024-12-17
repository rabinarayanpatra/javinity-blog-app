package com.javinity.app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a "like" made by a user on a post.
 * <p>
 * Ensures that each combination of user and post is unique.
 * </p>
 */
@Entity
@Getter
@Setter
@Table( name = "likes", uniqueConstraints = @UniqueConstraint( columnNames = { "post_id", "user_id" } ) )
public class Like extends BaseEntity {

  /**
   * The user who liked the post.
   */
  @ManyToOne
  private User user;

  /**
   * The post that was liked.
   */
  @ManyToOne
  private Post post;
}
