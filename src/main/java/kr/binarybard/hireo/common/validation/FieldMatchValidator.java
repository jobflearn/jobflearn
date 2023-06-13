package kr.binarybard.hireo.common.validation;

import java.util.Objects;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyAccessorFactory;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kr.binarybard.hireo.common.validation.constraints.FieldMatch;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
	private String firstFieldName;
	private String secondFieldName;

	@Override
	public void initialize(FieldMatch constraintAnnotation) {
		this.firstFieldName = constraintAnnotation.first();
		this.secondFieldName = constraintAnnotation.second();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		// 자바 빈에 대한 기본적인 작업을 지원함
		final BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(value);

		try {
			// getPropertyValue()를 사용해서 두 필드의 값을 가져옴
			final Object firstValue = wrapper.getPropertyValue(firstFieldName);
			final Object secondValue = wrapper.getPropertyValue(secondFieldName);

			return Objects.equals(firstValue, secondValue);
		} catch (BeansException e) {
			return false;
		}
	}
}
