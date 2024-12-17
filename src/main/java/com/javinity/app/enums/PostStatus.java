package com.javinity.app.enums;

/**
 * Enum representing the status of a post.
 * <ul>
 *   <li>{@code DRAFT} - The post is in draft state.</li>
 *   <li>{@code SCHEDULED} - The post is scheduled for publishing.</li>
 *   <li>{@code PUBLISHED} - The post has been published.</li>
 * </ul>
 */
public enum PostStatus {
  /**
   * The post is in draft state.
   */
  DRAFT,

  /**
   * The post is scheduled for publishing.
   */
  SCHEDULED,

  /**
   * The post has been published.
   */
  PUBLISHED
}
