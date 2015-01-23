import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Trend;
import twitter4j.Trends;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by James on 21/01/2015.
 */
public class Collector {

    public static ArrayList<Haystack> collect() {
        ArrayList<String> swapableTrends = new ArrayList<String>();
        TwitterHelper twitterHelper = new TwitterHelper();
        Trends trends = twitterHelper.getTrends();
        Trend[] currentTrends = trends.getTrends();

        for (Trend currentTrend : currentTrends) {
            String tmp = currentTrend.getName();
            if (!tmp.contains("#")) {
                swapableTrends.add(tmp);
            }
        }

        ArrayList<Haystack> tweets = new ArrayList<Haystack>();

        for(String trend : swapableTrends) {
            QueryResult queryResult = twitterHelper.getTweets(trend, swapableTrends.size());
            List<Status> trendTweets = queryResult.getTweets();

            ArrayList<Status> tmp = new ArrayList<Status>();

            for(int i = 0; i < swapableTrends.size(); ++i) {
                tmp.add(trendTweets.get(i));
            }
            tweets.add(new Haystack(tmp, trend));
        }
        return tweets;
    }
}
