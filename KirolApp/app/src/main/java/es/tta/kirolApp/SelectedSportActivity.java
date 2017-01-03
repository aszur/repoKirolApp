package es.tta.kirolApp;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.docencia.kirolApp.R;

import java.io.File;
import java.net.URI;

import es.tta.kirolApp.model.DeporteXid;
import es.tta.kirolApp.model.NetworkRequests;


public class SelectedSportActivity extends AppCompatActivity {
    private DeporteXid deporte;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_sport);
        Bundle extras = getIntent().getExtras();
        int sportId = extras.getInt("SportId"); //Recogemos el id devuelto por la actividad anterior para solicitar info sobre ese deporte
        deporte = NetworkRequests.cargaDeportesXid(sportId);
        deporte.setUrlDescEs("https://dl.dropboxusercontent.com/s/oulzgpgnq2ca1ly/KorfbalSpa.pdf?dl=0");
    }
    public void cargaDescripcion(View view){
        cargaPdf(Uri.parse(deporte.getUrlDescEs()));
    }
    public void cargaAdaptacion(){
        cargaPdf(Uri.parse(deporte.getUrlAdapEs()));
    }
    public void cargaForo1(){

    }
    public void cargaForo2(){

    }
    public void cargaForo3(){

    }

    private void cargaPdf(Uri url){
        //File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +"/"+ filename);
        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setDataAndType(url,"application/pdf");
        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        Intent intent = Intent.createChooser(target, "Open File");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // Instruct the user to install a PDF reader here, or something
        }
    }
}
