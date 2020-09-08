package com.example.pabellonlh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main_MisAlquiler_Activity extends AppCompatActivity {

    Button  btnMisPistas, btnMisBicicletas;
    String usuario;
   // TextView tvPrueba;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__mis_alquiler_);

        btnMisPistas=findViewById(R.id.btnMisPistas);
        btnMisBicicletas=findViewById(R.id.btnMisBicicletas);
       // tvPrueba = (TextView) findViewById(R.id.tvPrueba);
        Intent intent = getIntent();
        usuario = intent.getStringExtra("usuario");
       // tvPrueba.setText("prueba");
        //tvPrueba.setText(usuario);


        btnMisPistas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Main_MisPistas_Activity.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
                //   finish();

            }


        });

        btnMisBicicletas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Main_MisBicicletas_Activity.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
                //   finish();

            }


        });


    }
}
