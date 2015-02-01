package me.jamesfrost.trendswap;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Helps process/extract named entities from the API response.
 * <p/>
 * Created by James Frost on 25/01/2015.
 */
public class NamedEntityHelper {

    //All accepted swappable named entities
    private String[] acceptableEntities;

    public NamedEntityHelper() {
        acceptableEntities = new String[4];
        acceptableEntities[0] = "PERSON";
        acceptableEntities[1] = "LOCATION";
        acceptableEntities[2] = "ORGANIZATION";
        acceptableEntities[3] = "GPE";
    }

    /**
     * Extracts the trends named entity from the API response.
     *
     * @param trend       Trend to get the named entity for
     * @param apiResponse Response from the API to extract the trend entity out of
     * @return Trends named entity (null if not found)
     */
    public String extractTrendEntity(String trend, HttpResponse<JsonNode> apiResponse) {

        String namedEntityFound = null;
        JSONObject jsonObject = apiResponse.getBody().getObject();
        JSONArray keys = jsonObject.names();

        outerloop:
        for (int i = 0; i < jsonObject.length(); ++i) {
            JSONArray jsonArray = jsonObject.getJSONArray(String.valueOf(keys.get(i)));

            for (int j = 0; j < jsonArray.length(); ++j) {
                if (jsonArray.get(j).equals(trend) && acceptableEntity(keys.get(i).toString())) {
                    namedEntityFound = keys.get(i).toString();
                    break outerloop;
                }
            }

        }
        return namedEntityFound;
    }

    private boolean acceptableEntity(String key) {
        for (String acceptableEntity : acceptableEntities) {
            if (acceptableEntity.equals(key))
                return true;
        }
        return false;
    }

    /**
     * Searches for text the matches a named entity.
     *
     * @param entity      Entity to match
     * @param apiResponse API response to search through
     * @return Text that matches the named entity passed
     */
    public String matchEntity(String entity, HttpResponse<JsonNode> apiResponse) {

        String matchedText = null;
        JSONObject jsonObject = apiResponse.getBody().getObject();
        JSONArray keys = jsonObject.names();

        for (int i = 0; i < jsonObject.length(); ++i) {
            JSONArray jsonArray = jsonObject.getJSONArray(String.valueOf(keys.get(i)));

            if (keys.get(i).toString().equals(entity)) {
                for (int j = 0; j < jsonArray.length(); ++j) {
                    if (jsonArray.get(j).toString().length() > 1) {
                        matchedText = jsonArray.get(j).toString();
                        break;
                    }
                }
            }
        }

        return matchedText;
    }

}
