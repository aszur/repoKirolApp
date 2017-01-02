package es.tta.kirolApp;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.docencia.kirolApp.R;

import es.tta.kirolApp.model.NetworkRequests;
import es.tta.kirolApp.model.User;

public class RegisterActivity extends AppCompatActivity {
    //NetworkRequests solicitud = new NetworkRequests();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

public void registrarUsuario(){
    EditText Tnombre = (EditText)findViewById(R.id.regName);
    EditText Tapodo = (EditText)findViewById(R.id.regName);
    EditText Tapellido1 = (EditText)findViewById(R.id.regName);
    EditText Tapellido2 = (EditText)findViewById(R.id.regName);
    EditText Tpwd = (EditText)findViewById(R.id.regName);
    EditText TrepPwd = (EditText)findViewById(R.id.regName);
    EditText Temail = (EditText)findViewById(R.id.regName);
    if(Tpwd.equals(TrepPwd) && Tpwd.length()>4)
    {
        User usuario = new User();
        usuario.setNombre(Tnombre.toString());
        usuario.setApellido1(Tapellido1.toString());
        usuario.setApellido2(Tapellido2.toString());
        usuario.setApodo(Tapodo.toString());
        usuario.setPwd(Tpwd.toString());
        usuario.setEmail(Temail.toString());
        //solicitud.registra(usuario);
        NetworkRequests.registra(usuario);
    }
    else
    {
        if(Tpwd.equals(TrepPwd) == false)
        {
            Toast.makeText(this,getString(R.string.claveNoCoincide), Toast.LENGTH_SHORT).show();
        }
        else{
            if(Tpwd.length()<4){
                Toast.makeText(this,getString(R.string.claveEsCorta), Toast.LENGTH_SHORT).show();
            }
        }

    }

}
}
