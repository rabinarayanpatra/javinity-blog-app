package com.javinity.app.entity;

import java.util.List;

import com.javinity.app.enums.PostStatus;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a post in the application.
 * <p>
 * A post contains details such as its title, content, associated categories, status, tags, and the user who created
 * it.
 * </p>
 */
@Entity
@Getter
@Setter
public class Post extends BaseEntity {

  /**
   * The user who created the post.
   */
  @ManyToOne
  private User user;

  /**
   * The current status of the post (e.g., DRAFT, PUBLISHED).
   */
  @Enumerated( EnumType.STRING )
  private PostStatus status;

  /**
   * The categories to which the post belongs.
   */
  @ManyToMany
  private List<Category> category;

  /**
   * The title of the post.
   */
  private String title;

  /**
   * The content or body of the post.
   */
  private String content;

  /**
   * A collection of tags associated with the post.
   */
  @ElementCollection
  private List<String> tags;
}
