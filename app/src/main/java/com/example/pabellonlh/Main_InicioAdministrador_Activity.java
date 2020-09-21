package com.example.pabellonlh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Main_InicioAdministrador_Activity extends AppCompatActivity {

    Button btnCerrarSesion, btnBorrarCrear, btnUsuarios, btnAdministradores, btnListados, btnEditar;
    TextView tvNombre;
    //private AsyncHttpClient cliente;
    String usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__inicio_administrador_);

        Intent intent = getIntent();
        usuario = intent.getStringExtra("usuario");



        contarPendientes("http://jose-cordones.es/app/consultas/contarPendientes.php");

        btnCerrarSesion=findViewById(R.id.btnCerrarSesion);
        btnBorrarCrear=findViewById(R.id.btnBorrar);
        btnAdministradores=findViewById(R.id.btnAdministradores);
        btnUsuarios=findViewById(R.id.btnUsuarios);
        btnListados=findViewById(R.id.btnListados);
        btnEditar=findViewById(R.id.btnEditarHorarioM);
        tvNombre = findViewById(R.id.tvNombre1);

        if(usuario == null){
            recuperarPreferendiasAdmin();
           //Toast.makeText(getApplicationContext(), usuario, Toast.LENGTH_SHORT).show();
        }

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("preferenciasLoginAdmin", Context.MODE_PRIVATE);
                preferences.edit().clear().commit();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
                finish();
            }
        });

        btnUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Main_Usuarios_Activity.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
            }
        });

        btnAdministradores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Main_Administradores_Activity.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
            }
        });

        btnBorrarCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),Main_Borrar_Crear_Activity.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);

            }
        });
        btnListados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),Main_AlquilerAdministrador_Activity.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);

            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Main_Ediciones_Activity.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
            }
        });
    }

    public void contarPendientes(String URL) {

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replace("][", ",");
                response = response.replaceAll("\n", "");
                response = response.replaceAll("\"", "");
                response = response.replaceAll("\\[", "").replaceAll("\\]","");


                if(!response.equals("0")  ){
                    Toast.makeText(getApplicationContext(), "ACTIVACIONES DE USUARIOS PENDIENTES", Toast.LENGTH_SHORT).show();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

    }


    private void recuperarPreferendiasAdmin(){
        SharedPreferences preferences = getSharedPreferences("preferenciasLoginAdmin", Context.MODE_PRIVATE);
        tvNombre.setText(preferences.getString("usuario", ""));

        usuario = tvNombre.getText().toString();

    }

    public void IrInicio (View view){
        Intent Inicio = new Intent(this, MainActivity.class);
        startActivity(Inicio);
        finish();
    }
}
