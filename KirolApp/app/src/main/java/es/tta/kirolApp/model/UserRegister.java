package es.tta.kirolApp.model;

/**
 * Created by asier on 8/01/17.
 */


import java.io.BufferedReader;
import java.io.Reader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.AsyncTask;

public class UserRegister extends AsyncTask<User, Integer, Boolean>{
    @Override
    protected Boolean doInBackground(User... usuario) {
        boolean estado = false;
        String respuesta ="";
        HttpURLConnection urlConnection = null;
        String nombre = usuario[0].getNombre();
        String apellido1 = usuario[0].getApellido1();
        String apellido2 = usuario[0].getApellido2();
        String apodo = usuario[0].getApodo();
        String email = usuario[0].getEmail();
        String pwd = usuario[0].getPwd();
        System.out.println("Nombre: "+nombre);
        String surl = "http://194.30.12.79/putUser.php?nombre="+nombre+"&apodo="+apodo+"&apellido1="+apellido1+
                "&apellido2="+apellido2+"&email="+email+"&clave="+pwd;
        try {
            URL url = new URL(surl);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();

            InputStreamReader isr = new InputStreamReader(in,"UTF-8");

            BufferedReader br = new BufferedReader(isr);
            //System.out.println("La respuesta es: "+br.readLine());
            if((respuesta = br.readLine()).equals("true")){
                System.out.println("La respuesta es: "+respuesta);
                return true;
            }else{
                return false;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return estado;
    }
}

