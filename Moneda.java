package com.example.aplicacion5_.models;

import android.util.Log;

//Importamos Volley
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

//Importamos nuestras clases que vamos a utilizar
import com.example.aplicacion5_.MainActivity;
import com.example.aplicacion5_.helpers.QueueUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Moneda {
    //Variables que vamos a utilizar del JSON
    public String code;
    public String name;

    public String rate;
    // rate    1 euro  >  1,1282142 Dólares estadounidenses
    public String inverseRate;
    //inverseRate   1 USD   >  0,88636466 Euros

    //Constructor
    public Moneda(String _code, String _name, String _rate, String _inverseRate) {
        this.code = _code;
        this.name = _name;
        this.rate = _rate;
        this.inverseRate = _inverseRate;
    }

    //Creamos los Getters para acceder y los Setters para modificar la clase Moneda
    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getRate() {
        return rate;
    }

    public String getInverseRate() {
        return inverseRate;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public void setInverseRate(String inverseRate) {
        this.inverseRate = inverseRate;
    }



    //SI LOS DATOS LOS CREAMOS NOSOTROS DENTRO DE LA APP
    public static ArrayList getCollection() {
        ArrayList<Moneda> collection = new ArrayList<>();
        collection.add(new Moneda("USD", "U.S. Dollar", "1.1300941915987", "0.88488199252251"));
        collection.add(new Moneda("GBP", "U.K. Pound Sterling","0.84045383327365","1.1898333500424"));
        return collection;
    }



    //SI TRABAJAMOS CON EL ARCHIVO JSON
    public static void injectContactsFromCloud(
            final QueueUtils.QueueObject o,
            final ArrayList<Moneda> monedas,
            final MainActivity _interface) {
        //Accedemos a los datos JSON
        String url = "https://api.jsonbin.io/b/619af69862ed886f9152b718/2";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //Si el primer objeto se llama teams
                        if (response.has("moneda")) {
                            try {
                                //Accedemos al array de Objetos
                                JSONArray monedasObtendias = response.getJSONArray("moneda");

                                //Debug para verificar que nos llegan los datos
                                String longitudJsonArray= "" + (monedasObtendias.length());
                                Log.v("respuesta", " Longitud JSonArray " + longitudJsonArray);

                                for (int i = 0; i < monedasObtendias.length(); i++) {

                                    JSONObject datosMoneda = monedasObtendias.getJSONObject(i);
                                    String code =  datosMoneda.getString("code");
                                    String name = datosMoneda.getString("name");
                                    String rate = datosMoneda.getString("rate");
                                    String inverseRate = datosMoneda.getString("inverseRate");

                                    Log.v("datos", code + "- " + name + " -" + rate + "-" + inverseRate);
                                    //Añadimos los datos al final ArrayList<Moneda> monedas;
                                    monedas.add(new Moneda(code,name, rate, inverseRate));
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.v("respuesta", "Error al Acceder a los JSOM");
                            }
                            // Esta función se implementa en el MainActivity
                            _interface.refreshList();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("respuesta", "Error Volley");
                    }
                });

        o.addToRequestQueue(jsonObjectRequest);
    }


}
