package me.jamesfrost.trendswap;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by James on 25/01/2015.
 */
public class UrlRemover {
    public String remove(String text) {
        String urlPattern = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern pattern = Pattern.compile(urlPattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);

        int i = 0;
        while (matcher.find()) {
            text = text.replaceAll(matcher.group(i),"").trim();
            i++;
        }

        return text;
    }
}
