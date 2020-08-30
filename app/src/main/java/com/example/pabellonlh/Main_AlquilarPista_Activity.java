package com.example.pabellonlh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main_AlquilarPista_Activity extends AppCompatActivity {

    Button btnBicicletas;
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


        btnBicicletas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Main_DiasBicicletas_Activity.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
                //   finish();

            }


        });
    }


}
