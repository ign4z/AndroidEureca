package it.unirc.pwm.eureca.util;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import java.lang.ref.WeakReference;
import it.unirc.pwm.eureca.activity.JsonActivity;


public class AsyncReq extends AsyncTask<InternetConnection, Void , String> {

	private ProgressDialog progressDialog;
	private WeakReference<JsonActivity> act; //libero memoria perché la strong reference non veniva mai GB
	private String dtitle;
	private String dcontent;
	private String url;

	public AsyncReq(JsonActivity a, String dtitle, String dcontent, String url)
	{
		this.act= new WeakReference<JsonActivity>(a);
		this.dtitle=dtitle;
		this.dcontent=dcontent;
		this.url=url;
	}



	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		act.get().runOnUiThread(new Runnable()
		{
			public void run()
			{
				progressDialog = ProgressDialog.show(act.get(), dtitle, dcontent+"\n"+url, true);
			}
		});
	}

	@Override
	protected void onPostExecute(String jsonString) {
		// TODO Auto-generated method stub
		super.onPostExecute(jsonString);
		
		if(act!=null)
			act.get().jsonResult(jsonString);
		if(progressDialog!=null)
			progressDialog.dismiss();
	}



	@Override
	protected String doInBackground(InternetConnection... params) {

		InternetConnection internetConnection=params[0];
		String jsonString=internetConnection.getHttpSource();

		return jsonString;
	}




}
