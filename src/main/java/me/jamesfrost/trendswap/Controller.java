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
 * Created by James on 21/01/2015.
 */
public class Controller {

    public Controller() {
    }

    public void run() {

        int noTrends;
        int success = 0;

        NamedEntityExtractor namedEntityExtractor = new NamedEntityExtractor();
        UrlRemover urlRemover = new UrlRemover();
        ApiHelper apiHelper = new ApiHelper();
        TwitterHelper twitterHelper = new TwitterHelper();
        ArrayList<Trend> trends = twitterHelper.getTrends();
        List<Status> headlines = twitterHelper.getNews();

        noTrends = trends.size();

        Date date = twitterHelper.getTimeOfLastTweet();

        for (Trend trend : trends) {

            if (headlines.get(0).getCreatedAt().before(date))
                break;

            String headline = headlines.get(0).getText();
            headline = urlRemover.remove(headline);

            System.out.println("Headline: " + headline);
            System.out.println("Trend Context:" + twitterHelper.getTrendTweet(trend).get(0).getText());

            HttpResponse<JsonNode> apiResponce = apiHelper.makeRequest(twitterHelper.getTrendTweet(trend).get(0).getText());
            String trendEntity = namedEntityExtractor.extractTrendEntity(trend.getName(), apiResponce);

            if(trendEntity == null)
                continue;

            apiResponce = apiHelper.makeRequest(headline);
            String matchedText = namedEntityExtractor.matchEntity(trendEntity, apiResponce);

            System.out.println("Trend: " + trend.getName());
            System.out.println("Trend Entity: " + trendEntity);
            System.out.println("Matched Text: " + matchedText);

            String tmp = "";
            if (matchedText != null)
                tmp = TweetGenerator.generateTweet(headline, matchedText, trend.getName());

            if (tmp.contains(trend.getName())) {
                System.out.println("\nGenerated tweet: \n" + tmp);
                try {
                    twitterHelper.tweet(new StatusUpdate(tmp));
                    headlines.remove(0);
                    ++success;
                } catch (TwitterException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("---------------------------------------------------");
        }

        try {
            Unirest.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
        new LoggerHelper().log(success / noTrends);
    }
}
