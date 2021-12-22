package com.barauna.DEVinHouse.utils;

import java.util.regex.Pattern;

public class CPFUtils {

    private CPFUtils() {}

    public static boolean validate(final String cpf) {
        final Pattern pattern = Pattern.compile("^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$");
        return !pattern.matcher(cpf).matches();
    }
}
