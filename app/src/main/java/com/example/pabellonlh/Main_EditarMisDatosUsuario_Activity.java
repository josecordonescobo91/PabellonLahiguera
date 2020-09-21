package com.example.pabellonlh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
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
import java.util.regex.Pattern;

public class Main_EditarMisDatosUsuario_Activity extends AppCompatActivity {

    String usuario;
    EditText edTelefono, edEmail, edDireccion, edLocalidad, edProvincia;
    Button btnEditarMD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__editar_mis_datos_usuario_);


        Intent intent = getIntent();
        usuario = intent.getStringExtra("usuario");
        intent.putExtra("usuario", usuario);

      //  Toast.makeText(Main_EditarMisDatosUsuario_Activity.this, usuario, Toast.LENGTH_SHORT).show();

        //MIS DATOS
        edTelefono = (EditText) findViewById(R.id.edTelefono);
        edEmail = (EditText) findViewById(R.id.edEmail);
        edDireccion = (EditText) findViewById(R.id.edDireccion);
        edLocalidad = (EditText) findViewById(R.id.edLocalidad);
        edProvincia = (EditText) findViewById(R.id.edProvincia);
       // tvSeparadorMD = (TextView) findViewById(R.id.tvSeparadorMD);
        btnEditarMD = (Button) findViewById(R.id.btnEditarMD);

        String MisDatos = "http://jose-cordones.es/app/consultas/obtenerPersonaParaEditar.php?nick="+ usuario;
        obtenerMisDatos(MisDatos);


        //BOTON MIS DATOS
        btnEditarMD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String telefono = edTelefono.getText().toString(),
                        email = edEmail.getText().toString(),
                        provincia = edProvincia.getText().toString(),
                        localidad = edLocalidad.getText().toString(),
                        direccion = edDireccion.getText().toString();

                if (!validarEmail(edEmail.getText().toString())){
                    Toast.makeText(Main_EditarMisDatosUsuario_Activity.this, "EL CORREO NO ES CORRECTO", Toast.LENGTH_SHORT).show();
                    edEmail.setText("");
                }else{
                    ActualizarMisDatos("http://jose-cordones.es/app/actualizaciones/actualizarMisDatos.php?email="+ email+"&telefono="+ telefono+"&direccion="+ direccion+"&localidad="+ localidad+"&provincia="+ provincia+"&nick="+ usuario);
                    Toast.makeText(getApplicationContext(), "DATOS EDITADOS CORRECTAMENTE " ,  Toast.LENGTH_SHORT).show();
                }



            }
        });
    }


    //EDITAR MIS DATOS
    public void ActualizarMisDatos(String URL){


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

    //OBTENER MIS DATOS
    public void obtenerMisDatos(String URL){

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
                        CargarListViewMisDatos(ja);
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

    public void CargarListViewMisDatos(JSONArray ja){

        ArrayList<String> lista = new ArrayList<>();

        String telefono = null, email = null, direccion = null, localidad = null, provincia = null ;
        for(int i=0;i<ja.length();i+=5){

            try {

                telefono = ja.getString(i+1);
                email = ja.getString(i+0);
                direccion = ja.getString(i+2);
                localidad = ja.getString(i+3);
                provincia = ja.getString(i+4);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        edTelefono.setText(telefono);
        edEmail.setText(email);
        edDireccion.setText(direccion);
        edLocalidad.setText(localidad);
        edProvincia.setText(provincia);



    }

    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
}
