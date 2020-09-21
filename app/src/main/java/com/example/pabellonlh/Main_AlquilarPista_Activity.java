package com.example.pabellonlh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main_AlquilarPista_Activity extends AppCompatActivity {

    Button btnBicicletas, btnPistas, btnActividades;
    String usuario;
    TextView tvPrueba;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__alquilar_pista_);
        tvPrueba = findViewById(R.id.tvPrueba);
        Intent intent = getIntent();
        usuario = intent.getStringExtra("usuario");
        tvPrueba.setText(usuario);
        btnBicicletas=findViewById(R.id.btnBiclicletas);
        btnPistas=findViewById(R.id.btnPistas);
        btnActividades=findViewById(R.id.btnActividades);



        btnBicicletas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Main_DiasBicicletas_Activity.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
                //   finish();

            }


        });

        btnPistas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Main_PistasAlquiler_Activity.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
                //   finish();

            }


        });

        btnActividades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Main_AlquilarActividades_Activity.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
                //   finish();

            }


        });

    }


}
