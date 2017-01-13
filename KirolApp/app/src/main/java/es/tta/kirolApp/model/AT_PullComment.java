package es.tta.kirolApp.model;

/**
 * Created by asier on 13/01/17.
 */


import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AT_PullComment extends AsyncTask<Comentario, Integer, Boolean>{
    @Override
    protected Boolean doInBackground(Comentario... comentario) {
        boolean estado = false;
        String respuesta;
        HttpURLConnection urlConnection = null;
        String mensaje = comentario[0].getMensaje();
        String remite = comentario[0].getRemitente();
        int id = comentario[0].getId();
        String idForum = Integer.toString(id);
        String surl = "http://194.30.12.79/putMessageByForum.php?idForo="+idForum+"&message="+mensaje+"&emisor="+remite;
        try {
            URL url = new URL(surl);
            urlConnection = (HttpURLConnection) url.openConnection();
            if (urlConnection.getResponseCode() == 200) {
                InputStream in = urlConnection.getInputStream();

                InputStreamReader isr = new InputStreamReader(in, "UTF-8");
                BufferedReader br = new BufferedReader(isr);
                if ((respuesta = br.readLine()).equals("true")) {
                    System.out.println("La respuesta es: " + respuesta);
                    estado = true;
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return estado;
    }
}

