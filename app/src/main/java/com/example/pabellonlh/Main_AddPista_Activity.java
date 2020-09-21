package com.example.pabellonlh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class Main_AddPista_Activity extends AppCompatActivity {
    String objeto;
    EditText edUsuario, edPista, edDia, edHora;
    TextView tvIDUSUARIO, tvIDINFO;
    Spinner snMaterial;
    Button btnAlquilar;
    String mandar_fecha, mandar_hora, mandar_pista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__add_pista_);

        edUsuario = (EditText) findViewById(R.id.edNick);
        edPista = (EditText) findViewById(R.id.edNombre);
        edDia = (EditText) findViewById(R.id.edFecha);
        edHora = (EditText) findViewById(R.id.edHora);
        tvIDUSUARIO = (TextView) findViewById(R.id.tvIDUSUARIO);
        tvIDINFO = (TextView) findViewById(R.id.tvIDINFO);
        //tvSpiner = (TextView) findViewById(R.id.tvSpiner);
        snMaterial = (Spinner) findViewById(R.id.spDevuelo);
        btnAlquilar = (Button) findViewById(R.id.btnBorrar);

        Intent intent = getIntent();
        objeto = intent.getStringExtra("objetoData");
        String[] parts = objeto.split("          ");
        String usuario = parts[3];
        String dia = parts[0];
        String hora = parts[1];
        String pista = parts[2];

        String [] SpinnerMaterial = {"si", "no"};
        final ArrayAdapter <String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, SpinnerMaterial);
        snMaterial.setAdapter(adapter);

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


        EnviarRecibirIdPersona("http://jose-cordones.es/app/consultas/obtenerIdPersona_Telefono.php?nick=" + usuario);
        EnviarRecibirIdInfo_Pista("http://jose-cordones.es/app/consultas/obtenerIdInfo_Pista.php?pista=" + pista+"&fecha="+dia+"&hora="+hora);

        btnAlquilar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mandar_fecha = edDia.getText().toString();
                mandar_hora = edHora.getText().toString();
                mandar_pista = edPista.getText().toString();
                validarPista("http://jose-cordones.es/app/consultas/comprobarRegistoPistas.php?fecha=" + mandar_fecha +"&horas="+mandar_hora+"&pista="+mandar_pista);



            }
        });



    }


    public void validarPista(String URL){


        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("][", ",");
                response = response.replaceAll("\n", "");
                response = response.replaceAll("\"", "");
                response = response.replaceAll("\\[", "").replaceAll("\\]","");

                if(response.equals("1")){
                    Toast.makeText(Main_AddPista_Activity.this, "ERROR - LA PISTA YA ESTA ALQUILADA POR OTRO USUARIO ", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    ejecutarServicio("http://jose-cordones.es/app/registros/registrar_pista.php");
                    Intent intent = new Intent(getApplicationContext(), Main_PistasAlquiler_Activity.class);
                    intent.putExtra("usuario", edUsuario.getText().toString());
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
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
                String valorSpiner = snMaterial.getSelectedItem().toString();

                //tvSpiner.setText(valorSpiner);

                Map<String, String>parametros = new HashMap<String, String>();
                parametros.put("id_info_posible_reserva", tvIDINFO.getText().toString());
                parametros.put("idpersona", tvIDUSUARIO.getText().toString());
                parametros.put("material", valorSpiner);


                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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
