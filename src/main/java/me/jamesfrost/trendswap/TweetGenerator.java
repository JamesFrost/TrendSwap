package me.jamesfrost.trendswap;

/**
 * Created by James on 25/01/2015.
 */
public class TweetGenerator {

    public static String generateTweet(String originalTweets, String needle, String replacement) {

        String tmp;
        if (needle.contains("#"))
            tmp = originalTweets.replace(needle.replace("# ", "#"), replacement);
        else {

            if (originalTweets.contains(needle))
                tmp = originalTweets.replace(needle, replacement);
            else
                tmp = originalTweets.replace(needle.replace(" ", ""), " " + replacement + " ");
        }

        tmp = tmp.trim();
        tmp = tmp.replaceAll("#", "");
        tmp = tmp.replaceAll(":", "");
        tmp = tmp.replaceAll("\\s+", " ");

        return tmp;
    }

}
