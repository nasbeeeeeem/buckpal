package io.reflectoring.buckpal.application.port.in;

import java.lang.annotation.Target;

import io.reflectoring.buckpal.application.domain.model.Money;
import jakarta.validation.Constraint;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;


@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = PositiveMoneyValidator.class)
@Documented
public @interface PositiveMoney {
  String message() default "must be positive" + 
    " found: ${validatedValue}";

  Class<?>[] groups() default {};

  Class<? extends Money>[] payload() default{}; 

}
