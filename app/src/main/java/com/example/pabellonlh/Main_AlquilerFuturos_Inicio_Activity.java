package com.example.pabellonlh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class Main_AlquilerFuturos_Inicio_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__alquiler_futuros__inicio_);
    }

    public void Bicicletas (View view){
        Intent login = new Intent(this, Main_DiasBicis_Inicio_Activity.class);
        startActivity(login);
    }

    public void Pistas (View view){
        Intent login = new Intent(this, Main_Pistas_Inicio_Activity.class);
        startActivity(login);
    }

    public void Zumba (View view){
        Intent login = new Intent(this, Main_Zumba_Inicio_Activity.class);
        startActivity(login);
    }

    public void CloFit (View view){
        Intent login = new Intent(this, Main_CrossFit_Inicio_Activity.class);
        startActivity(login);
    }

    public void Pilates (View view){
        Intent login = new Intent(this, Main_Pilates_Inicio_Activity.class);
        startActivity(login);
    }
}
