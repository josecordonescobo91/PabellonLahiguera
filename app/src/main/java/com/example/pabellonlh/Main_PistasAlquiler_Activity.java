package com.example.pabellonlh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Main_PistasAlquiler_Activity extends AppCompatActivity {
    String usuario;
  //  TextView tvPrueba;
    String Futbol7 = "futbol 7", FutbolSala = "futbol sala", Padel = "padel", Tennis = "tennis";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__pistas_alquiler);
       // tvPrueba = findViewById(R.id.tvPrueba);
        Intent intent = getIntent();
        usuario = intent.getStringExtra("usuario");
       // tvPrueba.setText(usuario);
    }

    public void Futbol7 (View view){
       // Intent intent = getIntent();

        Intent intent = new Intent(this, Main_PistasLibres_Activity.class);
        //String usuario = intent.getStringExtra("usuario");
        intent.putExtra("pista", Futbol7);
        intent.putExtra("usuario", usuario);
       // startActivity();
        startActivity(intent);
    }

    public void FutbolSala (View view){
        // Intent intent = getIntent();

        Intent intent = new Intent(this, Main_PistasLibres_Activity.class);
        //String usuario = intent.getStringExtra("usuario");
        intent.putExtra("pista", FutbolSala);
        intent.putExtra("usuario", usuario);
        // startActivity();
        startActivity(intent);
    }

    public void Tennis (View view){
        // Intent intent = getIntent();

        Intent intent = new Intent(this, Main_PistasLibres_Activity.class);
        //String usuario = intent.getStringExtra("usuario");
        intent.putExtra("pista", Tennis);
        intent.putExtra("usuario", usuario);
        // startActivity();
        startActivity(intent);
    }

    public void Padel (View view){
        // Intent intent = getIntent();

        Intent intent = new Intent(this, Main_PistasLibres_Activity.class);
        //String usuario = intent.getStringExtra("usuario");
        intent.putExtra("pista", Padel);
        intent.putExtra("usuario", usuario);
        // startActivity();
        startActivity(intent);
    }
}
