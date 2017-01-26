package es.tta.kirolApp.presentation;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.docencia.kirolApp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import es.tta.kirolApp.model.SportById;
import es.tta.kirolApp.model.FAT_Downloader;


public class SelectedSportActivity extends AppCompatActivity {
    private SportById deporte;
    private boolean cargado = false;
    private String idioma;
    private String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_sport);
        Bundle extras = getIntent().getExtras();
        final String sportId = extras.getString("SportId"); //Recogemos el id devuelto por la actividad anterior para solicitar info sobre ese deporte
        System.out.println("SportId: "+sportId);
        idioma = extras.getString("Idioma");
        user = extras.getString("user");
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                cargaDeporteXid(sportId);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }.execute();
    }
    private void cargaDeporteXid(String sportId) {
        String respuesta = "";
        HttpURLConnection urlConnection = null;
        String surl = "http://194.30.12.79/getSportById.php?sportID=" + sportId;
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
                    deporte = new SportById();
                    JSONObject jo = new JSONObject(respuesta);
                    deporte.setNombre(jo.getString("nombre"));
                    System.out.println(deporte.getNombre());
                    deporte.setUrlAdapEs(jo.getString("adaptEs"));
                    System.out.println(deporte.getUrlAdapEs());
                    deporte.setUrlAdapEus(jo.getString("adaptEus"));
                    deporte.setUrlAdapEn(jo.getString("adaptEn"));
                    System.out.println(deporte.getUrlAdapEn());
                    deporte.setUrlDescEs(jo.getString("descEs"));
                    System.out.println(deporte.getUrlDescEs());
                    deporte.setUrlDescEus(jo.getString("descEus"));
                    deporte.setUrlDescEn(jo.getString("descEn"));
                    System.out.println(deporte.getUrlDescEn());
                    deporte.setUrlDescCat(jo.getString("descCat"));
                    deporte.setIdForoAlto(jo.getInt("foroNivelAlto"));
                    deporte.setIdForoMedio(jo.getInt("foroNivelMedio"));
                    deporte.setIdForoBajo(jo.getInt("foroNivelBajo"));

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

    public void cargaDescripcion(View view){
        System.out.println("Comprobando si "+idioma +" = es ");
        if(idioma.equals("es")){
            System.out.println(deporte.getUrlDescEs());//Aqui es null
            download(deporte.getUrlDescEs());
        }else if(idioma.equals("en")){
            System.out.println(deporte.getUrlDescEs());
            download(deporte.getUrlDescEn());
        }
    }
    public void cargaAdaptacion(View view){
        System.out.println("Comprobando si "+idioma +" = es ");
        if(idioma.equals("es")){
            System.out.println("Dentro de ES ");
            download(deporte.getUrlAdapEs());
        }else if(idioma.equals("en")){
            System.out.println("Dentro de EN ");
            download(deporte.getUrlAdapEn());
        }
    }
    public void cargaForo1(View view){
        Intent intent = new Intent(SelectedSportActivity.this, ForumActivity.class);
        intent.putExtra("user", user );
        System.out.println("IdForoBajo: "+deporte.getIdForoBajo());
        intent.putExtra("idForo", deporte.getIdForoBajo());
        startActivity(intent);
        finish();
    }
    public void cargaForo2(View view){
        Intent intent = new Intent(SelectedSportActivity.this, ForumActivity.class);
        intent.putExtra("user", user );
        intent.putExtra("idForo", deporte.getIdForoMedio());
        startActivity(intent);
        finish();
    }
    public void cargaForo3(View view) {
        Intent intent = new Intent(SelectedSportActivity.this, ForumActivity.class);
        intent.putExtra("user", user );
        intent.putExtra("idForo", deporte.getIdForoAlto());
        startActivity(intent);
        finish();
    }

    public void download(String url)
    {
        System.out.println("En download url = "+url);
        new DownloadFile().execute(url, "read.pdf");
    }

    public void showPdf()
    {
        String dir = Environment.getExternalStorageDirectory() + "/Mypdf/" + "read.pdf";
        Log.d("En ShowPdf",dir);
        File pdfFile = new File(dir);

        Uri path = Uri.fromFile(pdfFile);
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try{
            startActivity(pdfIntent);
        }catch(ActivityNotFoundException e){
            Toast.makeText(SelectedSportActivity.this, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
        }
    }
    private class DownloadFile extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            String fileUrl = strings[0];   // -> url del archivo
            String fileName = strings[1];  // -> nombre del archivo.pdf
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "Mypdf");
            folder.mkdir();
            File pdfFile = new File(folder, fileName);

            try{
                pdfFile.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
            FAT_Downloader.downloadFile(fileUrl, pdfFile);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            showPdf();
        }

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
