package com.example.pabellonlh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

public class Main_MisBicicletas_Activity extends AppCompatActivity {

    ListView lvMisBicicletas;
    TextView tvIDPERSONA;

    private AsyncHttpClient cliente;
    private String idPersona;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__mis_bicicletas_);
        lvMisBicicletas = (ListView) findViewById(R.id.lvMisBicicletas);
        tvIDPERSONA = (TextView) findViewById(R.id.tvIDPERSONA);

        cliente = new AsyncHttpClient();
        Intent intent = getIntent();
        String usuario = intent.getStringExtra("usuario");
        intent.putExtra("usuario", usuario);
        idPersona = tvIDPERSONA.getText().toString();
        EnviarRecibirIdPersona("http://jose-cordones.es/app/consultas/obtenerIdPersona_Telefono.php?nick=" + usuario);
     //   EnviarRecibirMisBicicletas("http://jose-cordones.es/app/consultas/obtenerMisBicicletas.php?idpersona=" + idPersona);
       // Toast.makeText(Main_MisBicicletas_Activity.this, idPersona , Toast.LENGTH_SHORT).show();

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
                        //Toast.makeText(Main_MisBicicletas_Activity.this, idPersona , Toast.LENGTH_SHORT).show();
                      //  EnviarRecibirMisBicicletas("http://jose-cordones.es/app/consultas/obtenerMisBicicletas.php?idpersona=" + idPersona);

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
       // Toast.makeText(Main_MisBicicletas_Activity.this, "biiiennn  " + id, Toast.LENGTH_SHORT).show();

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
       // final ArrayList<String> idPista = new ArrayList<>();
       // lista.add("\n" + "DIA                              " + "BICICLETA                    " + "USUARIO" );

        for (int i = 0; i < ja.length(); i += 4) {

            try {
                lista.add("\n" + "                 " + ja.getString(i +3) +"                   " + ja.getString(i +2));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        final ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);

        lvMisBicicletas.setAdapter(adaptador);


        lvMisBicicletas.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main_MisBicicletas_Activity.this, Main_EliminarBicicleta_Activity.class);
                //Toast.makeText(Main_MisPistas_Activity.this, lista.get(position)+"  "+usuario, Toast.LENGTH_SHORT).show();

                intent.putExtra("objetoData", lista.get(position) + "                   "+usuario);

                startActivity(intent);

            }
        });
    }
}
