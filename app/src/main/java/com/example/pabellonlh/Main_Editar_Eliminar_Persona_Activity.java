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

public class Main_Editar_Eliminar_Persona_Activity extends AppCompatActivity {

    String usuario;
   TextView tvSpinerActivo;
    EditText edNick, edNombre, edApellido1,edApellido2, edTelefono, edEmail, edDireccion, edLocalidad, edProvincia;
    Spinner snActivo;
    String objeto;
    Button btnEditar, btnBorrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__editar__eliminar__persona_);

        tvSpinerActivo = (TextView) findViewById(R.id.tvSpinerActivo);
        edNick = (EditText) findViewById(R.id.edNick);
        edNombre = (EditText) findViewById(R.id.edNombre);
        edApellido1 = (EditText) findViewById(R.id.edFecha);
        edApellido2 = (EditText) findViewById(R.id.edHora);
        edTelefono = (EditText) findViewById(R.id.edTelefono);
        edEmail = (EditText) findViewById(R.id.edEmail);
        edDireccion = (EditText) findViewById(R.id.edDireccion);
        edLocalidad = (EditText) findViewById(R.id.edLocalidad);
        edProvincia = (EditText) findViewById(R.id.edProvincia);
        btnEditar = (Button) findViewById(R.id.btnEditarHorarioM);
        btnBorrar = (Button) findViewById(R.id.btnBorrar);
        snActivo = (Spinner) findViewById(R.id.spDevuelo);

        Intent intent = getIntent();
        usuario = intent.getStringExtra("usuario");
        intent.putExtra("usuario", usuario);
        objeto = intent.getStringExtra("objetoData");
        String[] parts = objeto.split("            ");
        String nick = parts[2];


        obtenerAdministrador("http://jose-cordones.es/app/consultas/obtenerAdministradorSeleccionado.php?nick=" + nick);


    }

    public void obtenerAdministrador(String URL) {

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replace("][", ",");

                if (response.length() > 0) {
                    try {

                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson", "" + ja.length());
                        CargarListViewAdministrador(ja);


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

    public void CargarListViewAdministrador(JSONArray ja) {

        ArrayList<String> lista = new ArrayList<>();
        String idpersona = null, nick = null, nombre = null, apellido1 = null, apellido2 = null, activo = null, telefono = null, email = null, direccion = null, localidad = null, provincia = null;
        for (int i = 0; i < ja.length(); i += 12) {

            try {

               // idpersona = (ja.getString(i + 0));
                nick = (ja.getString(i + 1));
                nombre = (ja.getString(i + 3));
                apellido1 = (ja.getString(i + 4));
                apellido2 = (ja.getString(i + 5));
                telefono = (ja.getString(i + 7));
                email = (ja.getString(i + 8));
                direccion = (ja.getString(i + 9));
                localidad = (ja.getString(i + 10));
                provincia = (ja.getString(i + 11));
                activo = (ja.getString(i + 12));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);

        String [] SpinnerMaterial = {activo, "si", "no"};
        tvSpinerActivo.setText(activo);

        final ArrayAdapter <String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, SpinnerMaterial);
        snActivo.setAdapter(adapter);
        snActivo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object item = parent.getItemAtPosition(pos);

                final String valorSpiner = snActivo.getSelectedItem().toString();
                // spMate.setText("");
                tvSpinerActivo.setText(valorSpiner);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        edNick.setText(nick);
        edNombre.setText(nombre);
        edApellido1.setText(apellido1);
        edApellido2.setText(apellido2);
        edTelefono.setText(telefono);
        edEmail.setText(email);
        edDireccion.setText(direccion);
        edLocalidad.setText(localidad);
        edProvincia.setText(provincia);

        final String finalnick = nick;

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                snActivo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        Object item = parent.getItemAtPosition(pos);

                        String valorSpiner = snActivo.getSelectedItem().toString();
                        tvSpinerActivo.setText(valorSpiner);

                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

                String valorSpi = tvSpinerActivo.getText().toString();
                ActualizarPersona("http://jose-cordones.es/app/actualizaciones/actualizarPersona.php?nick="+ finalnick+"&activo="+ valorSpi);
                Intent intent = new Intent(getApplicationContext(), Main_InicioAdministrador_Activity.class);
                intent.putExtra("usuario", usuario);
                Toast.makeText(getApplicationContext(), "EDITADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
                finish();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ActualizarPersona("http://jose-cordones.es/app/eliminaciones/eliminarPersona.php?nick="+ finalnick);
                Intent intent = new Intent(getApplicationContext(), Main_InicioAdministrador_Activity.class);
                intent.putExtra("usuario", usuario);
                Toast.makeText(getApplicationContext(), "ELIMINADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
                finish();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

    }

    public void ActualizarPersona(String URL){


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
