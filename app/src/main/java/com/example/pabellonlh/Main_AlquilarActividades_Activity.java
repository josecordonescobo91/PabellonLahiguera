package com.example.pabellonlh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main_AlquilarActividades_Activity extends AppCompatActivity {
    //ZUMBA---------------------------------------------------------------------------
    String usuario;
    TextView tvIDPERSONA, tvNombre;
    EditText edUsuario, edActividad;
    Spinner spDia;
    Button btnAlquilar;
    String mandar_usuario, mandar_actividad, mandar_dia;

    //CLOFI---------------------------------------------------------------------------
    TextView tvIDPERSONA2;
    EditText edUsuario2, edActividad2;
    Spinner spDia2;
    Button btnAlquilar2;
    String mandar_usuario2, mandar_actividad2, mandar_dia2;

    //PILATES---------------------------------------------------------------------------
    TextView tvIDPERSONA3;
    EditText edUsuario3, edActividad3;
    Spinner spDia3;
    Button btnAlquilar3;
    String mandar_usuario3, mandar_actividad3, mandar_dia3;
    TabHost TbH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__alquilar_actividades_);

        TbH = (TabHost) findViewById(R.id.tabHost); //llamamos al Tabhost
        TbH.setup();                                                         //lo activamos

        TabHost.TabSpec tab1 = TbH.newTabSpec("tab1");  //aspectos de cada Tab (pestaña)
        TabHost.TabSpec tab2 = TbH.newTabSpec("tab2");
        TabHost.TabSpec tab3 = TbH.newTabSpec("tab3");

        tab1.setIndicator("ZUMBA");    //qué queremos que aparezca en las pestañas
        tab1.setContent(R.id.ZUMBA); //definimos el id de cada Tab (pestaña)


        tab2.setIndicator("CROSSFIT");
        tab2.setContent(R.id.CLOFIT);

        tab3.setIndicator("PILATES");
        tab3.setContent(R.id.PILATES);


        TbH.addTab(tab1); //añadimos los tabs ya programados
        TbH.addTab(tab2);
        TbH.addTab(tab3);

        Intent intent = getIntent();
        usuario = intent.getStringExtra("usuario");
        tvNombre = (TextView) findViewById(R.id.tvNombre1);
       // Toast.makeText(getApplicationContext(), usuario, Toast.LENGTH_SHORT).show();
        if(usuario == null){
            recuperarPreferendiasAdmin();
           //  Toast.makeText(getApplicationContext(),"pruebaaaaa "+ usuario, Toast.LENGTH_SHORT).show();
        }

        //ZUMBA-------------------------------------------------------------------------------------
        tvIDPERSONA = (TextView) findViewById(R.id.tvIDPERSONA);

        edUsuario = (EditText) findViewById(R.id.edNick);
        edActividad = (EditText) findViewById(R.id.edActividad);
        spDia = (Spinner) findViewById(R.id.spDia);
        btnAlquilar = (Button) findViewById(R.id.btnBorrar);

        edUsuario.setText(usuario);
        edActividad.setText("ZUMBA");

        String [] SpinnerMaterial = {"Lunes", "Martes","Miercoles", "Jueves", "Viernes"};
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, SpinnerMaterial);
        spDia.setAdapter(adapter);

        EnviarRecibirIdPersona("http://jose-cordones.es/app/consultas/obtenerIdPersona_Telefono.php?nick=" + usuario);
        EnviarRecibirIdPersonaClofit("http://jose-cordones.es/app/consultas/obtenerIdPersona_Telefono.php?nick=" + usuario);
        btnAlquilar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mandar_dia = spDia.getSelectedItem().toString();
                mandar_usuario = edUsuario.getText().toString();
                mandar_actividad = edActividad.getText().toString();
                validarZumba("http://jose-cordones.es/app/consultas/comprobarRegistroCroFit.php?dia=" + mandar_dia +"&nick="+mandar_usuario +"&nombre="+mandar_actividad);


            }
        });

        //CROSSFIT------------------------------------------------------------------------------------
        tvIDPERSONA2 = (TextView) findViewById(R.id.tvIDPERSONA2);
        edUsuario2 = (EditText) findViewById(R.id.edUsuario2);
        edActividad2 = (EditText) findViewById(R.id.edActividad2);
        spDia2 = (Spinner) findViewById(R.id.spDia2);
        btnAlquilar2 = (Button) findViewById(R.id.btnBorrarCrear2);

        edUsuario2.setText(usuario);
        edActividad2.setText("CROSSFIT");

        String [] SpinnerMaterial2 = {"Martes", "Jueves"};
        final ArrayAdapter <String> adapter2 = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, SpinnerMaterial2);
        spDia2.setAdapter(adapter2);

        btnAlquilar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mandar_dia2 = spDia2.getSelectedItem().toString();
                mandar_usuario2 = edUsuario2.getText().toString();
                mandar_actividad2 = edActividad2.getText().toString();
                validarClofit("http://jose-cordones.es/app/consultas/comprobarRegistroCroFit.php?dia=" + mandar_dia2 +"&nick="+mandar_usuario2 +"&nombre="+mandar_actividad2);


            }
        });

        //PILATES-----------------------------------------------------------------------------------
        tvIDPERSONA3 = (TextView) findViewById(R.id.tvIDPERSONA3);
        edUsuario3 = (EditText) findViewById(R.id.edUsuario3);
        edActividad3 = (EditText) findViewById(R.id.edActividad3);
        spDia3 = (Spinner) findViewById(R.id.spDia3);
        btnAlquilar3 = (Button) findViewById(R.id.btnBorrarCrear3);

        edUsuario3.setText(usuario);
        edActividad3.setText("PILATES");

        String [] SpinnerMateriaL3 = {"Lunes", "Jueves"};
        final ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, SpinnerMateriaL3);
        spDia3.setAdapter(adapter3);

        EnviarRecibirIdPersonaPilates("http://jose-cordones.es/app/consultas/obtenerIdPersona_Telefono.php?nick=" + usuario);
        btnAlquilar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mandar_dia3 = spDia3.getSelectedItem().toString();
                mandar_usuario3 = edUsuario3.getText().toString();
                mandar_actividad3 = edActividad3.getText().toString();
                validarPilates("http://jose-cordones.es/app/consultas/comprobarRegistroCroFit.php?dia=" + mandar_dia3 +"&nick="+mandar_usuario3 +"&nombre="+mandar_actividad3);


            }
        });


    }

    private void recuperarPreferendiasAdmin(){
        SharedPreferences preferences = getSharedPreferences("preferenciasLoginAdmin", Context.MODE_PRIVATE);
        tvNombre.setText(preferences.getString("usuario", ""));

        usuario = tvNombre.getText().toString();

    }
    //pilates------------------------------------------------------------------------------------------------------------------------------------------
    public void validarPilates(String URL){


        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("][", ",");
                response = response.replaceAll("\n", "");
                response = response.replaceAll("\"", "");
                response = response.replaceAll("\\[", "").replaceAll("\\]","");
                // tvIDPERSONA.setText(response);

                if(response.equals("1")){
                    Toast.makeText(Main_AlquilarActividades_Activity.this, "ERROR - YA LA TIENES ALQUILADA", Toast.LENGTH_SHORT).show();
                }else{
                    ejecutarPilates("http://jose-cordones.es/app/registros/registrar_crofit.php");
                    Intent intent = new Intent(getApplicationContext(), Main_AlquilarActividades_Activity.class);
                    intent.putExtra("usuario", edUsuario3.getText().toString());

                }


            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

    }


    private void ejecutarPilates(String URL1){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL1, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "REGISTRO CORRECTO", Toast.LENGTH_SHORT).show();
            }
        },new Response.ErrorListener(){
            @Override
            public  void  onErrorResponse(VolleyError error){
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String valorSpiner = spDia3.getSelectedItem().toString();


                Map<String, String>parametros = new HashMap<String, String>();
                parametros.put("idpersona", tvIDPERSONA3.getText().toString());
                parametros.put("nombre", edActividad3.getText().toString());
                parametros.put("dia", valorSpiner);


                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void EnviarRecibirIdPersonaPilates(String URL) {

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replace("][", ",");

                if (response.length() > 0) {
                    try {

                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson", "" + ja.length());
                        CargarListViewPilates(ja);

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

    public void CargarListViewPilates(JSONArray ja) {

        ArrayList<String> lista = new ArrayList<>();
        String id = null;
        for (int i = 0; i < ja.length(); i += 2) {

            try {

                id = ("\n" + ja.getString(i + 0));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        ArrayAdapter<String> adaptador3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        tvIDPERSONA3.setText(id);


    }

    //clofit------------------------------------------------------------------------------------------------------------------------------------------
    public void validarClofit(String URL){


        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("][", ",");
                response = response.replaceAll("\n", "");
                response = response.replaceAll("\"", "");
                response = response.replaceAll("\\[", "").replaceAll("\\]","");
                // tvIDPERSONA.setText(response);

                if(response.equals("1")){
                    Toast.makeText(Main_AlquilarActividades_Activity.this, "ERROR - YA LA TIENES ALQUILADA", Toast.LENGTH_SHORT).show();
                }else{
                    ejecutarClofit("http://jose-cordones.es/app/registros/registrar_crofit.php");
                    Intent intent = new Intent(getApplicationContext(), Main_AlquilarActividades_Activity.class);
                    intent.putExtra("usuario", edUsuario2.getText().toString());

                }


            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

    }


    private void ejecutarClofit(String URL1){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL1, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "REGISTRO CORRECTO", Toast.LENGTH_SHORT).show();
            }
        },new Response.ErrorListener(){
            @Override
            public  void  onErrorResponse(VolleyError error){
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String valorSpiner = spDia2.getSelectedItem().toString();


                Map<String, String>parametros = new HashMap<String, String>();
                parametros.put("idpersona", tvIDPERSONA2.getText().toString());
                parametros.put("nombre", edActividad2.getText().toString());
                parametros.put("dia", valorSpiner);


                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void EnviarRecibirIdPersonaClofit(String URL) {

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replace("][", ",");

                if (response.length() > 0) {
                    try {

                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson", "" + ja.length());
                        CargarListViewClofit(ja);

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

    public void CargarListViewClofit(JSONArray ja) {

        ArrayList<String> lista = new ArrayList<>();
        String id = null;
        for (int i = 0; i < ja.length(); i += 2) {

            try {

                id = ("\n" + ja.getString(i + 0));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        ArrayAdapter<String> adaptador2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        tvIDPERSONA2.setText(id);


    }

    //zumba---------------------------------------------------------------------------------------------------------------------------------------------------------------

    public void validarZumba(String URL){


        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("][", ",");
                response = response.replaceAll("\n", "");
                response = response.replaceAll("\"", "");
                response = response.replaceAll("\\[", "").replaceAll("\\]","");
                // tvIDPERSONA.setText(response);

                if(response.equals("1")){
                    Toast.makeText(Main_AlquilarActividades_Activity.this, "ERROR - YA LA TIENES ALQUILADA", Toast.LENGTH_SHORT).show();
                }else{
                    ejecutarServicio("http://jose-cordones.es/app/registros/registrar_crofit.php");
                    Intent intent = new Intent(getApplicationContext(), Main_AlquilarActividades_Activity.class);
                    intent.putExtra("usuario", edUsuario.getText().toString());

                }


            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

    }


    private void ejecutarServicio(String URL1){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL1, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "REGISTRO CORRECTO", Toast.LENGTH_SHORT).show();
            }
        },new Response.ErrorListener(){
            @Override
            public  void  onErrorResponse(VolleyError error){
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String valorSpiner = spDia.getSelectedItem().toString();


                Map<String, String>parametros = new HashMap<String, String>();
                parametros.put("idpersona", tvIDPERSONA.getText().toString());
                parametros.put("nombre", edActividad.getText().toString());
                parametros.put("dia", valorSpiner);


                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void EnviarRecibirIdPersona(String URL) {

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


    }
}
