package com.example.pabellonlh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.AsyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class Main_MisAlquiler_Activity extends AppCompatActivity {

    String usuario;
    TextView tvIDPERSONA, tvIDPERSONA2;
    ListView lvPistas, lvBicicletas, lvActividades;
    private AsyncHttpClient cliente;
    TabHost TbH;
    private String idPersona, idPersona2;
    TextView tvNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__mis_alquiler_);

        TbH = (TabHost) findViewById(R.id.tabHost); //llamamos al Tabhost
        TbH.setup();                                                         //lo activamos

        TabHost.TabSpec tab1 = TbH.newTabSpec("tab1");  //aspectos de cada Tab (pestaña)
        TabHost.TabSpec tab2 = TbH.newTabSpec("tab2");
        TabHost.TabSpec tab3 = TbH.newTabSpec("tab3");
        TabHost.TabSpec tab4 = TbH.newTabSpec("tab4");

        tab1.setIndicator("PISTAS");    //qué queremos que aparezca en las pestañas
        tab1.setContent(R.id.PISTAS); //definimos el id de cada Tab (pestaña)


        tab2.setIndicator("BICICLETAS");
        tab2.setContent(R.id.BICICLETAS);

        tab3.setIndicator("ACTIVIDADES");
        tab3.setContent(R.id.ACTIVIDADES);


        TbH.addTab(tab1); //añadimos los tabs ya programados
        TbH.addTab(tab2);
        TbH.addTab(tab3);

        tvNombre = (TextView) findViewById(R.id.tvNombre1);
        lvPistas = (ListView) findViewById(R.id.lvPistas);
        lvBicicletas = (ListView) findViewById(R.id.lvBicicletas);
        lvActividades = (ListView) findViewById(R.id.lvActividades);
        tvIDPERSONA = (TextView) findViewById(R.id.tvIDPERSONA);
        tvIDPERSONA2 = (TextView) findViewById(R.id.tvIDPERSONA2);
        cliente = new AsyncHttpClient ();
        idPersona = tvIDPERSONA.getText().toString();
        idPersona2 = tvIDPERSONA2.getText().toString();

        Intent intent = getIntent();
        usuario = intent.getStringExtra("usuario");
        intent.putExtra("usuario", usuario);
        if(usuario == null){
            recuperarPreferendiasAdmin();
            //  Toast.makeText(getApplicationContext(),"pruebaaaaa"+ usuario, Toast.LENGTH_SHORT).show();
        }
        MisPistas("http://jose-cordones.es/app/consultas/obtenerMisPistas.php?nick="+usuario);
        EnviarRecibirIdPersonaBicis("http://jose-cordones.es/app/consultas/obtenerIdPersona_Telefono.php?nick=" + usuario);
        EnviarRecibirIdPersonaActividades("http://jose-cordones.es/app/consultas/obtenerIdPersona_Telefono.php?nick=" + usuario);

    }

    public void EnviarRecibirIdPersonaActividades(String URL) {

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replace("][", ",");

                if (response.length() > 0) {
                    try {

                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson", "" + ja.length());
                        CargarListViewActividades(ja);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

    }

    public void CargarListViewActividades(JSONArray ja) {

        ArrayList<String> lista = new ArrayList<>();
        String id = null;
        for (int i = 0; i < ja.length(); i += 2) {

            try {

                id = ("\n" + ja.getString(i + 0));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        tvIDPERSONA2.setText(id);
        EnviarRecibirMisActividades("http://jose-cordones.es/app/consultas/obtenerMisActividades.php?idpersona=" + id);

    }

    public void EnviarRecibirMisActividades(String URL) {


        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replace("][", ",");
                if (response.length() > 0) {
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson", "" + ja.length());
                        Intent intent = getIntent();
                        String usuario = intent.getStringExtra("usuario");
                        CargarActividades(ja, usuario);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

    }

    public void CargarActividades(JSONArray ja, final String usuario) {

        final ArrayList<String> lista = new ArrayList<>();


        for (int i = 0; i < ja.length(); i += 4) {

            try {
                lista.add("                      " + ja.getString(i +2) +"                      " + ja.getString(i +3));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        final ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);

        lvActividades.setAdapter(adaptador);


        lvActividades.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main_MisAlquiler_Activity.this, Main_EliminarActividad_Activity.class);
                //Toast.makeText(Main_MisPistas_Activity.this, lista.get(position)+"  "+usuario, Toast.LENGTH_SHORT).show();

                intent.putExtra("objetoData", lista.get(position) + "                      "+usuario);

                startActivity(intent);


            }
        });
    }

    //mis bicicletas------------------------------------------------------------------------------------------------------------------------------------

    public void EnviarRecibirIdPersonaBicis(String URL) {

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replace("][", ",");

                if (response.length() > 0) {
                    try {

                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson", "" + ja.length());
                        CargarListView(ja);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

    }

    public void CargarListView(JSONArray ja) {

        ArrayList<String> lista = new ArrayList<>();
        String id = null;
        for (int i = 0; i < ja.length(); i += 2) {

            try {

                id = ("\n" + ja.getString(i + 0));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        tvIDPERSONA.setText(id);
        EnviarRecibirMisBicicletas("http://jose-cordones.es/app/consultas/obtenerMisBicicletas.php?idpersona=" + id);

    }

    public void EnviarRecibirMisBicicletas(String URL) {


        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replace("][", ",");
                if (response.length() > 0) {
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson", "" + ja.length());
                        Intent intent = getIntent();
                        String usuario = intent.getStringExtra("usuario");
                        CargarBicicletas(ja, usuario);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

    }

    public void CargarBicicletas(JSONArray ja, final String usuario) {

        final ArrayList<String> lista = new ArrayList<>();


        for (int i = 0; i < ja.length(); i += 4) {

            try {
                lista.add("\n" + "                 " + ja.getString(i +3) +"                   " + ja.getString(i +2));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        final ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);

        lvBicicletas.setAdapter(adaptador);


        lvBicicletas.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main_MisAlquiler_Activity.this, Main_EliminarBicicleta_Activity.class);
                //Toast.makeText(Main_MisPistas_Activity.this, lista.get(position)+"  "+usuario, Toast.LENGTH_SHORT).show();

                intent.putExtra("objetoData", lista.get(position) + "                   "+usuario);

                startActivity(intent);

            }
        });
    }

    //mis pistas----------------------------------------------------------------------------------------------------------

    public void MisPistas(String URL){


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
                        CargarListViewPistas(ja, usuario);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else{
                    Toast.makeText(Main_MisAlquiler_Activity.this, "NO HAY PISTAS", Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

    }

    public void CargarListViewPistas(JSONArray ja, final String usuario){

        final ArrayList<String> lista = new ArrayList<>();
        final ArrayList<String> idPista = new ArrayList<>();
        //   lista.add("\n"+"DIA                       " +"HORA               "+ "M        "+"PISTA");


        for(int i=0;i<ja.length();i+=25){

            try {
                lista.add("\n"+ja.getString(i+3)+"        "+ja.getString(i+4)+"        "+ja.getString(i+10)+"        "+ja.getString(i+5));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        final ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        if(adaptador == null || adaptador.isEmpty())
        {
            Toast.makeText(Main_MisAlquiler_Activity.this, "No hay pistas ", Toast.LENGTH_SHORT).show();
        }

        lvPistas.setAdapter(adaptador);


        lvPistas.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main_MisAlquiler_Activity.this, Main_EliminarEditar_MiPista_Activity.class);
                //Toast.makeText(Main_MisPistas_Activity.this, lista.get(position)+"  "+usuario, Toast.LENGTH_SHORT).show();

                intent.putExtra("objetoData", lista.get(position));

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
