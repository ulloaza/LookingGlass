package sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.GregorianCalendar;

import org.json.JSONArray;
import org.json.JSONObject;
import javafx.scene.image.Image;

/*
 * "Looking Glass" - Class Weather
 * @purpose used to obtain weather date from openweather api call
 * @class CS3443.003
 */
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
	// return 3 days weather
	public static int[][] getWeather(GregorianCalendar date) throws IOException {
		
    	Date date1 = date.getTime();
    	int diff=  (int) (date1.getTime()- new Date().getTime())/86400000;
    	
    	if(diff < 0 ) {
    		System.out.println("weather history is currently not supported\n");
    		diff=0;
    	}
    	else if(diff > 16) {
    		diff = 16;
    	}
    	
		/*create array of size 2 storing: high temp, low temp*/
		int highLow[][] = new int[3][2];
		/*establishing connection to api */
		URL call = new URL(url);
		URLConnection u = call.openConnection();
		BufferedReader input = new BufferedReader(new InputStreamReader(u.getInputStream()));
		/*parsing json object from api call*/
		JSONObject data = new JSONObject(input.readLine());
		JSONArray dataArray = data.getJSONArray("list");
		
		for(int i = 0; i<3; i++)
		{
			/*max temp is being stored into index 0*/
			highLow[i][0] = (int) dataArray.getJSONObject(diff+i).getJSONObject("main").getDouble("temp_max");
			/*low temp is being stored into index 1*/
			highLow[i][1] = (int) dataArray.getJSONObject(diff+i).getJSONObject("main").getDouble("temp_min");
		}
		input.close();
		return highLow;
	}
	/*
	 * @purpose gets the url for the given weather icon on a given day
	 * @returns icon image
	 */
	public static Image[] getIcon(GregorianCalendar date) throws IOException{
    	Date date1 = date.getTime();
    	int diff=  (int) (date1.getTime()- new Date().getTime())/86400000;
    	
    	if(diff < 0 ) {
    		System.out.println("weather history is currently not supported\n");
    		diff=0;
    	}
    	else if(diff > 16) 
    		diff = 16;
    	
		Image image[] = new Image[3];
		URL call = new URL(url);
		URLConnection u = call.openConnection();
		BufferedReader input = new BufferedReader(new InputStreamReader(u.getInputStream()));
		/*parsing json object from api call*/
		JSONObject data = new JSONObject(input.readLine());
		JSONArray dataArray = data.getJSONArray("list");

		for(int i = 0; i<3;i++) {
			String icon = dataArray.getJSONObject(diff+i).getJSONArray("weather").getJSONObject(0).getString("icon");
			image[i] = new Image(imageUrl + icon + ".png");
		}
        return image;
	}	
}