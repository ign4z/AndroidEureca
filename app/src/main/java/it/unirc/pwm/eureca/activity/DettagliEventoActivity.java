package it.unirc.pwm.eureca.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import it.unirc.pwm.eureca.R;
import it.unirc.pwm.eureca.model.Evento;
import it.unirc.pwm.eureca.util.AsincTaskImageDownloader;
import it.unirc.pwm.eureca.util.AsyncReqPost;
import it.unirc.pwm.eureca.util.Costanti;
import it.unirc.pwm.eureca.util.InternetConnection;
import it.unirc.pwm.eureca.util.MyTextWatcher;

public class DettagliEventoActivity extends JsonAbstractActivity {

    private String Tag = "DettagliEventoActivity";
    private Evento evento;


    private void salvaSuPreference() {
    }

    private void leggiDaPreference() {
    }

    public void partecipa(View view) {
        LinearLayout linearInvisible = findViewById(R.id.linearInvisible);
        linearInvisible.setVisibility(View.VISIBLE);

        final EditText date = (EditText) findViewById(R.id.dataNascitaTF);
        MyTextWatcher myTextWatcher = new MyTextWatcher(date);
        date.addTextChangedListener(myTextWatcher);
    }

    public void inviaPartecipazione(View view) {
        try {
            InternetConnection internetConnection = new InternetConnection(Costanti.URLBASE + Costanti.URLPARTECIPA);
            AsyncReqPost asyncReq = new AsyncReqPost(this, getString(R.string.running), getString(R.string.conser), Costanti.URLBASE + Costanti.URLPARTECIPA, getPostParamiter(view));
            asyncReq.execute(internetConnection);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private HashMap<String, String> getPostParamiter(View view) {
        HashMap<String, String> param = new HashMap<>();
        param.put("id", String.valueOf(evento.getIdEvento()));
        param.put("utente.nome", ((EditText) findViewById(R.id.nomeTF)).getText().toString());
        param.put("utente.cognome", ((EditText) findViewById(R.id.cognomeTF)).getText().toString());
        param.put("utente.email", ((EditText) findViewById(R.id.emailTF)).getText().toString());
        param.put("utente.telefono", ((EditText) findViewById(R.id.telefonoTF)).getText().toString());
        param.put("utente.dataNascita", ((EditText) findViewById(R.id.dataNascitaTF)).getText().toString());
        param.put("utente.ente", ((EditText) findViewById(R.id.enteTF)).getText().toString());
        return param;
    }


    private void popolaCampi() {


        ((TextView) findViewById(R.id.nomeTW)).setText(evento.getNome());
        ((TextView) findViewById(R.id.descrizioneTW)).setText(evento.getDescrizione());
        ((TextView) findViewById(R.id.luogoTW)).setText(evento.getLuogo());
        if (evento.getDataEvento() != null)
            ((TextView) findViewById(R.id.dataTW)).setText(evento.getDataEvento().toString());

        ImageView img = (ImageView) findViewById(R.id.imageViewEvento);

        if (img != null) {
            String url = Costanti.URLBASE + Costanti.URLIMMAGINEDETTAGLIEVENTO + evento.getLocandina();
            System.out.println(url);
            new AsincTaskImageDownloader(img).execute(url);
            Log.e(Tag, "immagine not nullll scarrica");
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dettaglio_evento_activity);
        //leggi evento
        //salva su oggetto evento
        evento = (Evento) getIntent().getParcelableExtra(QRCodeAbstractActivity.EXTRA_EVENTO);
        popolaCampi();
    }

    @Override
    protected void onResume() {
        super.onResume();
        leggiDaPreference();
    }

    @Override
    protected void onPause() {
        super.onPause();
        salvaSuPreference();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        leggiDaPreference();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        salvaSuPreference();
    }

    @Override
    public void jsonResult(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            if ((Boolean) jsonObject.get("risultato")) {
                Toast.makeText(this, "iscrizione ok", Toast.LENGTH_SHORT).show();
                finish();
            }
        } catch (JSONException jex) {
            jex.printStackTrace();
        }
    }
}
