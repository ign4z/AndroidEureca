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

public class QRCodeAbstractActivity extends JsonAbstractActivity {
	public final static String TAG = "QRCodeAbstractActivity";
	public static final String EXTRA_EVENTO = "it.unirc.pwm.eureca.model.Evento";
	private int idEvento = 1;
	private Evento evento;

	private void dispatchTakePictureIntent() {
		/*Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
			startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
		}*/

		Intent takeQRCode = new Intent(this,ScannerActivity.class);
		//takeQRCode.setType(Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
		startActivityForResult(takeQRCode, Costanti.REQUEST_SCANNER);

	}

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
						} catch (ArrayIndexOutOfBoundsException aex) {
							Toast.makeText(this, "qrcode non riconosciuto", Toast.LENGTH_LONG).show();
						}
					}
					break;
				}
		}
	}



	//onclick
	public void cattura(View view)
	{//permesso a runtime
		if(PermissionUtil.checkPermissionCamera(this)){
			dispatchTakePictureIntent();
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode,
										   String permissions[], int[] grantResults) {
		switch (requestCode) {
			case Costanti.MY_PERMISSIONS_REQUEST_CAMERA: {
				// If request is cancelled, the result arrays are empty.
				if (grantResults.length > 0
						&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {

					// permission was granted, yay! Do the
					// contacts-related task you need to do.
					dispatchTakePictureIntent();
				} else {
					return;
					// permission denied, boo! Disable the
					// functionality that depends on this permission.
				}
				return;
			}

			// other 'case' lines to check for other
			// permissions this app might request.
		}
	}

	@Override
	//conviene fare oncreate scarico e on resume con caricamenti lenti
	//altrimenti l'app impiegherà troppo per partire
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.qrcode_activity);
	}



	public void inviaDati(View view)
	{
		InternetConnection internetConnection = new InternetConnection(Costanti.URLBASE + Costanti.URLJSON + "?id=" + idEvento);
		AsyncReqGet asyncReq = new AsyncReqGet(this, getString(R.string.running), getString(R.string.conser), Costanti.URLBASE + Costanti.URLJSON + "?id=" + idEvento);
		asyncReq.execute(internetConnection);
	}

	/**
	 * callback della richiesta asincrona json classe astratta
	 * @param jsonString
	 */
	@Override
	public void jsonResult(String jsonString) {

		EventoImpl evnContract=new EventoImpl();
		evento = evnContract.getEvento(jsonString);

		Intent avviaDettagli = new Intent(this, DettagliEventoActivity.class);
		Bundle extras = new Bundle();
		extras.putParcelable(EXTRA_EVENTO, evento);
		avviaDettagli.putExtras(extras);
		startActivity(avviaDettagli);

	}
}