package kr.binarybard.hireo.utils;

public class ConvertUtils {
	private ConvertUtils() {
		throw new IllegalStateException("인스턴스화 할 수 없습니다.");
	}

	/**
	 * 안전하지 않은 캐스팅 시 {@code @SuppressWarnings("unchecked")}를 사용하여 경고를 회피합니다.
	 * 정말 부득이한 경우에는 이 메서드를 사용하세요.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T uncheckedCast(Object object) {
		return (T) object;
	}
}
