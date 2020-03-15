package sample;

import java.util.*;

/*
 * Class MyCalender
 * @purpose - used to store user 
 */
public class MyCalender implements java.io.Serializable {
	
	
	private String user;
	
	private String months[] = {"", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	private int daysInMonth[] = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	
	/*A dynamic array of all the days holing appointment/note information*/
	private ArrayList<Day> days = new ArrayList<Day>();
	
	Calendar calendar = Calendar.getInstance();
	
	
	public MyCalender(String user) {
		this.user = user;
		
	}
	
	
	public int getDaysInMonth(int month) {
		return daysInMonth[month];
	}
	
	public String getUser() {
		return user;
	}
	
	public boolean isLeapYear(int year) {
		if (year % 4 == 0) {
			return true;
		}
		else {
			return false;
		}
	}
	public String monthToString(int month) {
		return months[month];
	}
	
	public int dayExists(Day day) {
		int i;
		Day temp;
		for(i=0;i<days.size();i++) {
			temp = days.get(i);
			if(temp.equals(day)) {
				return i;
			}
		}
		return -1;
	}
	
	
	
}
