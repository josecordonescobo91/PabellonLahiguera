package com.example.pabellonlh;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
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
import java.util.Calendar;

public class Main_PistasAlquiler_Activity extends AppCompatActivity implements View.OnClickListener {

    String usuario;
    TextView tvNombre;
    String Futbol7 = "futbol 7", FutbolSala = "futbol sala", Padel = "padel", Tennis = "tennis", PadelCubierto = "padel c", Futbol7P2 = "futbol 7 P2";

    EditText edFechaF7;
    ImageButton imgFechaF7;

    EditText edFechaP2;
    ImageButton imgFechaP2;

    EditText edFechaFS;
    ImageButton imgFechaFS;

    EditText edFechaP;
    ImageButton imgFechaP;

    EditText edFechaC;
    ImageButton imgFechaC;

    EditText edFechaT;
    ImageButton imgFechaT;


    private static final String CERO = "0";
    private static final String DOS_PUNTOS = ":";
    private static final String BARRA = "-";
    final Calendar c = Calendar.getInstance();
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);

    ListView lvPistasLibresF7, lvBusquedaF7;
    ListView lvPistasLibresF7P2, lvBusquedaF7P2;
    ListView lvPistasLibresFS, lvBusquedaFS;
    ListView lvPistasLibresP, lvBusquedaP;
    ListView lvPistasLibresC, lvBusquedaC;
    ListView lvPistasLibresT, lvBusquedaT;


    Button btnBuscarF7, btnCancelarF7;
    Button btnBuscarP2, btnCancelarP2;
    Button btnBuscarFS, btnCancelarFS;
    Button btnBuscarP, btnCancelarP;
    Button btnBuscarC, btnCancelarC;
    Button btnBuscarT, btnCancelarT;

    TabHost TbH, FUTBOL7, PADEL;
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__pistas_alquiler);

        edFechaF7=(EditText) findViewById(R.id.edFecha);
        imgFechaF7=(ImageButton) findViewById(R.id.imgFecha);

        edFechaP2=(EditText) findViewById(R.id.edFechaP2);
        imgFechaP2=(ImageButton) findViewById(R.id.imgFechaP2);

        edFechaFS=(EditText) findViewById(R.id.edFechaFS);
        imgFechaFS=(ImageButton) findViewById(R.id.imgFechaFS);

        edFechaP=(EditText) findViewById(R.id.edFechaP);
        imgFechaP=(ImageButton) findViewById(R.id.imgFechaP);

        edFechaC=(EditText) findViewById(R.id.edFechaC);
        imgFechaC=(ImageButton) findViewById(R.id.imgFechaC);

        edFechaT=(EditText) findViewById(R.id.edFechaT);
        imgFechaT=(ImageButton) findViewById(R.id.imgFechaT);

        imgFechaF7.setOnClickListener(this);
        imgFechaP2.setOnClickListener(this);
        imgFechaFS.setOnClickListener(this);
        imgFechaP.setOnClickListener(this);
        imgFechaC.setOnClickListener(this);
        imgFechaT.setOnClickListener(this);


        Intent intent = getIntent();
        usuario = intent.getStringExtra("usuario");

        if(usuario == null){
            recuperarPreferendiasAdmin();
        }

        lvPistasLibresF7 = (ListView) findViewById(R.id.lvPistasLibresF7);
        lvBusquedaF7 = (ListView) findViewById(R.id.lvBusquedaF7);
        btnBuscarF7 = (Button) findViewById(R.id.btnBuscar);
        btnCancelarF7 = (Button) findViewById(R.id.btnCancelar);

        lvPistasLibresF7P2 = (ListView) findViewById(R.id.lvPistasLibresF7P2);
        lvBusquedaF7P2 = (ListView) findViewById(R.id.lvBusquedaF7P2);
        btnBuscarP2 = (Button) findViewById(R.id.btnBuscarP2);
        btnCancelarP2 = (Button) findViewById(R.id.btnCancelarP2);

        lvPistasLibresFS = (ListView) findViewById(R.id.lvPistasLibresFS);
        lvBusquedaFS = (ListView) findViewById(R.id.lvBusquedaFS);
        btnBuscarFS = (Button) findViewById(R.id.btnBuscarFS);
        btnCancelarFS = (Button) findViewById(R.id.btnCancelarFS);

        lvPistasLibresP = (ListView) findViewById(R.id.lvPistasLibresP);
        lvBusquedaP = (ListView) findViewById(R.id.lvBusquedaP);
        btnBuscarP = (Button) findViewById(R.id.btnBuscarP);
        btnCancelarP = (Button) findViewById(R.id.btnCancelarP);

        lvPistasLibresC = (ListView) findViewById(R.id.lvPistasLibresC);
        lvBusquedaC = (ListView) findViewById(R.id.lvBusquedaC);
        btnBuscarC = (Button) findViewById(R.id.btnBuscarC);
        btnCancelarC = (Button) findViewById(R.id.btnCancelarC);

        lvPistasLibresT = (ListView) findViewById(R.id.lvPistasLibresT);
        lvBusquedaT = (ListView) findViewById(R.id.lvBusquedaT);
        btnBuscarT = (Button) findViewById(R.id.btnBuscarT);
        btnCancelarT = (Button) findViewById(R.id.btnCancelarT);


        TbH = (TabHost) findViewById(R.id.tabHost); //llamamos al Tabhost
        TbH.setup();                                                         //lo activamos
        FUTBOL7 = (TabHost) findViewById(R.id.F7); //llamamos al Tabhost
        FUTBOL7.setup();
        PADEL = (TabHost) findViewById(R.id.PADEL_PISTAS); //llamamos al Tabhost
        PADEL.setup();

        TabHost.TabSpec tab1 = TbH.newTabSpec("tab1");
        TabHost.TabSpec tab2 = TbH.newTabSpec("tab2");
        TabHost.TabSpec tab3 = TbH.newTabSpec("tab3");
        TabHost.TabSpec tab4 = TbH.newTabSpec("tab4");



        TabHost.TabSpec F7P1 = FUTBOL7.newTabSpec("F7P1");
        TabHost.TabSpec F7P2 = FUTBOL7.newTabSpec("F7P2");

        TabHost.TabSpec PADEL_CENTRAL = PADEL.newTabSpec("PADEL_CENTRAL");
        TabHost.TabSpec PADEL_FUERA = PADEL.newTabSpec("PADEL_FUERA");


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



        TbH.addTab(tab1);
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








        PistasLibresF7("http://jose-cordones.es/app/consultas/obtenerPistasLibres.php?pista="+Futbol7);
        PistasLibresF7P2("http://jose-cordones.es/app/consultas/obtenerPistasLibres.php?pista="+Futbol7P2);
        PistasLibresFS("http://jose-cordones.es/app/consultas/obtenerPistasLibres.php?pista="+FutbolSala);
        PistasLibresP("http://jose-cordones.es/app/consultas/obtenerPistasLibres.php?pista="+Padel);
        PistasLibresC("http://jose-cordones.es/app/consultas/obtenerPistasLibres.php?pista="+PadelCubierto);
        PistasLibresT("http://jose-cordones.es/app/consultas/obtenerPistasLibres.php?pista="+Tennis);
        ejecutarTarea();

        lvBusquedaF7.setVisibility(View.INVISIBLE);
        lvBusquedaF7P2.setVisibility(View.INVISIBLE);
        lvBusquedaFS.setVisibility(View.INVISIBLE);
        lvBusquedaP.setVisibility(View.INVISIBLE);
        lvBusquedaC.setVisibility(View.INVISIBLE);
        lvBusquedaT.setVisibility(View.INVISIBLE);

        //botones f7----------------------------------------------------------------------------------------------------------------------------------------
        btnBuscarF7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String valorSpiner = edFechaF7.getText().toString();
                if(valorSpiner.equals("")){
                    Toast.makeText(Main_PistasAlquiler_Activity.this, "DEBE SELECCIONAR UNA FECHA", Toast.LENGTH_SHORT).show();
                }else{
                    BusquedaFechaF7("http://jose-cordones.es/app/consultas/obtenerBusquedaPistas.php?pista="+Futbol7+"&fecha="+ valorSpiner);
                    edFechaF7.setText("");

                    lvPistasLibresF7.setVisibility(View.INVISIBLE);
                    lvBusquedaF7.setVisibility(View.VISIBLE);
                }

            }
        });

        btnCancelarF7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lvPistasLibresF7.setVisibility(View.VISIBLE);
                lvBusquedaF7.setVisibility(View.INVISIBLE);

            }
        });

        //botones f7 pista 2----------------------------------------------------------------------------------------------------------------------------------------
        btnBuscarP2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String valorSpiner8= edFechaP2.getText().toString();
                if(valorSpiner8.equals("")){
                    Toast.makeText(Main_PistasAlquiler_Activity.this, "DEBE SELECCIONAR UNA FECHA", Toast.LENGTH_SHORT).show();
                }else{
                    BusquedaFechaF7P2("http://jose-cordones.es/app/consultas/obtenerBusquedaPistas.php?pista="+Futbol7P2+"&fecha="+ valorSpiner8);
                    edFechaP2.setText("");

                    lvPistasLibresF7P2.setVisibility(View.INVISIBLE);
                    lvBusquedaF7P2.setVisibility(View.VISIBLE);
                }

            }
        });

        btnCancelarP2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lvPistasLibresF7P2.setVisibility(View.VISIBLE);
                lvBusquedaF7P2.setVisibility(View.INVISIBLE);

            }
        });

        //botones fs----------------------------------------------------------------------------------------------------------------------------------------
        btnBuscarFS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String valorSpiner1 = edFechaFS.getText().toString();
                if(valorSpiner1.equals("")){
                    Toast.makeText(Main_PistasAlquiler_Activity.this, "DEBE SELECCIONAR UNA FECHA", Toast.LENGTH_SHORT).show();
                }else {
                    BusquedaFechaFS("http://jose-cordones.es/app/consultas/obtenerBusquedaPistas.php?pista=" + FutbolSala + "&fecha=" + valorSpiner1);


                    lvPistasLibresFS.setVisibility(View.INVISIBLE);
                    lvBusquedaFS.setVisibility(View.VISIBLE);
                }
            }
        });

        btnCancelarFS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lvPistasLibresFS.setVisibility(View.VISIBLE);
                lvBusquedaFS.setVisibility(View.INVISIBLE);

            }
        });

        //botones padel----------------------------------------------------------------------------------------------------------------------------------------
        btnBuscarP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String valorSpiner2 = edFechaP.getText().toString();
                if(valorSpiner2.equals("")){
                    Toast.makeText(Main_PistasAlquiler_Activity.this, "DEBE SELECCIONAR UNA FECHA", Toast.LENGTH_SHORT).show();
                }else {
                    BusquedaFechaP("http://jose-cordones.es/app/consultas/obtenerBusquedaPistas.php?pista=" + Padel + "&fecha=" + valorSpiner2);


                    lvPistasLibresP.setVisibility(View.INVISIBLE);
                    lvBusquedaP.setVisibility(View.VISIBLE);
                }
            }
        });

        btnCancelarP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lvPistasLibresP.setVisibility(View.VISIBLE);
                lvBusquedaP.setVisibility(View.INVISIBLE);
                edFechaP.setText("");
            }
        });


        //botones padel----------------------------------------------------------------------------------------------------------------------------------------
        btnBuscarC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String valorSpiner5 = edFechaC.getText().toString();
                if(valorSpiner5.equals("")){
                    Toast.makeText(Main_PistasAlquiler_Activity.this, "DEBE SELECCIONAR UNA FECHA", Toast.LENGTH_SHORT).show();
                }else {
                    BusquedaFechaC("http://jose-cordones.es/app/consultas/obtenerBusquedaPistas.php?pista=" + PadelCubierto + "&fecha=" + valorSpiner5);


                    lvPistasLibresC.setVisibility(View.INVISIBLE);
                    lvBusquedaC.setVisibility(View.VISIBLE);
                }
            }
        });

        btnCancelarC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lvPistasLibresC.setVisibility(View.VISIBLE);
                lvBusquedaC.setVisibility(View.INVISIBLE);
                edFechaC.setText("");
            }
        });

        //botones tennis----------------------------------------------------------------------------------------------------------------------------------------
        btnBuscarT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String valorSpiner3 = edFechaT.getText().toString();
                if(valorSpiner3.equals("")){
                    Toast.makeText(Main_PistasAlquiler_Activity.this, "DEBE SELECCIONAR UNA FECHA", Toast.LENGTH_SHORT).show();
                }else {
                    BusquedaFechaT("http://jose-cordones.es/app/consultas/obtenerBusquedaPistas.php?pista=" + Tennis + "&fecha=" + valorSpiner3);

                    lvPistasLibresT.setVisibility(View.INVISIBLE);
                    lvBusquedaT.setVisibility(View.VISIBLE);
                }
            }
        });

        btnCancelarT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lvPistasLibresT.setVisibility(View.VISIBLE);
                lvBusquedaT.setVisibility(View.INVISIBLE);
                edFechaT.setText("");
            }
        });
    }

    private final int TIEMPO = 20000;

    public void ejecutarTarea() {
        handler.postDelayed(new Runnable() {
            public void run() {

                PistasLibresF7("http://jose-cordones.es/app/consultas/obtenerPistasLibres.php?pista="+Futbol7);
                PistasLibresF7P2("http://jose-cordones.es/app/consultas/obtenerPistasLibres.php?pista="+Futbol7P2);
                PistasLibresFS("http://jose-cordones.es/app/consultas/obtenerPistasLibres.php?pista="+FutbolSala);
                PistasLibresP("http://jose-cordones.es/app/consultas/obtenerPistasLibres.php?pista="+Padel);
                PistasLibresC("http://jose-cordones.es/app/consultas/obtenerPistasLibres.php?pista="+PadelCubierto);
                PistasLibresT("http://jose-cordones.es/app/consultas/obtenerPistasLibres.php?pista="+Tennis);

                handler.postDelayed(this, TIEMPO);
            }

        }, TIEMPO);

    }

    //pistas libre tennis------------------------------------------------------------------------------------------------------------------------------

    public void BusquedaFechaT(String URL){


        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replace("][",",");
                String  response2 = response.replace("][", ",");
                response2 = response2.replaceAll("\n", "");
                response2 = response2.replaceAll("\"", "");
                response2 = response2.replaceAll("\\[", "").replaceAll("\\]","");

                if(response2.equals("")){
                    Toast.makeText(Main_PistasAlquiler_Activity.this, "NO HAY PISTAS LIBRES EL DIA SELECCIONADO ", Toast.LENGTH_SHORT).show();
                    lvPistasLibresT.setVisibility(View.VISIBLE);
                    lvBusquedaT.setVisibility(View.INVISIBLE);

                }else if (response.length()>0){
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson",""+ja.length());
                        Intent intent = getIntent();
                        String usuario = intent.getStringExtra("usuario");
                        String pista = intent.getStringExtra("pista");
                        CargarListViewBusquedaT(ja, usuario, pista);
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

    public void CargarListViewBusquedaT(JSONArray ja, final String usuario, final String dia) {

        final ArrayList<String> lista = new ArrayList<>();


        for (int i = 0; i < ja.length(); i += 3) {

            try {
                lista.add("\n"+ ja.getString(i + 2) + "          "+ ja.getString(i + 1) + "          "+ ja.getString(i + 0));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        final ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);

        lvBusquedaT.setAdapter(adaptador);

        lvBusquedaT.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main_PistasAlquiler_Activity.this, Main_AddPista_Activity.class);
                String infopista = lista.get(position);

                intent.putExtra("objetoData", infopista + "          " + usuario + "          " + Tennis);

                startActivity(intent);

            }


        });
    }

    public void PistasLibresT(String URL){


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
                lista.add("\n"+ ja.getString(i + 2) + "          "+ ja.getString(i + 1) + "          "+ ja.getString(i + 0));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        final ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);

        lvPistasLibresT.setAdapter(adaptador);

        lvPistasLibresT.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main_PistasAlquiler_Activity.this, Main_AddPista_Activity.class);
                String infopista = lista.get(position);

                intent.putExtra("objetoData", infopista + "          " + usuario + "          " + Tennis);

                startActivity(intent);

            }


        });
    }


    //pistas libre padel------------------------------------------------------------------------------------------------------------------------------
    public void BusquedaFechaP(String URL){


        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replace("][",",");
                String  response2 = response.replace("][", ",");
                response2 = response2.replaceAll("\n", "");
                response2 = response2.replaceAll("\"", "");
                response2 = response2.replaceAll("\\[", "").replaceAll("\\]","");

                if(response2.equals("")){
                    Toast.makeText(Main_PistasAlquiler_Activity.this, "NO HAY PISTAS LIBRES EL DIA SELECCIONADO ", Toast.LENGTH_SHORT).show();
                    lvPistasLibresP.setVisibility(View.VISIBLE);
                    lvBusquedaP.setVisibility(View.INVISIBLE);

                }else if (response.length()>0){
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson",""+ja.length());
                        Intent intent = getIntent();
                        String usuario = intent.getStringExtra("usuario");
                        String pista = intent.getStringExtra("pista");
                        CargarListViewBusquedaP(ja, usuario, pista);
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

    public void CargarListViewBusquedaP(JSONArray ja, final String usuario, final String dia) {

        final ArrayList<String> lista = new ArrayList<>();


        for (int i = 0; i < ja.length(); i += 3) {

            try {
                lista.add("\n"+ ja.getString(i + 2) + "          "+ ja.getString(i + 1) + "          "+ ja.getString(i + 0));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        final ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);

        lvBusquedaP.setAdapter(adaptador);

        lvBusquedaP.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main_PistasAlquiler_Activity.this, Main_AddPista_Activity.class);
                String infopista = lista.get(position);

                intent.putExtra("objetoData", infopista + "          " + usuario + "          " + Padel);

                startActivity(intent);

            }


        });
    }

    public void PistasLibresP(String URL){


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
                lista.add("\n"+ ja.getString(i + 2) + "          "+ ja.getString(i + 1) + "          "+ ja.getString(i + 0));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        final ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);

        lvPistasLibresP.setAdapter(adaptador);

        lvPistasLibresP.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main_PistasAlquiler_Activity.this, Main_AddPista_Activity.class);
                String infopista = lista.get(position);

                intent.putExtra("objetoData", infopista + "          " + usuario + "          " + Padel);

                startActivity(intent);

            }


        });
    }



    //pistas libre padel cubierta------------------------------------------------------------------------------------------------------------------------------

    public void BusquedaFechaC(String URL){


        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replace("][",",");
                String  response2 = response.replace("][", ",");
                response2 = response2.replaceAll("\n", "");
                response2 = response2.replaceAll("\"", "");
                response2 = response2.replaceAll("\\[", "").replaceAll("\\]","");

                if(response2.equals("")){
                    Toast.makeText(Main_PistasAlquiler_Activity.this, "NO HAY PISTAS LIBRES EL DIA SELECCIONADO ", Toast.LENGTH_SHORT).show();
                    lvPistasLibresC.setVisibility(View.VISIBLE);
                    lvBusquedaC.setVisibility(View.INVISIBLE);

                }else if (response.length()>0){
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson",""+ja.length());
                        Intent intent = getIntent();
                        String usuario = intent.getStringExtra("usuario");
                        String pista = intent.getStringExtra("pista");
                        CargarListViewBusquedaC(ja, usuario, pista);
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

    public void CargarListViewBusquedaC(JSONArray ja, final String usuario, final String dia) {

        final ArrayList<String> lista = new ArrayList<>();


        for (int i = 0; i < ja.length(); i += 3) {

            try {
                lista.add("\n"+ ja.getString(i + 2) + "          "+ ja.getString(i + 1) + "          "+ ja.getString(i + 0));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        final ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);

        lvBusquedaC.setAdapter(adaptador);

        lvBusquedaC.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main_PistasAlquiler_Activity.this, Main_AddPista_Activity.class);
                String infopista = lista.get(position);

                intent.putExtra("objetoData", infopista + "          " + usuario + "          " + PadelCubierto);

                startActivity(intent);

            }


        });
    }

    public void PistasLibresC(String URL){


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
                lista.add("\n"+ ja.getString(i + 2) + "          "+ ja.getString(i + 1) + "          "+ ja.getString(i + 0));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        final ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);

        lvPistasLibresC.setAdapter(adaptador);

        lvPistasLibresC.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main_PistasAlquiler_Activity.this, Main_AddPista_Activity.class);
                String infopista = lista.get(position);

                intent.putExtra("objetoData", infopista + "          " + usuario + "          " + PadelCubierto);

                startActivity(intent);

            }


        });
    }




    //pistas libre futbol sala-------------------------------------------------------------------------------------------------------------------------

    public void BusquedaFechaFS(String URL){


        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replace("][",",");
                String  response2 = response.replace("][", ",");
                response2 = response2.replaceAll("\n", "");
                response2 = response2.replaceAll("\"", "");
                response2 = response2.replaceAll("\\[", "").replaceAll("\\]","");

                if(response2.equals("")){
                    Toast.makeText(Main_PistasAlquiler_Activity.this, "NO HAY PISTAS LIBRES EL DIA SELECCIONADO ", Toast.LENGTH_SHORT).show();
                    lvPistasLibresFS.setVisibility(View.VISIBLE);
                    lvBusquedaFS.setVisibility(View.INVISIBLE);

                }else if (response.length()>0){
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson",""+ja.length());
                        Intent intent = getIntent();
                        String usuario = intent.getStringExtra("usuario");
                        String pista = intent.getStringExtra("pista");
                        CargarListViewBusquedaFS(ja, usuario, pista);
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

    public void CargarListViewBusquedaFS(JSONArray ja, final String usuario, final String dia) {

        final ArrayList<String> lista = new ArrayList<>();


        for (int i = 0; i < ja.length(); i += 3) {

            try {
                lista.add("\n"+ ja.getString(i + 2) + "          "+ ja.getString(i + 1) + "          "+ ja.getString(i + 0));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        final ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);

        lvBusquedaFS.setAdapter(adaptador);

        lvBusquedaFS.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main_PistasAlquiler_Activity.this, Main_AddPista_Activity.class);
                String infopista = lista.get(position);

                intent.putExtra("objetoData", infopista + "          " + usuario + "          " + FutbolSala);

                startActivity(intent);

            }


        });
    }

    public void PistasLibresFS(String URL){


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
                lista.add("\n"+ ja.getString(i + 2) + "          "+ ja.getString(i + 1) + "          "+ ja.getString(i + 0));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        final ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);

        lvPistasLibresFS.setAdapter(adaptador);

        lvPistasLibresFS.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main_PistasAlquiler_Activity.this, Main_AddPista_Activity.class);
                String infopista = lista.get(position);

                intent.putExtra("objetoData", infopista + "          " + usuario + "          " + Futbol7);

                startActivity(intent);

            }


        });
    }


//pistas libre futbol 7-------------------------------------------------------------------------------------------------------------------------

    public void BusquedaFechaF7(String URL){


        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replace("][",",");


                String  response2 = response.replace("][", ",");
                response2 = response2.replaceAll("\n", "");
                response2 = response2.replaceAll("\"", "");
                response2 = response2.replaceAll("\\[", "").replaceAll("\\]","");

                if(response2.equals("")){
                    Toast.makeText(Main_PistasAlquiler_Activity.this, "NO HAY PISTAS LIBRES EL DIA SELECCIONADO ", Toast.LENGTH_SHORT).show();
                    lvPistasLibresF7.setVisibility(View.VISIBLE);
                    lvBusquedaF7.setVisibility(View.INVISIBLE);

                } else if (response.length()>0){
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson",""+ja.length());
                        Intent intent = getIntent();
                        String usuario = intent.getStringExtra("usuario");
                        String pista = intent.getStringExtra("pista");
                        CargarListViewBusqueda(ja, usuario, pista);
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

    public void CargarListViewBusqueda(JSONArray ja, final String usuario, final String dia) {

        final ArrayList<String> lista = new ArrayList<>();


        for (int i = 0; i < ja.length(); i += 3) {

            try {
                lista.add("\n"+ ja.getString(i + 2) + "          "+ ja.getString(i + 1) + "          "+ ja.getString(i + 0));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        final ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);

        lvBusquedaF7.setAdapter(adaptador);

        lvBusquedaF7.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main_PistasAlquiler_Activity.this, Main_AddPista_Activity.class);
                String infopista = lista.get(position);

                intent.putExtra("objetoData", infopista + "          " + usuario + "          " + Futbol7);

                startActivity(intent);

            }


        });
    }



    public void PistasLibresF7(String URL){


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
                lista.add("\n"+ ja.getString(i + 2) + "          "+ ja.getString(i + 1) + "          "+ ja.getString(i + 0));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        final ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);

        lvPistasLibresF7.setAdapter(adaptador);

        lvPistasLibresF7.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main_PistasAlquiler_Activity.this, Main_AddPista_Activity.class);
                String infopista = lista.get(position);

                intent.putExtra("objetoData", infopista + "          " + usuario + "          " + Futbol7);

                startActivity(intent);

            }


        });
    }

    //pistas libre futbol 7 PISTA 2-------------------------------------------------------------------------------------------------------------------------

    public void BusquedaFechaF7P2(String URL){


        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replace("][",",");


                String  response2 = response.replace("][", ",");
                response2 = response2.replaceAll("\n", "");
                response2 = response2.replaceAll("\"", "");
                response2 = response2.replaceAll("\\[", "").replaceAll("\\]","");

                if(response2.equals("")){
                    Toast.makeText(Main_PistasAlquiler_Activity.this, "NO HAY PISTAS LIBRES EL DIA SELECCIONADO ", Toast.LENGTH_SHORT).show();
                    lvPistasLibresF7P2.setVisibility(View.VISIBLE);
                    lvBusquedaF7P2.setVisibility(View.INVISIBLE);

                } else if (response.length()>0){
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson",""+ja.length());
                        Intent intent = getIntent();
                        String usuario = intent.getStringExtra("usuario");
                        String pista = intent.getStringExtra("pista");
                        CargarListViewBusquedaP2(ja, usuario, pista);
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

    public void CargarListViewBusquedaP2(JSONArray ja, final String usuario, final String dia) {

        final ArrayList<String> lista = new ArrayList<>();


        for (int i = 0; i < ja.length(); i += 3) {

            try {
                lista.add("\n"+ ja.getString(i + 2) + "          "+ ja.getString(i + 1) + "          "+ ja.getString(i + 0));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        final ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);

        lvBusquedaF7P2.setAdapter(adaptador);

        lvBusquedaF7P2.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main_PistasAlquiler_Activity.this, Main_AddPista_Activity.class);
                String infopista = lista.get(position);

                intent.putExtra("objetoData", infopista + "          " + usuario + "          " + Futbol7P2);

                startActivity(intent);

            }


        });
    }


    public void PistasLibresF7P2(String URL){


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
                lista.add("\n" + ja.getString(i + 2) + "          " + ja.getString(i + 1) + "          " + ja.getString(i + 0));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        final ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);

        lvPistasLibresF7P2.setAdapter(adaptador);

        lvPistasLibresF7P2.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main_PistasAlquiler_Activity.this, Main_AddPista_Activity.class);
                String infopista = lista.get(position);

                intent.putExtra("objetoData", infopista + "          " + usuario + "          " + Futbol7P2);

                startActivity(intent);

            }


        });

    }

    @Override
    public void onClick(View v) {
        if(v==imgFechaF7 ){

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                    final int mesActual = month + 1;

                    String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                    String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);

                    edFechaF7.setText( year + BARRA + mesFormateado + BARRA + diaFormateado);

                }
            }
                    ,anio, mes, dia);
            datePickerDialog.show();

        }else if( v==imgFechaP2){

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                    final int mesActual = month + 1;

                    String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                    String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);

                    edFechaP2.setText( year + BARRA + mesFormateado + BARRA + diaFormateado);

                }
            }
                    ,anio, mes, dia);
            datePickerDialog.show();
        }else if( v==imgFechaFS){

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                    final int mesActual = month + 1;

                    String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                    String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);

                    edFechaFS.setText( year + BARRA + mesFormateado + BARRA + diaFormateado);

                }
            }
                    ,anio, mes, dia);
            datePickerDialog.show();
        }else if( v==imgFechaP){

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                    final int mesActual = month + 1;

                    String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                    String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);

                    edFechaP.setText( year + BARRA + mesFormateado + BARRA + diaFormateado);

                }
            }
                    ,anio, mes, dia);
            datePickerDialog.show();
        }else if( v==imgFechaC){

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                    final int mesActual = month + 1;

                    String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                    String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);

                    edFechaC.setText( year + BARRA + mesFormateado + BARRA + diaFormateado);

                }
            }
                    ,anio, mes, dia);
            datePickerDialog.show();
        }else if( v==imgFechaT){

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                    final int mesActual = month + 1;

                    String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                    String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);

                    edFechaT.setText( year + BARRA + mesFormateado + BARRA + diaFormateado);

                }
            }
                    ,anio, mes, dia);
            datePickerDialog.show();
        }
    }

    private void recuperarPreferendiasAdmin(){
        SharedPreferences preferences = getSharedPreferences("preferenciasLoginAdmin", Context.MODE_PRIVATE);
        tvNombre.setText(preferences.getString("usuario", ""));

        usuario = tvNombre.getText().toString();

    }
}
