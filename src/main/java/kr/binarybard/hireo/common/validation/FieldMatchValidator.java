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
		final BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(value);

		try {
			final Object firstValue = wrapper.getPropertyValue(firstFieldName);
			final Object secondValue = wrapper.getPropertyValue(secondFieldName);

			return Objects.equals(firstValue, secondValue);
		} catch (BeansException e) {
			return false;
		}
	}
}
