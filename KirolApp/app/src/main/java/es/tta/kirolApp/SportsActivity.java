package es.tta.kirolApp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.docencia.kirolApp.R;

import java.util.ArrayList;
import java.util.List;

import es.tta.kirolApp.model.Deporte;
import es.tta.kirolApp.model.NetworkRequests;
import es.tta.kirolApp.model.Pais;

public class SportsActivity extends AppCompatActivity {
    protected List<Pais> listaPaises;
    protected List<Deporte> listaDeportes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports);
        //Poblar spinners



    }
    @Override
    protected void onResume(){
        super.onResume();
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.grupoContinentes);
        radioGroup.setVisibility(View.VISIBLE);
        Button boton = (Button) findViewById(R.id.botonEligeContinente);
        boton.setVisibility(View.VISIBLE);
    }

    public void eligeContinente(View view){
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.grupoContinentes);
        radioGroup.setVisibility(View.GONE);
        Button boton = (Button) findViewById(R.id.botonEligeContinente);
        boton.setVisibility(View.GONE);
        Spinner spPaises = (Spinner) findViewById(R.id.paisSpinner);
        rellenaSpiner(spPaises,0);
        Spinner paises = (Spinner) findViewById(R.id.paisSpinner);
        paises.setVisibility(View.VISIBLE);
        Button boton2 = (Button) findViewById(R.id.botonEligePais);
        boton2.setVisibility(View.VISIBLE);

    }
    public void eligePais(View view){
        Spinner paises = (Spinner) findViewById(R.id.paisSpinner);
        paises.setVisibility(View.GONE);
        String paisElegido = paises.getSelectedItem().toString();

        Button boton = (Button) findViewById(R.id.botonEligePais);
        boton.setVisibility(View.GONE);
        Spinner spDeportes = (Spinner) findViewById(R.id.deporteSpinner);
        rellenaSpiner(spDeportes,1);
        Spinner deportes = (Spinner) findViewById(R.id.deporteSpinner);
        deportes.setVisibility(View.VISIBLE);
        Button boton2 = (Button) findViewById(R.id.botonEligeDeporte);
        boton2.setVisibility(View.VISIBLE);


    }
    public void eligeDeporte(View view){
        int idDeporte=0;
        Spinner deportes = (Spinner) findViewById(R.id.deporteSpinner);
        deportes.setVisibility(View.GONE);
        String deporteElegido = deportes.getSelectedItem().toString();
        Log.d("Deporte escogido", deporteElegido);
        for(int i=0; i<listaDeportes.size() ; i++){
            Log.d("Estado", "Dentro del for");
            if(deporteElegido == listaDeportes.get(i).getNombre()){
                i=listaDeportes.size();
                Log.d("Estado", "Antes de asignar Id");
               // idDeporte = listaDeportes.get(i).getId();
                Log.d("Estado", "Dentro del for/if. IdDeporte"+idDeporte);
                Intent intent = new Intent(SportsActivity.this,SelectedSportActivity.class);
                intent.putExtra("SportId",idDeporte);
                startActivity(intent);
            }
        }
        Button boton = (Button) findViewById(R.id.botonEligeDeporte);
        boton.setVisibility(View.GONE);

    }
    private void rellenaSpiner(Spinner sp, int select) {
        List<String> lista = new ArrayList<String>();
        if(select == 0)
        {
            listaPaises = new ArrayList<Pais>();
            listaPaises = NetworkRequests.cargaPaises("1");
            for (Pais pais: listaPaises) {
                String ctry = pais.getNombre();
                lista.add(ctry);
            }
            ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, lista);
            adaptador
                    .setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            sp.setAdapter(adaptador);
        }
        else
        {
            if(select == 1){
                listaDeportes = new ArrayList<Deporte>();
                String id = obtieneId();
                listaDeportes = NetworkRequests.cargaListaDeportes(id); // Recibe el id del pais del que quiere cargar los deportes
                for (Deporte deporte: listaDeportes) {
                    String sport = deporte.getNombre();
                    lista.add(sport);
                }
                ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, lista);
                adaptador
                        .setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                sp.setAdapter(adaptador);
            }

        }
    }
    private String  obtieneId(){
        Spinner sp = (Spinner)findViewById(R.id.paisSpinner);
        String pais = sp.getSelectedItem().toString();
        String id="";
        for (Pais p:listaPaises) {
            if(p.getNombre().equals(pais)){
                id = Integer.toString(p.getId());
                System.out.println("El id del pais "+p.getNombre()+ " es: "+id);
            }
        }
        return id;
    }
}
