package me.jamesfrost.trendswap;

import twitter4j.StatusUpdate;

import java.util.ArrayList;

/**
 * Created by James on 21/01/2015.
 */
public class Controller {

    public Controller() {
    }

    public void run() {
        ArrayList<Haystack> harvestedTweets = Collector.collect();

        ArrayList<StatusUpdate> generatedTweets = TweetGenerator.generate(harvestedTweets);

//        int noOfTweets = 0;
//        for (StatusUpdate tweet : generatedTweets) {
//            try {
//                TwitterHelper.tweet(tweet);
//                ++noOfTweets;
//
//            } catch (TwitterException ignored) {
//
//            }
//            if (noOfTweets >= 25)
//                break;
//            else {
//                try {
//                    Thread.sleep(10000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }

    }
}
