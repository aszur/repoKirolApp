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

public class GetSportById extends AsyncTask<String, Integer, DeporteXid>{
    @Override
    protected DeporteXid doInBackground(String... dep) {
        String respuesta ="";
        HttpURLConnection urlConnection = null;
        DeporteXid d = new DeporteXid();
        System.out.println("El id es: "+ dep[0]);
        String surl = "http://194.30.12.79/getSportById.php?sportID="+dep[0];
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
                    JSONObject jo = new JSONObject(respuesta);
                    /*JSONArray jr = new JSONArray(respuesta);
                    int size = jr.length();
                    JSONObject jo = (JSONObject) jr.getJSONObject(0);*/
                    d.setNombre(jo.getString("nombre"));
                    System.out.println(d.getNombre());
                    d.setUrlAdapCat(jo.getString("adaptCat"));
                    d.setUrlAdapEs(jo.getString("adaptEs"));
                    System.out.println(d.getUrlAdapEs());
                    d.setUrlAdapEus(jo.getString("adaptEus"));
                    d.setUrlAdapEn(jo.getString("adaptEn"));
                    System.out.println(d.getUrlAdapEn());
                    d.setUrlDescEs(jo.getString("descEs"));
                    System.out.println(d.getUrlDescEs());
                    d.setUrlDescEus(jo.getString("descEus"));
                    d.setUrlDescEn(jo.getString("descEn"));
                    System.out.println(d.getUrlDescEn());
                    d.setUrlDescCat(jo.getString("descCat"));
                    d.setIdForoAlto(jo.getInt("foroNivelAlto"));
                    d.setIdForoMedio(jo.getInt("foroNivelMedio"));
                    d.setIdForoBajo(jo.getInt("foroNivelBajo"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return d;
    }
}

