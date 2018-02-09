package it.unirc.pwm.eureca.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


import android.util.Log;

public class InternetConnection {
	private static final String TAG="JSON Activity";

	private URL url;

	public InternetConnection(String stringUrl) {
        try {
            this.url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

	public String getHttpSource()
	{
        StringBuilder stringBuilder = new StringBuilder();
        InputStream is =null;
		try {

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(15000 /* milliseconds */);
			// Starts the query
			conn.connect();
			int responseCode = conn.getResponseCode();
			Log.d(TAG, "The response is: " + responseCode);
			if (responseCode != 200) {
                Log.e(TAG, "Unreachable Source");
                return "";
            }
			else {
				is = conn.getInputStream();

                BufferedReader r = new BufferedReader(new InputStreamReader(is));
                stringBuilder = new StringBuilder();
                String line;
                while ((line=r.readLine()) != null) {
                    stringBuilder.append(line);
                }
            }
		} catch (IOException e) {
			Log.e(TAG, e.toString());
		}
		finally {
			if (is != null) try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		Log.d(TAG, stringBuilder.toString());
		return stringBuilder.toString();
	}
}
