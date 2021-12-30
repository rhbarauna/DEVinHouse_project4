package com.barauna.DEVinHouse.utils;

import java.util.regex.Pattern;

public class UserUtils {

    public static boolean isValidUsername(String username) {
        final Pattern pattern = Pattern.compile("^[a-z0-9.]+@[a-z0-9]+.[a-z]+.([a-z]+)?$");
        return pattern.matcher(username).matches();
    }

    public static boolean isValidPassword(String password) {
        final Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
        return pattern.matcher(password).matches();
    }
}
