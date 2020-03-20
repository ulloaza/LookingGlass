package sample;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/*
 * "Looking Glass" - Class MyCalendar
 * @purpose Models an a day for a user calendar
 * @class CS3443.003
 * @author Gilberto Ramirez vwz745
 */
public class MyCalendar implements Serializable {
	
	/*stores username/password*/
	private String user, password;
	/*stores list of all available users*/
	private static ArrayList<String> userList = new ArrayList<String>();
	
	
	
	private static String months[] = {"", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	private static int daysInMonth[] = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	
	/*A dynamic array of all the days holing appointment/note information*/
	private ArrayList<Day> dayList = new ArrayList<Day>();
	/*
	 * Invalid constructor for MyCalendar
	 */
	public MyCalendar() {
		
	}
	/* 
	 * Constructor for MyCalender Class
	 * @param String user, String password
	 */
	public MyCalendar(String user, String password) {
		this.user = user;
		this.password = password;
		addUser(user);
	}
	/*
	 * @purpose - adds user to the userlist so we know how many/what users are available
	 * @param = the user name
	 */
	public static void addUser(String user) {
		userList.add(user);
	}
	/*
	 * @purpose - get the name of all users
	 * @returns - the user list
	 */
	public static ArrayList<String> getUserList() {
		return userList;
	}
	/*
	 * @purpose returns the numbers of days withing a given month
	 * @param - int month - 1,12
	 * @return numbers of days withing the month array
	 */
	public static int getDaysInMonth(int month) {
		return daysInMonth[month];
	}
	/*
	 * @purpose check if given year is a leap year
	 * @return - boolean 
	 */
	public static boolean isLeapYear(int year) {
		if (year % 4 == 0) {
			return true;
		}
		else {
			return false;
		}
	}
	public static String monthToString(int month) {
		return months[month];
	}
	/*
	 * @purpose return user calendars name
	 */
	public String getUser() {
		return user;
	}
	/*
	 * @purpose return user calendars password
	 */
	public String getPassword() {
		return password;
	}
	/*
	 * @pupose return the index of a particular day from the arraylist
	 * @returns i - index
	 */
	public int findDay(Day day) {
		int i;
		/*iterate through the arraylist*/
		for(i=0;i<dayList.size();i++) {
			Day tempDay = dayList.get(i);
			if(tempDay.equals(day)) {
				return i;
			}
		}
		/*if not found*/
		return -1;
	}
	/*
	 * @purpose creates new appointment 
	 * @param String task, String startTime, String endTime, int day, int month, int year
	 */
	public void createAppointment(String task, int startHour, int startMinute, int endHour, int endMinute, int day, int month, int year) {
		Appointment appt = new Appointment(task, startHour, startMinute, endHour, endMinute, day, month, year);
		Day calDay = new Day(day, month, year);
		int index = findDay(calDay);
		/*if day does not exists yet, then create new day obj*/
		if(index == -1) {
			calDay.addAppointment(appt);
			dayList.add(calDay);
		}
		/*else add to existing day object*/
		else {
			calDay = dayList.get(index);
			calDay.addAppointment(appt);
		}
	}
	/*
	 * @purpose deletes  appointment 
	 * @param String task, String startTime, String endTime, int day, int month, int year
	 */
	public void deleteAppointment(int startHour, int startMinute, int endHour, int endMinute, int day, int month, int year) {
		Day calDay = new Day(day, month, year);
		int index = findDay(calDay);
		if(index == -1) {
			return;
		}
		else {
			calDay = dayList.get(index);
			Appointment tempAppt = new Appointment(startHour, startMinute, endHour, endMinute, day, month, year);
			calDay.removeAppointment(tempAppt);
		}
	}
	/*
	 * @purpose edits  appointment 
	 * @param String task, String startTime, String endTime, int day, int month, int year, String newTask
	 */
	public void editAppointment(int startHour, int startMinute, int endHour, int endMinute, int newStartHour, int newStartMinute, int newEndHour, int newEndMinute, String newTask, int day, int month, int year) {
		Day calDay = new Day(day, month, year);
		int index = findDay(calDay);
		if(index == -1) {
			return;
		}
		else {
			calDay = dayList.get(index);
			Appointment tempAppt = new Appointment(startHour, startMinute, endHour, endMinute, day, month, year);
			calDay.removeAppointment(tempAppt);
			tempAppt = new Appointment(newTask, newStartHour, newStartMinute, newEndHour, newEndMinute, day, month, year);
			calDay.addAppointment(tempAppt);
		}
	}
	/*
	 * @purpose edits note 
	 * @param String note, int day, int month, int year, String newNote
	 */
	public void editNote(String note, int day, int month, int year, String newNote) {
		Day calDay = new Day(day, month, year);
		int index = findDay(calDay);
		if(index == -1) {
			return;
		}
		else {
			calDay = dayList.get(index);
			calDay.editNote(note, newNote);
		}
	}
	/*
	 * @purpose deletes note 
	 * @param String note, int day, int month, int year
	 */
	public void deleteNote(String note, int day, int month, int year) {
		Day calDay = new Day(day, month, year);
		int index = findDay(calDay);
		if(index == -1) {
			return;
		}
		else {
			calDay = dayList.get(index);
			calDay.removeNote(note);
		}
	}
	/*
	 * @purpose creates note 
	 * @param String note, int day, int month, int year
	 */
	public void createNote(String note, int day, int month, int year) {
		Day calDay = new Day(day, month, year);
		int index = findDay(calDay);
		if(index == -1) {
			calDay.addNote(note);
			dayList.add(calDay);
		}
		else {
			calDay = dayList.get(index);
			calDay.addNote(note);
		}
	}
	/*
	 * @purpose get the all appts for specific day
	 * @param int day, int month, int year
	 * @returns array list of appts
	 */
	public ArrayList<Appointment> getAppointmentsSpecifiedDate(int day, int month, int year) {
		Day calDay = new Day(day, month, year);
		Day tempDay = null;
		for(int i = 0;i<dayList.size();i++) {
			tempDay = dayList.get(i);
			if(tempDay.equals(calDay)) {
				return tempDay.getAppointments();
			}
		}
		return calDay.getAppointments();
	}
	/*
	 * @purpose get the all notes for specific day
	 * @param int day, int month, int year
	 * @returns array list of notes
	 */
	public ArrayList<String> getNotesSpecifiedDate(int day, int month, int year) {
		Day calDay = new Day(day, month, year);
		Day tempDay = null;
		for(int i = 0;i<dayList.size();i++) {
			tempDay = dayList.get(i);
			if(tempDay.equals(calDay)) {
				return tempDay.getNotes();
			}
		}
		return calDay.getNotes();
	}
	/*
	 * @purpose get spec day object
	 * @param int INDEX
	 * @returns day object
	 */
	public Day getDayObject(int index) {
		return dayList.get(index);
	}
	/*
	 * @purpose checks  to see if time slot is taken already
	 * @param appt object
	 * @return true if it is take, false otherwise
	 */
	public boolean isTimeTaken(Appointment appt) {
		Calendar start1 = new GregorianCalendar(appt.getYear(), appt.getMonth(), appt.getDay(), appt.getStartHour(), appt.getStartMinute());
		Calendar end1 = new GregorianCalendar(appt.getYear(), appt.getMonth(), appt.getDay(), appt.getEndHour(), appt.getEndMinute());
		
		ArrayList<Appointment> apptList = getAppointmentsSpecifiedDate(appt.getDay(), appt.getMonth(), appt.getYear());
		for(Appointment a : apptList) {
			Calendar start2 = new GregorianCalendar(a.getYear(), a.getMonth(), a.getDay(), a.getStartHour(), a.getStartMinute());
			Calendar end2 = new GregorianCalendar(a.getYear(), a.getMonth(), a.getDay(), a.getEndHour(), a.getEndMinute());
			if(start1.before(end2) && start2.before(end1)) {
				return true;
			}
		}
		return false;
	}
}

