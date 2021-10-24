import java.io.IOException;
import java.util.ArrayList;

public class PlacesDriver {

    public ArrayList<Object> getInfo(String inputAddress) throws IOException {
        ArrayList <Object> allData = new ArrayList<>();
        ArrayList <String> names = new ArrayList<String>();
        ArrayList <String> descriptions = new ArrayList<String>();
        int[] temps = new int[5];
        int[] humidities = new int[5];


        GeocodingAPI myAddy = new GeocodingAPI(inputAddress);
        myAddy.getJSON();
        PlacesAPI atlanta = new PlacesAPI(myAddy.getLatitude(), myAddy.getLongitude());
        atlanta.getJSON();

        for (int i = 0; i < atlanta.getName().size(); i++) {
            double destinationLat = atlanta.getLat()[i];
            double destinationLong = atlanta.getLong()[i];
            WeatherAPI atlantaWeather = new WeatherAPI(destinationLat, destinationLong);
            atlantaWeather.getJSON();
            descriptions.add(atlantaWeather.getWeather());
            names.add(atlanta.getName().get(i));
            temps[i] = (atlantaWeather.getTemp());
            humidities[i] = (atlantaWeather.getTemp());
//            String pinInfo = (i+1) + ". " + atlanta.getName().get(i) + ": There is " + atlantaWeather.getWeather() +
//                " with a temperature of " + atlantaWeather.getTemp() + "" + "°F and a humidity of " +
//                atlantaWeather.getHumidity() + "%.";
//            descriptions.add(pinInfo);
        }
        allData.add(names);
        allData.add(descriptions); // weather
        allData.add(temps);
        allData.add(humidities);
        return allData;
    }
}
