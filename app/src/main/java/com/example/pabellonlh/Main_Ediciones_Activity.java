package com.example.pabellonlh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Main_Ediciones_Activity extends AppCompatActivity {

    EditText edLunesM, edMartesM, edMiercolesM, edJuevesM, edViernesM, edSabadoM, edDomingoM;
    EditText edLunesT, edMartesT, edMiercolesT, edJuevesT, edViernesT, edSabadoT, edDomingoT;
    EditText edFutbol7, edFutbolSala, edTennis, edPadel, edSpinning, edZumba, edClofit, edPilates;
    EditText edTelefono, edEmail, edDireccion, edLocalidad, edProvincia;
    TextView tvSeparador, tvSeparadorT, tvSeparadorPrecios, tvSeparadorMD;
    Button btnEditarHorarioM, btnEditarHorarioT, btnEditarPrecios, btnEditarMD;
    String usuario;
    TabHost TbH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__ediciones_);

        Intent intent = getIntent();
        usuario = intent.getStringExtra("usuario");
        intent.putExtra("usuario", usuario);



        //MIS DATOS
        edTelefono = (EditText) findViewById(R.id.edTelefono);
        edEmail = (EditText) findViewById(R.id.edEmail);
        edDireccion = (EditText) findViewById(R.id.edDireccion);
        edLocalidad = (EditText) findViewById(R.id.edLocalidad);
        edProvincia = (EditText) findViewById(R.id.edProvincia);
        tvSeparadorMD = (TextView) findViewById(R.id.tvSeparadorMD);
        btnEditarMD = (Button) findViewById(R.id.btnEditarMD);

        //PRECIOS
        edFutbol7 = (EditText) findViewById(R.id.edFutbol7);
        edFutbolSala = (EditText) findViewById(R.id.edFutbolSala);
        edTennis = (EditText) findViewById(R.id.edTennis);
        edPadel = (EditText) findViewById(R.id.edPadel);
        edSpinning = (EditText) findViewById(R.id.edSpinning);
        edZumba = (EditText) findViewById(R.id.edZumba);
        edClofit = (EditText) findViewById(R.id.edClofit);
        edPilates = (EditText) findViewById(R.id.edPilates);
        tvSeparadorPrecios = (TextView) findViewById(R.id.tvSeparadorPrecios);
        btnEditarPrecios = (Button) findViewById(R.id.btnEditarPrecios);

        //MAÑANA
        edLunesM = (EditText) findViewById(R.id.edLunesM);
        edMartesM = (EditText) findViewById(R.id.edMartesM);
        edMiercolesM = (EditText) findViewById(R.id.edMiercolesM);
        edJuevesM = (EditText) findViewById(R.id.edJuevesM);
        edViernesM = (EditText) findViewById(R.id.edViernesM);
        edSabadoM = (EditText) findViewById(R.id.edSabadoM);
        edDomingoM = (EditText) findViewById(R.id.edDomingoM);
        tvSeparador = (TextView) findViewById(R.id.tvSeparador);
        btnEditarHorarioM = (Button) findViewById(R.id.btnEditarHorarioM);

        //TARDE
        edLunesT = (EditText) findViewById(R.id.edLunesT);
        edMartesT = (EditText) findViewById(R.id.edMartesT);
        edMiercolesT = (EditText) findViewById(R.id.edMiercolesT);
        edJuevesT = (EditText) findViewById(R.id.edJuevesT);
        edViernesT = (EditText) findViewById(R.id.edViernesT);
        edSabadoT = (EditText) findViewById(R.id.edSabadoT);
        edDomingoT = (EditText) findViewById(R.id.edDomingoT);
        tvSeparadorT = (TextView) findViewById(R.id.tvSeparadorT);
        btnEditarHorarioT = (Button) findViewById(R.id.btnEditarHorarioT);

        TbH = (TabHost) findViewById(R.id.tabHost); //llamamos al Tabhost
        TbH.setup();                                                         //lo activamos

        TabHost.TabSpec tab1 = TbH.newTabSpec("tab1");  //aspectos de cada Tab (pestaña)
        TabHost.TabSpec tab2 = TbH.newTabSpec("tab2");
        TabHost.TabSpec tab3 = TbH.newTabSpec("tab3");
        TabHost.TabSpec tab4 = TbH.newTabSpec("tab4");


        tab1.setIndicator("H.MAÑANA");    //qué queremos que aparezca en las pestañas
        tab1.setContent(R.id.mañana); //definimos el id de cada Tab (pestaña)


        tab2.setIndicator("H.TARDE");
        tab2.setContent(R.id.tarde);

        tab3.setIndicator("PRECIOS");
        tab3.setContent(R.id.precios);

        tab4.setIndicator("MIS DATOS");
        tab4.setContent(R.id.datos);

        TbH.addTab(tab1); //añadimos los tabs ya programados
        TbH.addTab(tab2);
        TbH.addTab(tab3);
        TbH.addTab(tab4);

        TabWidget tabs = (TabWidget)TbH.findViewById(android.R.id.tabs);
        View tabView1 = tabs.getChildTabViewAt(0);
        View tabView2 = tabs.getChildTabViewAt(1);
        View tabView3= tabs.getChildTabViewAt(2);
        View tabView4= tabs.getChildTabViewAt(3);

        TextView tv1 = (TextView)tabView1.findViewById(android.R.id.title);
        TextView tv2 = (TextView)tabView2.findViewById(android.R.id.title);
        TextView tv3 = (TextView)tabView3.findViewById(android.R.id.title);
        TextView tv4 = (TextView)tabView4.findViewById(android.R.id.title);

        tv1.setTextSize(12);
        tv2.setTextSize(12);
        tv3.setTextSize(12);
        tv4.setTextSize(12);

        String MomentoMañana = "Mañana";
        String MomentoTarde = "Tarde";

        String HorarioMañana = "http://jose-cordones.es/app/consultas/obtenerHorarioAdministrador.php?momento="+ MomentoMañana;
        obtenerHorarioMañana(HorarioMañana);

        String HorarioTarde = "http://jose-cordones.es/app/consultas/obtenerHorarioAdministrador.php?momento="+ MomentoTarde;
        obtenerHorarioTarde(HorarioTarde);

        String Precios = "http://jose-cordones.es/app/consultas/obtenerPrecios.php";
        obtenerPrecios(Precios);

        String MisDatos = "http://jose-cordones.es/app/consultas/obtenerPersonaParaEditar.php?nick="+ usuario;
        obtenerMisDatos(MisDatos);


        //BOTON MIS DATOS
        btnEditarMD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String telefono = edTelefono.getText().toString(),
                        email = edEmail.getText().toString(),
                        provincia = edProvincia.getText().toString(),
                        localidad = edLocalidad.getText().toString(),
                        direccion = edDireccion.getText().toString();

                if (!validarEmail(edEmail.getText().toString())){
                        Toast.makeText(Main_Ediciones_Activity.this, "EL CORREO NO ES CORRECTO", Toast.LENGTH_SHORT).show();
                    edEmail.setText("");
                }else{
                    ActualizarMisDatos("http://jose-cordones.es/app/actualizaciones/actualizarMisDatos.php?email="+ email+"&telefono="+ telefono+"&direccion="+ direccion+"&localidad="+ localidad+"&provincia="+ provincia+"&nick="+ usuario);
                    Toast.makeText(getApplicationContext(), "DATOS EDITADOS CORRECTAMENTE " ,  Toast.LENGTH_SHORT).show();
                }



            }
        });

        //BOTON PRECIOS
        btnEditarPrecios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String f7 = edFutbol7.getText().toString(),
                        fs = edFutbolSala.getText().toString(),
                        tennis = edTennis.getText().toString(),
                        padel = edPadel.getText().toString(),
                        spinning = edSpinning.getText().toString(),
                        zumba = edZumba.getText().toString(),
                        clofit = edClofit.getText().toString(),
                        pilates = edPilates.getText().toString();
                ActualizarPrecios("http://jose-cordones.es/app/actualizaciones/actualizarPrecios.php?futbol7="+ f7+"&futbolSala="+ fs+"&tennis="+ tennis+"&padel="+ padel+"&spinning="+ spinning+"&zumba="+ zumba+"&clofit="+ clofit+"&pilates="+ pilates);
                Toast.makeText(getApplicationContext(), "PRECIOS EDITADOS CORRECTAMENTE ",  Toast.LENGTH_SHORT).show();


            }
        });

        //BOTON MAÑANA
        btnEditarHorarioM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Momento = "Mañana",
                        LunesM = edLunesM.getText().toString(),
                        MartesM = edMartesM.getText().toString(),
                        MiercolesM = edMiercolesM.getText().toString(),
                        JuevesM = edJuevesM.getText().toString(),
                        ViernesM = edViernesM.getText().toString(),
                        SabadoM = edSabadoM.getText().toString(),
                        DomingoM = edDomingoM.getText().toString();
                ActualizarHorarioM("http://jose-cordones.es/app/actualizaciones/actualizarHorario.php?lunes="+ LunesM+"&martes="+ MartesM+"&miercoles="+ MiercolesM+"&jueves="+ JuevesM+"&viernes="+ ViernesM+"&sabado="+ SabadoM+"&domingo="+ DomingoM+"&momento="+ Momento);
                Toast.makeText(getApplicationContext(), "HORARIO EDITADO CORRECTAMENTE ", Toast.LENGTH_SHORT).show();


            }
        });

        //BOTON TARDE
        btnEditarHorarioT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Momento = "Tarde",
                        LunesT = edLunesT.getText().toString(),
                        MartesT = edMartesT.getText().toString(),
                        MiercolesT = edMiercolesT.getText().toString(),
                        JuevesT = edJuevesT.getText().toString(),
                        ViernesT = edViernesT.getText().toString(),
                        SabadoT = edSabadoT.getText().toString(),
                        DomingoT = edDomingoT.getText().toString();
                ActualizarHorarioM("http://jose-cordones.es/app/actualizaciones/actualizarHorario.php?lunes="+ LunesT+"&martes="+ MartesT+"&miercoles="+ MiercolesT+"&jueves="+ JuevesT+"&viernes="+ ViernesT+"&sabado="+ SabadoT+"&domingo="+ DomingoT+"&momento="+ Momento);
                Toast.makeText(getApplicationContext(), "HORARIO EDITADO CORRECTAMENTE ", Toast.LENGTH_SHORT).show();


            }
        });


    }

    //EDITAR MIS DATOS
    public void ActualizarMisDatos(String URL){


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

    //EDITAR PRECIOS
    public void ActualizarPrecios(String URL){


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


    //OBTENER MIS DATOS
    public void obtenerMisDatos(String URL){

        //Toast.makeText(getApplicationContext(), ""+URL, Toast.LENGTH_SHORT).show();

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replace("][",",");
                if (response.length()>0){
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson",""+ja.length());
                        CargarListViewMisDatos(ja);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

    }

    public void CargarListViewMisDatos(JSONArray ja){

        ArrayList<String> lista = new ArrayList<>();

        String telefono = null, email = null, direccion = null, localidad = null, provincia = null ;
        for(int i=0;i<ja.length();i+=5){

            try {

                telefono = ja.getString(i+1);
                email = ja.getString(i+0);
                direccion = ja.getString(i+2);
                localidad = ja.getString(i+3);
                provincia = ja.getString(i+4);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        edTelefono.setText(telefono);
        edEmail.setText(email);
        edDireccion.setText(direccion);
        edLocalidad.setText(localidad);
        edProvincia.setText(provincia);

    }

    //OBTENER PRECIOS
    public void obtenerPrecios(String URL){

        //Toast.makeText(getApplicationContext(), ""+URL, Toast.LENGTH_SHORT).show();

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replace("][",",");
                if (response.length()>0){
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson",""+ja.length());
                        CargarListViewPrecios(ja);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

    }

    public void CargarListViewPrecios(JSONArray ja){

        ArrayList<String> lista = new ArrayList<>();

        String futbol7 = null, futbols = null, padel = null, tennis = null, spinning = null, zumba = null, clofit = null, pilates = null;
        for(int i=0;i<ja.length();i+=8){

            try {

                futbol7 = ja.getString(i+1);
                futbols = ja.getString(i+2);
                tennis = ja.getString(i+3);
                padel = ja.getString(i+4);
                spinning = ja.getString(i+5);
                zumba = ja.getString(i+6);
                clofit = ja.getString(i+7);
                pilates = ja.getString(i+8);
            //    lista.add("\n"+"               ACTIVIDAD                            PRECIO"+"\n \n"+"               FUTBOL 7                              "+ja.getString(i+2)+"€"+"\n \n"+"               FUTBOL SALA                      "+ja.getString(i+3)+"€"+"\n \n"+"               TENNIS                                  "+ja.getString(i+4)+"€"+"\n \n"+"               PADEL                                    "+ja.getString(i+1)+"€" +"\n \n"+"               SPINNING                              "+ja.getString(i+5)+"€"+"\n \n"+"               ZUMBA                                    "+ja.getString(i+6)+"€"+"\n \n"+"               CLOFIT                                    "+ja.getString(i+7)+"€"+"\n \n"+"               PILATES                                  "+ja.getString(i+8)+"€");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        edFutbol7.setText(futbol7);
        edFutbolSala.setText(futbols);
        edTennis.setText(tennis);
        edPadel.setText(padel);
        edSpinning.setText(spinning);
        edZumba.setText(zumba);
        edClofit.setText(clofit);
        edPilates.setText(pilates);


    }

    //OBTENER HORARIO POR LA TARDE
    public void obtenerHorarioTarde(String URL){


        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replace("][",",");
                if (response.length()>0){
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson",""+ja.length());
                        CargarListViewTarde(ja);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

    }

    public void CargarListViewTarde(JSONArray ja){

        ArrayList<String> lista = new ArrayList<>();

        String MomentoTarde = null, LunesTarde = null, MarterTarde = null, MiercolesTarde = null, JuevesTarde = null, ViernesTarde = null, SabadoTarde = null, DomingoTarde = null;
        for(int i=0;i<ja.length();i+=18){

            try {

                MomentoTarde = ja.getString(i+1);
                LunesTarde = ja.getString(i+2);
                MarterTarde = ja.getString(i+3);
                MiercolesTarde = ja.getString(i+4);
                JuevesTarde = ja.getString(i+5);
                ViernesTarde = ja.getString(i+6);
                SabadoTarde = ja.getString(i+7);
                DomingoTarde = ja.getString(i+8);

                //lista.add("\n"+"                           "+ja.getString(i+1) + "             "+ja.getString(i+10)+" \n \n"+" LUNES:             "+ja.getString(i+2)+"      "+ja.getString(i+11)+"\n \n"+" MARTES:          "+ja.getString(i+3)+"      "+ja.getString(i+12)+"\n \n"+"MIERCOLES:     "+ja.getString(i+4)+"      "+ja.getString(i+13)+"\n \n"+"JUEVES:            "+ja.getString(i+5)+"      "+ja.getString(i+14)+"\n \n"+"VIERNES:          "+ja.getString(i+6)+"      "+ja.getString(i+15)+"\n \n"+"SABADO:           "+ja.getString(i+7)+"      "+ja.getString(i+16)+"\n \n"+"DOMINGO:         "+ja.getString(i+8)+"             "+ja.getString(i+17)+"\n \n");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        // lvDatos.setAdapter(adaptador);
        edLunesT.setText(LunesTarde);
        edMartesT.setText(MarterTarde);
        edMiercolesT.setText(MiercolesTarde);
        edJuevesT.setText(JuevesTarde);
        edViernesT.setText(ViernesTarde);
        edSabadoT.setText(SabadoTarde);
        edDomingoT.setText(DomingoTarde);
        tvSeparadorT.setText(MomentoTarde);


    }


    //EDITAR HORARIO POR LA MAÑANA
    public void ActualizarHorarioM(String URL){


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

    //OBTENER HORARIO POR LA MAÑANA
    public void obtenerHorarioMañana(String URL){
        

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replace("][",",");
                if (response.length()>0){
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson",""+ja.length());
                        CargarListView(ja);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

    }

    public void CargarListView(JSONArray ja){

        ArrayList<String> lista = new ArrayList<>();

        String Momento = null, LunesMañana = null, MarterMañana = null, MiercolesMañana = null, JuevesMañana = null, ViernesMañana = null, SabadoMañana = null, DomingoMañana = null;
        for(int i=0;i<ja.length();i+=18){

            try {

                Momento = ja.getString(i+1);
                LunesMañana = ja.getString(i+2);
                MarterMañana = ja.getString(i+3);
                MiercolesMañana = ja.getString(i+4);
                JuevesMañana = ja.getString(i+5);
                ViernesMañana = ja.getString(i+6);
                SabadoMañana = ja.getString(i+7);
                DomingoMañana = ja.getString(i+8);

                //lista.add("\n"+"                           "+ja.getString(i+1) + "             "+ja.getString(i+10)+" \n \n"+" LUNES:             "+ja.getString(i+2)+"      "+ja.getString(i+11)+"\n \n"+" MARTES:          "+ja.getString(i+3)+"      "+ja.getString(i+12)+"\n \n"+"MIERCOLES:     "+ja.getString(i+4)+"      "+ja.getString(i+13)+"\n \n"+"JUEVES:            "+ja.getString(i+5)+"      "+ja.getString(i+14)+"\n \n"+"VIERNES:          "+ja.getString(i+6)+"      "+ja.getString(i+15)+"\n \n"+"SABADO:           "+ja.getString(i+7)+"      "+ja.getString(i+16)+"\n \n"+"DOMINGO:         "+ja.getString(i+8)+"             "+ja.getString(i+17)+"\n \n");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
       // lvDatos.setAdapter(adaptador);
        edLunesM.setText(LunesMañana);
        edMartesM.setText(MarterMañana);
        edMiercolesM.setText(MiercolesMañana);
        edJuevesM.setText(JuevesMañana);
        edViernesM.setText(ViernesMañana);
        edSabadoM.setText(SabadoMañana);
        edDomingoM.setText(DomingoMañana);
        tvSeparador.setText(Momento);


    }

    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

}
