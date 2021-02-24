package com.svenhandt.app.cinemaapp.forms.creditcardform.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { ExpiryDateValidator.class })
public @interface ValidExpiryDate
{

	String message() default "Das Ablaufdatum muss in der Zukunft liegen";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
