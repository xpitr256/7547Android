package com.example.tallerdyp2.client;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tallerdyp2.client.Entities.Attraction;
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

    private ListView citiesList;
    private CitiesAdapter citiesAdapter;
    private List<City> cities;
    private String filterName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        getCitiesInfo();

        // Instancia del ListView.
        citiesList = (ListView) findViewById(R.id.cities_list);

        citiesList.setVisibility(View.GONE);

        cities = new ArrayList<>();

        // Inicializar el adaptador con la fuente de datos.
        citiesAdapter = new CitiesAdapter(getApplicationContext(), cities);

        //Relacionando la lista con el adaptador
        citiesList.setAdapter(citiesAdapter);

        // Eventos
        citiesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(CityActivity.this, InitialActivity.class);
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
                citiesAdapter.addAll(procesarResponse(response));

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
        JSONArray attractionsJSON;
        List<Attraction> attractions ;
        for(int i  = 0; i < response.length(); i++) {
            try {
                city = response.getJSONObject(i);
                attractionsJSON = city.getJSONArray("attractions");
                attractions = new ArrayList<>();
                for(int j = 0; j < attractionsJSON.length(); j++){
                    attractions.add(
                            new Attraction(
                                    attractionsJSON.getJSONObject(j).getString("_id"),
                                    attractionsJSON.getJSONObject(j).getString("name"),
                                    attractionsJSON.getJSONObject(j).getString("description"),
                                    attractionsJSON.getJSONObject(j).getString("imageURL"),
                                    attractionsJSON.getJSONObject(j).getJSONObject("location").getDouble("lat"),
                                    attractionsJSON.getJSONObject(j).getJSONObject("location").getDouble("lng"),
                                    "FAMILY",
                                    "00:00",
                                    "00:00",
//                                    attractionsJSON.getJSONObject(j).getString("type"),
//                                    attractionsJSON.getJSONObject(j).getString("openTime"),
//                                    attractionsJSON.getJSONObject(j).getString("closeTime"),
                                    attractionsJSON.getJSONObject(j).getInt("price")
                                    ));
                }
                cities.add(new City(city.getString("_id"),
                        city.getString("name"),
                        city.getString("description"),
                        city.getString("imageURL"),
                        city.getJSONObject("location").getDouble("lat"),
                        city.getJSONObject("location").getDouble("lng"),
                        attractions));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        this.cities = cities;
        return cities;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search_city, menu);
        MenuItem item = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterName = newText;
                if(!filterName.isEmpty())
                    getCitiesFilterByText(newText);
                else{
                    getAllCities();
                }
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    private void getAllCities() {
        citiesAdapter.clear();
        citiesAdapter.addAll(cities);
        citiesAdapter.notifyDataSetChanged();
    }

    private void getCitiesFilterByText(String newText) {
        List<City> citiesAux = new ArrayList<City>();
        for(City city : cities){
            if (city.getName().toLowerCase().contains(newText))
                citiesAux.add(city);
        }
        citiesAdapter.clear();
        citiesAdapter.addAll(citiesAux);
        citiesAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if(!this.filterName.isEmpty()){
            getCitiesFilterByText(this.filterName);
        }else{
            getAllCities();
        }
    }

}
