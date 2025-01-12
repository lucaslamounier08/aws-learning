package com.lucaslamounier.aws.s3.validator.annotation;

import com.lucaslamounier.aws.s3.validator.BucketExistenceValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BucketExistenceValidator.class)
public @interface BucketExists {

    String message() default "No bucket exists with the configured name.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}