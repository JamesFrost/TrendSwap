package me.jamesfrost.trendswap;

import twitter4j.Status;
import twitter4j.Trends;

import java.util.List;

/**
 * Created by James on 21/01/2015.
 */
public class Controller {

    public Controller() {
    }

    public void run() {
        TwitterHelper twitterHelper = new TwitterHelper();
        List<Status> headlines = twitterHelper.getNews();
        Trends trends = twitterHelper.getTrends();




    }
}
