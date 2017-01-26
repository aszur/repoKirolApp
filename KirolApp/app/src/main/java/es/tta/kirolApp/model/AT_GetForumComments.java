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
import java.util.Collections;
import java.util.List;

public class AT_GetForumComments extends AsyncTask<String, Integer, List<Comentario>>{
    @Override
    protected List<Comentario> doInBackground(String... foro) {
        String respuesta ="";
        HttpURLConnection urlConnection = null;
        List<Comentario> comentarios = new ArrayList<Comentario>();
        String surl = "http://194.30.12.79/getMessagesByForum.php?forumID="+foro[0];
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
                        Comentario com = new Comentario();
                        JSONObject jo = (JSONObject) jr.getJSONObject(i);
                        com.setId(jo.getInt("id"));
                        com.setFecha(jo.getString("fecha"));
                        com.setMensaje(jo.getString("mensaje"));
                        com.setRemitente(jo.getString("emisor"));
                        comentarios.add(com);
                    }
                    Collections.reverse(comentarios);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return comentarios;
    }
}

