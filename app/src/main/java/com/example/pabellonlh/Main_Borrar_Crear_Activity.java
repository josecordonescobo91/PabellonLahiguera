package com.example.pabellonlh;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Calendar;

public class Main_Borrar_Crear_Activity extends AppCompatActivity implements View.OnClickListener {


    Button btnFecha, btnEliminarBicis, btnEliminarPistas, btnFecha1, btnEliminarLunes, btnEliminarMartes, btnEliminarTodos, btnEliminarJueves, btnEliminarViernes;
    Button btnEliminarMartesClofit, btnEliminarJuevesClofit, btnEliminarTodosClofit, btnEliminarLunesPilates, btnEliminarJuevesPilates, btnEliminarTodosPilates;
    EditText edFecha, edFecha1;
    ImageButton imgFecha, imgFecha1;
    String usuario;
   // private int dia, mes, ano;
    private static final String CERO = "0";
    private static final String DOS_PUNTOS = ":";
    private static final String BARRA = "-";
    final Calendar c = Calendar.getInstance();
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);
    String zumba = "ZUMBA", clofit = "CROSSFIT", pilates="PILATES";
    String diaLunes = "Lunes", diaMartes = "Martes", diaMiercoles = "Miercoles", diaJueves = "Jueves", diaViernes = "Viernes";
    TabHost TbH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__borrar__crear_);

        Intent intent = getIntent();
        usuario = intent.getStringExtra("usuario");
       // Toast.makeText(getApplicationContext(), usuario, Toast.LENGTH_SHORT).show();

        TbH = (TabHost) findViewById(R.id.tabHost); //llamamos al Tabhost
        TbH.setup();                                                         //lo activamos

        TabHost.TabSpec tab1 = TbH.newTabSpec("tab1");  //aspectos de cada Tab (pestaña)
        TabHost.TabSpec tab2 = TbH.newTabSpec("tab2");
        TabHost.TabSpec tab3 = TbH.newTabSpec("tab3");
        TabHost.TabSpec tab4 = TbH.newTabSpec("tab4");
        TabHost.TabSpec tab5 = TbH.newTabSpec("tab5");

        tab1.setIndicator("PISTAS");    //qué queremos que aparezca en las pestañas
        tab1.setContent(R.id.PISTA); //definimos el id de cada Tab (pestaña)


        tab2.setIndicator("BICIS");
        tab2.setContent(R.id.BICI);

        tab3.setIndicator("ZUMBA");
        tab3.setContent(R.id.ZUMBA);

        tab4.setIndicator("CROSSFIT");
        tab4.setContent(R.id.CLOFIT);

        tab5.setIndicator("PILATES");
        tab5.setContent(R.id.PILATE);


        TbH.addTab(tab1); //añadimos los tabs ya programados
        TbH.addTab(tab2);
        TbH.addTab(tab3);
        TbH.addTab(tab4);
        TbH.addTab(tab5);

        TabWidget tabs = (TabWidget)TbH.findViewById(android.R.id.tabs);
        View tabView1 = tabs.getChildTabViewAt(0);
        View tabView2 = tabs.getChildTabViewAt(1);
        View tabView3= tabs.getChildTabViewAt(2);
        View tabView4= tabs.getChildTabViewAt(3);
        View tabView5 = tabs.getChildTabViewAt(4);
        TextView tv1 = (TextView)tabView1.findViewById(android.R.id.title);
        TextView tv2 = (TextView)tabView2.findViewById(android.R.id.title);
        TextView tv3 = (TextView)tabView3.findViewById(android.R.id.title);
        TextView tv4 = (TextView)tabView4.findViewById(android.R.id.title);
        TextView tv5 = (TextView)tabView5.findViewById(android.R.id.title);
        tv1.setTextSize(10);
        tv2.setTextSize(10);
        tv3.setTextSize(10);
        tv4.setTextSize(10);
        tv5.setTextSize(10);

        btnFecha=(Button)findViewById(R.id.btnFecha);
        btnEliminarBicis=(Button)findViewById(R.id.btnEliminarBicis);
        btnEliminarPistas=(Button)findViewById(R.id.btnEliminarPistas);
        btnEliminarLunes=(Button)findViewById(R.id.btnEliminarLunes);
        btnEliminarMartes=(Button)findViewById(R.id.btnEliminarMartes);
        btnEliminarJueves=(Button)findViewById(R.id.btnEliminarJueves);
        btnEliminarViernes=(Button)findViewById(R.id.btnEliminarViernes);
        btnEliminarTodos=(Button)findViewById(R.id.btnEliminarTodos);
        btnEliminarMartesClofit=(Button)findViewById(R.id.btnEliminarMartesClofit);
        btnEliminarJuevesClofit=(Button)findViewById(R.id.btnEliminarJuevesClofit);
        btnEliminarTodosClofit=(Button)findViewById(R.id.btnEliminarTodosClofit);
        btnEliminarLunesPilates=(Button)findViewById(R.id.btnEliminarLunesPilates);
        btnEliminarJuevesPilates=(Button)findViewById(R.id.btnEliminarJuevesPilates);
        btnEliminarTodosPilates=(Button)findViewById(R.id.btnEliminarTodosPilates);

        edFecha=(EditText) findViewById(R.id.edFecha);
        imgFecha=(ImageButton) findViewById(R.id.imgFecha);
        edFecha1=(EditText) findViewById(R.id.edFecha1);
        imgFecha1=(ImageButton) findViewById(R.id.imgFecha1);
        btnFecha1=(Button)findViewById(R.id.btnFecha1);

        imgFecha.setOnClickListener(this);
        imgFecha1.setOnClickListener(this);

        btnFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fecha  = edFecha.getText().toString();
                if(fecha.equals("")){
                    Toast.makeText(Main_Borrar_Crear_Activity.this, "NO HA SELECCIONADO UNA FECHA", Toast.LENGTH_SHORT).show();
                }else{
                    crearPistas("http://jose-cordones.es/app/registros/crearPistas.php?fecha="+fecha);
                    Toast.makeText(Main_Borrar_Crear_Activity.this, "PISTAS CREADAS CORRECTAMENTE", Toast.LENGTH_SHORT).show();
                    edFecha.setText("");
                }

            }
        });

        btnFecha1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fecha1  = edFecha1.getText().toString();
                if(fecha1.equals("")){
                    Toast.makeText(Main_Borrar_Crear_Activity.this, "NO HA SELECCIONADO UNA FECHA", Toast.LENGTH_SHORT).show();
                }else{
                    borrarPistas("http://jose-cordones.es/app/eliminaciones/eliminarPistasPorDia.php?fecha="+fecha1);
                    Toast.makeText(Main_Borrar_Crear_Activity.this, "PISTAS ELIMINADAS CORRECTAMENTE", Toast.LENGTH_SHORT).show();
                    edFecha1.setText("");
                }

            }
        });

        btnEliminarBicis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                borrarBicicletas("http://jose-cordones.es/app/eliminaciones/liberarBicicletas.php");
                Toast.makeText(Main_Borrar_Crear_Activity.this, "LAS BICICLETAS VUELVEN A ESTAR LIBRE", Toast.LENGTH_SHORT).show();
            }
        });

        btnEliminarPistas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                borrarPistasVacias("http://jose-cordones.es/app/eliminaciones/eliminarPistasVaciasPasadas.php");
                Toast.makeText(Main_Borrar_Crear_Activity.this, "LAS PISTAS VACIAS HAN SIDO ELIMINADAS CORRECTAMENTE", Toast.LENGTH_SHORT).show();
            }
        });

        btnEliminarLunes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                borrarActividad("http://jose-cordones.es/app/eliminaciones/eliminarActividadesDias.php?nombre=" + zumba +"&dia="+diaLunes);
                Toast.makeText(Main_Borrar_Crear_Activity.this, "SE HA ELINADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
            }
        });

        btnEliminarMartes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                borrarActividad("http://jose-cordones.es/app/eliminaciones/eliminarActividadesDias.php?nombre=" + zumba +"&dia="+diaMartes);
                Toast.makeText(Main_Borrar_Crear_Activity.this, "SE HA ELINADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
            }
        });
        btnEliminarJueves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                borrarActividad("http://jose-cordones.es/app/eliminaciones/eliminarActividadesDias.php?nombre=" + zumba +"&dia="+diaJueves);
                Toast.makeText(Main_Borrar_Crear_Activity.this, "SE HA ELINADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
            }
        });

        btnEliminarViernes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                borrarActividad("http://jose-cordones.es/app/eliminaciones/eliminarActividadesDias.php?nombre=" + zumba +"&dia="+diaViernes);
                Toast.makeText(Main_Borrar_Crear_Activity.this, "SE HA ELINADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
            }
        });

        btnEliminarTodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                borrarActividad("http://jose-cordones.es/app/eliminaciones/eliminarActividadesTodos.php?nombre=" + zumba);
                Toast.makeText(Main_Borrar_Crear_Activity.this, "SE HA ELINADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
            }
        });

        btnEliminarMartesClofit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                borrarActividad("http://jose-cordones.es/app/eliminaciones/eliminarActividadesDias.php?nombre=" + clofit +"&dia="+diaMartes);
                Toast.makeText(Main_Borrar_Crear_Activity.this, "SE HA ELINADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
            }
        });

        btnEliminarJuevesClofit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                borrarActividad("http://jose-cordones.es/app/eliminaciones/eliminarActividadesDias.php?nombre=" + clofit +"&dia="+diaJueves);
                Toast.makeText(Main_Borrar_Crear_Activity.this, "SE HA ELINADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
            }
        });

        btnEliminarTodosClofit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                borrarActividad("http://jose-cordones.es/app/eliminaciones/eliminarActividadesTodos.php?nombre=" + clofit);
                Toast.makeText(Main_Borrar_Crear_Activity.this, "SE HA ELINADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
            }
        });

        btnEliminarLunesPilates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                borrarActividad("http://jose-cordones.es/app/eliminaciones/eliminarActividadesDias.php?nombre=" + pilates +"&dia="+diaLunes);
                Toast.makeText(Main_Borrar_Crear_Activity.this, "SE HA ELINADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
            }
        });

        btnEliminarJuevesPilates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                borrarActividad("http://jose-cordones.es/app/eliminaciones/eliminarActividadesDias.php?nombre=" + pilates +"&dia="+diaJueves);
                Toast.makeText(Main_Borrar_Crear_Activity.this, "SE HA ELINADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
            }
        });

        btnEliminarTodosPilates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                borrarActividad("http://jose-cordones.es/app/eliminaciones/eliminarActividadesTodos.php?nombre=" + pilates);
                Toast.makeText(Main_Borrar_Crear_Activity.this, "SE HA ELINADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void borrarActividad(String URL){


        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

    }

    public void borrarPistasVacias(String URL){


        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

    }

    public void borrarBicicletas(String URL){


        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

    }

    public void borrarPistas(String URL){


        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

    }
    public void crearPistas(String URL){


        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

    }


    @Override
    public void onClick(View v) {
        if(v==imgFecha){

           DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                    final int mesActual = month + 1;

                    String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                    String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);

                    edFecha.setText( year + BARRA + mesFormateado + BARRA + diaFormateado);

                }
            }
                    ,anio, mes, dia);
            datePickerDialog.show();
        }

        if(v==imgFecha1){

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                    final int mesActual = month + 1;

                    String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                    String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);

                    edFecha1.setText( year + BARRA + mesFormateado + BARRA + diaFormateado);

                }
            }
                    ,anio, mes, dia);
            datePickerDialog.show();
        }
    }


}
