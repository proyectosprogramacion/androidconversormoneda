package com.example.aplicacion5_;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity2 extends AppCompatActivity {
    //Declaro las variables
    private String datoDelCode;
    private String datoDelName;
    private String datoDelRate;
    private String datoDelInverseRate;


    private TextView textView;
    private EditText editText;

    /*Variable para calcular el cambio*/
    private double conversion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //Recibimos la información a través de un Budle
        Bundle datosRecibir = this.getIntent().getExtras();
        //Si no es null obtenemos los datos
        if (datosRecibir !=null) {
            datoDelCode = datosRecibir.getString("code");
            datoDelName = datosRecibir.getString("name");
            datoDelRate = datosRecibir.getString("rate");
            datoDelInverseRate = datosRecibir.getString("inverseRate");

            Toast.makeText(MainActivity2.this,"Ha seleccionado: " + " code: " + datoDelCode + " name: " + datoDelName + " rate(1 euro = ? moneda:) " + datoDelRate   + " inverteRate (1 moneda = ? euro): " +  datoDelInverseRate   , Toast.LENGTH_SHORT).show();
        }
    }

    /* Al dar al botón Convertir realizamos la operacíon y mostramos los datos*/
    public void btConvertir(View view) {

        /*Obtenemos el valor introducido por el usuario*/
        editText= (EditText) findViewById(R.id.etMonedaIntroducida);

        /*Verificamos que se introdujo una cantidad*/
        if(!editText.getText().toString().isEmpty()){/*En caso de que no este vacio*/
            double valorIntroducido= Double.parseDouble(editText.getText().toString());
            //Realizamos la operación de conversión
            conversion= valorIntroducido * Double.parseDouble(datoDelRate);

            /*Mostramos en el textView  el resultado de moneda convertida*/
            textView = (TextView)findViewById(R.id.tvResultado);
            textView.setText( valorIntroducido + " Euros son: " + Double.toString(conversion) + "  " + datoDelCode );

        } else {//En caso de que no se haya introducido ninguna cantidad en el editText
            Toast.makeText(getApplicationContext(), "Ingresa la cantidad a convertir en Euros.", Toast.LENGTH_LONG).show();
        }

    }


    //Para eliminar la Activity cuando pulsemos el botón de retroceso del móvil y volvamos a la Activity1
    @Override public void onBackPressed() {
        finish();
    }
    //Para eliminar la Activity cuando salimos de la aplicación y volvemos a ejecutar luego la app que no aparezca esta activity
    public void onPause() {
        super.onPause();
        finish();
    }


}
