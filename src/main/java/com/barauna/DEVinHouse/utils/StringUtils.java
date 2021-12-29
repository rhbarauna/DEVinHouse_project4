package com.barauna.DEVinHouse.utils;

import java.util.Random;
import java.util.regex.Pattern;

public class StringUtils {

    private StringUtils() {}

    public static boolean validateCPF(final String cpf) {
        if( null == cpf ) {
            return false;
        }

        final Pattern pattern = Pattern.compile("^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$");
        return !pattern.matcher(cpf).matches();
    }

    public static boolean validateName(final String name) {
        if( null == name) {
            return false;
        }

        final Pattern pattern = Pattern.compile("^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$");
        return pattern.matcher(name).matches();
    }

    /** https://www.tutorialspoint.com/Generating-password-in-Java */
    public static char[] generatePassword(int length) {
        String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String specialCharacters = "!@#$";
        String numbers = "1234567890";
        String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
        Random random = new Random();
        char[] password = new char[length];

        password[0] = lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length()));
        password[1] = capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length()));
        password[2] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));
        password[3] = numbers.charAt(random.nextInt(numbers.length()));

        for (int i = 4; i < length; i++) {
            password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
        }
        return password;
    }
}
