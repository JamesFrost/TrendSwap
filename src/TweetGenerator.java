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

        for (Haystack haystack : harvestedTweets) {
            for (Haystack needle : harvestedTweets) {
                if (!needle.getTrend().equals(haystack.getTrend())) {
                    Status needleTweet = needle.getTweets().get(0);
                    String tmp = needleTweet.getText();
                    System.out.println("Original: " + tmp);

                    String trend = needle.getTrend();
                    System.out.println("Trend: " + trend + " --- Haystack Trend: " + haystack.getTrend());
                    trend = trend.trim();
                    trend = "\\b" + trend + "\\b";

                    Pattern pattern = Pattern.compile(trend, Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(tmp);
                    tmp = matcher.replaceAll(haystack.getTrend());

                    System.out.println("Trend regex: " + trend);

                    if (!needleTweet.isRetweet())
                        tmp = tmp + " via @" + needleTweet.getUser().getScreenName();
                    else
                        tmp = tmp + "via @" + needleTweet.getRetweetedStatus().getUser().getScreenName();

                    StatusUpdate status = new StatusUpdate(tmp);
                    status.setInReplyToStatusId(needleTweet.getId());

                    generatedTweets.add(status);

                    System.out.println("Generated: " + tmp);
                    System.out.println("");

                    needle.getTweets().remove(0);
                }
            }
            System.out.println("-------------------------------------------");
        }
        return generatedTweets;
    }
}
