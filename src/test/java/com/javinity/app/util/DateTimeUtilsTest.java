package com.javinity.app.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockStatic;

class DateTimeUtilsTest {

  @Test
  void testGetCurrentInstant() {
    // given
    final Instant expectedInstant = Instant.parse( "2023-12-07T14:30:00Z" ); // Sample Instant

    try( final MockedStatic<Instant> mockedInstant = mockStatic( Instant.class ) ) {
      mockedInstant.when( Instant::now ).thenReturn( expectedInstant );

      // when
      final Instant result = DateTimeUtils.getCurrentInstant();

      // then
      assertThat( result ).isEqualTo( expectedInstant );
    }
  }

  @Test
  void testGetCurrentZonedDateTime() {
    // given
    final ZonedDateTime expectedZonedDateTime = ZonedDateTime.of( 2023, 12, 7, 14, 30, 0, 0, ZoneId.systemDefault() );

    try( final MockedStatic<ZonedDateTime> mockedZonedDateTime = mockStatic( ZonedDateTime.class ) ) {
      mockedZonedDateTime.when( () -> ZonedDateTime.now( ZoneId.systemDefault() ) ).thenReturn( expectedZonedDateTime );

      // when
      final ZonedDateTime result = DateTimeUtils.getCurrentZonedDateTime();

      // then
      assertThat( result ).isEqualTo( expectedZonedDateTime );
    }
  }
}