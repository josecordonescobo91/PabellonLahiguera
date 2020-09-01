package com.example.pabellonlh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.GoogleMap;
import com.loopj.android.http.AsyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;


public class Main_AlquilarBicicleta_Activity extends AppCompatActivity {

    ListView lvMisBicicletas;
    private AsyncHttpClient cliente;
    TextView tvPrueba;
    Handler handler = new Handler();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__alquilar_bicicleta_);
       tvPrueba = findViewById(R.id.tvPrueba);
        lvMisBicicletas = (ListView) findViewById(R.id.lvMisBicicletas);
        cliente = new AsyncHttpClient ();
        Intent intent = getIntent();
        String usuario = intent.getStringExtra("usuario");
        String dia = intent.getStringExtra("dia");
        intent.putExtra("usuario", usuario);
        tvPrueba.setText(usuario);
        EnviarRecibirDatos("http://jose-cordones.es/app/consultas/obtenerBicisVacias.php?dia="+dia);
        ejecutarTarea();

    }


    private final int TIEMPO = 2000;

    public void ejecutarTarea() {
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = getIntent();
                String dia = intent.getStringExtra("dia");
                EnviarRecibirDatos("http://jose-cordones.es/app/consultas/obtenerBicisVacias.php?dia="+dia);

                handler.postDelayed(this, TIEMPO);
            }

        }, TIEMPO);

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
                        String dia = intent.getStringExtra("dia");
                        CargarListView(ja, usuario, dia);
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

    public void CargarListView(JSONArray ja, final String usuario, final String dia){

        final ArrayList<String> lista = new ArrayList<>();
        //final ArrayList<String> idPista = new ArrayList<>();
        //lista.add("\n"+"DIA                       " +"HORA               "+ "M        "+"PISTA");

        for(int i=0;i<ja.length();i+=3){

            try {
                lista.add("\n"+"                                     "+ja.getString(i+2)+"                                     ");

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        final ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);

        lvMisBicicletas.setAdapter(adaptador);

        lvMisBicicletas.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main_AlquilarBicicleta_Activity.this, Main_AddBicicleta_Activity.class);
                String bici = lista.get(position);
                //
               // String bici = lista.get(position);
             //   EnviarRecibirInfoBici("http://jose-cordones.es/app/consultas/obtenerBicisVacias.php?dia="+dia);

                intent.putExtra("objetoData", bici+"          "+usuario+"          "+dia);

                startActivity(intent);

            }




        });






    }

}
