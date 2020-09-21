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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class Main_EliminarBicicletasAdministrador_Activity extends AppCompatActivity {
    String objeto;
    EditText edDia, edUsuario, edBicicleta, edID;
    TextView tvIDALQUILER, tvprueba, tvprueba1;
    Button btnEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__eliminar_bicicletas_administrador_);

        Intent intent = getIntent();

        edDia = (EditText) findViewById(R.id.edFecha);
        edUsuario = (EditText) findViewById(R.id.edNick);
        edBicicleta = (EditText) findViewById(R.id.edActividad);
        //edID = (EditText) findViewById(R.id.edID);
        tvIDALQUILER = (TextView) findViewById(R.id.tvIDALQUILER);
        tvprueba = (TextView) findViewById(R.id.tvprueba);
        tvprueba1 = (TextView) findViewById(R.id.tvprueba1);
        btnEliminar = (Button) findViewById(R.id.btnEliminar);

        objeto = intent.getStringExtra("objetoData");
        String[] parts = objeto.split("           ");
        final String bicicleta = parts[0];
        final String dia = parts[1];
        final String nick = parts[2];
      //  String sesionNick = parts[2];



        edDia.setText(dia);
        final String bici = bicicleta.replaceAll(" ", "");
        edBicicleta.setText(bici);
        ///String nick = usuario.replaceAll(" ", "");
        edUsuario.setText(nick);

        EnviarRecibirIdPersona("http://jose-cordones.es/app/consultas/obtenerIdAlquilerBici.php?nick=" + nick+"&bici="+bici+"&dia="+dia);
        //Toast.makeText(Main_EliminarBicicleta_Activity.this,    dia + bici + nick, Toast.LENGTH_SHORT).show();

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bici1 = bici.replaceAll("\n", "");
                tvprueba.setText(dia);
                String Reserva01 = "Reserva01";
                String Reserva02 = "Reserva02";
                String Reserva03 = "Reserva03";
                String Reserva04 = "Reserva04";
                String Reserva05 = "Reserva05";
                String Reserva06 = "Reserva06";
                String Reserva07 = "Reserva07";
                String Reserva08 = "Reserva08";
                String Reserva09 = "Reserva09";
                String Reserva10 = "Reserva10";
                // tvprueba1.setText(Reserva01);
                if(bici1.equals(Reserva01) || bici1.equals(Reserva02) || bici1.equals(Reserva03) || bici1.equals(Reserva04) || bici1.equals(Reserva05) || bici1.equals(Reserva06) || bici1.equals(Reserva07) || bici1.equals(Reserva08) || bici1.equals(Reserva09) || bici1.equals(Reserva10) ){
                    Toast.makeText(getApplicationContext(), "LAS RESERVAS NO SE PUEDEN ELIMINAR", Toast.LENGTH_SHORT).show();
                }else {
                    ContarResrvasAlquiladas("http://jose-cordones.es/app/consultas/contarRegistrosBicis.php?dia=" + dia);
                }


            }
        });
    }

    public void ContarResrvasAlquiladas(String URL) {

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replace("][", ",");
                response = response.replaceAll("\n", "");
                response = response.replaceAll("\"", "");
                //response = response.replaceAll("[ ]", "");
                response = response.replaceAll("\\[", "").replaceAll("\\]","");

                tvprueba1.setText(response);
                String id_alquiler_bicicletas = tvIDALQUILER.getText().toString();
                String finalNick = edUsuario.getText().toString();
                // String registro6 = "6";
                if(response.equals("1") || response.equals("2") || response.equals("3") || response.equals("4") || response.equals("5") || response.equals("6") || response.equals("7") || response.equals("8") || response.equals("9") || response.equals("10") || response.equals("11") || response.equals("12") || response.equals("13") || response.equals("14") || response.equals("15")  ){
                    // Toast.makeText(getApplicationContext(), "pasa", Toast.LENGTH_SHORT).show();
                    EliminarBici("http://jose-cordones.es/app/eliminaciones/eliminarBici.php?id_alquiler_bicicletas=" + id_alquiler_bicicletas);
                    Intent intent = new Intent(getApplicationContext(), Main_GestionBicicletas_Activity.class);
                    intent.putExtra("usuario", finalNick);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "BICICLETA ELIMINADA", Toast.LENGTH_SHORT).show();
                }else{
                    String dia = edDia.getText().toString();
                    ResrvasPositivas("http://jose-cordones.es/app/consultas/obtenerReservasAlquiladas.php?dia=" + dia);
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

    }


    public void ResrvasPositivas(String URL) {

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replace("][", ",");
                response = response.replaceAll("\n", "");
                response = response.replaceAll("\"", "");
                //response = response.replaceAll("[ ]", "");
                response = response.replaceAll("\\[", "").replaceAll("\\]","");

                String numReserva = "Reserva0"+response;
                String dia = edDia.getText().toString();
                // Toast.makeText(Main_EliminarBicicleta_Activity.this,    numReserva, Toast.LENGTH_SHORT).show();
                NickReserva("http://jose-cordones.es/app/consultas/obtenerNickReserva.php?dia=" + dia +"&bici="+ numReserva);

                IdReserva("http://jose-cordones.es/app/consultas/obtenerIdReserva.php?dia=" + dia +"&bici="+ numReserva);





            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

    }


    public void IdReserva(String URL){

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replace("][", ",");
                response = response.replaceAll("\n", "");
                response = response.replaceAll("\"", "");
                response = response.replaceAll("\\[", "").replaceAll("\\]","");
                ActualizarReserva("http://jose-cordones.es/app/actualizaciones/actualizarReserva.php?id_alquiler_bicicletas=" + response );
                if(response.equals("")){
                    //    Toast.makeText(Main_EliminarBicicleta_Activity.this,    "biiiiiiiiien", Toast.LENGTH_SHORT).show();
                    String finalNick = edUsuario.getText().toString();
                    String id_alquiler_bicicletas = tvIDALQUILER.getText().toString();
                    EliminarBici("http://jose-cordones.es/app/eliminaciones/eliminarBici.php?id_alquiler_bicicletas=" + id_alquiler_bicicletas);
                    Intent intent = new Intent(getApplicationContext(), Main_GestionBicicletas_Activity.class);
                    intent.putExtra("usuario", finalNick);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "BICICLETA ELIMINADA", Toast.LENGTH_SHORT).show();
                }




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

    }



    public void ActualizarReserva(String URL){


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

    public void NickReserva(String URL){

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replace("][",",");

                if (response.length()>0){
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson",""+ja.length());
                        CargarListViewNickReserva(ja);
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


    public void CargarListViewNickReserva(JSONArray ja){

        ArrayList<String> lista = new ArrayList<>();
        String nick = null;
        String telefono = null;

        for(int i=0;i<ja.length();i+=1){

            try {

                nick = ("\n"+ja.getString(i+-1));
                telefono = ("\n"+ja.getString(i+0));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        String id_alquiler_bicicletas = tvIDALQUILER.getText().toString();
        nick = nick.replaceAll("\n", "");
        id_alquiler_bicicletas = id_alquiler_bicicletas.replaceAll("\n", "");
        String finalNick = edUsuario.getText().toString();
        ActualizarBiciEliminada("http://jose-cordones.es/app/actualizaciones/actualizarBici.php?nick=" + nick +"&id_alquiler_bicicletas="+ id_alquiler_bicicletas +"&telefono="+ telefono);
        //Toast.makeText(Main_EliminarBicicleta_Activity.this,    nick + " "+id_alquiler_bicicletas, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), Main_GestionBicicletas_Activity.class);
        intent.putExtra("usuario", finalNick);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), "BICICLETA ELIMINADA", Toast.LENGTH_SHORT).show();




    }

    public void ActualizarBiciEliminada(String URL){


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


    public void EliminarBici(String URL){


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
        for (int i = 0; i < ja.length(); i += 1) {

            try {

                id = ("\n" + ja.getString(i + -1));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        tvIDALQUILER.setText(id);


    }
}
