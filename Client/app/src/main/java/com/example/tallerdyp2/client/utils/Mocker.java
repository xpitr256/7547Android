package com.example.tallerdyp2.client.utils;

import com.example.tallerdyp2.client.Entities.Attraction;
import com.example.tallerdyp2.client.Entities.City;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sebastian on 2/4/2017.
 */

public class Mocker {
    public static List<City> parseCities(JSONArray response) {
        List<City> cities = new ArrayList<>();
        List<Attraction> attractions = new ArrayList<>();
        List<String> urls = new ArrayList<>();
        urls.add("http://i.imgur.com/sRvjuhY.jpg");
        urls.add("http://i.imgur.com/sRvjuhY.jpg");
        Attraction parqueDeLaCosta = new Attraction(
                "1",
                "Parque de la costa",
                "Descripcion",
                urls,
                22.2,
                21.1,
                "FAMILY",
                "00:00",
                "00:00",
                20
        );
        attractions.add(parqueDeLaCosta);
        attractions.add(parqueDeLaCosta);
        attractions.add(parqueDeLaCosta);
        City buenosAires = new City(
                "1",
                "Buenos Aires",
                "Buenos Aires, denominada oficialmente Ciudad Autónoma de Buenos Aires, es la capital de la República Argentina y el principal núcleo urbano del país. Está situada en la región centroeste del país, sobre la orilla occidental del Río de la Plata, en la llanura pampeana.",
                urls,
                23.1,
                24.1,
                attractions
        );
        cities.add(buenosAires);
        cities.add(buenosAires);
        cities.add(buenosAires);

        return cities;

    }
}
