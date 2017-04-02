package com.example.tallerdyp2.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.android.volley.VolleyError;
import com.example.tallerdyp2.client.Entities.City;
import com.example.tallerdyp2.client.utils.Callable;
import com.example.tallerdyp2.client.utils.ElementViewUtils;
import com.example.tallerdyp2.client.utils.Helper;
import com.example.tallerdyp2.client.utils.Mocker;
import com.example.tallerdyp2.client.utils.Parser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sebastian on 14/3/2017.
 */

public class CitiesActivity extends AppCompatActivity implements Callable{

    private ListView citiesList;
    private CitiesAdapter citiesAdapter;
    private List<City> cities;
    private String filterName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_cities);
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
                Intent intent = new Intent(CitiesActivity.this, CityActivity.class);
                intent.putExtra("cityName", cities.get(position).getName());
                startActivity(intent);
            }
        });
    }

    public void getCitiesInfo() {
        AttractionGOApplication.getVolleyRequestService().getCities(this);
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
            if (Helper.stripAccents(city.getName().toLowerCase()).contains(Helper.stripAccents(newText.toLowerCase())))
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

    @Override
    public void execute(JSONArray response) {

        // Inicializar el adaptador con la fuente de datos.

//        this.cities = Parser.parseCities(response);
        this.cities = Mocker.parseCities(response);
        citiesAdapter.addAll(this.cities);

        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        if(!this.cities.isEmpty()){
            citiesList.setVisibility(View.VISIBLE);
        }else{
            ElementViewUtils.setText(findViewById(R.id.textErrorRequest), R.id.textErrorRequest, AttractionGOApplication.getAppContext().getString(R.string.no_cities));
            findViewById(R.id.textErrorRequest).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void execute(JSONObject response) {

    }

    @Override
    public void error(VolleyError error) {
        this.hideInformation();
        this.showError(getResources().getString(R.string.error_request));
    }

    private void showError(String error) {
        findViewById(R.id.image_error).setVisibility(View.VISIBLE);
        ElementViewUtils.setText(findViewById(R.id.textErrorRequest), R.id.textErrorRequest, error);
        findViewById(R.id.textErrorRequest).setVisibility(View.VISIBLE);
    }

    private void hideInformation() {
        //hide views
        findViewById(R.id.cities_list).setVisibility(View.GONE);
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
