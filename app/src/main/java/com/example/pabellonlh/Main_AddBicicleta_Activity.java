package com.example.pabellonlh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class Main_AddBicicleta_Activity extends AppCompatActivity {

    String objeto;
    EditText edDia, edBici, edUsuario, edTelefono;
    TextView tvIDPERSONA, tvIDBICI, tvPrueba, tvContador, tvRespo;
    Button btnAlquilarBici;
    String mandar_dia, mandar_bici;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__add_bicicleta_);

        edDia = (EditText) findViewById(R.id.edFecha);
        edBici = (EditText) findViewById(R.id.edBici);
        edUsuario = (EditText) findViewById(R.id.edNick);
        edTelefono = (EditText) findViewById(R.id.edTelefono);
        tvIDPERSONA = (TextView) findViewById(R.id.tvIDPERSONA);
        tvIDBICI = (TextView) findViewById(R.id.tvIDBICI);
        tvPrueba = (TextView) findViewById(R.id.tvPrueba);
        tvContador = (TextView) findViewById(R.id.tvContador);
        tvRespo = (TextView) findViewById(R.id.tvRespo);
        btnAlquilarBici = (Button) findViewById(R.id.btnAlquilarBici);
      /*  Intent intent = getIntent();
        String usuario = intent.getStringExtra("usuario");
        String dia = intent.getStringExtra("dia");
        intent.putExtra("usuario", usuario);*/
        Intent intent = getIntent();
        objeto = intent.getStringExtra("objetoData");
        String[] parts = objeto.split("          ");
        final String dia = parts[8];
        String nick = parts[7];
        String bicicleta = parts[3];


        edDia.setText(dia);
        final String bici = bicicleta.replaceAll(" ", "");
        edBici.setText(bici);
        String usuario = nick.replaceAll(" ", "");
        edUsuario.setText(usuario);

        EnviarRecibirIdPersona("http://jose-cordones.es/app/consultas/obtenerIdPersona_Telefono.php?nick=" + usuario);


        EnviarRecibirIdBicicleta("http://jose-cordones.es/app/consultas/obtenerIdBicicleta.php?dia=" + dia +"&bici="+bici);


        btnAlquilarBici.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mandar_dia = edDia.getText().toString();
                mandar_bici = edBici.getText().toString();
                validarId("http://jose-cordones.es/app/consultas/comprobarRegistroBici.php?dia=" + mandar_dia +"&bici="+mandar_bici);



            }
        });

    }


    public void validarId(String URL){


        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("][", ",");
                response = response.replaceAll("\n", "");
                response = response.replaceAll("\"", "");
                response = response.replaceAll("\\[", "").replaceAll("\\]","");

                if(response.equals("1")){
                    Toast.makeText(Main_AddBicicleta_Activity.this, "ERROR - LA BICICLETA YA ESTA ALQUILADA POR OTRO USUARIO ", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    ejecutarServicio("http://jose-cordones.es/app/registros/registrar_bicicleta.php");
                    Intent intent = new Intent(getApplicationContext(), Main_DiasBicicletas_Activity.class);
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
        String telefono = null;
        for (int i = 0; i < ja.length(); i += 2) {

            try {

                id = ("\n" + ja.getString(i + 0));
                telefono = ("\n" + ja.getString(i + 1));


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        edTelefono.setText(telefono);
        tvIDPERSONA.setText(id);
    }



    public void EnviarRecibirIdBicicleta(String URL) {

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replace("][", ",");


                if (response.length() > 0) {
                    try {

                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson", "" + ja.length());
                        CargarListViewIdBici(ja);


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

    public void CargarListViewIdBici(JSONArray ja) {

        ArrayList<String> lista = new ArrayList<>();
        String idBici = null;
        String telefono = null;
        for (int i = 0; i < ja.length(); i += 1) {

            try {

                idBici = ("\n" + ja.getString(i + 0));


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);

        tvIDBICI.setText(idBici);

        final String prueba = tvIDBICI.getText().toString();

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
                Map<String, String>parametros = new HashMap<String, String>();
                parametros.put("id_info_bicicletas", tvIDBICI.getText().toString());
                parametros.put("idpersona", tvIDPERSONA.getText().toString());
                parametros.put("telefono", edTelefono.getText().toString());


                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}
