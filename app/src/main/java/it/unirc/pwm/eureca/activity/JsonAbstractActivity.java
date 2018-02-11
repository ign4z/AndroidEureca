package it.unirc.pwm.eureca.activity;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by ignaz on 09/02/2018.
 */

public abstract class JsonAbstractActivity extends AppCompatActivity {
    /**
     * callback della richiesta asincrona json classe astratta
     *
     * @param jsonString
     */
    public abstract void jsonResult(String jsonString);
}
