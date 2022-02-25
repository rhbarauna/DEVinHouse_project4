package com.barauna.DEVinHouse.utils;

import java.util.regex.Pattern;

public class UserUtils {

    public static boolean isValidUsername(String username) {
        if(username == null) {
            return false;
        }
        final Pattern pattern = Pattern.compile("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
        return pattern.matcher(username).matches();
    }

    public static boolean isValidPassword(String password) {
        if(password == null){
            return false;
        }
        final Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,}$");
        return pattern.matcher(password).matches();
    }
}
