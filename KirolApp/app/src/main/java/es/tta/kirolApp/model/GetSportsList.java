package es.tta.kirolApp.model;

/**
 * Created by asier on 13/01/17.
 */


import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GetSportsList extends AsyncTask<String, Integer, List<Deporte>>{
    @Override
    protected List<Deporte> doInBackground(String... pais) {
        String respuesta ="";
        HttpURLConnection urlConnection = null;
        List<Deporte> deportes = new ArrayList<Deporte>();
        String surl = "http://194.30.12.79/getSportsByCountry.php?idPais="+pais[0];
        System.out.println(surl);
        try {
            URL url = new URL(surl);
            urlConnection = (HttpURLConnection) url.openConnection();
            if (urlConnection.getResponseCode() == 200) {
                InputStream in = urlConnection.getInputStream();
                InputStreamReader isr = new InputStreamReader(in, "UTF-8");
                BufferedReader br = new BufferedReader(isr);
                respuesta = br.readLine();
                System.out.println(respuesta);
                try {
                    JSONArray jr = new JSONArray(respuesta);
                    int size = jr.length();
                    for (int i = 0; i < size; i++) {
                        Deporte d = new Deporte();
                        JSONObject jo = (JSONObject) jr.getJSONObject(i);
                        d.setId(jo.getInt("id"));
                        System.out.println("Id: " + d.getId());
                        d.setNombre(jo.getString("nombre"));
                        System.out.println("Id: " + d.getNombre());
                        deportes.add(d);
                    }
                    ;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return deportes;
    }
}

