package com.example.pabellonlh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Main_CargandoUsuario_Activity extends AppCompatActivity {

    ProgressBar progressBar;
    //TextView tvPrueba;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__cargando_usuario_);
        progressBar=findViewById(R.id.progressBar);

        progressBar.setVisibility(View.VISIBLE);
      //  tvPrueba = findViewById(R.id.tvPrueba);
       // Intent intent = getIntent();
     //   String usuario = intent.getStringExtra("usuario");
      //  tvPrueba.setText(usuario);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences = getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
                boolean sesion = preferences.getBoolean("sesion", false);
                if(sesion){
                    Intent intent = new Intent(getApplicationContext(), Main_Inicio_Activity.class);
                 //   String usuario= intent.getStringExtra("usuario");
                //    intent.putExtra("usuario", usuario);
                 //   tvPrueba.setText("BIIIIIIEEEEEEEN"+usuario);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 2000);
    }
}
