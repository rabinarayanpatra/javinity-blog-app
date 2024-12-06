package com.javinity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
}