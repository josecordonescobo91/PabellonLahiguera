package com.example.pabellonlh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Main_RegistrarUsuario_Activity extends AppCompatActivity {

    EditText nick, pass, nombre, apellido1, apellido2, telefono, email, direccion, localidad, provincia;
    Button btAgregar;
    String usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__registrar_usuario_);

        nick = (EditText) findViewById(R.id.nick);
        pass = (EditText) findViewById(R.id.pass);
        nombre = (EditText) findViewById(R.id.nombre);
        apellido1 = (EditText) findViewById(R.id.apellido1);
        apellido2 = (EditText) findViewById(R.id.apellido2);
        telefono = (EditText) findViewById(R.id.telefono);
        email = (EditText) findViewById(R.id.email);
        direccion = (EditText) findViewById(R.id.direccion);
        localidad = (EditText) findViewById(R.id.localidad);
        provincia = (EditText) findViewById(R.id.provincia);
        btAgregar = (Button) findViewById(R.id.btAgregar);

        btAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario = nick.getText().toString();
                if(!usuario.isEmpty() ){
                    if (!validarEmail(email.getText().toString())){
                        Toast.makeText(Main_RegistrarUsuario_Activity.this, "EL CORREO NO ES CORRECTO", Toast.LENGTH_SHORT).show();
                        email.setText("");
                    }else{
                        validarUsuario("http://jose-cordones.es/app/consultas/comprobar_usuario_registro.php");
                    }

                }else{
                    Toast.makeText(Main_RegistrarUsuario_Activity.this, "No se permiten nick sin rellenar", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private void validarUsuario (String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    Toast.makeText(Main_RegistrarUsuario_Activity.this, "EL USUARIO YA EXISTE", Toast.LENGTH_SHORT).show();
                    nick.setText("");
                }else{
                    ejecutarServicio("http://jose-cordones.es/app/registrar_usuario.php");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Main_RegistrarUsuario_Activity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String, String>();
                parametros.put("nick", usuario);

                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void ejecutarServicio(String URL1){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL1, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "REGISTRO CORRECTO", Toast.LENGTH_SHORT).show();
            }
        },new Response.ErrorListener(){
            @Override
            public  void  onErrorResponse(VolleyError error){
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>parametros = new HashMap<String, String>();
                parametros.put("nick", nick.getText().toString());
                parametros.put("pass", pass.getText().toString());
                parametros.put("nombre", nombre.getText().toString());
                parametros.put("apellido1", apellido1.getText().toString());
                parametros.put("apellido2", apellido2.getText().toString());
                parametros.put("telefono", telefono.getText().toString());
                parametros.put("email", email.getText().toString());
                parametros.put("direccion", direccion.getText().toString());
                parametros.put("localidad", localidad.getText().toString());
                parametros.put("provincia", provincia.getText().toString());

                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }



}
