package it.unirc.pwm.eureca.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import it.unirc.pwm.eureca.R;
import it.unirc.pwm.eureca.model.Evento;
import it.unirc.pwm.eureca.model.EventoImpl;
import it.unirc.pwm.eureca.util.AsyncReqGet;
import it.unirc.pwm.eureca.util.Costanti;
import it.unirc.pwm.eureca.util.InternetConnection;
import it.unirc.pwm.eureca.util.PermissionUtil;

public class QRCodeActivity extends JsonAbstractActivity {
	public final static String TAG = "QRCodeActivity";
	public static final String EXTRA_EVENTO = "it.unirc.pwm.eureca.model.Evento";
	private int idEvento;
	private Evento evento;

	//avvio action qrcode
	private void dispatchTakePictureIntent() {
		Intent takeQRCode = new Intent(this,ScannerActivity.class);
		startActivityForResult(takeQRCode, Costanti.REQUEST_SCANNER);
	}

	//risultato activity lettura qrcode
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode) {
			case (Costanti.REQUEST_SCANNER) : {
				if (resultCode == Activity.RESULT_OK) {
					TextView textView = (TextView) findViewById(R.id.textView);
					try {
						String returnValue = data.getStringExtra(String.valueOf(Costanti.KEY_QR_CODE));
						String nome = returnValue.split("@")[0];
						idEvento = Integer.parseInt(returnValue.split("@")[1]);
						textView.setText(nome);

						View button = findViewById(R.id.button);
						button.setVisibility(View.VISIBLE);

						View tV = findViewById(R.id.textView);
						tV.setVisibility(View.VISIBLE);

					} catch (ArrayIndexOutOfBoundsException aex) {
						Toast.makeText(this, getString(R.string.errore_qrcode), Toast.LENGTH_LONG).show();
					}
				}
				break;
			}
		}
	}

	//onclick
	public void cattura(View view) {
		//richiesta permessi a runtime
		if(PermissionUtil.checkPermissionCamera(this)){
			dispatchTakePictureIntent();
		}
	}

	//risultato richiesta permessi
	@Override
	public void onRequestPermissionsResult(int requestCode,
										   String permissions[], int[] grantResults) {
		switch (requestCode) {
			case Costanti.MY_PERMISSIONS_REQUEST_CAMERA: {
				// If request is cancelled, the result arrays are empty.
				if (grantResults.length > 0
						&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					//permesso ottenuto
					dispatchTakePictureIntent();
				} else {
					return;
					// permesso negato
				}
				return;
			}
		}
	}

	@Override
	//conviene fare oncreate scarico e on resume con caricamenti lenti
	//altrimenti l'app impiegherà troppo per partire
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.qrcode_activity);
		setTitle(Costanti.titolo);
	}



	public void inviaDati(View view)
	{
		InternetConnection internetConnection = new InternetConnection(Costanti.URLBASE + Costanti.URLJSON + "?id=" + idEvento);
		AsyncReqGet asyncReq = new AsyncReqGet(this, getString(R.string.running), getString(R.string.conser), Costanti.URLBASE + Costanti.URLJSON + "?id=" + idEvento);
		asyncReq.execute(internetConnection);
	}


	@Override
	public void jsonResult(String jsonString) {
		if (!jsonString.isEmpty()) { //se la risposta della comunicazione è vuota non faccio nulla
			EventoImpl evnContract = new EventoImpl();
			evento = evnContract.getEvento(jsonString);
			Intent avviaDettagli = new Intent(this, DettagliEventoActivity.class);
			Bundle extras = new Bundle();
			extras.putParcelable(EXTRA_EVENTO, evento);
			avviaDettagli.putExtras(extras);
			startActivity(avviaDettagli);
		}

	}
}