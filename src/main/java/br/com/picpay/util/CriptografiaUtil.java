package br.com.picpay.util;

public class CriptografiaUtil {

    public static String criptografar(String texto, int chave) {
        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < texto.length(); i++) {
            char caractere = texto.charAt(i);

            if (Character.isLetter(caractere)) {
                char inicioAlfabeto = Character.isUpperCase(caractere) ? 'A' : 'a';
                char criptografado = (char) ((caractere - inicioAlfabeto + chave) % 26 + inicioAlfabeto);
                resultado.append(criptografado);
            } else {
                resultado.append(caractere);
            }
        }

        return resultado.toString();
    }

}
