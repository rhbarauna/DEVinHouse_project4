package com.barauna.DEVinHouse.utils;

import java.util.regex.Pattern;

public class VillagerUtils {

    private VillagerUtils() {}

    public static boolean isValidCPF(final String cpf) {
        if( null == cpf ) {
            return false;
        }

        final Pattern pattern = Pattern.compile("(^\\d{3}\\x2E\\d{3}\\x2E\\d{3}\\x2D\\d{2}$)");
        return pattern.matcher(cpf).matches();
    }

    public static boolean isValidName(final String name) {
        if( null == name || name.trim().isEmpty()) {
            return false;
        }

        final Pattern pattern = Pattern.compile("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$");
        return pattern.matcher(name).matches();
    }
}
