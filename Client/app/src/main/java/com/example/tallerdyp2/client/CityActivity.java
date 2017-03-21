package com.example.tallerdyp2.client;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tallerdyp2.client.Entities.City;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sebastian on 14/3/2017.
 */

public class CityActivity extends AppCompatActivity{

    private Toolbar mToolbar;
    private ListView citiesList;
    private CitiesAdapter citiesAdapter;
    private List<City> cities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        
        getCitiesInfo();

        // Instancia del ListView.
        citiesList = (ListView) findViewById(R.id.cities_list);

        citiesList.setVisibility(View.GONE);

        // Eventos
        citiesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(CityActivity.this, AttractionsActivity.class);
                intent.putExtra("city", cities.get(position).getName());
                startActivity(intent);
            }
        });
    }

    public void getCitiesInfo() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = getResources().getString(R.string.http_ip) + "/city";
        final Context context = getApplicationContext();

        JsonArrayRequest jsObjRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // Inicializar el adaptador con la fuente de datos.
//                Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show();

                // Inicializar el adaptador con la fuente de datos.
                citiesAdapter = new CitiesAdapter(context, procesarResponse(response));

                //Relacionando la lista con el adaptador
                citiesList.setAdapter(citiesAdapter);

//                citiesAdapter.notifyDataSetChanged();
                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                citiesList.setVisibility(View.VISIBLE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error de conexion", Toast.LENGTH_LONG).show();
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(jsObjRequest);
    }

    public List<City> procesarResponse(JSONArray response) {
        List<City> cities = new ArrayList<>();
        JSONObject city;
        for(int i  = 0; i < response.length(); i++) {
            try {
                city = response.getJSONObject(i);
                cities.add(new City(city.getString("_id"),
                        city.getString("name"),
                        city.getString("description"),
                        city.getString("imageURL"),
                        city.getJSONObject("location").getDouble("lat"),
                        city.getJSONObject("location").getDouble("lng")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        this.cities = cities;
        return cities;

    }

}
