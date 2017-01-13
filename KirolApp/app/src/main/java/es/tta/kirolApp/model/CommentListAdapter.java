package es.tta.kirolApp.model;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.docencia.kirolApp.R;


import java.util.List;

/**
 * Created by asier on 13/01/17.
 */

@SuppressLint("ViewHolder") public class CommentListAdapter extends BaseAdapter {
    protected Activity activity;
    protected List<Comentario> comentarios;
    Context mcontext;


    public CommentListAdapter(Activity activity, List<Comentario> comentarios, Context context){
        this.activity= activity;
        this.comentarios= comentarios;
        this.mcontext= context;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return comentarios.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return comentarios.get(position);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View arg1, ViewGroup arg2) {
        LayoutInflater inf=(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);;
        View v= inf.inflate(R.layout.comentariolist,null);

        //Asociar el layout de la lista que hemos creado.
        //Definimos un objeto a partir del array, vamos a cargar el contenido
        //de ese objeto en el view de la lista.
        Comentario comentario= comentarios.get(position);
        TextView nombre= (TextView)v.findViewById(R.id.nombreComentarista);
        nombre.setText(comentario.getRemitente());
        System.out.println("Paso por comentarista"+ nombre.getText());
        //Cargamos el comentario
        TextView coment= (TextView)v.findViewById(R.id.comentario);
        coment.setText(comentario.getMensaje());
        TextView fecha = (TextView)v.findViewById(R.id.fechaComentario);
        fecha.setText(comentario.getFecha());

        return v;
    }


}
