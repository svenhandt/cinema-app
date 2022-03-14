package com.svenhandt.app.cinemaapp.commands.booking.validation;

import com.svenhandt.app.cinemaapp.commands.booking.ExpiryDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;


public class ExpiryDateValidator implements ConstraintValidator<ValidExpiryDate, ExpiryDate>
{
	@Override
	public boolean isValid(ExpiryDate expiryDateObj, ConstraintValidatorContext constraintValidatorContext)
	{
		int expiryMonth = Integer.parseInt(expiryDateObj.getExpiryMonth());
		int expiryYear = Integer.parseInt(expiryDateObj.getExpiryYear());
		LocalDate currentDate = LocalDate.now();
		LocalDate expiryDate = LocalDate.of(expiryYear, expiryMonth, currentDate.getDayOfMonth());
		return expiryDate.isEqual(currentDate) || expiryDate.isAfter(currentDate);
	}
}
