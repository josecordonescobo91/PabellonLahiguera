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

public class Main_GestionBicicletas_Activity extends AppCompatActivity {

    String usuario;
    TextView tvNombre;
    ListView lvLunes, lvMiercoles, lvViernes;
    Handler handler = new Handler();
    String Lunes = "Lunes", Miercoles = "Miercoles", Viernes = "Viernes";
    TabHost TbH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__gestion_bicicletas_);


        lvLunes = (ListView) findViewById(R.id.lvDatos);
        lvMiercoles = (ListView) findViewById(R.id.lvMiercoles);
        lvViernes = (ListView) findViewById(R.id.lvViernes);
        tvNombre = (TextView) findViewById(R.id.tvNombre1);

        TbH = (TabHost) findViewById(R.id.tabHost); //llamamos al Tabhost
        TbH.setup();                                                         //lo activamos

        TabHost.TabSpec tab1 = TbH.newTabSpec("tab1");  //aspectos de cada Tab (pestaña)
        TabHost.TabSpec tab2 = TbH.newTabSpec("tab2");
        TabHost.TabSpec tab3 = TbH.newTabSpec("tab3");

        tab1.setIndicator("LUNES");    //qué queremos que aparezca en las pestañas
        tab1.setContent(R.id.Lunes); //definimos el id de cada Tab (pestaña)


        tab2.setIndicator("MIERCOLES");
        tab2.setContent(R.id.Miercoles);

        tab3.setIndicator("VIERNES");
        tab3.setContent(R.id.PILATES);


        TbH.addTab(tab1); //añadimos los tabs ya programados
        TbH.addTab(tab2);
        TbH.addTab(tab3);

        if(usuario == null){
            recuperarPreferendiasAdmin();
           // Toast.makeText(getApplicationContext(), usuario, Toast.LENGTH_SHORT).show();
        }

        BicicletasOcupadasLunes("http://jose-cordones.es/app/consultas/obtenerBiciOcupadas_Inicio.php?dia="+Lunes);
        BicicletasOcupadasMiercoles("http://jose-cordones.es/app/consultas/obtenerBiciOcupadas_Inicio.php?dia="+ Miercoles);
        BicicletasOcupadasViernes("http://jose-cordones.es/app/consultas/obtenerBiciOcupadas_Inicio.php?dia="+ Viernes);
        ejecutarTarea();
    }

    private final int TIEMPO = 15000;

    public void ejecutarTarea() {
        handler.postDelayed(new Runnable() {
            public void run() {

                BicicletasOcupadasLunes("http://jose-cordones.es/app/consultas/obtenerBiciOcupadas_Inicio.php?dia="+Lunes);
                BicicletasOcupadasMiercoles("http://jose-cordones.es/app/consultas/obtenerBiciOcupadas_Inicio.php?dia="+ Miercoles);
                BicicletasOcupadasViernes("http://jose-cordones.es/app/consultas/obtenerBiciOcupadas_Inicio.php?dia="+ Viernes);

                handler.postDelayed(this, TIEMPO);
            }

        }, TIEMPO);

    }
    //lv del viernes------------------------------------------------------------------------------------------------------------------------------------------
    public void BicicletasOcupadasViernes(String URL){


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
                        // String usuario = intent.getStringExtra("usuario");
                        String dia = intent.getStringExtra("dia");
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

    public void CargarListViewViernes(JSONArray ja){

        final ArrayList<String> lista = new ArrayList<>();


        for(int i=0;i<ja.length();i+=3){

            try {
                lista.add("      "+ja.getString(i+0)+"           "+ja.getString(i+2)+"           "+ja.getString(i+1)+"         ");

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        final ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);

        lvViernes.setAdapter(adaptador);
        lvViernes.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main_GestionBicicletas_Activity.this, Main_EliminarBicicletasAdministrador_Activity.class);
                String bici = lista.get(position);

                intent.putExtra("objetoData", bici+"          "+usuario+"          "+Viernes);

                startActivity(intent);

            }




        });

    }


    //lv del miercoles------------------------------------------------------------------------------------------------------------------------------------------
    public void BicicletasOcupadasMiercoles(String URL){


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
                        // String usuario = intent.getStringExtra("usuario");
                        String dia = intent.getStringExtra("dia");
                        CargarListViewMiercoles(ja);
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

    public void CargarListViewMiercoles(JSONArray ja){

        final ArrayList<String> lista = new ArrayList<>();


        for(int i=0;i<ja.length();i+=3){

            try {
                lista.add("      "+ja.getString(i+0)+"           "+ja.getString(i+2)+"           "+ja.getString(i+1)+"         ");

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        final ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);

        lvMiercoles.setAdapter(adaptador);
        lvMiercoles.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main_GestionBicicletas_Activity.this, Main_EliminarBicicletasAdministrador_Activity.class);
                String bici = lista.get(position);

                intent.putExtra("objetoData", bici+"          "+usuario+"          "+Miercoles);

                startActivity(intent);

            }




        });

    }

    //lv del lunes------------------------------------------------------------------------------------------------------------------------------------------
    public void BicicletasOcupadasLunes(String URL){


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
                        // String usuario = intent.getStringExtra("usuario");
                        String dia = intent.getStringExtra("dia");
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

    public void CargarListViewLunes(JSONArray ja){

        final ArrayList<String> lista = new ArrayList<>();


        for(int i=0;i<ja.length();i+=3){

            try {
                lista.add("      "+ja.getString(i+0)+"           "+ja.getString(i+2)+"           "+ja.getString(i+1)+"         ");

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        final ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);

        lvLunes.setAdapter(adaptador);

        lvLunes.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main_GestionBicicletas_Activity.this, Main_EliminarBicicletasAdministrador_Activity.class);
                String bici = lista.get(position);

                intent.putExtra("objetoData", bici+"          "+usuario+"          "+Lunes);

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
