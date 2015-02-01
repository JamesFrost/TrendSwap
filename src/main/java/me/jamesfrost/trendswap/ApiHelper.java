package me.jamesfrost.trendswap;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Handles all communication with the API.
 * <p/>
 * Created by James Frost on 24/01/2015.
 */
public class ApiHelper implements Constants {

    /**
     * Makes a request to the API.
     *
     * @param text Text to analyse
     * @return API response
     */
    public static HttpResponse<JsonNode> makeRequest(String text) {
        try {

            return Unirest.post("https://japerk-text-processing.p.mashape.com/phrases/")
                    .header("X-Mashape-Key", MASHAPE_KEY)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Accept", "application/json")
                    .field("language", "english")
                    .field("text", text)
                    .asJson();

        } catch (UnirestException e) {
            e.printStackTrace();
            return null;
        }
    }
}
