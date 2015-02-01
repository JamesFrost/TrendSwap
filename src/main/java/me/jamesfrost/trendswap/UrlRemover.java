package me.jamesfrost.trendswap;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Removes URLs from Strings.
 * <p/>
 * Created by James Frost on 25/01/2015.
 */
public class UrlRemover {

    /**
     * Removes URLs from a String.
     *
     * @param text Text to remove URLs from
     * @return Text with URLs removed
     */
    public static String remove(String text) {
        String urlPattern = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern pattern = Pattern.compile(urlPattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);

        int i = 0;
        while (matcher.find()) {
            text = text.replaceAll(matcher.group(i), "").trim();
            i++;
        }

        return text;
    }
}
