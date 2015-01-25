package me.jamesfrost.trendswap;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by James on 24/01/2015.
 */
public class ApiHelper {

    public HttpResponse<JsonNode> makeRequest(String text) {
        try {

            HttpResponse<JsonNode> responce = Unirest.post("https://japerk-text-processing.p.mashape.com/phrases/")
                    .header("X-Mashape-Key", "r8XGSVhp35mshOIRMX2zNL4UFcgAp1SxfsYjsnzSc6reBspESZ")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Accept", "application/json")
                    .field("language", "english")
                    .field("text", text)
                    .asJson();

            System.out.println("API Responce: " + responce.getBody().toString());

            return responce;

        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return null;
    }

}
