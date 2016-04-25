package com.example.artists;
import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class GetJsonIntent extends IntentService {
    public static final String TRANSACTION_DONE = "com.example.artists.TRANSACTION_DONE";
    public GetJsonIntent()
    {
        super("GetJsonIntent");
    }

    HttpURLConnection urlConnection;



    @Override
    protected void onHandleIntent(Intent intent) {
        StringBuilder result = new StringBuilder();
        JSONArray aboutJson = null;
        ArrayList<AboutArtist> AA;




            try {
                URL url = new URL("http://download.cdn.yandex.net/mobilization-2016/artists.json"); //подключение и помещение в поток json файла
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setUseCaches(true);
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null){ //чтение json файла
                result.append(line);
                }
                reader.close();
                in.close();


            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();

            }

            try {
                aboutJson = new JSONArray(result.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            AA = AboutArtist.listFromJsonArtist(aboutJson);
        Intent i = new Intent(TRANSACTION_DONE);
        i.putParcelableArrayListExtra("com.example.artists.AA", AA);
        LocalBroadcastManager.getInstance(this).sendBroadcast(i);
        sendBroadcast(i);


    }

}

