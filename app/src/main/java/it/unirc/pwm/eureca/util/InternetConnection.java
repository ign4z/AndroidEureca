package it.unirc.pwm.eureca.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

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

    String getHttpSource() {
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


    public String performPostCall(HashMap<String, String> postDataParams) {

        String response = "";
        try {

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }
}
