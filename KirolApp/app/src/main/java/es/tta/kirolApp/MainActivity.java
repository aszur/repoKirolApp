package es.tta.kirolApp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.docencia.kirolApp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle(R.string.app_name);
    }

    public boolean authenticate (String login, String pwd){
        boolean comprobacion = true;
        return comprobacion;
    }
    public void accede(View view) {
        Intent intent = new Intent(this, LanguageActivity.class);
        String login = ((EditText) findViewById(R.id.nombreUsuario)).getText().toString();
        String pwd = ((EditText) findViewById(R.id.claveUsuario)).getText().toString();
        if (authenticate(login, pwd)) {
            intent.putExtra(LanguageActivity.EXTRA_LOGIN, login);
            startActivity(intent);
        }
    }
    public void registrar(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
