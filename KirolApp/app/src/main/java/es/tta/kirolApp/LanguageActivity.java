package es.tta.kirolApp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.docencia.kirolApp.R;

public class LanguageActivity extends AppCompatActivity {
    public final static String EXTRA_LOGIN = "es.tta.kirolApp.login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String usuario = bundle.getString(EXTRA_LOGIN);

        String bienvenida = getString(R.string.bienvenida) +" "+ usuario;
        Toast.makeText(this,bienvenida, Toast.LENGTH_SHORT).show();
    }



}
