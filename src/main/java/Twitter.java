import org.json.JSONArray;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Twitter {


    public  List<Tweet> getTodayTweet() throws Exception {

        String bearerToken = "AAAAAAAAAAAAAAAAAAAAANw2bQEAAAAA7CxJhaarDSXibKWhDD4tRc8ZmmI%3DZt6Uf03ikTXLcDms2CjwI2O5zB7twnHRztKepYPlthF2rCMOtv";

        URL url = new URL("https://api.twitter.com/2/tweets/search/recent?query=bitcoin&tweet.fields=author_id,created_at,text");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestProperty("Authorization","Bearer "+ bearerToken);


        conn.setRequestProperty("Content-Type","application/json");
        conn.setRequestMethod("GET");


        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String output;

        StringBuffer response = new StringBuffer();
        while ((output = in.readLine()) != null) {
            response.append(output);
        }

        in.close();
        // printing result from response
        System.out.println("Response:-" + response.toString());

        JSONObject obj = new JSONObject(response.toString());
        JSONArray obj1 =  obj.optJSONArray("data");

        List<Tweet> tweetList = new ArrayList<Tweet>();
        for (int i = 0; i < obj1.length(); i++){
            JSONObject o = obj1.getJSONObject(i);

            Tweet t = new Tweet(o.getString("created_at"),o.getString("author_id"),o.getString("id"),o.getString("text"));
            tweetList.add(t);
        }


        return tweetList;


    }
}