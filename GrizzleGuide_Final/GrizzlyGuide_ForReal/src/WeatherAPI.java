import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class WeatherAPI {

    private double latitude;
    private double longitude;
    private String weather;
    private int temperature;
    private int humidity;

    WeatherAPI(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.weather = "unknown";
        this.temperature = 0;
        this.humidity = 0;
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public void getJSON () throws IOException {
        String base = "http://api.openweathermap.org/data/2.5/weather?lat=" + this.latitude +
            "&lon=" + this.longitude + "&appid=3692f3cb7d94a4d034490c1bce9cd709";
        JSONObject json = readJsonFromUrl(base);
        List<String> toOutput = new ArrayList<String>();
        toOutput = jsonDecode(json.toString());

        this.weather = toOutput.get(0);
        double temporaryTemp = Double.parseDouble(toOutput.get(1));
        temporaryTemp = (((temporaryTemp) - 273.15) * 1.8) + 32;
        this.temperature = (int) Math.round(temporaryTemp);
        this.humidity = Integer.parseInt(toOutput.get(2));
    }

    private static ArrayList jsonDecode (String jsonData) {
        JSONObject categories = new JSONObject(jsonData);
        JSONArray weather = categories.getJSONArray("weather");
        JSONObject weatherObject = weather.getJSONObject(0);
        String description = weatherObject.getString("description");
        String returnWeather = description;

        JSONObject main = categories.getJSONObject("main");
        String temp = String.valueOf(main.getInt("temp"));
        String humidity = String.valueOf(main.getInt("humidity"));

        ArrayList<String> returnData = new ArrayList<String>();
        returnData.add(description);
        returnData.add(temp);
        returnData.add(humidity);
        return returnData;
    }

    public String getWeather() {
        return this.weather;
    }

    public int getTemp() {
        return this.temperature;
    }

    public int getHumidity() {
        return this.humidity;
    }
}
