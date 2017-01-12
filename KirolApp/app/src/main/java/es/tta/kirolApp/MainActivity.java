package es.tta.kirolApp;

import android.content.Intent;
import android.net.NetworkRequest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.docencia.kirolApp.R;

import es.tta.kirolApp.model.NetworkRequests;
import es.tta.kirolApp.model.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle(R.string.app_name);
    }

    public void accede(View view) { //Funcion llamada desde el view
        Intent intent = new Intent(this, LanguageActivity.class);
        String login = ((EditText) findViewById(R.id.nombreUsuario)).getText().toString();
        String pwd = ((EditText) findViewById(R.id.claveUsuario)).getText().toString();
        if (authenticate(login, pwd)) {
            intent.putExtra(LanguageActivity.EXTRA_LOGIN, login);
            startActivity(intent);
        }
    }
    public boolean authenticate (String login, String pwd){
        boolean comprobacion = false;
        User usuario = new User();
        usuario.setApodo(login);
        usuario.setPwd(pwd);
        if(NetworkRequests.checkUser(usuario)){
            System.out.println("checUser devuelve true");
            comprobacion = true;
        }else{
            Toast.makeText(this,R.string.errorAcceso, Toast.LENGTH_SHORT).show();
        }
        return comprobacion;
    }

    public void registrar(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
