package it.unirc.pwm.eureca.activity;

import android.os.Bundle;
import android.text.TextUtils;
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
import it.unirc.pwm.eureca.util.Varie;

public class DettagliEventoActivity extends JsonAbstractActivity {

    private String Tag = "DettagliEventoActivity";
    private Evento evento;

    //onclik
    public void partecipa(View view) {
        LinearLayout linearInvisible = findViewById(R.id.linearInvisible);
        linearInvisible.setVisibility(View.VISIBLE);

        final EditText date = (EditText) findViewById(R.id.dataNascitaTF);
        MyTextWatcher myTextWatcher = new MyTextWatcher(date);
        date.addTextChangedListener(myTextWatcher);
    }

    //onclick
    public void inviaPartecipazione(View view) {
        if (controllaCampi())
            try {
                InternetConnection internetConnection = new InternetConnection(Costanti.URLBASE + Costanti.URLPARTECIPA);
                AsyncReqPost asyncReq = new AsyncReqPost(this, getString(R.string.running), getString(R.string.conser), Costanti.URLBASE + Costanti.URLPARTECIPA, getPostParamiter());
                asyncReq.execute(internetConnection);
            } catch (Exception e) {
                e.printStackTrace();
            }

    }

    private boolean controllaCampi() {
        if (TextUtils.isEmpty(((EditText) findViewById(R.id.nomeTF)).getText())) {
            ((EditText) findViewById(R.id.nomeTF)).setError("First name is required!");
            return false;
        }
        if (TextUtils.isEmpty(((EditText) findViewById(R.id.cognomeTF)).getText())) {
            ((EditText) findViewById(R.id.cognomeTF)).setError("First name is required!");
            return false;
        }
        if (TextUtils.isEmpty(((EditText) findViewById(R.id.emailTF)).getText())) {
            ((EditText) findViewById(R.id.emailTF)).setError("First name is required!");
            return false;
        }
        String email = ((EditText) findViewById(R.id.emailTF)).getText().toString();
        if (!Varie.isValidEmail(email)) {
            ((EditText) findViewById(R.id.emailTF)).setError("First name is required!");
            return false;
        }
        if (TextUtils.isEmpty(((EditText) findViewById(R.id.telefonoTF)).getText())) {
            ((EditText) findViewById(R.id.telefonoTF)).setError("First name is required!");
            return false;
        }
        if (TextUtils.isEmpty(((EditText) findViewById(R.id.dataNascitaTF)).getText())) {
            ((EditText) findViewById(R.id.dataNascitaTF)).setError("First name is required!");
            return false;
        }
        if (TextUtils.isEmpty(((EditText) findViewById(R.id.enteTF)).getText())) {
            ((EditText) findViewById(R.id.enteTF)).setError("First name is required!");
            return false;
        }
        return true;
    }

    private HashMap<String, String> getPostParamiter() {
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
            Log.e(Tag, "immagine scarrrrica");
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dettaglio_evento_activity);
        setTitle(Costanti.titolo);
        //leggi evento
        //salva su oggetto evento
        evento = (Evento) getIntent().getParcelableExtra(QRCodeActivity.EXTRA_EVENTO);
        popolaCampi();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void jsonResult(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            if ((Boolean) jsonObject.get("risultato")) {
                Toast.makeText(this, getString(R.string.success), Toast.LENGTH_SHORT).show();
                finish();
            }
        } catch (JSONException jex) {
            Toast.makeText(this, getString(R.string.errore_generico), Toast.LENGTH_SHORT).show();
            jex.printStackTrace();
        }
    }
}
