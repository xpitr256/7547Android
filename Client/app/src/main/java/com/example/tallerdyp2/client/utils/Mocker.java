package com.example.tallerdyp2.client.utils;

import com.example.tallerdyp2.client.Entities.Attraction;
import com.example.tallerdyp2.client.Entities.City;
import com.example.tallerdyp2.client.Entities.PointOfInterest;
import com.example.tallerdyp2.client.Entities.Review;
import com.example.tallerdyp2.client.Entities.Tour;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sebastian on 2/4/2017.
 */

public class Mocker {
    public static List<City> parseCities(JSONArray response) {
        List<City> cities = new ArrayList<>();
        City buenosAires = new City(
                "1",
                "Buenos Aires",
                "Buenos Aires, denominada oficialmente Ciudad Autónoma de Buenos Aires, es la capital de la República Argentina y el principal núcleo urbano del país. Está situada en la región centroeste del país, sobre la orilla occidental del Río de la Plata, en la llanura pampeana.",
                Mocker.parseImagesURL(),
                23.1,
                24.1,
                Mocker.parseAttractions(),
                Mocker.parseTours()
        );
        City mendoza = new City(
                "2",
                "Mendoza",
                "Ciudad del buen vino",
                Mocker.parseImagesURL(),
                23.1,
                24.1,
                Mocker.parseAttractions(),
                Mocker.parseTours()
        );
        cities.add(buenosAires);
        cities.add(mendoza);

        return cities;

    }

    private static List<String> parseImagesURL() {
        List<String> urls = new ArrayList<>();

        urls.add("http://i.imgur.com/sRvjuhY.jpg");
        urls.add("http://i.imgur.com/sRvjuhY.jpg");

        return urls;
    }

    private static List<Attraction> parseAttractions() {
        List<Attraction> attractions = new ArrayList<>();

        attractions.add(new Attraction(
                        "1",
                        "Parque de la costa",
                        "Descripcion",
                        3.5,
                        Mocker.parseImagesURL(),
                        "https://archive.org/download/Popeye_forPresident/Popeye_forPresident_512kb.mp4",
                        22.2,
                        21.1,
                        "FAMILY",
                        "00:00",
                        "00:00",
                        20,
                        Mocker.parseReviews(),
                        Mocker.parsePOIs(),
                        Mocker.parseTours()));

        attractions.add(new Attraction(
                "2",
                "Obelisco",
                "Descripcion",
                3.5,
                Mocker.parseImagesURL(),
                "https://archive.org/download/Popeye_forPresident/Popeye_forPresident_512kb.mp4",
                0.2,
                0.1,
                "FAMILY",
                "00:00",
                "00:00",
                20,
                Mocker.parseReviews(),
                Mocker.parsePOIs(),
                Mocker.parseTours()));
        return attractions;
    }

    private static List<Review> parseReviews() {
        List<Review> reviews = new ArrayList<>();

        reviews.add(new Review("Sebastian Vicario", SharedPreferencesUtils.getFacebookUserId(), "", "I love it!!!", 3.5, "19/12/2016", 1));
        return reviews;
    }

    public static List<PointOfInterest> parsePOIs() {
        List<PointOfInterest> pois = new ArrayList<>();
        List<String> urls = new ArrayList<>();

        urls.add("http://i.imgur.com/sRvjuhY.jpg");
            pois.add(new PointOfInterest(
                    "0",
                    "Mona Lisa",
                    "La Mona Lisa es una de las pinturas más famosas a nivel mundial.",
                    urls,
                    "https://archive.org/download/Popeye_forPresident/Popeye_forPresident_512kb.mp4",
                    "1st floor"
            ));

        return pois;
    }

    public static List<Tour> parseTours() {
        List<Tour> tours = new ArrayList<>();
        tours.add(new Tour(
                "0",
                "Recorrido Turistico",
                "Puntos mas importante.",
                Mocker.parseAttractions()
        ));

        tours.add(new Tour(
                "1",
                "Recorrido Caminito",
                "Puntos mas importante.",
                Mocker.parseAttractions()
        ));

        return tours;
    }
}
