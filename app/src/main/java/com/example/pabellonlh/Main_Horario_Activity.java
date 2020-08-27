package com.example.pabellonlh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.ArrayLinkedVariables;

import android.os.Bundle;
import android.util.Log;
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
import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class Main_Horario_Activity extends AppCompatActivity {
    ListView lvDatos;
    private AsyncHttpClient cliente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__horario_);
        Toast.makeText(Main_Horario_Activity.this, "HORARIO PABELLON LAHIGUERA", Toast.LENGTH_SHORT).show();
        lvDatos = (ListView) findViewById(R.id.lvDatos);
        cliente = new AsyncHttpClient ();
        String consulta = "http://jose-cordones.es/app/consultas/obtenerHorario.php";
        EnviarRecibirDatos(consulta);
        //obtenerHorario();

    }


    public void EnviarRecibirDatos(String URL){

      //  Toast.makeText(getApplicationContext(), ""+URL, Toast.LENGTH_SHORT).show();

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

        for(int i=0;i<ja.length();i+=18){

            try {

                lista.add("\n"+"                           "+ja.getString(i+1) + "             "+ja.getString(i+10)+" \n \n"+" LUNES:             "+ja.getString(i+2)+"      "+ja.getString(i+11)+"\n \n"+" MARTES:          "+ja.getString(i+3)+"      "+ja.getString(i+12)+"\n \n"+"MIERCOLES:     "+ja.getString(i+4)+"      "+ja.getString(i+13)+"\n \n"+"JUEVES:            "+ja.getString(i+5)+"      "+ja.getString(i+14)+"\n \n"+"VIERNES:          "+ja.getString(i+6)+"      "+ja.getString(i+15)+"\n \n"+"SABADO:           "+ja.getString(i+7)+"      "+ja.getString(i+16)+"\n \n"+"DOMINGO:         "+ja.getString(i+8)+"             "+ja.getString(i+17)+"\n \n");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        lvDatos.setAdapter(adaptador);



    }


}
