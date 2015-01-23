import twitter4j.Status;

import java.util.ArrayList;

/**
 * Created by James on 21/01/2015.
 */
public class Haystack {

    private ArrayList<Status> tweets;
    private String trend;

    public Haystack(ArrayList<Status> tweets, String trend) {
        this.tweets = tweets;
        this.trend = trend;
    }

    public ArrayList<Status> getTweets() {
        return tweets;
    }

    public String getTrend() {
        return trend;
    }
}
