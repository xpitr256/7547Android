package com.example.tallerdyp2.client;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sebastian on 14/3/2017.
 */

public class CityActivity extends AppCompatActivity{

    private ListView citiesList;
    private CitiesAdapter citiesAdapter;
    private List<String> cities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);



        getCitiesInfo();

        cities = new ArrayList<String>();

        // Instancia del ListView.
        citiesList = (ListView) findViewById(R.id.cities_list);

        // Inicializar el adaptador con la fuente de datos.
        citiesAdapter = new CitiesAdapter(this, cities);

        //Relacionando la lista con el adaptador
        citiesList.setAdapter(citiesAdapter);

        citiesList.setVisibility(View.GONE);
//        // Eventos
//        citiesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                Intent intent = new Intent(MainActivity.this, ItemDetailActivity.class);
//                intent.putExtra("viaje", viajes.get(position));
//                intent.putExtra("mostrar_fab", false);
//                startActivity(intent);
//            }
//        });
    }

    public void getCitiesInfo() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = getResources().getString(R.string.http_ip) + "/user";
        final Context context = getApplicationContext();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        cities.add("Buenos Aires");
                        cities.add("Neuquen");
                        cities.add("Pinamar");
                        citiesAdapter.notifyDataSetChanged();
                        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                        citiesList.setVisibility(View.VISIBLE);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });

        // Add the request to the RequestQueue.
        queue.add(jsObjRequest);
    }

}
