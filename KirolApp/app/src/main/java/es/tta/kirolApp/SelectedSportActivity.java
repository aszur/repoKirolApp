package es.tta.kirolApp;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.docencia.kirolApp.R;

import java.io.File;
import java.io.IOException;

import es.tta.kirolApp.model.DeporteXid;
import es.tta.kirolApp.model.FAT_Downloader;
import es.tta.kirolApp.model.NetworkRequests;


public class SelectedSportActivity extends AppCompatActivity {
    private DeporteXid deporte;
    private boolean cargado = false;
    private String idioma;
    private String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_sport);
        Bundle extras = getIntent().getExtras();
        String sportId = extras.getString("SportId"); //Recogemos el id devuelto por la actividad anterior para solicitar info sobre ese deporte
        idioma = extras.getString("Idioma");
        user = extras.getString("user");
        deporte = NetworkRequests.cargaDeportesXid(sportId);
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
    }
    public void cargaForo2(View view){
        Intent intent = new Intent(SelectedSportActivity.this, ForumActivity.class);
        intent.putExtra("user", user );
        intent.putExtra("idForo", deporte.getIdForoMedio());
        startActivity(intent);
    }
    public void cargaForo3(View view) {
        Intent intent = new Intent(SelectedSportActivity.this, ForumActivity.class);
        intent.putExtra("user", user );
        intent.putExtra("idForo", deporte.getIdForoAlto());
        startActivity(intent);
    }

    /*public void showPdf()
    {
        Log.d("showPdf", "Entramos en show pdf");
        File file = new File(Environment.getExternalStorageDirectory()+"/Mypdf/Read.pdf");
        PackageManager packageManager = getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/pdf");
        startActivity(intent);
        //file.delete();
    }*/

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

}
