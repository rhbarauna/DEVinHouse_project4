package com.barauna.DEVinHouse.utils;

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
}
