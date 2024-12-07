package com.javinity.app.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

/**
 * Utility class for working with date and time.
 *
 * <p>Provides static methods to retrieve the current date, time, and date-time
 * based on the system's default time zone.</p>
 *
 * <p>This class cannot be instantiated.</p>
 */
public class DateTimeUtils {

  /**
   * Private constructor to prevent instantiation of the utility class.
   *
   * <p>Throws {@link UnsupportedOperationException} if instantiation is attempted.</p>
   */
  private DateTimeUtils() {
    throw new UnsupportedOperationException( "Utility class" );
  }

  /**
   * Retrieves the current date using the system's default time zone.
   *
   * @return the current date as a {@link LocalDate}.
   */
  public static LocalDate getCurrentDate() {
    return LocalDate.now( ZoneId.systemDefault() );
  }

  /**
   * Retrieves the current time using the system's default time zone.
   *
   * @return the current time as a {@link LocalTime}.
   */
  public static LocalTime getCurrentTime() {
    return LocalTime.now( ZoneId.systemDefault() );
  }

  /**
   * Retrieves the current date and time using the system's default time zone.
   *
   * @return the current date and time as a {@link LocalDateTime}.
   */
  public static LocalDateTime getCurrentDateTime() {
    return LocalDateTime.now( ZoneId.systemDefault() );
  }
}