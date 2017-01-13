package es.tta.kirolApp.model;

/**
 * Created by asier on 8/01/17.
 */


import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AT_CheckUser extends AsyncTask<User, Integer, Boolean>{
    @Override
    protected Boolean doInBackground(User... usuario) {
        boolean estado = false;
        String respuesta;
        HttpURLConnection urlConnection = null;
        String apodo = usuario[0].getApodo();
        String pwd = usuario[0].getPwd();
        String surl = "http://194.30.12.79/checkUser.php?&apodo="+apodo+"&clave="+pwd;
        try {
            URL url = new URL(surl);
            urlConnection = (HttpURLConnection) url.openConnection();
            if (urlConnection.getResponseCode() == 200) {
                InputStream in = urlConnection.getInputStream();

                InputStreamReader isr = new InputStreamReader(in, "UTF-8");
                BufferedReader br = new BufferedReader(isr);
                //System.out.println("La respuesta es: "+br.readLine());
                if ((respuesta = br.readLine()).equals("true")) {
                    System.out.println("La respuesta es: " + respuesta);
                    return true;
                } else {
                    return false;
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

