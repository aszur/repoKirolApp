package es.tta.kirolApp.model;

/**
 * Created by asier on 8/01/17.
 */


import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CheckUser extends AsyncTask<User, Integer, Boolean>{
    @Override
    protected Boolean doInBackground(User... usuario) {
        boolean estado = false;
        HttpURLConnection urlConnection = null;
        String apodo = usuario[0].getApodo();
        String pwd = usuario[0].getPwd();
        String surl = "http://194.30.12.79/checkUser.php?&apodo="+apodo+"&clave="+pwd;
        try {
            URL url = new URL(surl);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();

            InputStreamReader isr = new InputStreamReader(in,"UTF-8");
            String respuesta = isr.toString();
            System.out.println(respuesta);
            /*int data = isr.read(); //Devuelve -1 si falla
            while (data != -1) {
                estado = true;
                char current = (char) data;
                data = isr.read();
                System.out.print(current);
            }*/
            /*try(BufferedReader br = new BufferedReader(isr)){
                if(br.readLine() == "true"){
                    return true;
                }else{
                    return false;
                }
            }*/
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return estado;
    }
}

