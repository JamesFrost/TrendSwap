package me.jamesfrost.trendswap;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Handles all interactions with Twitter.
 * <p/>
 * Created by James Frost on 30/10/2014.
 */
public class TwitterHelper implements Constants {

    private ConfigurationBuilder getAuth() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        //Auth details here
        return cb;
    }

    /**
     * Gets tweets from a twitter new account.
     *
     * @return News tweets
     */
    public List<Status> getNews() {
        try {
            Twitter unauthenticatedTwitter = new TwitterFactory(getAuth().build()).getInstance();
            return unauthenticatedTwitter.getUserTimeline(NEWS_ACCOUNT, new Paging(1, 50));
        } catch (TwitterException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gets the date of the last tweet on the account.
     *
     * @return Date of the last tweet
     */
    public Date getDateOfLastTweet() {
        Twitter twitter = new TwitterFactory(getAuth().build()).getInstance();
        try {
            ResponseList<Status> timeline = twitter.getUserTimeline();
            return timeline.get(0).getCreatedAt();
        } catch (TwitterException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gets the most popular tweet for a trend.
     *
     * @param trend Trend to get the tweet for
     * @return The most popular tweet for a trend
     */
    public List<Status> getTrendTweet(Trend trend) {

        Twitter twitter = new TwitterFactory(getAuth().build()).getInstance();
        Query query = new Query(trend.getName());
        query.count(1);
        query.resultType(Query.ResultType.popular);

        try {
            return twitter.search(query).getTweets();
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
    public ArrayList<Trend> getTrends() {
        try {
            Twitter twitter = new TwitterFactory(getAuth().build()).getInstance();
            Trends trends = twitter.getPlaceTrends(LOCATION_WOEID);
            ArrayList<Trend> nonHashTagTrends = new ArrayList<Trend>();

            for (int i = 0; i < trends.getTrends().length; ++i) {
                if (!trends.getTrends()[i].toString().contains("#")) {
                    nonHashTagTrends.add(trends.getTrends()[i]);
                }
            }

            return nonHashTagTrends;
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
    public void tweet(StatusUpdate tweet) throws TwitterException {
        Twitter twitter = new TwitterFactory(getAuth().build()).getInstance();
        twitter.updateStatus(tweet);
    }
}
