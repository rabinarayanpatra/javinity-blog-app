package com.javinity.app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a comment made by a user on a post.
 */
@Entity
@Getter
@Setter
public class Comment extends BaseEntity {

  /**
   * The post on which the comment was made.
   */
  @ManyToOne
  private Post post;

  /**
   * The user who made the comment.
   */
  @ManyToOne
  private User user;

  /**
   * The actual content of the comment.
   */
  private String content;
}
