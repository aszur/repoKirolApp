package es.tta.kirolApp.model;

/**
 * Created by asier on 8/01/17.
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

public class GetCountries extends AsyncTask<String, Integer, List<Pais>>{
    @Override
    protected List<Pais> doInBackground(String... continente) {
        String respuesta ="";
        HttpURLConnection urlConnection = null;
        List<Pais> paises = new ArrayList<Pais>();
        String surl = "http://194.30.12.79/getCountries.php?idContinente="+continente;
        try {
            URL url = new URL(surl);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(in,"UTF-8");
            BufferedReader br = new BufferedReader(isr);
            respuesta = br.readLine();
            //System.out.println(respuesta);
            try {
                JSONArray jr = new JSONArray(respuesta);
                int size = jr.length();
                for(int i=0;i<size;i++){
                    Pais p = new Pais();
                    JSONObject jo = (JSONObject) jr.getJSONObject(i);
                    p.setId(jo.getInt("idPais"));
                    System.out.println("Id: "+p.getId());
                    p.setNombre(jo.getString("nombre"));
                    System.out.println("Id: "+p.getNombre());
                    paises.add(p);
                }
                ;
            } catch (JSONException e) {
                e.printStackTrace();
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return paises;
    }
}

