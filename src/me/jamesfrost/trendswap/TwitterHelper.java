package me.jamesfrost.trendswap;

import twitter4j.*;

import java.util.List;

/**
 * Handles all interactions with Twitter.
 * <p/>
 * Created by James Frost on 30/10/2014.
 */
public class TwitterHelper implements Constants {

    /**
     * Gets tweets for a specific trend.
     *
     * @return Tweets found using the search term
     */
    public List<Status> getNews() {

        List<Status> statuses = null;
        try {
            Twitter unauthenticatedTwitter = new TwitterFactory().getInstance();
            Paging paging = new Paging(1, 50);
            statuses = unauthenticatedTwitter.getUserTimeline("BBCBreaking", paging);
            for (Status t : statuses) {
                System.out.println(t.getText());
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        return statuses;
    }

    public List<Status> getTrendTweet(Trend trend) {

        List<Status> statuses = null;
        Twitter twitter = new TwitterFactory().getInstance();

        Query query = new Query(trend.getName());
        query.count(1);
        query.resultType(Query.ResultType.popular);
        try {
            statuses = twitter.search(query).getTweets();
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return statuses;
    }


    /**
     * Gets the trends for a specific location.
     *
     * @return Trends
     */
    public Trends getTrends() {
        try {
            Twitter twitter = new TwitterFactory().getInstance();
            Trends trends = twitter.getPlaceTrends(LOCATION_WOEID);

            for (int i = 0; i < trends.getTrends().length; i++) {
                if (trends.getTrends()[i].toString().contains("#")) {
                    //remove
                }
            }

            return trends;
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get trends: " + te.getMessage());
            return null;
        }
    }

    /**
     * Sends a tweet.
     *
     * @param tweet The text to tweet
     * @throws TwitterException
     */
    public static void tweet(StatusUpdate tweet) throws TwitterException {
        Twitter twitter = new TwitterFactory().getInstance();
        twitter.updateStatus(tweet);
    }
}
