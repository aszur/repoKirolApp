package es.tta.kirolApp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.docencia.kirolApp.R;


public class ForumActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String user = extras.getString("user"); //Recojo el usuario que podría comentar
    }
    //Revisr los adapters para cargar las listas de comentarios. layout creado ya con el elemento comentariolist.
    public void cargaComentarios(){

    }
    public void enviaComentario(){


    }
}
