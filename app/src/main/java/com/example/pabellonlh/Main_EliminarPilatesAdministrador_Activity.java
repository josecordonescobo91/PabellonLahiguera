package com.example.pabellonlh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Main_EliminarPilatesAdministrador_Activity extends AppCompatActivity {

    String objeto;
    EditText edFecha, edNick, edActividad, edID;
    TextView tvIDALQUILER, tvprueba, tvprueba1;
    Button btnEliminar;
    String usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__eliminar_pilates_administrador_);

        Intent intent = getIntent();

        usuario = intent.getStringExtra("usuario");
       // Toast.makeText(getApplicationContext(), usuario, Toast.LENGTH_SHORT).show();


        edFecha = (EditText) findViewById(R.id.edFecha);
        edNick = (EditText) findViewById(R.id.edNick);
        edActividad = (EditText) findViewById(R.id.edActividad);
        //edID = (EditText) findViewById(R.id.edID);
        tvIDALQUILER = (TextView) findViewById(R.id.tvIDALQUILER);
        tvprueba = (TextView) findViewById(R.id.tvprueba);
        tvprueba1 = (TextView) findViewById(R.id.tvprueba1);
        btnEliminar = (Button) findViewById(R.id.btnEliminar);

        objeto = intent.getStringExtra("objetoData");
        String[] parts = objeto.split("                 ");
        // final String actividad = parts[1];
        // final String dia = parts[2];
        final String nick = parts[2];
        final String dia = parts[3];
        final String actividad = parts[4];

        edFecha.setText(dia);
        final String acti = actividad.replaceAll(" ", "");
        edActividad.setText(acti);
        edNick.setText(nick);

        EnviarRecibirIdActividad("http://jose-cordones.es/app/consultas/obtenerIdAlquilerActividad.php?nick=" + nick+"&nombre="+acti+"&dia="+dia);
        btnEliminar.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {
                EliminarActividad("http://jose-cordones.es/app/eliminaciones/eliminarActividad.php?id_actividad=" + tvprueba.getText().toString());
                Intent intent = new Intent(getApplicationContext(), Main_GestionPilates_Activity.class);
                intent.putExtra("usuario", usuario);
                Toast.makeText(getApplicationContext(), "ACTIVIDAD ELIMINADA", Toast.LENGTH_SHORT).show();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);


            }
        });
    }

    public void EliminarActividad(String URL){


        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

    }

    public void EnviarRecibirIdActividad(String URL) {

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replace("][", ",");
                response = response.replaceAll("\n", "");
                response = response.replaceAll("\"", "");
                response = response.replaceAll("\\[", "").replaceAll("\\]","");
                tvprueba.setText(response);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

    }
}
