package com.example.pabellonlh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class Main_EditarEliminarPistasFuturas_Activity extends AppCompatActivity {

    String usuario;
    String objeto;
    EditText edUsuario, edPista, edDia, edHora;
    Spinner spMaterial, spPagado;
    Button btnEliminar, btnEditar;
    TextView snPaga, snMate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__editar_eliminar_pistas_futuras_);

        Intent intent = getIntent();
        usuario = intent.getStringExtra("usuario");
        intent.putExtra("usuario", usuario);

        edUsuario = (EditText) findViewById(R.id.edNick);
        edPista = (EditText) findViewById(R.id.edNombre);
        edDia = (EditText) findViewById(R.id.edFecha);
        edHora = (EditText) findViewById(R.id.edHora);

        btnEliminar = (Button) findViewById(R.id.btnEliminar);
        btnEditar = (Button) findViewById(R.id.btnEditarHorarioM);

        spMaterial = (Spinner) findViewById(R.id.spMaterial);
        snPaga = (TextView) findViewById(R.id.snPaga);

        spPagado = (Spinner) findViewById(R.id.spPagado);
        snMate = (TextView) findViewById(R.id.snMate);

        //Intent intent = getIntent();
        objeto = intent.getStringExtra("objetoData");
        String[] parts = objeto.split("          ");
        String pista = parts[3];
        String dia = parts[0];
        String hora = parts[1];
        String usuario = parts[2];


        usuario = usuario.replace("][", ",");
        usuario = usuario.replaceAll("\n", "");
        usuario = usuario.replaceAll("\"", "");
        usuario = usuario.replaceAll("\\[", "").replaceAll("\\]","");


        dia = dia.replace("][", ",");
        dia = dia.replaceAll("\n", "");
        dia = dia.replaceAll("\"", "");
        dia = dia.replaceAll("\\[", "").replaceAll("\\]","");

        hora = hora.replace("][", ",");
        hora = hora.replaceAll("\n", "");
        hora = hora.replaceAll("\"", "");
        hora = hora.replaceAll("\\[", "").replaceAll("\\]","");

        pista = pista.replace("][", ",");
        pista = pista.replaceAll("\n", "");
        pista = pista.replaceAll("\"", "");
        pista = pista.replaceAll("\\[", "").replaceAll("\\]","");

        edUsuario.setText(usuario);
        edPista.setText(pista);
        edDia.setText(dia);
        edHora.setText(hora);

        obtenerPista("http://jose-cordones.es/app/consultas/obtener_MiPista_Seleccionada.php?fecha="+dia+"&horas="+hora+"&pista="+pista);
    }


    public void obtenerPista(String URL){

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
       // String idInformacion = null;
       // String idPersona = null;
        String pagado = null;
       // String devuelto = null;
        for(int i=0;i<ja.length();i+=6){

            try {

                id = ("\n"+ja.getString(i+-6));
                material = ("\n"+ja.getString(i+-5));
                nick = ("\n"+ja.getString(i+-4));
                //  idInformacion = ("\n"+ja.getString(i+-3));
                //  idPersona = ("\n"+ja.getString(i+-2));
                pagado = ("\n"+ja.getString(i+-1));
                //devuelto = ("\n"+ja.getString(i+0));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        Intent intent = getIntent();

        //pagado---------------------------------------------------------------------------------------------------------------------------------

        String [] SpinnerPagado = {pagado, "si", "no"};
        snPaga.setText(pagado);

        final ArrayAdapter <String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, SpinnerPagado);
        spPagado.setAdapter(adapter);
        spPagado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object item = parent.getItemAtPosition(pos);

                final String valorSpiner = spPagado.getSelectedItem().toString();
                // spMate.setText("");
                snPaga.setText(valorSpiner);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        //devuelto-------------------------------------------------------------------------------------------------------------------------------------
        String [] SpinnerMaterial = {material, "si", "no"};
        snMate.setText(material);

        final ArrayAdapter <String> adapter1 = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, SpinnerMaterial);
        spMaterial.setAdapter(adapter1);
        spMaterial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object item = parent.getItemAtPosition(pos);

                final String valorSpiner1 = spMaterial.getSelectedItem().toString();
                // spMate.setText("");
                snMate.setText(valorSpiner1);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });





        final String finalId = id;
        final String finalNick = nick;
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                EliminarPista("http://jose-cordones.es/app/eliminaciones/eliminarAlquiler.php?idalquiler="+ finalId);
                Intent intent = new Intent(getApplicationContext(), Main_GestionPistasFuturas_Activity.class);
                //Intent intent = getIntent();
                usuario = intent.getStringExtra("usuario");
                intent.putExtra("usuario", usuario);
                startActivity(intent);
                finish();
                Toast.makeText(getApplicationContext(), "PISTA ELIMINADA", Toast.LENGTH_SHORT).show();

            }
        });



        final String finalPagado = pagado;
        final String finalMaterial = material;
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                spMaterial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        Object item = parent.getItemAtPosition(pos);

                        String valorSpinerDevu = spMaterial.getSelectedItem().toString();
                        snMate.setText(valorSpinerDevu);

                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

                spPagado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        Object item1 = parent.getItemAtPosition(pos);

                        String valorSpinerPaga = spPagado.getSelectedItem().toString();
                        snPaga.setText(valorSpinerPaga);

                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

                String valorSpiMate = snMate.getText().toString();
                String valorSpiPaga = snPaga.getText().toString();
                ActualizarPistasPasadas("http://jose-cordones.es/app/actualizaciones/actualizarPistaFuturas.php?pagado="+ valorSpiPaga+"&idalquiler="+ finalId+"&material="+ valorSpiMate);
                Intent intent = new Intent(getApplicationContext(), Main_GestionPistasFuturas_Activity.class);
                // Intent intent = getIntent();
                usuario = intent.getStringExtra("usuario");
                intent.putExtra("usuario", usuario);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "PISTA EDITADA", Toast.LENGTH_SHORT).show();

            }
        });

    }
    public void ActualizarPistasPasadas(String URL){


       // RequestQueue queue = Volley.newRequestQueue(this);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

       // Toast.makeText(getApplicationContext(), "PISTA EDITADA" +stringRequest, Toast.LENGTH_SHORT).show();
        queue.add(stringRequest);

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
}
