package es.tta.kirolApp.presentation;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.docencia.kirolApp.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle(R.string.app_name);
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
                if( auth ) {
                    Intent intent = new Intent(MainActivity.this, LanguageActivity.class);
                    intent.putExtra(LanguageActivity.EXTRA_LOGIN, login);
                    startActivity(intent);
                }
            }
        }.execute();
    }
    public boolean authenticate (String login, String pwd){
        boolean estado = false;
        String respuesta;
        HttpURLConnection urlConnection = null;
        String surl = "http://194.30.12.79/checkUser.php?&apodo="+login+"&clave="+pwd;
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
                    estado = true;
                } else {
                    estado = false;
                }
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
}
