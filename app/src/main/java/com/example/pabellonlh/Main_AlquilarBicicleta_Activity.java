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

public class Main_AlquilarBicicleta_Activity extends AppCompatActivity {

    ListView lvMisBicicletas;
    private AsyncHttpClient cliente;
   // TextView tvPrueba;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__alquilar_bicicleta_);
      //  tvPrueba = findViewById(R.id.tvPrueba);
        lvMisBicicletas = (ListView) findViewById(R.id.lvMisBicicletas);
        cliente = new AsyncHttpClient ();
        Intent intent = getIntent();
        String usuario = intent.getStringExtra("usuario");
        String dia = intent.getStringExtra("dia");
        intent.putExtra("usuario", usuario);
       // tvPrueba.setText(dia);
        EnviarRecibirDatos("http://jose-cordones.es/app/consultas/obtenerBicisVacias.php?dia="+dia);
    }

    public void EnviarRecibirDatos(String URL){


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
                        CargarListView(ja);
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

    public void CargarListView(JSONArray ja){

        final ArrayList<String> lista = new ArrayList<>();
        //final ArrayList<String> idPista = new ArrayList<>();
        //lista.add("\n"+"DIA                       " +"HORA               "+ "M        "+"PISTA");

        for(int i=0;i<ja.length();i+=4){

            try {
                lista.add("\n"+"                                     "+ja.getString(i+1)+"                                     ");

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        final ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);

        lvMisBicicletas.setAdapter(adaptador);


      /*  lvMisBicicletas.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main_MisPistas_Activity.this, Main_EliminarEditar_MiPista_Activity.class);
                //Toast.makeText(Main_MisPistas_Activity.this, lista.get(position)+"  "+usuario, Toast.LENGTH_SHORT).show();

                intent.putExtra("objetoData", lista.get(position)+"          "+usuario);

                startActivity(intent);

            }
        });*/



    }

}
