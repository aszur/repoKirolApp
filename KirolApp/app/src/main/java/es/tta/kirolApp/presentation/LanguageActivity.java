package es.tta.kirolApp.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.docencia.kirolApp.R;

import es.tta.kirolApp.model.others.Utils;
/*Activity to select the language*/
public class LanguageActivity extends AppCompatActivity {
    public final static String EXTRA_LOGIN = "es.tta.kirolApp.login";
    private String usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        usuario = bundle.getString(EXTRA_LOGIN);
        ImageView banderaEsp = (ImageView) findViewById(R.id.botonEspana);
        ImageView banderaEn = (ImageView) findViewById(R.id.botonInglaterra);
        banderaEsp.setOnClickListener(toLanguage);
        banderaEn.setOnClickListener(toLanguage);
        String bienvenida = getString(R.string.bienvenida) +" "+ usuario;
        Toast.makeText(this,bienvenida, Toast.LENGTH_SHORT).show();
    }


    View.OnClickListener toLanguage = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("Estado", "En onClick por Java");
            if(v.getId() == R.id.botonEspana){
                Log.d("Estado", "En onClick por Java:. España");
                Utils.cambiaIdioma("es",getResources());
                Utils.cambiaIdioma("es",getResources());
                Intent intent = new Intent(LanguageActivity.this, SportsActivity.class);
                intent.putExtra("Idioma","es");
                intent.putExtra("user",usuario);
                startActivity(intent);
            }else if(v.getId() == R.id.botonInglaterra){
                Log.d("Estado", "En onClick por Java. Inglaterra");
                Utils.cambiaIdioma("en",getResources());
                Intent intent = new Intent(LanguageActivity.this, SportsActivity.class);
                intent.putExtra("Idioma","en");
                intent.putExtra("user",usuario);
                startActivity(intent);
            }
        }
    };




}
