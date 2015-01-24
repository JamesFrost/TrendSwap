package me.jamesfrost.trendswap;

import twitter4j.*;

/**
 * Handles all interactions with Twitter.
 * <p/>
 * Created by James Frost on 30/10/2014.
 */
public class TwitterHelper implements Constants {

    /**
     * Gets tweets for a specific trend.
     *
     * @param trend The search term
     * @return Tweets found using the search term
     */
    public QueryResult getTweets(String trend, int numberOfTweets) {
        Query query = new Query(trend);
        Twitter twitter = new TwitterFactory().getInstance();
        query.count(numberOfTweets);
        query.resultType(Query.ResultType.popular);
        try {
            return twitter.search(query);
        } catch (TwitterException e) {
            e.printStackTrace();
            return null;
        }
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
            System.out.println("Showing available trends");

            for (int i = 0; i < trends.getTrends().length; i++) {
                System.out.println(trends.getTrends()[i].getName());
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
