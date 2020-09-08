package com.example.pabellonlh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class Main_PistasLibres_Activity extends AppCompatActivity {
    ListView lvPistasLibres;
    String usuario, pista;
   // TextView tvUsuario, tvPista;
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__pistas_libres_);

        lvPistasLibres = (ListView) findViewById(R.id.lvPistasLibres);
        //tvUsuario = findViewById(R.id.tvUsuario);
        //tvPista = findViewById(R.id.tvPista);
        Intent intent = getIntent();
        usuario = intent.getStringExtra("usuario");
        pista = intent.getStringExtra("pista");
       // tvUsuario.setText(usuario);
        //tvPista.setText(pista);
        PistasLibres("http://jose-cordones.es/app/consultas/obtenerPistasLibres.php?pista="+pista);
        ejecutarTarea();
    }

    private final int TIEMPO = 2000;

    public void ejecutarTarea() {
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = getIntent();
                String pista = intent.getStringExtra("pista");
                PistasLibres("http://jose-cordones.es/app/consultas/obtenerPistasLibres.php?pista="+pista);

                handler.postDelayed(this, TIEMPO);
            }

        }, TIEMPO);

    }

    public void PistasLibres(String URL){


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
        //final ArrayList<String> idPista = new ArrayList<>();
        //lista.add("\n"+"DIA                       " +"HORA               "+ "M        "+"PISTA");

        for (int i = 0; i < ja.length(); i += 6) {

            try {
                lista.add("\n"+ ja.getString(i + 5) + "          "+ ja.getString(i + 4) + "          "+ ja.getString(i + 3));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        final ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);

        lvPistasLibres.setAdapter(adaptador);

        lvPistasLibres.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main_PistasLibres_Activity.this, Main_AddPista_Activity.class);
                String infopista = lista.get(position);

                intent.putExtra("objetoData", infopista + "          " + usuario + "          " + pista);

                startActivity(intent);

            }


        });
    }

}
