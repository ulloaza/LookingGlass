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
	public void createAppointment(String task, String time, int day, int month, int year) {
		Appointment appt = new Appointment(task, time, day, month, year);
		Day calDay = new Day(day, month, year);
		int index = dayExists(calDay);
		if(index == -1) {
			calDay.addAppointment(appt);
			days.add(calDay);
		}
		else {
			Day temp = days.get(index);
			temp.addAppointment(appt);
		}
		calDay = null;
	}
	public void deleteAppointment(String task, String time, int day, int month, int year) {
		Day calDay = new Day(day, month, year);
		int index = dayExists(calDay);
		if(index == -1) {
			return;
		}
		else {
			Day temp = days.get(index);
			temp.removeAppointment(task);
		}
	}
	public void editAppointment(String task, String time, int day, int month, int year, String newTask) {
		Day calDay = new Day(day, month, year);
		int index = dayExists(calDay);
		if(index == -1) {
			return;
		}
		else {
			calDay = days.get(index);
			calDay.editAppointmentTask(task, newTask);
		}
	}
	public void deleteNote(String note, String time, int day, int month, int year) {
		Day calDay = new Day(day, month, year);
		int index = dayExists(calDay);
		if(index == -1) {
			return;
		}
		else {
			Day temp = days.get(index);
			temp.removeNote(note);
			temp = null;
		}
	}
	public void createNote(String note, int day, int month, int year) {
		Day calDay = new Day(day, month, year);
		int index = dayExists(calDay);
		if(index == -1) {
			calDay.addNote(note);
			days.add(calDay);
		}
		else {
			Day temp;
			temp = days.get(index);
			temp.addNote(note);
		}
		calDay = null;
	}
	public ArrayList<Appointment> getAppointmentsSpecifiedDate(int day, int month, int year) {
		Day target = new Day(day, month, year);
		Day temp = null;
		for(int i = 0;i<days.size();i++) {
			temp = days.get(i);
			if(temp.equals(target)) {
				break;
			}
		}
		target = null;
		return temp.getAppointments();
	}
	
	
	
}
