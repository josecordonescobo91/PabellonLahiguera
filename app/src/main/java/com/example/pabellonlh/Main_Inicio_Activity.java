package com.example.pabellonlh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.AsyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Main_Inicio_Activity extends AppCompatActivity {

    Button btnCerrarSesion, btnMisPistas;
    TextView tvNombre;
    //private AsyncHttpClient cliente;
    String usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__inicio_);


        Intent intent = getIntent();
        usuario = intent.getStringExtra("usuario");


        btnCerrarSesion=findViewById(R.id.btnCerrarSesion);
        btnMisPistas=findViewById(R.id.btnMisPistas);

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
                preferences.edit().clear().commit();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
                finish();
            }
        });

        btnMisPistas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Main_MisPistas_Activity.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
             //   finish();

            }


        });



    }

    public void AlquilarPista (View view){
        Intent Alquilar = new Intent(this, Main_AlquilarPista_Activity.class);
        Alquilar.putExtra("usuario", usuario);
        startActivity(Alquilar);
    }

    public void IrInicio (View view){
        Intent Inicio = new Intent(this, MainActivity.class);
        startActivity(Inicio);
    }
}
