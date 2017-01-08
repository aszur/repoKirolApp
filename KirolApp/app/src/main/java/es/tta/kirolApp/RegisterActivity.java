package es.tta.kirolApp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
    private static int MY_PERMISSIONS_REQUEST_CAMERA=0;
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
        int permissionCheck = ContextCompat.checkSelfPermission(RegisterActivity.this,
                Manifest.permission.CAMERA);
        System.out.println("Permiso: "+ permissionCheck);
        //Request permissions
        if (ContextCompat.checkSelfPermission(RegisterActivity.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(RegisterActivity.this,
                    Manifest.permission.CAMERA)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(RegisterActivity.this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_CAMERA);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    public void registrarUsuario(View v){
        if(compruebaCampos()){
            NetworkRequests.registra(usuario, getBaseContext());
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
        System.out.println(Tnombre.toString());
        if(Tnombre.getText().toString().equals("")
                || Tapodo.getText().toString().equals("")
                || Tapellido1.getText().toString().equals("")
                || Tapellido2.getText().toString().equals("")
                || Tpwd.getText().toString().equals("")
                || Temail.getText().toString().equals("") )
        {
            System.out.println("Antes del toast de los campos");
            Log.d("Comprobando campos","Antes del toast de campos");
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
        else{//Si los campos no estan vacios
            System.out.println("Campos completados. Comprobando...");
            if(Tpwd.getText().toString().equals(TrepPwd.getText().toString()) && Tpwd.getText().length()>4 && fields)
            {
                System.out.println("Campos completados. Dentro del if...");

                usuario.setNombre(Tnombre.getText().toString());
                usuario.setApellido1(Tapellido1.getText().toString());
                usuario.setApellido2(Tapellido2.getText().toString());
                usuario.setApodo(Tapodo.getText().toString());
                usuario.setPwd(Tpwd.getText().toString());
                usuario.setEmail(Temail.getText().toString());
                pwd = true;
            }
            else
            {
                if(Tpwd.getText().toString().equals(TrepPwd.getText().toString()) == false)
                {
                    System.out.println("Campos completados. Claves no coinciden...");

                    Toast.makeText(this,getString(R.string.claveNoCoincide), Toast.LENGTH_SHORT).show();
                }
                else{
                    if(Tpwd.length()<4){
                        System.out.println("Campos completados. Clave pequeña...");

                        Toast.makeText(this,getString(R.string.claveEsCorta), Toast.LENGTH_SHORT).show();
                    }
                }

            }
            fields = true;
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

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 0: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
