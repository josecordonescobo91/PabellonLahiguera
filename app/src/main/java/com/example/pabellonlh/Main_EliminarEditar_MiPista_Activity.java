package com.example.pabellonlh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class Main_EliminarEditar_MiPista_Activity extends AppCompatActivity {
    //ArrayList<String> lista = new String[];
   // ArrayList<String> lista = new ArrayList<String>();
    private Adapter item;
    String idPista;
    EditText edFecha, edHora, edPista;
    Button btEliminar, btEditar;
    Spinner snMaterial;
    TextView tvSesion, spMate;

     //intent.putExtra("usuario", usuario);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__eliminar_editar__mi_pista_);

        Intent intent = getIntent();

        edFecha = (EditText) findViewById(R.id.edFecha);
        edHora = (EditText) findViewById(R.id.edHora);
        edPista = (EditText) findViewById(R.id.edNombre);
        snMaterial = (Spinner) findViewById(R.id.spDevuelo);
        spMate = (TextView) findViewById(R.id.spMate);
        btEliminar = (Button) findViewById(R.id.btEliminar);
        btEditar = (Button) findViewById(R.id.btEditar);
        idPista = intent.getStringExtra("objetoData");
        String[] parts = idPista.split("        ");
        String fecha = parts[0];
        String hora = parts[1];
        String pista = parts[3];
       // String sesionNick = parts[4];

        edFecha.setText(fecha);
        edHora.setText(hora);
        edPista.setText(pista);



        String nick = intent.getStringExtra("nick");




        EnviarRecibirDatos("http://jose-cordones.es/app/consultas/obtener_MiPista_Seleccionada.php?fecha="+fecha+"&horas="+hora+"&pista="+pista);


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
        String id = null;
        String nick = null;
        String material = null;
        String idInformacion = null;
        String idPersona = null;
        String pagado = null;
        String devuelto = null;
        for(int i=0;i<ja.length();i+=6){

            try {

                id = ("\n"+ja.getString(i+-6));
                material = ("\n"+ja.getString(i+-5));
                nick = ("\n"+ja.getString(i+-4));
                idInformacion = ("\n"+ja.getString(i+-3));
                idPersona = ("\n"+ja.getString(i+-2));
                pagado = ("\n"+ja.getString(i+-1));
                devuelto = ("\n"+ja.getString(i+0));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        Intent intent = getIntent();

        String [] SpinnerMaterial = {material, "si", "no"};
        spMate.setText(material);

        final ArrayAdapter <String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, SpinnerMaterial);
        snMaterial.setAdapter(adapter);
        snMaterial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object item = parent.getItemAtPosition(pos);

               final String valorSpiner = snMaterial.getSelectedItem().toString();
               // spMate.setText("");
                spMate.setText(valorSpiner);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });




        final String finalId = id;
        final String finalNick = nick;
        btEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                EliminarPista("http://jose-cordones.es/app/eliminaciones/eliminarAlquiler.php?idalquiler="+ finalId);
                Intent intent = new Intent(getApplicationContext(), Main_MisAlquiler_Activity.class);
                intent.putExtra("usuario", finalNick);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "PISTA ELIMINADA", Toast.LENGTH_SHORT).show();

            }
        });

        final String finalIdInformacion = idInformacion;
        final String finalIdPersona = idPersona;
        final String finalPagado = pagado;
        final String finalDevuelto = devuelto;
        btEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                snMaterial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        Object item = parent.getItemAtPosition(pos);

                        String valorSpiner = snMaterial.getSelectedItem().toString();
                        spMate.setText(valorSpiner);

                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

                String valorSpi = spMate.getText().toString();
                ActualizarPistas("http://jose-cordones.es/app/actualizaciones/actualizarMiPista.php?material="+ valorSpi+"&id_info_posible_reserva="+ finalIdInformacion+"&idalquiler="+ finalId+"&idpersona="+ finalIdPersona+"&pagado="+ finalPagado+"&devuelto="+ finalDevuelto);
                Intent intent = new Intent(getApplicationContext(), Main_MisAlquiler_Activity.class);
                intent.putExtra("usuario", finalNick);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "PISTA EDITADA", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void EliminarPista(String URL){


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

    public void ActualizarPistas(String URL){


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

}
