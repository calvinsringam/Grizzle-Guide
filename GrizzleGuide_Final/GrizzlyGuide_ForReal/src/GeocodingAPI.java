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

public class GeocodingAPI {

    private double latitude;
    private double longitude;
    private String address;


    public GeocodingAPI(String address) {
        this.latitude = 0;
        this.longitude = 0;
        this.address = address;
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
        String base = "https://maps.googleapis.com/maps/api/geocode/json?address=" +
            (this.address.replaceAll(" ","%20")) + "&key=AIzaSyCZg22cxRacIH1iljp7g6AYKixC5kSEb8U";
        JSONObject json = readJsonFromUrl(base);
        List<String> toOutput = new ArrayList<String>();
        toOutput = jsonDecode(json.toString());

        this.latitude = Double.parseDouble(toOutput.get(0));
        this.longitude = Double.parseDouble(toOutput.get(1));
    }

    private static ArrayList jsonDecode (String jsonData) {
        JSONObject allJSON = new JSONObject(jsonData);
        JSONArray results = allJSON.getJSONArray("results");
        JSONObject weatherObject = results.getJSONObject(0);
        JSONObject geometry = weatherObject.getJSONObject("geometry");
        JSONObject viewport = geometry.getJSONObject("viewport");
        JSONObject southwest = viewport.getJSONObject("southwest");
        double localeLat = southwest.getDouble("lat");
        double localeLong = southwest.getDouble("lng");

        ArrayList<String> returnData = new ArrayList<String>();
        returnData.add(((Double)localeLat).toString());
        returnData.add(((Double)localeLong).toString());

        return returnData;
    }


    public double getLongitude() {
        return this.longitude;
    }

    public double getLatitude() {
        return this.latitude;
    }
}
