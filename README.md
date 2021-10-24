# Grizzle Guide
Application focused on connecting customers with local campsites and outdoor activites

In creating this app, the team decided that the most important factors revolved around the user's ability to determine their location, to realize the closest places for their activities, and to have the local weather readily accessible in the case of any last-minute emergencies. In order to accomodate these factors, a series of APIs were used, most notably those from the Google Cloud Platform. These APIs were incorporated into Java on the Android Studio platform, with some additional JavaFX used for the GUI. The only API used outside of the google cloud platform is the weather API, which tracks weather data for the specified area. Many of the Google APIs centered around location services, therefore utilizing Google Maps APIs the most. Specifically Places, Maps SDK and Geocoding, with some attempts at using Geolocating for real-time location updates.

## APIs

OpenWeather Map - collected local weather data at each location  
Places - finds nearby locations, specifically parks and hiking spots  
Maps SDK - general Maps API for Android  
Geocoding - takes in physcal address and retrieves lattitude and longitude for address
