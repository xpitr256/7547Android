package com.example.tallerdyp2.client;

/**
 * Created by Sebastian on 15/03/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
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

public class AttractionsActivity extends AppCompatActivity {

    private ListView attractionsList;
    private AttractionsAdapter attractionsAdapter;
    private List<String> attractions;
    private String citySelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attraction);

        citySelected = getIntent().getExtras().getString("city");

        getAttractionsInfo();

        attractions = new ArrayList<String>();

        // Instancia del ListView.
        attractionsList = (ListView) findViewById(R.id.attractions_list);

        // Inicializar el adaptador con la fuente de datos.
        attractionsAdapter = new AttractionsAdapter(this, attractions);

        //Relacionando la lista con el adaptador
        attractionsList.setAdapter(attractionsAdapter);

        attractionsList.setVisibility(View.GONE);

        // Eventos
        attractionsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                Intent intent = new Intent(CityActivity.this, AttractionsActivity.class);
//                intent.putExtra("viaje", attractions.get(position));
//                intent.putExtra("mostrar_fab", false);
//                startActivity(intent);


            }
        });
    }

    public void getAttractionsInfo() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = getResources().getString(R.string.http_ip) + "/user";
        final Context context = getApplicationContext();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        attractions.add("Costanera");
                        attractions.add("Luna Park");
                        attractions.add("Parque de la costa");
                        attractionsAdapter.notifyDataSetChanged();
                        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                        attractionsList.setVisibility(View.VISIBLE);


                        Toast.makeText(AttractionsActivity.this, "City: "+citySelected, Toast.LENGTH_LONG).show();

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