import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import org.json.JSONException;

public class PlacesAPI {

    private double latitude;
    private double longitude;
    private ArrayList<String> name;
    private double[] parkLat;
    private double[] parkLong;

    PlacesAPI(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = new ArrayList<>();
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
        String base = "https://maps.googleapis.com/maps/api/place/nearbysearch/json" +
            "?location=" + Double.toString(this.latitude) + "%2C" + Double.toString(this.longitude) + "&radius=10467&type=park&keyword=hiking" +
            "&key=AIzaSyCZg22cxRacIH1iljp7g6AYKixC5kSEb8U";
        JSONObject json = readJsonFromUrl(base);
        ArrayList<Object> toOutput = new ArrayList<Object>();
        toOutput = jsonDecode(json.toString());
        this.name = (ArrayList) toOutput.get(0);
        this.parkLat = (double[]) toOutput.get(1);
        this.parkLong = (double[]) toOutput.get(2);
    }

    private static ArrayList<Object> jsonDecode (String jsonData) {
        JSONObject categories = new JSONObject(jsonData);
        JSONArray results = categories.getJSONArray("results");
        int size = results.length();
        size = Math.min(size, 5);


        ArrayList<String> nameList = new ArrayList<>();
        double[] parkLatList = new double[size];
        double[] parkLongList = new double[size];

        for (int i = 0; i < size; i++) {
            JSONObject locales = results.getJSONObject(i);
            String name = locales.getString("name");
            JSONObject geometry = locales.getJSONObject("geometry");
            JSONObject viewport = geometry.getJSONObject("viewport");
            JSONObject southwest = viewport.getJSONObject("southwest");
            double localeLat = southwest.getDouble("lat");
            double localeLong = southwest.getDouble("lng");
            nameList.add(name);
            parkLatList[i] = localeLat;
            parkLongList[i] = localeLong;
        }
        ArrayList<Object> allOutput = new ArrayList<>();
        allOutput.add(nameList);
        allOutput.add(parkLatList);
        allOutput.add(parkLongList);
        return allOutput;
    }

    public ArrayList<String> getName() {
        return this.name;
    }

    public double[] getLat() {
        return this.parkLat;
    }

    public double[] getLong() {
        return this.parkLong;
    }
}
