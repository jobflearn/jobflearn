package kr.binarybard.hireo.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("변환 유틸리티 테스트")
class ConvertUtilsTest {

	@Test
	@DisplayName("안전하지 않은 캐스팅이 성공적으로 수행된다")
	void testUncheckedCast() {
		String original = "test";

		String result = ConvertUtils.uncheckedCast(original);

		assertEquals(original, result);
	}

	@Test
	@DisplayName("ConvertUtils 클래스는 인스턴스화 할 수 없다")
	void testPrivateConstructor() throws NoSuchMethodException {
		Constructor<ConvertUtils> constructor = ConvertUtils.class.getDeclaredConstructor();
		assertTrue(Modifier.isPrivate(constructor.getModifiers()));

		constructor.setAccessible(true);

		assertThrows(InvocationTargetException.class, constructor::newInstance);
	}
}
