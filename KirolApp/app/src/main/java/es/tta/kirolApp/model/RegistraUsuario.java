package es.tta.kirolApp.model;

/**
 * Created by asier on 8/01/17.
 */


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.AsyncTask;

public class RegistraUsuario extends AsyncTask<User, Integer, Boolean>{
    @Override
    protected Boolean doInBackground(User... usuario) {
        boolean estado = false;
        HttpURLConnection urlConnection = null;
        String nombre = usuario[0].getNombre();
        String apellido1 = usuario[0].getApellido1();
        String apellido2 = usuario[0].getApellido2();
        String apodo = usuario[0].getApodo();
        String email = usuario[0].getEmail();
        String pwd = usuario[0].getPwd();
        String surl = "http://192.168.0.11/putUser.php?nombre="+nombre+"&apodo="+apodo+"&apellido1="+apellido1+
                "&apellido2="+apellido2+"&email="+email+"&clave="+pwd;
        try {
            URL url = new URL(surl);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();

            InputStreamReader isw = new InputStreamReader(in);

            int data = isw.read(); //Devuelve -1 si falla
            while (data != -1) {
                estado = true;
                char current = (char) data;
                data = isw.read();
                System.out.print(current);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return estado;
    }
}

