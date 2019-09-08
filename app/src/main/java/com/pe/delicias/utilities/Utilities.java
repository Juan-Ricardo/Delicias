package com.pe.delicias.utilities;

import android.util.Patterns;

import java.util.regex.Pattern;

public class Utilities {
    public static boolean emailIsValid(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
}
