package it.unirc.pwm.eureca.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import it.unirc.pwm.eureca.R;
import it.unirc.pwm.eureca.model.Evento;
import it.unirc.pwm.eureca.model.EventoImpl;
import it.unirc.pwm.eureca.util.AsyncReq;
import it.unirc.pwm.eureca.util.Costanti;
import it.unirc.pwm.eureca.util.InternetConnection;
import it.unirc.pwm.eureca.util.PermissionUtil;

public class QRCodeActivity extends JsonActivity {
	public final static String TAG = "QRCodeActivity";
	private int idEvento = 0;

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
						String returnValue = data.getStringExtra(String.valueOf(Costanti.KEY_QR_CODE));
						String nome = returnValue.split("@")[0];
						idEvento =Integer.parseInt(returnValue.split("@")[1]);
						TextView textView = (TextView) findViewById(R.id.textView);
						textView.setText(nome);
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
		InternetConnection internetConnection=
				new InternetConnection(Costanti.urlBase+Costanti.urlJson+"?id=1");
		AsyncReq asyncReq=new AsyncReq(this, getString(R.string.running),
				getString(R.string.conser), Costanti.urlBase+Costanti.urlJson+"?id=1");
		asyncReq.execute(internetConnection);
	}

	/**
	 * callback della richiesta asincrona json classe astratta
	 * @param jsonString
	 */
	@Override
	public void jsonResult(String jsonString) {

		EventoImpl evnContract=new EventoImpl();
		Evento e = evnContract.getEvento(jsonString);
		System.out.println(e.getDescrizione());
	}
}