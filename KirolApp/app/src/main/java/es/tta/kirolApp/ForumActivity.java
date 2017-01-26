package es.tta.kirolApp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.docencia.kirolApp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import es.tta.kirolApp.model.Comentario;
import es.tta.kirolApp.model.CommentListAdapter;
import es.tta.kirolApp.model.NetworkRequests;


public class ForumActivity extends AppCompatActivity {
    private List<Comentario> listaComentarios = new ArrayList<Comentario>();
    private String user;
    private int id;//id del foro
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        user = extras.getString("user"); //Recojo el usuario que podría comentar
        id = extras.getInt("idForo");
        System.out.println("IdForo en forumActivity es:" +id);
        listaComentarios = NetworkRequests.cargaComentarios(Integer.toString(id));
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                cargaComentariosAT(Integer.toString(id));
                return null;
            }
        };
        cargaComentarios();
    }

    public void cargaComentariosAT(String idForo){
        String respuesta ="";
        HttpURLConnection urlConnection = null;
        String surl = "http://194.30.12.79/getMessagesByForum.php?forumID="+idForo;
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
                        listaComentarios.add(com);
                    }
                    Collections.reverse(listaComentarios);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Revisr los adapters para cargar las listas de comentarios. layout creado ya con el elemento comentariolist.
    public void cargaComentarios(){

        ListView lista= (ListView) findViewById(R.id.listaComentarios);
        //Obtener el contenedor de comentarios del usuario con una peticion http
        //Guardar los comentarios en un contenedor de comentarios.
        //Pasar el contenedor de comentarios o un arrayList al adapter
        // Creamos el adapter
        CommentListAdapter adapter= new CommentListAdapter(this,listaComentarios,getBaseContext());
        // Una vez hecha la conexi�n pasamos los datos.
        lista.setAdapter(adapter);
    }
    public void enviaComentario(View view){
        System.out.println("Estamos en envia comentario");
        EditText cuadroTexto = (EditText)findViewById(R.id.cuadroComentario);
        String mensaje = cuadroTexto.getText().toString();
        final Comentario comentario = new Comentario();
        comentario.setRemitente(user);
        comentario.setMensaje(mensaje);
        System.out.println("--------------El mensaje a enviar es: "+comentario.getMensaje());
        comentario.setId(id);
        System.out.println("Comentario recogido es: "+comentario.getMensaje());
        //boolean rp = NetworkRequests.enviaComentario(comentario);
        new AsyncTask<Void, Void, Boolean>(){
            @Override
            protected Boolean doInBackground(Void... params) {
                return pullComment(comentario);
            }

            @Override
            protected void onPostExecute(Boolean submited) {
                super.onPostExecute(submited);
                if (submited){
                    Toast.makeText(ForumActivity.this,R.string.comentarioEnviado, Toast.LENGTH_SHORT).show();
                    cargaComentarios();
                    finish();
                    startActivity(getIntent());
                }else{
                    Toast.makeText(ForumActivity.this,R.string.comentarioNoEnviado, Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(getIntent());
                }
            }
        }.execute();

        


    }

    public boolean pullComment(Comentario comentario){
        boolean estado = false;
        String respuesta;
        HttpURLConnection urlConnection = null;
        String mensaje = comentario.getMensaje();
        String remite = comentario.getRemitente();
        int id = comentario.getId();//Aqui no va el id del comentario, se utiliza el campo para el id del foro
        String idForum = Integer.toString(id);
        try {
            String msg = URLEncoder.encode(mensaje, "utf-8");
            String surl = "http://194.30.12.79/putMessageByForum.php?idForo="+idForum+"&message="+msg+"&emisor="+remite;
            System.out.println(surl);
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

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return estado;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                // User chose exit
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
