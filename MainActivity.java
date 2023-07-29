package com.example.aplicacion5_;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

//Importamos nuestras clases que vamos a utilizar
import com.example.aplicacion5_.adapters.MonedaAdaptador;
import com.example.aplicacion5_.helpers.QueueUtils;
import com.example.aplicacion5_.models.Moneda;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ListView cambioMonedaList;
    MonedaAdaptador monedaAdaptador;
    QueueUtils.QueueObject queue = null;
    ArrayList<Moneda> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //1º Mostramos los datos usando el Adaptador  y la clasee por pantalla
        cambioMonedaList = findViewById(R.id.cambioMonedaList);
        queue = QueueUtils.getInstance(this.getApplicationContext());
        items = new ArrayList<>();
        Moneda.injectContactsFromCloud(queue, items, this);
        monedaAdaptador = new MonedaAdaptador(this, items);
        cambioMonedaList.setAdapter(monedaAdaptador);


        //2º Cuando se hace click sobre un ITEM obtener cual ha sido el elemento seleccionado
        cambioMonedaList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicion, long l) {
                //Obtengo la posición del item seleccionado con la variable posicion
                Moneda listChoice = monedaAdaptador.getItem(posicion);

                String valorItem_Code = listChoice.getCode(); //obtengo el valor del código de la moneda
                String valorItem_Name =listChoice.getName(); //obtengo el valor del name de la moneda
                String valorItem_Rate =listChoice.getRate(); //obtengo el valor del rate de la moneda
                String valorItem_InverseRate = listChoice.getInverseRate(); //obtengo el valor del InverseRate de la moneda

               // Toast.makeText(MainActivity.this,"code: " + listChoice.getCode() + " name: " +listChoice.getName() + " rate(1 euro = ? moneda:) " +listChoice.getRate()   + " inverteRate (1 moneda = ? euro): " +listChoice.getInverseRate()  , Toast.LENGTH_SHORT).show();

                // Envio todos los datos a través de un Budle al Activity 2
                Bundle datoeEnviar = new Bundle();
                //El primer valor será la KEY con el que lo recuperaré en el segundo Activity Y el segundo el VALUE
                datoeEnviar.putString("code",valorItem_Code);
                datoeEnviar.putString("name",valorItem_Name);
                datoeEnviar.putString("rate",valorItem_Rate);
                datoeEnviar.putString("inverseRate",valorItem_InverseRate);

                Intent modify_intent =  new Intent(MainActivity.this, MainActivity2.class);
                modify_intent.putExtras(datoeEnviar);
                startActivity(modify_intent);

            }
        });
    }

    //Para refrescar la pantalla
    public void refreshList(){
        if ( monedaAdaptador!= null ) {
            monedaAdaptador.notifyDataSetChanged();
        }
    }
}
