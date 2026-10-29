package minibank;

import java.util.regex.Pattern;

public class Validator {
    public static boolean isValidMobile(String mobile) {
        return Pattern.compile("^[6-9][0-9]{9}$").matcher(mobile).matches();
    }

    public static boolean isValidEmail(String email) {
        return Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$").matcher(email).matches();
    }
}