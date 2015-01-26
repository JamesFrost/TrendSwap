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
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("tCguJT4KT1EAjdKaOhD6xLa0H")
                .setOAuthConsumerSecret("7IxUAbnNJRalmbToj66afgizoBLuDWRdhdakCFE646HuvGf7vz")
                .setOAuthAccessToken("2985673373-8oFAiRktrOkjhyqzbVmrrfPc3HSW1zq8tt7j4w2")
                .setOAuthAccessTokenSecret("JrfwTmlrRbhBqzy7rKVlBnQeTbdEFITkadDnx63U2zfDa");
        return cb;
    }

    /**
     * Gets tweets for a specific trend.
     *
     * @return Tweets found using the search term
     */
    public List<Status> getNews() {

        List<Status> statuses = null;
        try {
            Twitter unauthenticatedTwitter = new TwitterFactory(getAuth().build()).getInstance();
            Paging paging = new Paging(1, 50);
            statuses = unauthenticatedTwitter.getUserTimeline("Reuters", paging);
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        return statuses;
    }

    public Date getTimeOfLastTweet() {
        Twitter twitter = new TwitterFactory(getAuth().build()).getInstance();
        Date date = null;
        try {
            ResponseList<Status> timeline = twitter.getUserTimeline();
            date = timeline.get(0).getCreatedAt();
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return date;
    }

    public List<Status> getTrendTweet(Trend trend) {

        List<Status> statuses = null;
        Twitter twitter = new TwitterFactory(getAuth().build()).getInstance();

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
