package com.example.pabellonlh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main_AlquilerAdministrador_Activity extends AppCompatActivity {

    Button btnPistas, btnBicicletas, btnZumba, btnCloFit, btnPilates;
    String usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__alquiler_administrador_);

        Intent intent = getIntent();
        usuario = intent.getStringExtra("usuario");
        intent.putExtra("usuario", usuario);


        btnPistas=findViewById(R.id.btnPistas);
        btnBicicletas=findViewById(R.id.btnBicicletas);
        btnZumba=findViewById(R.id.btnZumba);
        btnCloFit=findViewById(R.id.btnCloFit);
        btnPilates=findViewById(R.id.btnPilates);



        btnPistas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Main_MomentoPistas_Activity.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
            }
        });

        btnBicicletas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Main_GestionBicicletas_Activity.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
            }
        });

        btnZumba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Main_GestionZumba_Activity.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
            }
        });

        btnCloFit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Main_GestionCrossFit_Activity.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
            }
        });

        btnPilates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Main_GestionPilates_Activity.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
            }
        });

    }
}
