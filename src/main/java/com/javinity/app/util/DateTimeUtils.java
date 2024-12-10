package com.javinity.app.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Utility class providing methods for date and time operations.
 * <p>
 * This class is not intended to be instantiated.
 * </p>
 */
public class DateTimeUtils {

  /**
   * Private constructor to prevent instantiation of this utility class.
   *
   * @throws UnsupportedOperationException always thrown to enforce non-instantiability
   */
  private DateTimeUtils() {
    throw new UnsupportedOperationException( "Utility class" );
  }

  /**
   * Retrieves the current instant in UTC.
   *
   * @return the current {@link Instant}
   */
  public static Instant getCurrentInstant() {
    return Instant.now();
  }

  /**
   * Retrieves the current date and time in the system default time zone.
   *
   * @return the current {@link ZonedDateTime}
   */
  public static ZonedDateTime getCurrentZonedDateTime() {
    return ZonedDateTime.now( ZoneId.systemDefault() );
  }
}