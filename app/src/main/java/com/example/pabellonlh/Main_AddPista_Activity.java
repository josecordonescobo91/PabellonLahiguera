package com.example.pabellonlh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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

public class Main_AddPista_Activity extends AppCompatActivity {
    String objeto;
    EditText edUsuario, edPista, edDia, edHora;
    TextView tvIDUSUARIO, tvIDINFO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__add_pista_);

        edUsuario = (EditText) findViewById(R.id.edUsuario);
        edPista = (EditText) findViewById(R.id.edPista);
        edDia = (EditText) findViewById(R.id.edDia);
        edHora = (EditText) findViewById(R.id.edHora);
        tvIDUSUARIO = (TextView) findViewById(R.id.tvIDUSUARIO);
        tvIDINFO = (TextView) findViewById(R.id.tvIDINFO);

        Intent intent = getIntent();
        objeto = intent.getStringExtra("objetoData");
        String[] parts = objeto.split("          ");
        String usuario = parts[3];
        String dia = parts[0];
        String hora = parts[1];
        String pista = parts[2];

        usuario = usuario.replace("][", ",");
        usuario = usuario.replaceAll("\n", "");
        usuario = usuario.replaceAll("\"", "");
        usuario = usuario.replaceAll("\\[", "").replaceAll("\\]","");


        dia = dia.replace("][", ",");
        dia = dia.replaceAll("\n", "");
        dia = dia.replaceAll("\"", "");
        dia = dia.replaceAll("\\[", "").replaceAll("\\]","");

        hora = hora.replace("][", ",");
        hora = hora.replaceAll("\n", "");
        hora = hora.replaceAll("\"", "");
        hora = hora.replaceAll("\\[", "").replaceAll("\\]","");

        pista = pista.replace("][", ",");
        pista = pista.replaceAll("\n", "");
        pista = pista.replaceAll("\"", "");
        pista = pista.replaceAll("\\[", "").replaceAll("\\]","");

        edUsuario.setText(usuario);
        edPista.setText(pista);
        edDia.setText(dia);
        edHora.setText(hora);

       /* String fecha1="2020-05-18";
        String hora1="10:00-11:00";
        String pista1="futbol 7";*/

        EnviarRecibirIdPersona("http://jose-cordones.es/app/consultas/obtenerIdPersona_Telefono.php?nick=" + usuario);
        EnviarRecibirIdInfo_Pista("http://jose-cordones.es/app/consultas/obtenerIdInfo_Pista.php?pista=" + pista+"&fecha="+dia+"&hora="+hora);


    }

    public void EnviarRecibirIdInfo_Pista(String URL2) {

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replace("][", ",");
                response = response.replaceAll("\n", "");
                response = response.replaceAll("\"", "");
                response = response.replaceAll("\\[", "").replaceAll("\\]","");
                tvIDINFO.setText(response);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

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

                id = (ja.getString(i + 0));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        tvIDUSUARIO.setText(id);
    }
}
