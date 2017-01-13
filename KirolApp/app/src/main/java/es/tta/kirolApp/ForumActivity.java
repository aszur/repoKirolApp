package es.tta.kirolApp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.docencia.kirolApp.R;

import java.util.ArrayList;
import java.util.List;

import es.tta.kirolApp.model.Comentario;
import es.tta.kirolApp.model.CommentListAdapter;
import es.tta.kirolApp.model.NetworkRequests;


public class ForumActivity extends AppCompatActivity {
    private List<Comentario> listaComentarios = new ArrayList<Comentario>();
    private String user;
    private int id;//id del foro
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        user = extras.getString("user"); //Recojo el usuario que podría comentar
        id = extras.getInt("idForo");
        System.out.println("IdForo en forumActivity es:" +id);
        listaComentarios = NetworkRequests.cargaComentarios(Integer.toString(id));
        //FALTA CARGA COMENTARIOS AQUI
        cargaComentarios();
    }
    //Revisr los adapters para cargar las listas de comentarios. layout creado ya con el elemento comentariolist.
    public void cargaComentarios(){

        ListView lista= (ListView) findViewById(R.id.listaComentarios);
        //Obtener el contenedor de comentarios del usuario con una peticion http
        //Guardar los comentarios en un contenedor de comentarios.
        //Pasar el contenedor de comentarios o un arrayList al adapter
        // Creamos el adapter
        CommentListAdapter adapter= new CommentListAdapter(this,listaComentarios,getBaseContext());
        // Una vez hecha la conexi�n pasamos los datos.
        lista.setAdapter(adapter);
    }
    public void enviaComentario(View view){
        System.out.println("Estamos en envia comentario");
        EditText cuadroTexto = (EditText)findViewById(R.id.cuadroComentario);
        String mensaje = cuadroTexto.getText().toString();
        Comentario comentario = new Comentario();
        comentario.setRemitente(user);
        comentario.setMensaje(mensaje);
        System.out.println("--------------El mensaje a enviar es: "+comentario.getMensaje());
        comentario.setId(id);
        System.out.println("Comentario recogido es: "+comentario.getMensaje());
        boolean rp = NetworkRequests.enviaComentario(comentario);
        System.out.println("Envia comentario devuelve: "+rp);
        if (rp){
            Toast.makeText(this,R.string.comentarioEnviado, Toast.LENGTH_SHORT).show();
            cargaComentarios();
            finish();
            startActivity(getIntent());
        }else{
            Toast.makeText(this,R.string.comentarioNoEnviado, Toast.LENGTH_SHORT).show();
            finish();
            startActivity(getIntent());
        }

    }
}
