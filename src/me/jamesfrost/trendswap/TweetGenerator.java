package me.jamesfrost.trendswap;

import twitter4j.Status;
import twitter4j.StatusUpdate;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by James on 21/01/2015.
 */
public class TweetGenerator {
    public static ArrayList<StatusUpdate> generate(ArrayList<Haystack> harvestedTweets) {
        ArrayList<StatusUpdate> generatedTweets = new ArrayList<StatusUpdate>();

        System.out.println("Generator---------------- ");

        for (Haystack needle : harvestedTweets) {
            for (Haystack haystack : harvestedTweets) {
                if (!haystack.getTrend().equals(needle.getTrend())) {

                    Status haystackTweet = haystack.getTweets().get(0);
                    String tmp = haystackTweet.getText();

                    System.out.println("Original: " + tmp);

                    String trend = haystack.getTrend();

                    System.out.println("Haystack Trend: " + trend + " --- Needle Trend: " + needle.getTrend());

                    trend = trend.trim();
                    trend = trend.replace(" ", "[\\s]*[\\w]*[\\s]*");
                    trend = "\\b[#|@]?" + trend + "\\b";

                    Pattern pattern = Pattern.compile(trend, Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(tmp);
                    tmp = matcher.replaceAll(needle.getTrend());

                    System.out.println("Trend regex: " + trend);

                    String urlPattern = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
                    pattern = Pattern.compile(urlPattern, Pattern.CASE_INSENSITIVE);
                    matcher = pattern.matcher(tmp);
                    tmp = matcher.replaceAll("");
                    tmp = tmp.trim();

                    if (!haystackTweet.isRetweet())
                        tmp = tmp + " via @" + haystackTweet.getUser().getScreenName();
                    else
                        tmp = tmp + " via @" + haystackTweet.getRetweetedStatus().getUser().getScreenName();

                    if (tmp.contains(needle.getTrend())) {
                        StatusUpdate status = new StatusUpdate(tmp);
                        status.setInReplyToStatusId(haystackTweet.getId());
                        generatedTweets.add(status);
                        System.out.println("Generated: " + tmp);
                    }

                    System.out.println("");

                    haystack.getTweets().remove(0);
                }
            }
            System.out.println("-------------------------------------------");
        }
        return generatedTweets;
    }
}
