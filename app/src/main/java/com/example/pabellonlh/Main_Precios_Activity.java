package com.example.pabellonlh;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

public class Main_Precios_Activity extends AppCompatActivity {
    ListView lvPrecios;
    private AsyncHttpClient cliente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__precios_);
        Toast.makeText(Main_Precios_Activity.this, "CON LUZ SE COBRARA 2€ MAS", Toast.LENGTH_SHORT).show();
        lvPrecios = (ListView) findViewById(R.id.lvPrecios);
        cliente = new AsyncHttpClient ();
        String consulta = "http://jose-cordones.es/app/consultas/obtenerPrecios.php";
        EnviarRecibirDatos(consulta);
    }

    public void EnviarRecibirDatos(String URL){

        //Toast.makeText(getApplicationContext(), ""+URL, Toast.LENGTH_SHORT).show();

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replace("][",",");
                if (response.length()>0){
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson",""+ja.length());
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

        ArrayList<String> lista = new ArrayList<>();

        for(int i=0;i<ja.length();i+=8){

            try {

                lista.add("\n"+"               ACTIVIDAD                            PRECIO"+"\n \n"+"               FUTBOL 7                              "+ja.getString(i+1)+"€"+"\n \n"+"               FUTBOL SALA                      "+ja.getString(i+2)+"€"+"\n \n"+"               TENNIS                                  "+ja.getString(i+3)+"€"+"\n \n"+"               PADEL                                    "+ja.getString(i+4)+"€" +"\n \n"+"               SPINNING                              "+ja.getString(i+5)+"€"+"\n \n"+"               ZUMBA                                    "+ja.getString(i+6)+"€"+"\n \n"+"               CROSSFIT                                    "+ja.getString(i+7)+"€"+"\n \n"+"               PILATES                                  "+ja.getString(i+8)+"€");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        lvPrecios.setAdapter(adaptador);



    }
}
