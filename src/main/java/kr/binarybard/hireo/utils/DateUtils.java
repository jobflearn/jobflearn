package kr.binarybard.hireo.utils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DateUtils {
	private DateUtils() {
		throw new IllegalStateException("인스턴스화 할 수 없습니다.");
	}

	public static String getElapsedDateTime(LocalDateTime past) {
		LocalDateTime now = LocalDateTime.now();

		long years = ChronoUnit.YEARS.between(past, now);
		long months = ChronoUnit.MONTHS.between(past, now);
		long days = ChronoUnit.DAYS.between(past, now);
		long hours = ChronoUnit.HOURS.between(past, now);
		long minutes = ChronoUnit.MINUTES.between(past, now);
		long seconds = ChronoUnit.SECONDS.between(past, now);

		if (years > 0) {
			return years + "년 전";
		} else if (months > 0) {
			return months + "개월 전";
		} else if (days > 0) {
			return days + "일 전";
		} else if (hours > 0) {
			return hours + "시간 전";
		} else if (minutes > 0) {
			return minutes + "분 전";
		} else if (seconds > 0) {
			return seconds + "초 전";
		} else {
			return "방금 전";
		}
	}
}
