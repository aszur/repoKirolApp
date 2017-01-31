package es.tta.kirolApp.presentation;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.docencia.kirolApp.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements AlertDialog.OnClickListener {

    public final static int BUTTON_POSITIVE = -1 ;
    public final static int BUTTON_NEGATIVE = -2 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle(R.string.app_name);

        compruebaConexion();
    }

    public void accede(View view) { //Funcion llamada desde el view
        final String login = ((EditText) findViewById(R.id.nombreUsuario)).getText().toString();
        final String pwd = ((EditText) findViewById(R.id.claveUsuario)).getText().toString();

        new AsyncTask<Void,Void,Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                return authenticate(login, pwd);
            }

            @Override
            protected void onPostExecute(Boolean auth) {
                super.onPostExecute(auth);
                if( auth) {
                    Intent intent = new Intent(MainActivity.this, LanguageActivity.class);
                    intent.putExtra(LanguageActivity.EXTRA_LOGIN, login);
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this, R.string.errorAcceso,Toast.LENGTH_SHORT);
                }
            }
        }.execute();
    }
    public boolean authenticate (String login, String pwd){
        boolean estado = false;
        String respuesta;
        HttpURLConnection urlConnection = null;
        String surl = "http://194.30.12.79/checkUser.php?&apodo="+login+"&clave="+pwd;
        System.out.println("URL: " +surl);
        try {
            URL url = new URL(surl);
            urlConnection = (HttpURLConnection) url.openConnection();
            if (urlConnection.getResponseCode() == 200) {
                InputStream in = urlConnection.getInputStream();

                InputStreamReader isr = new InputStreamReader(in, "UTF-8");
                BufferedReader br = new BufferedReader(isr);
                //System.out.println("La respuesta es der: "+br.readLine());
                if ((respuesta = br.readLine()).equals("true")) {
                    System.out.println("La respuesta es: " + respuesta);
                    estado = true;
                } else {
                    estado = false;
                }
                br.close();
                urlConnection.disconnect();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return estado;
    }

    public void registrar(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
    public void compruebaConexion(){
        ConnectivityManager cM = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cM.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            if(networkInfo.getType() ==  1){
                //Si la conexion es WIFI
                Toast.makeText(this,getString(R.string.wifiOk), Toast.LENGTH_SHORT).show();
            }
            else
            {
                //Si la conexion NO es WIFI
                //Toast.makeText(this,getString(R.string.wifiAdvice), Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.dialogo);
                builder.setMessage(R.string.wifiAdvice);
                builder.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        activaWifi();
                    }
                });
                builder.setNegativeButton(R.string.negar, null);
                builder.show();
            }
        }
        else
        {
            //Si no hay conexion
            Toast.makeText(this,getString(R.string.noConexion), Toast.LENGTH_SHORT).show();
        }
    }
    public boolean activaWifi(){
        WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        wifi.setWifiEnabled(true);
        return true;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case BUTTON_NEGATIVE:
                // int which = -2
                dialog.dismiss();
                break;
            case BUTTON_POSITIVE:
                // int which = -1
                dialog.dismiss();
                break;
        }
    }
}
