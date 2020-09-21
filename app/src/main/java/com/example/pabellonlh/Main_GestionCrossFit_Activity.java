package com.example.pabellonlh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class Main_GestionCrossFit_Activity extends AppCompatActivity {

    String  Martes = "Martes", Jueves = "Jueves", actividad = "CROSSFIT";
    ListView lvMartes, lvJueves;
    TabHost TbH;
    Handler handler = new Handler();
    TextView tvNombre;
    String usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__gestion_cross_fit_);

        Intent intent = getIntent();
        usuario = intent.getStringExtra("usuario");
        intent.putExtra("usuario", usuario);

        lvMartes = (ListView) findViewById(R.id.lvActividades);
        lvJueves = (ListView) findViewById(R.id.lvJueves);
        tvNombre = (TextView) findViewById(R.id.tvNombre1);
        TbH = (TabHost) findViewById(R.id.tabHost); //llamamos al Tabhost
        TbH.setup();                                                         //lo activamos

        TabHost.TabSpec tab1 = TbH.newTabSpec("tab1");  //aspectos de cada Tab (pestaña)
        TabHost.TabSpec tab2 = TbH.newTabSpec("tab2");


        tab1.setIndicator("MARTES");
        tab1.setContent(R.id.HActividades);

        tab2.setIndicator("JUEVES");
        tab2.setContent(R.id.JUEVES);



        TbH.addTab(tab1); //añadimos los tabs ya programados
        TbH.addTab(tab2);

        if(usuario == null){
            recuperarPreferendiasAdmin();
            //  Toast.makeText(getApplicationContext(),"pruebaaaaa"+ usuario, Toast.LENGTH_SHORT).show();
        }

        ActividadOcupadasMartes("http://jose-cordones.es/app/consultas/obtenerActividadesOcupadas_Inicio.php?nombre="+actividad+"&dia="+ Martes);
        ActividadOcupadasJueves("http://jose-cordones.es/app/consultas/obtenerActividadesOcupadas_Inicio.php?nombre="+actividad+"&dia="+ Jueves);
        ejecutarTarea();
    }

    private final int TIEMPO = 15000;

    public void ejecutarTarea() {
        handler.postDelayed(new Runnable() {
            public void run() {

                ActividadOcupadasMartes("http://jose-cordones.es/app/consultas/obtenerActividadesOcupadas_Inicio.php?nombre="+actividad+"&dia="+ Martes);
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
        lvJueves.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main_GestionCrossFit_Activity.this, Main_EliminarCloFitAdministrador_Activity.class);
                // Toast.makeText(Main_GestionZumba_Activity.this, lista.get(position)+"  "+finalUsu, Toast.LENGTH_SHORT).show();


                intent.putExtra("objetoData", lista.get(position) +  "                 "+Jueves+"                 "+actividad);
                intent.putExtra("usuario", usuario);
                startActivity(intent);

            }
        });

    }

    //lv del martes------------------------------------------------------------------------------------------------------------------------------------------
    public void ActividadOcupadasMartes(String URL){


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
                        CargarListViewMartes(ja);
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

    public void CargarListViewMartes(JSONArray ja) {

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
        lvMartes.setAdapter(adaptador);
        lvMartes.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main_GestionCrossFit_Activity.this, Main_EliminarCloFitAdministrador_Activity.class);
                // Toast.makeText(Main_GestionZumba_Activity.this, lista.get(position)+"  "+finalUsu, Toast.LENGTH_SHORT).show();


                intent.putExtra("objetoData", lista.get(position) +  "                 "+Martes+"                 "+actividad);
                intent.putExtra("usuario", usuario);
                startActivity(intent);

            }
        });

    }

    private void recuperarPreferendiasAdmin(){
        SharedPreferences preferences = getSharedPreferences("preferenciasLoginAdmin", Context.MODE_PRIVATE);
        tvNombre.setText(preferences.getString("usuario", ""));

        usuario = tvNombre.getText().toString();

    }
}
