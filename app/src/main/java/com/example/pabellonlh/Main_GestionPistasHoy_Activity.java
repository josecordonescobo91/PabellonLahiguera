package com.example.pabellonlh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class Main_GestionPistasHoy_Activity extends AppCompatActivity {

    String usuario;
    TextView tvNombre;
    String Futbol7 = "futbol 7", FutbolSala = "futbol sala", Padel = "padel", Tennis = "tennis", PadelCubierto = "padel c", Futbol7P2 = "futbol 7 P2";


    ListView lvPistasLibresF7;
    ListView lvPistasLibresFS;
    ListView lvPistasLibresP;
    ListView lvPistasLibresT;
    ListView lvPistasLibresF7P2;
    ListView lvPistasLibresC;

    TabHost TbH, FUTBOL7, PADEL;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__gestion_pistas_hoy_);

        Intent intent = getIntent();
        usuario = intent.getStringExtra("usuario");
        intent.putExtra("usuario", usuario);

       // Toast.makeText(Main_GestionPistasHoy_Activity.this, usuario, Toast.LENGTH_SHORT).show();

        tvNombre = findViewById(R.id.tvNombre1);

        lvPistasLibresF7 = (ListView) findViewById(R.id.lvPistasLibresF7);
        lvPistasLibresF7P2 = (ListView) findViewById(R.id.lvPistasLibresF7P2);
        lvPistasLibresFS = (ListView) findViewById(R.id.lvPistasLibresFS);
        lvPistasLibresP = (ListView) findViewById(R.id.lvPistasLibresP);
        lvPistasLibresC = (ListView) findViewById(R.id.lvPistasLibresC);
        lvPistasLibresT = (ListView) findViewById(R.id.lvPistasLibresT);

        if(usuario == null){
            recuperarPreferendiasAdmin();
        }

        TbH = (TabHost) findViewById(R.id.tabHost);
        TbH.setup();

        FUTBOL7 = (TabHost) findViewById(R.id.F7);
        FUTBOL7.setup();

        PADEL = (TabHost) findViewById(R.id.PADEL_PISTAS); //llamamos al Tabhost
        PADEL.setup();

        TabHost.TabSpec PADEL_CENTRAL = PADEL.newTabSpec("PADEL_CENTRAL");
        TabHost.TabSpec PADEL_FUERA = PADEL.newTabSpec("PADEL_FUERA");


        TabHost.TabSpec F7P1 = FUTBOL7.newTabSpec("F7P1");
        TabHost.TabSpec F7P2 = FUTBOL7.newTabSpec("F7P2");

        TabHost.TabSpec tab1 = TbH.newTabSpec("tab1");
        TabHost.TabSpec tab2 = TbH.newTabSpec("tab2");
        TabHost.TabSpec tab3 = TbH.newTabSpec("tab3");
        TabHost.TabSpec tab4 = TbH.newTabSpec("tab4");

        PADEL_CENTRAL.setIndicator("PADEL CUBIERTO");
        PADEL_CENTRAL.setContent(R.id.PADEL_CENTRAL);


        PADEL_FUERA.setIndicator("PADEL DE FUERA");
        PADEL_FUERA.setContent(R.id.PADEL_FUERA);


        F7P1.setIndicator("FUTBOL 7 PISTA 1");
        F7P1.setContent(R.id.F7P1);


        F7P2.setIndicator("FUTBOL 7 PISTA 2");
        F7P2.setContent(R.id.F7P2);

        tab1.setIndicator("FUTBOL 7");
        tab1.setContent(R.id.Futbol7);


        tab2.setIndicator("F.SALA");
        tab2.setContent(R.id.FS);

        tab3.setIndicator("PADEL");
        tab3.setContent(R.id.PADEL);

        tab4.setIndicator("TENNIS");
        tab4.setContent(R.id.TENNIS);

        TbH.addTab(tab1); //añadimos los tabs ya programados
        TbH.addTab(tab2);
        TbH.addTab(tab3);
        TbH.addTab(tab4);


        FUTBOL7.addTab(F7P1);
        FUTBOL7.addTab(F7P2);

        PADEL.addTab(PADEL_CENTRAL);
        PADEL.addTab(PADEL_FUERA);


        TabWidget tabsPADEL = (TabWidget)PADEL.findViewById(android.R.id.tabs);
        View tabViewP1 = tabsPADEL.getChildTabViewAt(0);
        View tabViewP2 = tabsPADEL.getChildTabViewAt(1);

        TextView P1 = (TextView)tabViewP1.findViewById(android.R.id.title);
        TextView P2 = (TextView)tabViewP2.findViewById(android.R.id.title);

        P1.setTextSize(12);
        P1.setTypeface(null, Typeface.BOLD);
        P2.setTextSize(12);
        P2.setTypeface(null, Typeface.BOLD);

        TabWidget tabsF7 = (TabWidget)FUTBOL7.findViewById(android.R.id.tabs);
        View tabViewF71 = tabsF7.getChildTabViewAt(0);
        View tabView2F72 = tabsF7.getChildTabViewAt(1);

        TextView F71 = (TextView)tabViewF71.findViewById(android.R.id.title);
        TextView F72 = (TextView)tabView2F72.findViewById(android.R.id.title);

        F71.setTextSize(12);
        F71.setTypeface(null, Typeface.BOLD);
        F72.setTextSize(12);
        F72.setTypeface(null, Typeface.BOLD);

        TabWidget tabs = (TabWidget)TbH.findViewById(android.R.id.tabs);
        View tabView1 = tabs.getChildTabViewAt(0);
        View tabView2 = tabs.getChildTabViewAt(1);
        View tabView3= tabs.getChildTabViewAt(2);
        View tabView4= tabs.getChildTabViewAt(3);

        TextView tv1 = (TextView)tabView1.findViewById(android.R.id.title);
        TextView tv2 = (TextView)tabView2.findViewById(android.R.id.title);
        TextView tv3 = (TextView)tabView3.findViewById(android.R.id.title);
        TextView tv4 = (TextView)tabView4.findViewById(android.R.id.title);


        tv1.setTextSize(14);
        tv1.setTextColor(Color.parseColor("#ad4f2b"));
        tv1.setTypeface(null, Typeface.BOLD);
        tv2.setTextSize(14);
        tv2.setTextColor(Color.parseColor("#ad4f2b"));
        tv2.setTypeface(null, Typeface.BOLD);
        tv3.setTextSize(14);
        tv3.setTextColor(Color.parseColor("#ad4f2b"));
        tv3.setTypeface(null, Typeface.BOLD);
        tv4.setTextSize(14);
        tv4.setTextColor(Color.parseColor("#ad4f2b"));
        tv4.setTypeface(null, Typeface.BOLD);



        PistasOcupadasF7("http://jose-cordones.es/app/consultas/obtenerPistasHoy.php?pista="+Futbol7);
        PistasOcupadasF7P2("http://jose-cordones.es/app/consultas/obtenerPistasHoy.php?pista="+Futbol7P2);
        PistasOcupadasFS("http://jose-cordones.es/app/consultas/obtenerPistasHoy.php?pista="+FutbolSala);
        PistasOcupadasP("http://jose-cordones.es/app/consultas/obtenerPistasHoy.php?pista="+Padel);
        PistasOcupadasC("http://jose-cordones.es/app/consultas/obtenerPistasHoy.php?pista="+PadelCubierto);
        PistasOcupadasT("http://jose-cordones.es/app/consultas/obtenerPistasHoy.php?pista="+Tennis);
        ejecutarTarea();


    }

    //actualizar casa 20 sefunddos

    private final int TIEMPO = 20000;

    public void ejecutarTarea() {
        handler.postDelayed(new Runnable() {
            public void run() {

                PistasOcupadasF7("http://jose-cordones.es/app/consultas/obtenerPistasHoy.php?pista="+Futbol7);
                PistasOcupadasF7P2("http://jose-cordones.es/app/consultas/obtenerPistasHoy.php?pista="+Futbol7P2);
                PistasOcupadasFS("http://jose-cordones.es/app/consultas/obtenerPistasHoy.php?pista="+FutbolSala);
                PistasOcupadasP("http://jose-cordones.es/app/consultas/obtenerPistasHoy.php?pista="+Padel);
                PistasOcupadasC("http://jose-cordones.es/app/consultas/obtenerPistasHoy.php?pista="+PadelCubierto);
                PistasOcupadasT("http://jose-cordones.es/app/consultas/obtenerPistasHoy.php?pista="+Tennis);

                handler.postDelayed(this, TIEMPO);
            }

        }, TIEMPO);

    }
    private void recuperarPreferendiasAdmin(){
        SharedPreferences preferences = getSharedPreferences("preferenciasLoginAdmin", Context.MODE_PRIVATE);
        tvNombre.setText(preferences.getString("usuario", ""));

        usuario = tvNombre.getText().toString();

    }

    //pistas  tennis------------------------------------------------------------------------------------------------------------------------------

    public void PistasOcupadasT(String URL){


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
                        String pista = intent.getStringExtra("pista");
                        CargarListViewT(ja, usuario, pista);
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

    public void CargarListViewT(JSONArray ja, final String usuario, final String dia) {

        final ArrayList<String> lista = new ArrayList<>();


        for (int i = 0; i < ja.length(); i += 3) {

            try {
                lista.add("\n"+ ja.getString(i + 0) + "          "+ ja.getString(i + 1) + "          "+ ja.getString(i + 2));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        final ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);

        lvPistasLibresT.setAdapter(adaptador);

        lvPistasLibresT.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main_GestionPistasHoy_Activity.this, Main_EditarEliminarPistasHoy_Activity.class);
                String infopista = lista.get(position);

                intent.putExtra("objetoData", infopista + "          " + Tennis);

                startActivity(intent);

            }


        });
    }




    //pistas libre padel------------------------------------------------------------------------------------------------------------------------------

    public void PistasOcupadasP(String URL){


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
                        String pista = intent.getStringExtra("pista");
                        CargarListViewP(ja, usuario, pista);
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

    public void CargarListViewP(JSONArray ja, final String usuario, final String dia) {

        final ArrayList<String> lista = new ArrayList<>();


        for (int i = 0; i < ja.length(); i += 3) {

            try {
                lista.add("\n"+ ja.getString(i + 0) + "          "+ ja.getString(i + 1) + "          "+ ja.getString(i + 2));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        final ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);

        lvPistasLibresP.setAdapter(adaptador);

        lvPistasLibresP.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main_GestionPistasHoy_Activity.this, Main_EditarEliminarPistasHoy_Activity.class);
                String infopista = lista.get(position);

                intent.putExtra("objetoData", infopista + "          " + Padel);

                startActivity(intent);

            }


        });
    }


    //pistas libre padel CUBIERTO------------------------------------------------------------------------------------------------------------------------------

    public void PistasOcupadasC(String URL){


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
                        String pista = intent.getStringExtra("pista");
                        CargarListViewC(ja, usuario, pista);
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

    public void CargarListViewC(JSONArray ja, final String usuario, final String dia) {

        final ArrayList<String> lista = new ArrayList<>();


        for (int i = 0; i < ja.length(); i += 3) {

            try {
                lista.add("\n"+ ja.getString(i + 0) + "          "+ ja.getString(i + 1) + "          "+ ja.getString(i + 2));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        final ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);

        lvPistasLibresC.setAdapter(adaptador);

        lvPistasLibresC.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main_GestionPistasHoy_Activity.this, Main_EditarEliminarPistasHoy_Activity.class);
                String infopista = lista.get(position);

                intent.putExtra("objetoData", infopista + "          " + PadelCubierto);

                startActivity(intent);

            }


        });
    }







    //pistas futbol sala-------------------------------------------------------------------------------------------------------------------------


    public void PistasOcupadasFS(String URL){


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
                        String pista = intent.getStringExtra("pista");
                        CargarListViewFS(ja, usuario, pista);
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

    public void CargarListViewFS(JSONArray ja, final String usuario, final String dia) {

        final ArrayList<String> lista = new ArrayList<>();


        for (int i = 0; i < ja.length(); i += 3) {

            try {
                lista.add("\n"+ ja.getString(i + 0) + "          "+ ja.getString(i + 1) + "          "+ ja.getString(i + 2));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        final ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);

        lvPistasLibresFS.setAdapter(adaptador);

        lvPistasLibresFS.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main_GestionPistasHoy_Activity.this, Main_EditarEliminarPistasHoy_Activity.class);
                String infopista = lista.get(position);

                intent.putExtra("objetoData", infopista + "          " + FutbolSala );

                startActivity(intent);

            }


        });
    }






    //pistas futbol 7-------------------------------------------------------------------------------------------------------------------------

    public void PistasOcupadasF7(String URL){


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
                        String pista = intent.getStringExtra("pista");
                        CargarListView(ja, usuario, pista);
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

    public void CargarListView(JSONArray ja, final String usuario, final String dia) {

        final ArrayList<String> lista = new ArrayList<>();

        for (int i = 0; i < ja.length(); i += 3) {

            try {
                lista.add("\n"+ ja.getString(i + 0) + "          "+ ja.getString(i + 1) + "          "+ ja.getString(i + 2));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        final ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);

        lvPistasLibresF7.setAdapter(adaptador);

        lvPistasLibresF7.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main_GestionPistasHoy_Activity.this, Main_EditarEliminarPistasHoy_Activity.class);
                String infopista = lista.get(position);

                intent.putExtra("objetoData", infopista + "          " + Futbol7 );

                startActivity(intent);

            }


        });
    }

    //pistas futbol 7 PISTA 2-------------------------------------------------------------------------------------------------------------------------

    public void PistasOcupadasF7P2(String URL){


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
                        String pista = intent.getStringExtra("pista");
                        CargarListViewP2(ja, usuario, pista);
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

    public void CargarListViewP2(JSONArray ja, final String usuario, final String dia) {

        final ArrayList<String> lista = new ArrayList<>();

        for (int i = 0; i < ja.length(); i += 3) {

            try {
                lista.add("\n"+ ja.getString(i + 0) + "          "+ ja.getString(i + 1) + "          "+ ja.getString(i + 2));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        final ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);

        lvPistasLibresF7P2.setAdapter(adaptador);

        lvPistasLibresF7P2.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main_GestionPistasHoy_Activity.this, Main_EditarEliminarPistasHoy_Activity.class);
                String infopista = lista.get(position);

                intent.putExtra("objetoData", infopista + "          " + Futbol7P2 );

                startActivity(intent);

            }


        });
    }
}
