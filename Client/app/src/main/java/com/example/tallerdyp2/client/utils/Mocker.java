package com.example.tallerdyp2.client.utils;

import com.example.tallerdyp2.client.Entities.Attraction;
import com.example.tallerdyp2.client.Entities.City;
import com.example.tallerdyp2.client.Entities.Review;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sebastian on 2/4/2017.
 */

public class Mocker {
    public static List<City> parseCities(JSONArray response) {
        List<City> cities = new ArrayList<>();
        List<Attraction> attractions = new ArrayList<>();
        List<Review> reviews = new ArrayList<>();
        List<String> urls = new ArrayList<>();

        urls.add("http://i.imgur.com/sRvjuhY.jpg");
        urls.add("http://i.imgur.com/sRvjuhY.jpg");

        Review review = new Review("Sebastian Vicario", SharedPreferencesUtils.getFacebookUserId(), "", "I love it!!!", 3.5);

        reviews.add(review);

        Attraction parqueDeLaCosta = new Attraction(
                "1",
                "Parque de la costa",
                "Descripcion",
                3.5,
                urls,
                "https://archive.org/download/Popeye_forPresident/Popeye_forPresident_512kb.mp4",
                22.2,
                21.1,
                "FAMILY",
                "00:00",
                "00:00",
                20,
                reviews);
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
        City mendoza = new City(
                "2",
                "Mendoza",
                "Ciudad del buen vino",
                urls,
                23.1,
                24.1,
                attractions
        );
        cities.add(buenosAires);
        cities.add(mendoza);

        return cities;

    }
}
