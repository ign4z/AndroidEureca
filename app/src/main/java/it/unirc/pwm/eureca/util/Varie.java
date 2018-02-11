package it.unirc.pwm.eureca.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ignaz on 11/02/2018.
 */

public class Varie {

    //verifica blanda email
    public static boolean isValidEmail(String email) {
        return email.contains("@");
    }


    public static Date stringToDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(convertedDate);
        return convertedDate;
    }

}
