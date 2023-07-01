package kr.binarybard.hireo.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

class DateUtilsTest {

	@Test
	@DisplayName("지난 시간을 '년' 단위로 반환하는지 확인한다")
	void testGetElapsedDateTimeInYears() {
		LocalDateTime past = LocalDateTime.now().minus(2, ChronoUnit.YEARS);
		assertThat(DateUtils.getElapsedDateTime(past)).isEqualTo("2년 전");
	}

	@Test
	@DisplayName("지난 시간을 '개월' 단위로 반환하는지 확인한다")
	void testGetElapsedDateTimeInMonths() {
		LocalDateTime past = LocalDateTime.now().minus(3, ChronoUnit.MONTHS);
		assertThat(DateUtils.getElapsedDateTime(past)).isEqualTo("3개월 전");
	}

	@Test
	@DisplayName("지난 시간을 '일' 단위로 반환하는지 확인한다")
	void testGetElapsedDateTimeInDays() {
		LocalDateTime past = LocalDateTime.now().minus(4, ChronoUnit.DAYS);
		assertThat(DateUtils.getElapsedDateTime(past)).isEqualTo("4일 전");
	}

	@Test
	@DisplayName("지난 시간을 '시간' 단위로 반환하는지 확인한다")
	void testGetElapsedDateTimeInHours() {
		LocalDateTime past = LocalDateTime.now().minus(5, ChronoUnit.HOURS);
		assertThat(DateUtils.getElapsedDateTime(past)).isEqualTo("5시간 전");
	}

	@Test
	@DisplayName("지난 시간을 '분' 단위로 반환하는지 확인한다")
	void testGetElapsedDateTimeInMinutes() {
		LocalDateTime past = LocalDateTime.now().minus(6, ChronoUnit.MINUTES);
		assertThat(DateUtils.getElapsedDateTime(past)).isEqualTo("6분 전");
	}

	@Test
	@DisplayName("지난 시간을 '초' 단위로 반환하는지 확인한다")
	void testGetElapsedDateTimeInSeconds() {
		LocalDateTime past = LocalDateTime.now().minus(7, ChronoUnit.SECONDS);
		assertThat(DateUtils.getElapsedDateTime(past)).isEqualTo("7초 전");
	}

	@Test
	@DisplayName("방금 전 시간을 제대로 반환하는지 확인한다")
	void testGetElapsedDateTimeJustNow() {
		LocalDateTime now = LocalDateTime.now();
		assertThat(DateUtils.getElapsedDateTime(now)).isEqualTo("방금 전");
	}
}
