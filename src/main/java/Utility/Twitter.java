package Utility;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import  Models.Tweet;


public class Twitter {

    private static Utility.Config config;

    /**
     * Ciste ztahuje data z API twitteru podle klicoveho slova, API url twitteru a bearer token jsou v config souboru
     * @param keyword klicove slovo
     * @return vraci list tweetu bez sentimentu
     * @throws Exception
     */

    public  List<Tweet> getTodayTweet(String keyword) throws Exception {
       config = new Config();
        String bearerToken = config.getBearerToken();

        URL url =  new URL(String.format(config.getUrl(),URLEncoder.encode(keyword,StandardCharsets.UTF_8.toString())));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestProperty("Authorization","Bearer "+ bearerToken);


        conn.setRequestProperty("Content-Type","application/json");
        conn.setRequestMethod("GET");


        BufferedReader in = new  BufferedReader(new InputStreamReader(conn.getInputStream()));
        String output;

        StringBuffer response = new StringBuffer();
        while ((output = in.readLine()) != null) {
            response.append(output);
        }

        in.close();
        System.out.println("Response:-" + response.toString());

        JSONObject obj = new JSONObject(response.toString());
        JSONArray obj1 =  obj.optJSONArray("data");

        List<Tweet> tweetList = new ArrayList<Tweet>();
        for (int i = 0; i < obj1.length(); i++){
            JSONObject o = obj1.getJSONObject(i);

            Tweet t = new Tweet(o.getString("created_at"),o.getString("author_id"),o.getString("id"),o.getString("text"),"");
            tweetList.add(t);
        }



        return tweetList;


    }
}