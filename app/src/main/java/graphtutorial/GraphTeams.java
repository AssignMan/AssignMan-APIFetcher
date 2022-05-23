package graphtutorial;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


import static graphtutorial.GraphAuth.graphClient;

public class GraphTeams {
    public static void main(String[] args) throws IOException {
        final GraphTeams scraper = new GraphTeams();
        final String htmlContent = scraper.getContent();
        final String extractedTitle = scraper.extractTitle(htmlContent);
        System.out.println(extractedTitle);
    }

    private String getContent() throws IOException {
        final OkHttpClient client = new OkHttpClient.Builder().build();
        final String urlToScrape = "https://scrapingant.com/blog/web-scraping-java";
        final Request request = new Request.Builder().url(urlToScrape).build();
        final Response response = client.newCall(request).execute();
        return response.body().string();
    }

    private String extractTitle(String content) {
        final Pattern titleRegExp = Pattern.compile("<title>(.*?)</title>", Pattern.DOTALL);
        final Matcher matcher = titleRegExp.matcher(content);
        matcher.find();
        return matcher.group(1);
    }
}
