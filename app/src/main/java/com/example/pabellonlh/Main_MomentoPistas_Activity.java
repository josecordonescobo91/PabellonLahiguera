package com.example.pabellonlh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main_MomentoPistas_Activity extends AppCompatActivity {

    Button btnPasadas, btnFuturas, btnHoy;
    String usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__momento_pistas_);

        btnPasadas=findViewById(R.id.btnPasadas);
        btnFuturas=findViewById(R.id.btnFuturas);
        btnHoy=findViewById(R.id.btnHoy);

        Intent intent = getIntent();
        usuario = intent.getStringExtra("usuario");
        intent.putExtra("usuario", usuario);

        btnPasadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Main_GestionPistasPasadas_Activity.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
            }
        });

        btnFuturas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Main_GestionPistasFuturas_Activity.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
            }
        });

        btnHoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Main_GestionPistasHoy_Activity.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
            }
        });
    }
}
