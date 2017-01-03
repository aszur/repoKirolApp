package es.tta.kirolApp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.docencia.kirolApp.R;

import java.io.File;
import java.io.IOException;

import es.tta.kirolApp.model.NetworkRequests;
import es.tta.kirolApp.model.User;

public class RegisterActivity extends AppCompatActivity {
    //NetworkRequests solicitud = new NetworkRequests();
    Uri pictureUri;
    public static final int PICTURE_REQUEST_CODE = 1;
    User usuario = new User();
    EditText Tnombre;
    EditText Tapodo;
    EditText Tapellido1;
    EditText Tapellido2;
    EditText Tpwd;
    EditText TrepPwd;
    EditText Temail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void registrarUsuario(){
        if(compruebaCampos()){
            NetworkRequests.registra(usuario);
        }

    }
    private boolean compruebaCampos(){
        boolean fields = false;
        boolean pwd = false;
        Tnombre = (EditText)findViewById(R.id.regName);
        Tapodo = (EditText)findViewById(R.id.regName);
        Tapellido1 = (EditText)findViewById(R.id.regName);
        Tapellido2 = (EditText)findViewById(R.id.regName);
        Tpwd = (EditText)findViewById(R.id.regName);
        TrepPwd = (EditText)findViewById(R.id.regName);
        Temail = (EditText)findViewById(R.id.regName);
        if(Tnombre.getText().equals("")
                || Tapodo.getText().equals("")
                || Tapellido1.getText().equals("")
                || Tapellido2.getText().equals("")
                || Tpwd.getText().equals("")
                || Temail.getText().equals("") )
        {
            Toast.makeText(this,getString(R.string.completeCampos), Toast.LENGTH_SHORT).show();
           /* if(Tnombre.getText().equals("")){
                Toast.makeText(this,getString(R.string.claveEsCorta), Toast.LENGTH_SHORT).show();
            }
            if(Tapodo.getText().equals("")){
                Toast.makeText(this,getString(R.string.claveEsCorta), Toast.LENGTH_SHORT).show();
            }
            if(Tapellido1.getText().equals("")){
                Toast.makeText(this,getString(R.string.claveEsCorta), Toast.LENGTH_SHORT).show();
            }
            if(Tapellido2.getText().equals("")){
                Toast.makeText(this,getString(R.string.claveEsCorta), Toast.LENGTH_SHORT).show();
            }
            if(Tpwd.getText().equals("")){
                Toast.makeText(this,getString(R.string.claveEsCorta), Toast.LENGTH_SHORT).show();
            }
            if(Temail.getText().equals("")){
                Toast.makeText(this,getString(R.string.claveEsCorta), Toast.LENGTH_SHORT).show();
            }*/

        }
        else{
            fields = true;
        }

        if(Tpwd.equals(TrepPwd) && Tpwd.length()>4 && fields)
        {
            usuario.setNombre(Tnombre.toString());
            usuario.setApellido1(Tapellido1.toString());
            usuario.setApellido2(Tapellido2.toString());
            usuario.setApodo(Tapodo.toString());
            usuario.setPwd(Tpwd.toString());
            usuario.setEmail(Temail.toString());
            pwd = true;
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
        if(pwd  && fields){
            return true;
        }else{
            return false;
        }
    }
    public void takePicture(View view){
        Log.d("Estado", "En takePicture");
        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            Log.d("Estado", "En takePicture. No camara");
            Toast.makeText(this,R.string.noCamera, Toast.LENGTH_SHORT).show();
        }
        else
        {
            Log.d("Estado", "En takePicture. Con camara");
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Log.d("Estado", "En takePicture. Intent creado");
            if(intent.resolveActivity(getPackageManager())!= null){
                File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                try {
                    File file = File.createTempFile("tta",".jpg", dir);
                    pictureUri = Uri.fromFile(file);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,pictureUri);
                    Log.d("Estado", "En takePicture. Antes del StartForResult");
                    startActivityForResult(intent, PICTURE_REQUEST_CODE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                Log.d("Estado", "En takePicture. No app disponible");
                Toast.makeText(this,R.string.noApp, Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode != Activity.RESULT_OK);
        return;
        /*switch (requestCode){
            case READ_REQUEST_CODE:
            case VIDEO_REQUEST_CODE:
            case AUDIO_REQUEST_CODE:
                sendFile(data.getData());
                //break;
            case PICTURE_REQUEST_CODE:
                sendFile(pictureUri);
                //break;
        }*/
    }
}
