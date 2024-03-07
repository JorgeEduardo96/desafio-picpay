package br.com.picpay.util;

public class CpfCnpjUtil {

    public static boolean isCpfValid(String cpf) {
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            int[] cpfArray = cpf.chars().map(Character::getNumericValue).toArray();

            int sum = 0;
            for (int i = 0; i < 9; i++) {
                sum += cpfArray[i] * (10 - i);
            }
            int remainder = sum % 11;
            int digit1 = (remainder < 2) ? 0 : (11 - remainder);

            sum = 0;
            for (int i = 0; i < 10; i++) {
                sum += cpfArray[i] * (11 - i);
            }
            remainder = sum % 11;
            int digit2 = (remainder < 2) ? 0 : (11 - remainder);

            return (cpfArray[9] == digit1) && (cpfArray[10] == digit2);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isCnpjValid(String cnpj) {
        try {
            int[] cnpjArray = cnpj.chars().map(Character::getNumericValue).toArray();

            int sum = 0;
            int factor = 5;
            for (int i = 0; i < 12; i++) {
                sum += cnpjArray[i] * factor;
                factor = (factor == 2) ? 9 : (factor - 1);
            }
            int remainder = sum % 11;
            int digit1 = (remainder < 2) ? 0 : (11 - remainder);

            sum = 0;
            factor = 6;
            for (int i = 0; i < 13; i++) {
                sum += cnpjArray[i] * factor;
                factor = (factor == 2) ? 9 : (factor - 1);
            }
            remainder = sum % 11;
            int digit2 = (remainder < 2) ? 0 : (11 - remainder);

            return (cnpjArray[12] == digit1) && (cnpjArray[13] == digit2);
        } catch (Exception e) {
            return false;
        }
    }

}
