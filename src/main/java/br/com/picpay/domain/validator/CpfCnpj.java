package br.com.picpay.domain.validator;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static br.com.picpay.util.CpfCnpjUtil.isCnpjValid;
import static br.com.picpay.util.CpfCnpjUtil.isCpfValid;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import static br.com.picpay.util.RegexUtil.*;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = {CpfCnpj.CpfCnpjValidator.class})
public @interface CpfCnpj {

    String message() default "O CPF/CNPJ inserido não é válido.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class CpfCnpjValidator implements ConstraintValidator<CpfCnpj, String> {

        @Override
        public boolean isValid(String cpfCnpj, ConstraintValidatorContext constraintValidatorContext) {
            cpfCnpj = replaceCaracteresComRegex(cpfCnpj, REGEX_APENAS_NUMEROS);
            if (cpfCnpj.length() == 11) return isCpfValid(cpfCnpj);
            else if (cpfCnpj.length() == 14) return isCnpjValid(cpfCnpj);
            return false;
        }

    }


}
