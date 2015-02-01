package me.jamesfrost.trendswap;

/**
 * Generates tweets.
 * <p/>
 * Created by James Frost on 25/01/2015.
 */
public class TweetGenerator {

    /**
     * Generates a Tweet where
     *
     * @param originalTweet
     * @param needle        Text to search for in the original tweet
     * @param replacement   Text to replace the needle with
     * @return Tweet with needle swapped with replace
     */
    public static String generateTweet(String originalTweet, String needle, String replacement) {

        String tmp;
        if (needle.contains("#"))
            tmp = originalTweet.replace(needle.replace("# ", "#"), replacement);
        else {
            if (originalTweet.contains(needle))
                tmp = originalTweet.replace(needle, replacement);
            else
                tmp = originalTweet.replace(needle.replace(" ", ""), " " + replacement + " ");
        }

        tmp = tmp.replaceAll("#", "").replaceAll(":", "").replaceAll("\\s+", " ");
        tmp = tmp.trim();

        return tmp;
    }

}
