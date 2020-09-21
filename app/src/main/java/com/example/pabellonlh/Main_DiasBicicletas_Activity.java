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

public class Main_DiasBicicletas_Activity extends AppCompatActivity {

    ListView lvLunes, lvMiercoles, lvViernes;
    Handler handler = new Handler();
    String Lunes = "Lunes", Miercoles = "Miercoles", Viernes = "Viernes";
    TextView tvNombre;
    TabHost TbH;
    String usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__dias_bicicletas_);


        Intent intent = getIntent();
        String usuario = intent.getStringExtra("usuario");

        tvNombre = (TextView) findViewById(R.id.tvNombre1);
        lvLunes = (ListView) findViewById(R.id.lvDatos);
        lvMiercoles = (ListView) findViewById(R.id.lvMiercoles);
        lvViernes = (ListView) findViewById(R.id.lvViernes);

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
            //  Toast.makeText(getApplicationContext(),"pruebaaaaa"+ usuario, Toast.LENGTH_SHORT).show();
        }

        BicicletasLibreLunes("http://jose-cordones.es/app/consultas/obtenerBicisVacias.php?dia="+Lunes);
        BicicletasLibreMiercoles("http://jose-cordones.es/app/consultas/obtenerBicisVacias.php?dia="+Miercoles);
        BicicletasLibreViernes("http://jose-cordones.es/app/consultas/obtenerBicisVacias.php?dia="+Viernes);
        ejecutarTarea();

    }

    private final int TIEMPO = 10000;

    public void ejecutarTarea() {
        handler.postDelayed(new Runnable() {
            public void run() {

                BicicletasLibreLunes("http://jose-cordones.es/app/consultas/obtenerBicisVacias.php?dia="+Lunes);
                BicicletasLibreMiercoles("http://jose-cordones.es/app/consultas/obtenerBicisVacias.php?dia="+Miercoles);
                BicicletasLibreViernes("http://jose-cordones.es/app/consultas/obtenerBicisVacias.php?dia="+Viernes);

                handler.postDelayed(this, TIEMPO);
            }

        }, TIEMPO);

    }

    //lv del viernes------------------------------------------------------------------------------------------------------------------------------------------
    public void BicicletasLibreViernes(String URL){


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
                        String usuario = intent.getStringExtra("usuario");
                        CargarListViewViernes(ja, usuario);
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

    public void CargarListViewViernes(JSONArray ja, final String usuario) {

        final ArrayList<String> lista = new ArrayList<>();


        for (int i = 0; i < ja.length(); i += 3) {

            try {
                lista.add("\n" + "                                     " + ja.getString(i + 2) + "                                     ");

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        final ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);

        lvViernes.setAdapter(adaptador);

        lvViernes.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main_DiasBicicletas_Activity.this, Main_AddBicicleta_Activity.class);
                String bici = lista.get(position);


                intent.putExtra("objetoData", bici + "          " + usuario + "          " + Viernes);

                startActivity(intent);

            }


        });

    }

    //lv del miercoles------------------------------------------------------------------------------------------------------------------------------------------
    public void BicicletasLibreMiercoles(String URL){


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
                        String usuario = intent.getStringExtra("usuario");
                        CargarListViewMiercoles(ja, usuario);
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

    public void CargarListViewMiercoles(JSONArray ja, final String usuario) {

        final ArrayList<String> lista = new ArrayList<>();


        for (int i = 0; i < ja.length(); i += 3) {

            try {
                lista.add("\n" + "                                     " + ja.getString(i + 2) + "                                     ");

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        final ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);

        lvMiercoles.setAdapter(adaptador);

        lvMiercoles.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main_DiasBicicletas_Activity.this, Main_AddBicicleta_Activity.class);
                String bici = lista.get(position);


                intent.putExtra("objetoData", bici + "          " + usuario + "          " + Miercoles);

                startActivity(intent);

            }


        });

    }

        //lv del lunes------------------------------------------------------------------------------------------------------------------------------------------
    public void BicicletasLibreLunes(String URL){


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
                         String usuario = intent.getStringExtra("usuario");
                        //String dia = "Lunes"";
                        CargarListViewLunes(ja, usuario);
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

    public void CargarListViewLunes(JSONArray ja, final String usuario){

        final ArrayList<String> lista = new ArrayList<>();


        for(int i=0;i<ja.length();i+=3){

            try {
                lista.add("\n"+"                                     "+ja.getString(i+2)+"                                     ");

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        final ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);

        lvLunes.setAdapter(adaptador);

        lvLunes.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main_DiasBicicletas_Activity.this, Main_AddBicicleta_Activity.class);
                String bici = lista.get(position);
                //
                // String bici = lista.get(position);
                //   EnviarRecibirInfoBici("http://jose-cordones.es/app/consultas/obtenerBicisVacias.php?dia="+dia);

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
