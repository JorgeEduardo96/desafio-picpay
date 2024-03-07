package br.com.picpay.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class FormatadorMonetarioUtil {

    public static String formatarParaMonetario(BigDecimal valor, String language, String country) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale(language,country));
        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);
        return df.format(valor);
    }

}
