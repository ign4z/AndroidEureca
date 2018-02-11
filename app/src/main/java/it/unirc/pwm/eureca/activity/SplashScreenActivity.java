package it.unirc.pwm.eureca.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import it.unirc.pwm.eureca.R;
import it.unirc.pwm.eureca.util.Costanti;

public class SplashScreenActivity extends AppCompatActivity {

    private static final String TAG = "SplashScreenActivity";
    private int progressStatus = 0; //progresso falso caricamento
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        setTitle(Costanti.titolo);
    }

    private void rendiVisibile() {
        //essendo questo metodo chiamato da un Threadn NON UI
        //devo necessariamente schedularlo per una successiva esecuzione nel thread UI
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                View b = findViewById(R.id.avviaButton);
                b.setVisibility(View.VISIBLE);
                View bs = findViewById(R.id.progressBar);
                bs.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        // Simulo caricamento lento
        Runnable r = new Runnable() {
            public void run() {
                while (progressStatus < 100) {
                    progressStatus += 5;
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressStatus);
                        }
                    });
                    try {
                        Thread.sleep(150);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                rendiVisibile();
            }
        };
        Thread t = new Thread(r);
        t.setDaemon(true);
        t.start();
    }

    //onclick
    public void secondaActivity(View view) {
        Intent intent = new Intent(this, QRCodeActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}
