package it.unirc.pwm.eureca.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import it.unirc.pwm.eureca.R;
import it.unirc.pwm.eureca.util.Costanti;

public class QRCodeActivity extends  Activity {
	public final static String TAG = "QRCodeActivity";

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
						TextView textView = (TextView) findViewById(R.id.textView);
						textView.setText(nome);
					}
					break;
				}
		}
	}

	private void showExplanation(String title, String message, final String permission,
								 final int permissionRequestCode) {
		AlertDialog.Builder builder = new AlertDialog.Builder(QRCodeActivity.this);
		builder.setTitle(title)
				.setMessage(message)
				.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						requestPermission(permission, permissionRequestCode);
					}
				});
		builder.create().show();
	}

	private void requestPermission(String permissionName, int permissionRequestCode) {
		ActivityCompat.requestPermissions(QRCodeActivity.this,new String[]{permissionName}, permissionRequestCode);
	}

	//onclick
	public void cattura(View view)
	{//permesso a runtime
		int permissionCheck = ContextCompat.checkSelfPermission(
				QRCodeActivity.this, Manifest.permission.CAMERA);
		if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
			if (ActivityCompat.shouldShowRequestPermissionRationale(QRCodeActivity.this,
					Manifest.permission.CAMERA)) {
				showExplanation("Permission Needed", "Rationale", Manifest.permission.CAMERA, Costanti.MY_PERMISSIONS_REQUEST_CAMERA);
			} else {
				requestPermission(Manifest.permission.CAMERA, Costanti.MY_PERMISSIONS_REQUEST_CAMERA);
			}
		} else {
			Toast.makeText(QRCodeActivity.this, "Permission (already) Granted!", Toast.LENGTH_SHORT).show();
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
}