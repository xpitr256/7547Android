package com.example.tallerdyp2.client;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

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

        //Se obtienen las ciudades
        Bundle bundle = getIntent().getExtras();

        cities = (ArrayList<String>) getIntent().getSerializableExtra("cities");

        // Instancia del ListView.
        citiesList = (ListView) findViewById(R.id.cities_list);

        // Inicializar el adaptador con la fuente de datos.
        citiesAdapter = new CitiesAdapter(this, cities);

        //Relacionando la lista con el adaptador
        citiesList.setAdapter(citiesAdapter);

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

}
