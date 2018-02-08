package it.unirc.pwm.eureca.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.Result;

import it.unirc.pwm.eureca.util.Costanti;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScannerActivity extends  Activity implements ZXingScannerView.ResultHandler {
	public final static String TAG = "ScannerActivity";

	private ZXingScannerView mScannerView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mScannerView = new ZXingScannerView(this);
		setContentView(mScannerView);
	}

	protected void onResume() {
		super.onResume();
		mScannerView.setResultHandler(this);
		//avvio la camera se l'app viene ripresa
		mScannerView.startCamera();
	}

	@Override
	public void onPause() {
		super.onPause();
		//stoppo sempre la camera nell'onpause
		mScannerView.stopCamera();
	}


	@Override
	public void handleResult(Result result) {
		Log.e("res",result.getText());
		Log.e("result", result.getBarcodeFormat().toString());

		Intent risultato = new Intent();
		risultato.putExtra(String.valueOf(Costanti.KEY_QR_CODE), result.getText());
		setResult(RESULT_OK, risultato);
		finish();
	}

	@Override
	protected void onStop() {
		super.onStop();
		//stoppo la camera
		mScannerView.stopCamera();
	}
}