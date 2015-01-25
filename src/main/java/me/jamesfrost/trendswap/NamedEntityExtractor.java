package me.jamesfrost.trendswap;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by James on 25/01/2015.
 */
public class NamedEntityExtractor {
    public String extractTrendEntity(String trend, HttpResponse<JsonNode> apiResponce) {

        String namedEntityFound = null;
        JSONObject jsonObject = apiResponce.getBody().getObject();
        JSONArray keys = jsonObject.names();

        outerloop:
        for (int i = 0; i < jsonObject.length(); ++i) {
            JSONArray jsonArray = jsonObject.getJSONArray(String.valueOf(keys.get(i)));

            for (int j = 0; j < jsonArray.length(); ++j) {
                if (jsonArray.get(j).equals(trend)) {
                    namedEntityFound = keys.get(i).toString();
                    System.out.println("Found!");
                    break outerloop;
                }
            }

        }
        return namedEntityFound;
    }

    public String matchEntity(String entity, HttpResponse<JsonNode> apiResponce) {

        String matchedText = null;
        JSONObject jsonObject = apiResponce.getBody().getObject();
        JSONArray keys = jsonObject.names();

        for (int i = 0; i < jsonObject.length(); ++i) {
            JSONArray jsonArray = jsonObject.getJSONArray(String.valueOf(keys.get(i)));

            if (keys.get(i).toString().equals(entity)) {
                matchedText = jsonArray.get(0).toString();
                break;
            }

        }

        return matchedText;

    }

}
