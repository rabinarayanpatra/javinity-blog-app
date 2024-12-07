package com.javinity.app.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockStatic;

class DateTimeUtilsTest {

  @Test
  void testGetCurrentDate() {
    // given
    final LocalDate expectedDate = LocalDate.of( 2023, 12, 7 ); // Sample date
    final ZoneId defaultZoneId = ZoneId.systemDefault();

    try( final MockedStatic<LocalDate> mockedLocalDate = mockStatic( LocalDate.class ) ) {
      mockedLocalDate.when( () -> LocalDate.now( defaultZoneId ) ).thenReturn( expectedDate );

      // when
      final LocalDate result = DateTimeUtils.getCurrentDate();

      // then
      assertThat( result ).isEqualTo( expectedDate );
    }
  }

  @Test
  void testGetCurrentTime() {
    // given
    final LocalTime expectedTime = LocalTime.of( 14, 30, 0 ); // Sample time
    final ZoneId defaultZoneId = ZoneId.systemDefault();

    try( final MockedStatic<LocalTime> mockedLocalTime = mockStatic( LocalTime.class ) ) {
      mockedLocalTime.when( () -> LocalTime.now( defaultZoneId ) ).thenReturn( expectedTime );

      // when
      final LocalTime result = DateTimeUtils.getCurrentTime();

      // then
      assertThat( result ).isEqualTo( expectedTime );
    }
  }

  @Test
  void testGetCurrentDateTime() {
    // given
    final LocalDateTime expectedDateTime = LocalDateTime.of( 2023, 12, 7, 14, 30, 0 ); // Sample date-time
    final ZoneId defaultZoneId = ZoneId.systemDefault();

    try( final MockedStatic<LocalDateTime> mockedLocalDateTime = mockStatic( LocalDateTime.class ) ) {
      mockedLocalDateTime.when( () -> LocalDateTime.now( defaultZoneId ) ).thenReturn( expectedDateTime );

      // when
      final LocalDateTime result = DateTimeUtils.getCurrentDateTime();

      // then
      assertThat( result ).isEqualTo( expectedDateTime );
    }
  }
}