package me.jamesfrost.trendswap;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Trend;
import twitter4j.TwitterException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Trend Swap controller.
 * <p/>
 * Created by James Frost on 21/01/2015.
 */
public class Controller {

    public void run() {

        NamedEntityHelper namedEntityHelper = new NamedEntityHelper();
        TwitterHelper twitterHelper = new TwitterHelper();

        ArrayList<Trend> trends = twitterHelper.getTrends();
        List<Status> headlines = twitterHelper.getNews();
        Date dateOfLastTweet = twitterHelper.getDateOfLastTweet();

        for (Trend trend : trends) {

            if (headlines.get(0).getCreatedAt().before(dateOfLastTweet))
                break;
            if (headlines.get(0).isRetweet()) {
                headlines.remove(0);
                continue;
            }

            String headline = headlines.get(0).getText();
            headline = UrlRemover.remove(headline);

            HttpResponse<JsonNode> apiResponce = ApiHelper.makeRequest(twitterHelper.getTrendTweet(trend).get(0).getText());
            String trendEntity = namedEntityHelper.extractTrendEntity(trend.getName(), apiResponce);

            if (trendEntity == null)
                continue;

            apiResponce = ApiHelper.makeRequest(headline);
            String matchedText = namedEntityHelper.matchEntity(trendEntity, apiResponce);

            String tmp = "";
            if (matchedText != null)
                tmp = TweetGenerator.generateTweet(headline, matchedText, trend.getName());

            if (tmp.contains(trend.getName())) {
                try {
                    twitterHelper.tweet(new StatusUpdate(tmp));
                    headlines.remove(0);
                } catch (TwitterException e) {
                    e.printStackTrace();
                }
            }

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        try {
            Unirest.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
