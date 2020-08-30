package com.example.pabellonlh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main_DiasBicicletas_Activity extends AppCompatActivity {

    Button btnLunes;
    Button btnMiercoles;
    Button btnViernes;
   // TextView tvPrueba;
    String Lunes = "Lunes", Miercoles = "Miercoles", Viernes = "Viernes";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__dias_bicicletas_);
        Intent intent = getIntent();
        String usuario = intent.getStringExtra("usuario");
      //  tvPrueba = findViewById(R.id.tvPrueba);
     //   tvPrueba.setText(usuario);
       // Intent intent = getIntent();
       // String usuario = intent.getStringExtra("usuario");
    }


    public void Lunes (View view){
        Intent intent = getIntent();
        String usuario = intent.getStringExtra("usuario");
        Intent lunes = new Intent(this, Main_AlquilarBicicleta_Activity.class);
        lunes.putExtra("dia", Lunes);
        lunes.putExtra("usuario", usuario);
        startActivity(lunes);
    }

    public void Miercoles (View view){
        Intent intent = getIntent();
        String usuario = intent.getStringExtra("usuario");
        Intent miercoles = new Intent(this, Main_AlquilarBicicleta_Activity.class);
        miercoles.putExtra("dia", Miercoles);
        miercoles.putExtra("usuario", usuario);
        startActivity(miercoles);
    }

    public void Viernes (View view){
        Intent intent = getIntent();
        String usuario = intent.getStringExtra("usuario");
        Intent viernes = new Intent(this, Main_AlquilarBicicleta_Activity.class);
        viernes.putExtra("dia", Viernes);
        viernes.putExtra("usuario", usuario);
        startActivity(viernes);
    }
}
