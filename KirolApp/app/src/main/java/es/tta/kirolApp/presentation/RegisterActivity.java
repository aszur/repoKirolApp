package es.tta.kirolApp.presentation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.docencia.kirolApp.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import es.tta.kirolApp.model.classes.User;

public class RegisterActivity extends AppCompatActivity {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void registrarUsuario(View v){
        if(compruebaCampos()){
            System.out.println("Campos comprobados. Vamos a registrar usuario.");

            new AsyncTask<Void, Void, Boolean>() {
                @Override
                protected Boolean doInBackground(Void... params) {
                    return registra();
                }

                @Override
                protected void onPostExecute(Boolean regStatus) {
                    if(regStatus){
                        Toast.makeText(RegisterActivity.this, R.string.userAdded,
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(RegisterActivity.this, R.string.userNoAdded,
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }.execute();
        }

    }

    public boolean registra(){
        boolean estado = false;
        HttpURLConnection urlConnection = null;
        String respuesta="";
        String surl = "http://194.30.12.79/putUser.php?nombre="+usuario.getNombre()+"&apodo="+usuario.getApodo()+"&apellido1="+usuario.getApellido1()+
                "&apellido2="+usuario.getApellido2()+"&email="+usuario.getEmail()+"&clave="+usuario.getPwd();
        System.out.println(surl);
        try {
            URL url = new URL(surl);
            urlConnection = (HttpURLConnection) url.openConnection();
            if (urlConnection.getResponseCode() == 200) {
                InputStream in = urlConnection.getInputStream();
                InputStreamReader isr = new InputStreamReader(in, "UTF-8");
                BufferedReader br = new BufferedReader(isr);
                if ((respuesta = br.readLine()).equals("true")) {
                    System.out.println("La respuesta es: " + respuesta);
                    estado = true;
                } else {
                    estado = false;
                }
                br.close();
                urlConnection.disconnect();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return estado;
    }

    private boolean compruebaCampos(){
        boolean fields = false;
        Tnombre = (EditText)findViewById(R.id.regName);
        Tapodo = (EditText)findViewById(R.id.regNoName);
        Tapellido1 = (EditText)findViewById(R.id.regApellido);
        Tapellido2 = (EditText)findViewById(R.id.regApellido2);
        Tpwd = (EditText)findViewById(R.id.regPass);
        TrepPwd = (EditText)findViewById(R.id.regRepPass);
        Temail = (EditText)findViewById(R.id.regEmail);
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
        }
        else{//Si los campos no estan vacios
            System.out.println("Campos completados. Comprobando si "+TrepPwd.getText().toString()+"="+Tpwd.getText().toString()+
            "La longitud es: "+Tpwd.getText().length());
            if(Tpwd.getText().toString().equals(TrepPwd.getText().toString()) && Tpwd.getText().length()>4)
            {
                System.out.println("Campos completados. Dentro del if...");

                usuario.setNombre(Tnombre.getText().toString());
                usuario.setApellido1(Tapellido1.getText().toString());
                usuario.setApellido2(Tapellido2.getText().toString());
                usuario.setApodo(Tapodo.getText().toString());
                usuario.setPwd(Tpwd.getText().toString());
                usuario.setEmail(Temail.getText().toString());
                fields = true;
            }
            else {
                if (Tpwd.getText().toString().equals(TrepPwd.getText().toString()) == false) {
                    System.out.println("Campos completados. Claves no coinciden...");

                    Toast.makeText(this, getString(R.string.claveNoCoincide), Toast.LENGTH_SHORT).show();
                } else {
                    if (Tpwd.length() < 4) {
                        System.out.println("Campos completados. Clave pequeña...");

                        Toast.makeText(this, getString(R.string.claveEsCorta), Toast.LENGTH_SHORT).show();
                    }
                }
                fields = false;
            }
        }
        return fields;
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
