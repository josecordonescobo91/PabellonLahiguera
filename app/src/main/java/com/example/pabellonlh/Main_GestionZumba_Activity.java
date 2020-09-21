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

public class Main_GestionZumba_Activity extends AppCompatActivity {

    String Lunes = "Lunes", Martes = "Martes", Jueves = "Jueves", Viernes = "Viernes", actividad = "ZUMBA";
    ListView lvLunes, lvMartes, lvJueves, lvViernes;
    TabHost TbH;
    TextView tvNombre;
    Handler handler = new Handler();
    String usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__gestion_zumba_);

        Intent intent = getIntent();
        usuario = intent.getStringExtra("usuario");
        intent.putExtra("usuario", usuario);

        lvLunes = (ListView) findViewById(R.id.lvDatos);
        lvMartes = (ListView) findViewById(R.id.lvActividades);
        lvJueves = (ListView) findViewById(R.id.lvJueves);
        lvViernes = (ListView) findViewById(R.id.lvViernes);
        tvNombre = (TextView) findViewById(R.id.tvNombre1);

        TbH = (TabHost) findViewById(R.id.tabHost); //llamamos al Tabhost
        TbH.setup();                                                         //lo activamos

        TabHost.TabSpec tab1 = TbH.newTabSpec("tab1");  //aspectos de cada Tab (pestaña)
        TabHost.TabSpec tab2 = TbH.newTabSpec("tab2");
        TabHost.TabSpec tab3 = TbH.newTabSpec("tab3");
        TabHost.TabSpec tab4 = TbH.newTabSpec("tab4");

        tab1.setIndicator("LUNES");    //qué queremos que aparezca en las pestañas
        tab1.setContent(R.id.HPolideportivo); //definimos el id de cada Tab (pestaña)


        tab2.setIndicator("MARTES");
        tab2.setContent(R.id.HActividades);

        tab3.setIndicator("JUEVES");
        tab3.setContent(R.id.JUEVES);

        tab4.setIndicator("VIERNES");
        tab4.setContent(R.id.VIERNES);

        TbH.addTab(tab1); //añadimos los tabs ya programados
        TbH.addTab(tab2);
        TbH.addTab(tab3);
        TbH.addTab(tab4);

        //Toast.makeText(getApplicationContext(),"pruebaaaaa"+ usuario, Toast.LENGTH_SHORT).show();
        if(usuario == null){
            recuperarPreferendiasAdmin();
          //  Toast.makeText(getApplicationContext(),"pruebaaaaa"+ usuario, Toast.LENGTH_SHORT).show();
        }

        ActividadOcupadasLunes("http://jose-cordones.es/app/consultas/obtenerActividadesOcupadas_Inicio.php?nombre="+actividad+"&dia="+Lunes);
        ActividadOcupadasMartes("http://jose-cordones.es/app/consultas/obtenerActividadesOcupadas_Inicio.php?nombre="+actividad+"&dia="+ Martes);
        ActividadOcupadasJueves("http://jose-cordones.es/app/consultas/obtenerActividadesOcupadas_Inicio.php?nombre="+actividad+"&dia="+ Jueves);
        ActividadOcupadasViernes("http://jose-cordones.es/app/consultas/obtenerActividadesOcupadas_Inicio.php?nombre="+actividad+"&dia="+ Viernes);
        ejecutarTarea();
    }

    private final int TIEMPO = 15000;

    public void ejecutarTarea() {
        handler.postDelayed(new Runnable() {
            public void run() {

                ActividadOcupadasLunes("http://jose-cordones.es/app/consultas/obtenerActividadesOcupadas_Inicio.php?nombre="+actividad+"&dia="+Lunes);
                ActividadOcupadasMartes("http://jose-cordones.es/app/consultas/obtenerActividadesOcupadas_Inicio.php?nombre="+actividad+"&dia="+ Martes);
                ActividadOcupadasJueves("http://jose-cordones.es/app/consultas/obtenerActividadesOcupadas_Inicio.php?nombre="+actividad+"&dia="+ Jueves);
                ActividadOcupadasViernes("http://jose-cordones.es/app/consultas/obtenerActividadesOcupadas_Inicio.php?nombre="+actividad+"&dia="+ Viernes);

                handler.postDelayed(this, TIEMPO);
            }

        }, TIEMPO);

    }

    //lv del viernes------------------------------------------------------------------------------------------------------------------------------------------
    public void ActividadOcupadasViernes(String URL){


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
                        CargarListViewViernes(ja);
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

    public void CargarListViewViernes(JSONArray ja) {

        final ArrayList<String> lista = new ArrayList<>();
        int numero = 0;
      //  String usu = null;

        for (int i = 0; i < ja.length(); i += 1) {

            numero ++;

            try {
                lista.add("\n"+"                      Numero "+ numero +"                 "+ ja.getString(i + 0) );
             //   usu = ja.getString(i + 0);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        final ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        lvViernes.setAdapter(adaptador);

       // final String finalUsu = usu;
        lvViernes.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main_GestionZumba_Activity.this, Main_EliminarZumbaAdministrador_Activity.class);
               // Toast.makeText(Main_GestionZumba_Activity.this, lista.get(position)+"  "+finalUsu, Toast.LENGTH_SHORT).show();


                intent.putExtra("objetoData", lista.get(position) +  "                 "+Viernes+"                 "+actividad);
                intent.putExtra("usuario", usuario);
                startActivity(intent);

            }
        });

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
                Intent intent = new Intent(Main_GestionZumba_Activity.this, Main_EliminarZumbaAdministrador_Activity.class);
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
                Intent intent = new Intent(Main_GestionZumba_Activity.this, Main_EliminarZumbaAdministrador_Activity.class);
                // Toast.makeText(Main_GestionZumba_Activity.this, lista.get(position)+"  "+finalUsu, Toast.LENGTH_SHORT).show();


                intent.putExtra("objetoData", lista.get(position) +  "                 "+Martes+"                 "+actividad);
                intent.putExtra("usuario", usuario);
                startActivity(intent);

            }
        });

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
        lvLunes.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main_GestionZumba_Activity.this, Main_EliminarZumbaAdministrador_Activity.class);
                // Toast.makeText(Main_GestionZumba_Activity.this, lista.get(position)+"  "+finalUsu, Toast.LENGTH_SHORT).show();


                intent.putExtra("objetoData", lista.get(position) +  "                 "+Lunes+"                 "+actividad);
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
