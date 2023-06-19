package kr.binarybard.hireo.utils;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

class ConvertUtilsTest {

	@Test
	void testUncheckedCast() {
		String original = "test";

		String result = ConvertUtils.uncheckedCast(original);

		assertEquals(original, result);
	}

	@Test
	void testPrivateConstructor() throws NoSuchMethodException {
		Constructor<ConvertUtils> constructor = ConvertUtils.class.getDeclaredConstructor();
		assertTrue(Modifier.isPrivate(constructor.getModifiers()));

		constructor.setAccessible(true);

		assertThrows(InvocationTargetException.class, constructor::newInstance);
	}
}
