package es.tta.kirolApp.presentation;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.docencia.kirolApp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import es.tta.kirolApp.model.classes.Sport;
import es.tta.kirolApp.model.classes.Country;

public class SportsActivity extends AppCompatActivity {
    protected List<Country> listaPaises;
    protected List<Sport> listaDeportes;
    protected RadioGroup grupoContinentes;
    private String idDeporte="0";
    private String idPais ="0";
    private String idioma;
    private String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports);
        Bundle extras = getIntent().getExtras();
        idioma = extras.getString("Idioma");
        user = extras.getString("user");
        System.out.println(idioma);
        grupoContinentes = (RadioGroup)findViewById(R.id.grupoContinentes);
        grupoContinentes.check(R.id.radioEuropa);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
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
        Spinner spPaises = (Spinner) findViewById(R.id.paisSpinner);
        grupoContinentes.setVisibility(View.GONE);
        switch (grupoContinentes.getCheckedRadioButtonId()){
            case R.id.radioEuropa:
                Log.d("Continente: ", "Europa");
                rellenaSpiner(spPaises,0,0);//0
                break;
            case R.id.radioAfrica:
                Log.d("Continente: ", "Africa");
                rellenaSpiner(spPaises,0,0);//1
                break;
            case R.id.radioAmerica:
                Log.d("Continente: ", "America");
                rellenaSpiner(spPaises,0,0);//2
                break;
            case R.id.radioAsia:
                Log.d("Continente: ", "Asia");
                rellenaSpiner(spPaises,0,0);//3
                break;
            case R.id.radioOceania:
                Log.d("Continente: ", "Oceania");
                rellenaSpiner(spPaises,0,0);//4
                break;
        }
        Button boton = (Button) findViewById(R.id.botonEligeContinente);
        boton.setVisibility(View.GONE);
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
        rellenaSpiner(spDeportes,1,0);
        Spinner deportes = (Spinner) findViewById(R.id.deporteSpinner);
        deportes.setVisibility(View.VISIBLE);
        Button boton2 = (Button) findViewById(R.id.botonEligeDeporte);
        boton2.setVisibility(View.VISIBLE);


    }
    public void eligeDeporte(View view){
        Spinner deportes = (Spinner) findViewById(R.id.deporteSpinner);
        deportes.setVisibility(View.GONE);
        String deporteElegido = deportes.getSelectedItem().toString();
        Log.d("Sport escogido", deporteElegido);
        for(int i=0; i<listaDeportes.size() ; i++){
            Log.d("Estado", "Dentro del for");
            if(deporteElegido.equals(listaDeportes.get(i).getNombre())){
                Log.d("Estado", "Antes de asignar Id");
                idDeporte = Integer.toString(listaDeportes.get(i).getId());
                Log.d("Estado", "Dentro del for/if. IdDeporte "+idDeporte);
                Intent intent = new Intent(SportsActivity.this,SelectedSportActivity.class);
                intent.putExtra("SportId",idDeporte);
                intent.putExtra("Idioma", idioma);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        }
        Button boton = (Button) findViewById(R.id.botonEligeDeporte);
        boton.setVisibility(View.GONE);

    }
    private void rellenaSpiner(final Spinner sp, int select, int ctry) { //ctry .> Variable para implementar resto de paises, no aplicada.
        final List<String> lista = new ArrayList<String>(); // Lista que se pasa al adapter
        if(select == 0)
        {
            listaPaises = new ArrayList<Country>();
            new AsyncTask<Void, Void, Void> () {
                @Override
                protected Void doInBackground(Void... params) {
                    listaPaises = cargaPaises("1");
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    for (Country pais: listaPaises) {
                        String ctry = pais.getNombre();
                        lista.add(ctry);
                    }
                    ArrayAdapter<String> adaptador = new ArrayAdapter<String>(SportsActivity.this,
                            android.R.layout.simple_spinner_item, lista);
                    adaptador
                            .setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                    sp.setAdapter(adaptador);
                }
            }.execute();
        }
        else
        {
            if(select == 1){
                idPais = obtieneId();
                new AsyncTask<Void, Void, Void> () {
                    @Override
                    protected Void doInBackground(Void... params) {
                        listaDeportes = cargaListaDeportes(idPais);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        for (Sport deporte: listaDeportes) {
                            String sport = deporte.getNombre();
                            lista.add(sport);
                        }
                        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(SportsActivity.this,
                                android.R.layout.simple_spinner_item, lista);
                        adaptador
                                .setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                        sp.setAdapter(adaptador);
                    }
                }.execute();
            }

        }
    }
    private String  obtieneId(){
        Spinner sp = (Spinner)findViewById(R.id.paisSpinner);
        String pais = sp.getSelectedItem().toString();
        String id="";
        for (Country p:listaPaises) {
            if(p.getNombre().equals(pais)){
                id = Integer.toString(p.getId());
                System.out.println("El id del pais "+p.getNombre()+ " es: "+id);
            }
        }
        return id;
    }


    private List<Country> cargaPaises(String idContinente){
        String respuesta ="";
        HttpURLConnection urlConnection = null;
        List<Country> paises = new ArrayList<Country>();
        String surl = "http://194.30.12.79/getCountries.php?idContinente="+idContinente;
        try {
            URL url = new URL(surl);
            urlConnection = (HttpURLConnection) url.openConnection();
            if (urlConnection.getResponseCode() == 200) {
                InputStream in = urlConnection.getInputStream();
                InputStreamReader isr = new InputStreamReader(in, "UTF-8");
                BufferedReader br = new BufferedReader(isr);
                respuesta = br.readLine();
                //System.out.println(respuesta);
                try {
                    JSONArray jr = new JSONArray(respuesta);
                    int size = jr.length();
                    for (int i = 0; i < size; i++) {
                        Country p = new Country();
                        JSONObject jo = (JSONObject) jr.getJSONObject(i);
                        p.setId(jo.getInt("idPais"));
                        System.out.println("Id: " + p.getId());
                        p.setNombre(jo.getString("nombre"));
                        System.out.println("Id: " + p.getNombre());
                        paises.add(p);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                br.close();
                urlConnection.disconnect();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return paises;
    }

    private List<Sport> cargaListaDeportes(String idPais){
        String respuesta ="";
        HttpURLConnection urlConnection = null;
        List<Sport> deportes = new ArrayList<Sport>();
        String surl = "http://194.30.12.79/getSportsByCountry.php?idPais="+idPais;
        System.out.println(surl);
        try {
            URL url = new URL(surl);
            urlConnection = (HttpURLConnection) url.openConnection();
            if (urlConnection.getResponseCode() == 200) {
                InputStream in = urlConnection.getInputStream();
                InputStreamReader isr = new InputStreamReader(in, "UTF-8");
                BufferedReader br = new BufferedReader(isr);
                respuesta = br.readLine();
                System.out.println(respuesta);
                try {
                    JSONArray jr = new JSONArray(respuesta);
                    int size = jr.length();
                    for (int i = 0; i < size; i++) {
                        Sport d = new Sport();
                        JSONObject jo = (JSONObject) jr.getJSONObject(i);
                        d.setId(jo.getInt("id"));
                        System.out.println("Id: " + d.getId());
                        d.setNombre(jo.getString("nombre"));
                        System.out.println("Id: " + d.getNombre());
                        deportes.add(d);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                br.close();
                urlConnection.disconnect();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return deportes;
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
