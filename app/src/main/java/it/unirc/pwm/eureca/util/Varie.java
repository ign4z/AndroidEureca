package it.unirc.pwm.eureca.util;

/**
 * Created by ignaz on 11/02/2018.
 */

public class Varie {

    public static boolean isValidEmail(String email) {
        return email.contains("@");
        //return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
