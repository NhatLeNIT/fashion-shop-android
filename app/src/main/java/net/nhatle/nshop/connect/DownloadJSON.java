package net.nhatle.nshop.connect;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by NhatLe on 25-Dec-17.
 */

// onPreExecute() - doInBackground() (onProgressUpdate()) - onPostExecute()
public class DownloadJSON extends AsyncTask<String, Void, String> {
    private String link;
    private JSONObject jsonParam;
    private boolean method = true; // get = true, post = false

    public DownloadJSON(String link) {
        this.link = link;
        method = true;
    }

    public DownloadJSON(String link, JSONObject jsonParam) {
        this.link = link;
        this.jsonParam = jsonParam;
        method = false;
    }

    @Override
    protected String doInBackground(String... strings) {

        String dataJSON = "";
        try {
            URL url = new URL(link);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            if (!method) {
                dataJSON = methodPOST(httpURLConnection);
            } else {
                dataJSON = methodGET(httpURLConnection);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataJSON;
    }

    private String methodGET(HttpURLConnection httpURLConnection) {
        String dataResult = "";
        try {
            httpURLConnection.connect();
            InputStream inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder dataStringBuilder = new StringBuilder();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                dataStringBuilder.append(line);
            }

            dataResult = dataStringBuilder.toString();
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataResult;
    }

    private String methodPOST(HttpURLConnection httpURLConnection) {
        String dataResult = "";
        try {
            httpURLConnection.setRequestMethod("POST");

            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            OutputStream outputStream = httpURLConnection.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            bufferedWriter.write(this.jsonParam.toString());
            bufferedWriter.flush();

            bufferedWriter.close();
            outputStreamWriter.close();
            outputStream.close();

            dataResult = methodGET(httpURLConnection);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataResult;
    }
}