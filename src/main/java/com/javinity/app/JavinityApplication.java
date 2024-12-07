package com.javinity.app;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;

/**
 * The entry point for the Javinity Spring Boot application.
 * <p>
 * This class initializes and runs the application using {@link SpringApplication#run(Class, String...)}.
 * </p>
 *
 * @author Rabinarayan Patra
 * @version 1.0
 */
@SpringBootApplication
public class JavinityApplication {

  /**
   * The main method to start the Javinity Spring Boot application.
   *
   * @param args the command-line arguments passed to the application
   */
  public static void main( final String[] args ) {
    SpringApplication.run( JavinityApplication.class, args );
  }

  /**
   * Initializes the application by setting the default time zone to UTC.
   *
   * <p>This method is executed after the bean's properties have been set and is
   * annotated with {@link PostConstruct}, ensuring it runs during the bean initialization phase.</p>
   */
  @PostConstruct
  public void init() {
    // Set default time zone to UTC
    TimeZone.setDefault( TimeZone.getTimeZone( "UTC" ) );
  }

}