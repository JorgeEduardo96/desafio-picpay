package br.com.picpay.domain.validator;

import org.springframework.beans.factory.annotation.Value;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.List;
import java.util.regex.Pattern;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = { SegurancaSenha.SegurancaSenhaValidator.class })
public @interface SegurancaSenha {


    String message() default "A senha necessita conter ao menos um caractere da seguinte forma: :requisitos";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    class SegurancaSenhaValidator implements ConstraintValidator<SegurancaSenha, String> {

        @Value("#{'${picpay-desafio.senha.requisitos}'.split(',')}")
        private List<RequisitosSenha> requisitosParaValidar;

        @Override
        public boolean isValid(String senha, ConstraintValidatorContext context) {
            String regex = ".*";
            for (RequisitosSenha requisito : requisitosParaValidar) {
                regex += requisito.getRegexValidacao();
            }
            regex += ".*";
            return Pattern.compile(regex).matcher(senha).matches();
        }
    }

    enum RequisitosSenha {
        MAIUSCULO("Maiúsculo", "(?=.*[A-Z])"),
        MINUSCULO("Minúsculo", "(?=.*[a-z])"),
        CARACTERE_ESPECIAL("Caractere Especial", "(?=.*[@#$%^&+=!?.])"),
        DIGITO("Dígito","(?=.*[0-9])"),
        MAIOR_8_DIGITOS("Maior que 8 caracteres", "^.{8,}$"),
        ESPACO_EM_BRANCO("Não conter espçao em branco", "(?=\\S+$) ");

        private String descricao;
        private String regexValidacao;

        public String getDescricao() {
            return descricao;
        }

        public String getRegexValidacao() { return regexValidacao; }

        RequisitosSenha(String descricao, String regexValidacao) {
            this.descricao = descricao;
            this.regexValidacao = regexValidacao;
        }
    }
}