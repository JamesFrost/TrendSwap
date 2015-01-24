package me.jamesfrost.trendswap;

/**
 * Created by James on 24/01/2015.
 */
public class ApiHelper {
    public static final String API_URL = "http://text-processing.com/api/tag";

    public static void makeRequest() {
        HttpResponse<JsonNode> response = Unirest.post("https://japerk-text-processing.p.mashape.com/phrases/")
                .header("X-Mashape-Key", "r8XGSVhp35mshOIRMX2zNL4UFcgAp1SxfsYjsnzSc6reBspESZ")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Accept", "application/json")
                .field("language", "spanish")
                .field("text", "California is nice")
                .asJson();
    }

}
