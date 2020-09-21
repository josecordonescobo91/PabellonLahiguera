package com.example.pabellonlh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class Main_Pilates_Inicio_Activity extends AppCompatActivity {


    ListView lvLunes, lvJueves;
    TabHost TbH;
    Handler handler = new Handler();
    String  Lunes = "Lunes", Jueves = "Jueves", actividad = "PILATES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__pilates__inicio_);

        lvLunes = (ListView) findViewById(R.id.lvDatos);
        lvJueves = (ListView) findViewById(R.id.lvJueves);
        TbH = (TabHost) findViewById(R.id.tabHost); //llamamos al Tabhost
        TbH.setup();                                                         //lo activamos

        TabHost.TabSpec tab1 = TbH.newTabSpec("tab1");  //aspectos de cada Tab (pestaña)
        TabHost.TabSpec tab2 = TbH.newTabSpec("tab2");


        tab1.setIndicator("LUNES");
        tab1.setContent(R.id.HPolideportivo);

        tab2.setIndicator("JUEVES");
        tab2.setContent(R.id.JUEVES);



        TbH.addTab(tab1); //añadimos los tabs ya programados
        TbH.addTab(tab2);

        ActividadOcupadasLunes("http://jose-cordones.es/app/consultas/obtenerActividadesOcupadas_Inicio.php?nombre="+actividad+"&dia="+ Lunes);
        ActividadOcupadasJueves("http://jose-cordones.es/app/consultas/obtenerActividadesOcupadas_Inicio.php?nombre="+actividad+"&dia="+ Jueves);
        ejecutarTarea();
    }

    private final int TIEMPO = 10000;

    public void ejecutarTarea() {
        handler.postDelayed(new Runnable() {
            public void run() {

                ActividadOcupadasLunes("http://jose-cordones.es/app/consultas/obtenerActividadesOcupadas_Inicio.php?nombre="+actividad+"&dia="+ Lunes);
                ActividadOcupadasJueves("http://jose-cordones.es/app/consultas/obtenerActividadesOcupadas_Inicio.php?nombre="+actividad+"&dia="+ Jueves);

                handler.postDelayed(this, TIEMPO);
            }

        }, TIEMPO);

    }

    //lv del jueves------------------------------------------------------------------------------------------------------------------------------------------
    public void ActividadOcupadasJueves(String URL){


        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replace("][",",");
                if (response.length()>0){
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson",""+ja.length());
                        Intent intent = getIntent();
                        //String usuario = intent.getStringExtra("usuario");
                        String pista = intent.getStringExtra("pista");
                        CargarListViewJueves(ja);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

    }

    public void CargarListViewJueves(JSONArray ja) {

        final ArrayList<String> lista = new ArrayList<>();
        int numero = 0;

        for (int i = 0; i < ja.length(); i += 1) {

            numero ++;

            try {
                lista.add("\n"+"                        Numero "+ numero +"                 "+ ja.getString(i + 0) );

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        final ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        lvJueves.setAdapter(adaptador);

    }

    //lv del lunes------------------------------------------------------------------------------------------------------------------------------------------
    public void ActividadOcupadasLunes(String URL){


        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replace("][",",");
                if (response.length()>0){
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson",""+ja.length());
                        Intent intent = getIntent();
                        //String usuario = intent.getStringExtra("usuario");
                        String pista = intent.getStringExtra("pista");
                        CargarListViewLunes(ja);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

    }

    public void CargarListViewLunes(JSONArray ja) {

        final ArrayList<String> lista = new ArrayList<>();
        int numero = 0;

        for (int i = 0; i < ja.length(); i += 1) {

            numero ++;

            try {
                lista.add("\n"+"                        Numero "+ numero +"                 "+ ja.getString(i + 0) );

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        final ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        lvLunes.setAdapter(adaptador);

    }
}
