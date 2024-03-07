package br.com.picpay.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

    public static final String REGEX_APENAS_NUMEROS = "[^0-9]";

    public static String replaceCaracteresComRegex(String stringParaFormatar, String regexParaValidacao) {
        Pattern pattern = Pattern.compile(regexParaValidacao);
        Matcher matcher = pattern.matcher(stringParaFormatar);
        return matcher.replaceAll("");
    }

}
