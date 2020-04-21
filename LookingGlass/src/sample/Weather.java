package sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import org.json.JSONArray;
import org.json.JSONObject;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Weather {
	/*api id*/
	private static final String apiKey = "52670856cb171ae00417a5174e135676";
	/*call for weather icon*/
	private static final String imageUrl = "http://openweathermap.org/img/w/";
	/*api call url*/	
	private static final String url = "http://api.openweathermap.org/data/2.5/forecast?q=san%20antonio,texas&units=imperial&appid=52670856cb171ae00417a5174e135676";

	/*
	 * @purpose = to get the high and low temps of a given day
	 * @notes - param day can range from 0 to 16
	 * @returns - an array in the order: high, low
	 */
	public static int[] getWeather(int day) throws IOException {
		/*create array of size 2 storing: high temp, low temp*/
		int highLow[] = new int[2];
		/*establishing connection to api */
		URL call = new URL(url);
		URLConnection u = call.openConnection();
		BufferedReader input = new BufferedReader(new InputStreamReader(u.getInputStream()));
		/*parsing json object from api call*/
		JSONObject data = new JSONObject(input.readLine());
		JSONArray dataArray = data.getJSONArray("list");
		/*max temp is being stored into index 0*/
		highLow[0] = (int) dataArray.getJSONObject(day).getJSONObject("main").getDouble("temp_max");
		/*low temp is being stored into index 1*/
		highLow[1] = (int) dataArray.getJSONObject(day).getJSONObject("main").getDouble("temp_min");
		input.close();
		return highLow;
	}
	/*
	 * @purpose gets the url for the given weather icon on a given day
	 * @returns icon image
	 */
	public static ImageView getIcon(int day) throws IOException{
		URL call = new URL(url);
		URLConnection u = call.openConnection();
		BufferedReader input = new BufferedReader(new InputStreamReader(u.getInputStream()));
		/*parsing json object from api call*/
		JSONObject data = new JSONObject(input.readLine());
		JSONArray dataArray = data.getJSONArray("list");
		String icon = dataArray.getJSONObject(day).getJSONArray("weather").getJSONObject(0).getString("icon");
		
		Image image = new Image(imageUrl + icon + ".png");
        ImageView imageView = new ImageView(image); 
        return imageView;
	}	
}

