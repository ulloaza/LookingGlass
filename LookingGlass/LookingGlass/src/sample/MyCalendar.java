package sample;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/*
 * "Looking Glass" - Class MyCalendar
 * @purpose Models an a day for a user calendar
 * @class CS3443.003
 */
public class MyCalendar implements Serializable {
	
	//stores username/password
	private String user, password;
	//stores list of all available users
	private static ArrayList<String> userList = new ArrayList<String>();	
	//A dynamic array of all the days holing appointment/note information
	private ArrayList<Day> dayList = new ArrayList<Day>();

	public static final String days[] = {"", "Sun", "Mon", "Tues", "Wed", "Thurs", "Fri", "Sat"};
    
	/*
	 * Invalid constructor for MyCalendar
	 * @purpose - used to create invalid object and load another object into it
	 */
	public MyCalendar() {
		userList = Persistence.getAvailableUsers();
	}
	/* 
	 * Constructor for MyCalender Class
	 * @param String user, String password
	 */
	public MyCalendar(String user, String password) {
		this.user = user;
		this.password = password;
		addUser(user);
		userList = Persistence.getAvailableUsers();
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
	 * @purpose return the index of a particular day from the arraylist
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
	public void createAppointment(String task, String desc, int startHour, int startMinute, int endHour, int endMinute, int day, int month, int year, boolean privacy) {
		Appointment appt = new Appointment(task, desc, startHour, startMinute, endHour, endMinute, day, month, year, privacy);
		//check to see if there is a collision
		if(isTimeTaken(appt)) {
			return;
		}
		Day calDay = new Day(day, month, year);
		int index = findDay(calDay);
		/*if day does not exists yet, then create new day obj*/
		if(index == -1) {
			calDay.addAppointment(appt);
			addDay(calDay);
		}
		/*else add to existing day object*/
		else {
			calDay = dayList.get(index);
			calDay.addAppointment(appt);
		}
	}
	
	public boolean createAppointmentWithInvite(String task, String desc, String invitedMember, int startHour, int startMinute, int endHour, int endMinute, int day, int month, int year, boolean privacy) {
		Appointment appt = new Appointment(task, desc, invitedMember, startHour, startMinute, endHour, endMinute, day, month, year, privacy);
		
		MyCalendar userCal = Persistence.load(invitedMember);
		//check to see if there is a collision
		if(isTimeTaken(appt) || userCal.isTimeTaken(appt)) {
			return false;
		}
		
		Day calDay = new Day(day, month, year);
		int index = findDay(calDay);
		int userIndex = userCal.findDay(calDay);
		
		/*if day does not exists yet, then create new day obj*/
		if(index == -1) {
			calDay.addAppointment(appt);
			addDay(calDay);
		}
		else if (userIndex == -1) {
			calDay.addAppointment(appt);
			userCal.addDay(calDay);
		}
		/*else add to existing day object*/
		else {
			calDay = dayList.get(index);
			calDay.addAppointment(appt);
			
			calDay = userCal.dayList.get(userIndex);
			calDay.addAppointment(appt);
		}
		
		Persistence.save(userCal);
		return true;
	}
	/*
	 * @purpose - deletes an appointment from a specific day
	 * 
	 */
	public void deleteAppointment(int index, int day, int month, int year) {
		Day calDay = new Day(day, month, year);
		int i = findDay(calDay);
		if(index == -1) {
			return;
		}
		else {
			calDay = dayList.get(i);
			//make sure index is not out of bounds
			if(calDay.getAppointments().size() < index) {
				return;
			}
			
			calDay.removeAppointment(index);
		}
	}
	
	/*
	 * @purpose - get an appointment from a specific time
	 * @returns - cal day appt
	 * 
	 */
	public Appointment getAppointment(int index, int day, int month, int year) {
		Day calDay = new Day(day, month, year);
		int i = findDay(calDay);
		if(index == -1) {
			return null;
		}
		else {
			calDay = dayList.get(i);
			//make sure index is not out of bounds
			if(calDay.getAppointments().size() < index) {
				return null;
			}
			
			return calDay.getAppointment(index);
		}
	}
	
	
	/*
	 * @purpose edits  appointment 
	 * @param String task, String startTime, String endTime, int day, int month, int year, String newTask
	 */
	public void editAppointment(int index, String task, String desc, int startHour, int startMinute, int endHour, int endMinute, int day, int month, int year, boolean privacy) {
		Day calDay = new Day(day, month, year);
		int i = findDay(calDay);
		if(i == -1) {
			return;
		}
		else {
			calDay = dayList.get(i);
			//make sure index is not out of bounds
			if(calDay.getAppointments().size() < index) {
				return;
			}
			calDay.editAppointment(index, task, desc, startHour, startMinute, endHour, endMinute, privacy);
		}
	}
	/*
	 * @purpose edits note 
	 * @param String note, int day, int month, int year, String newNote
	 */
	public void editNote(int index, String note, int day, int month, int year) {
		Day calDay = new Day(day, month, year);
		int i = findDay(calDay);
		if(i == -1) {
			return;
		}
		else {
			calDay = dayList.get(i);
			calDay.editNote(index, note);
		}
	}
	/*
	 * @purpose deletes note 
	 * @param String note, int day, int month, int year
	 */
	public void deleteNote(int index, int day, int month, int year) {
		Day calDay = new Day(day, month, year);
		int i = findDay(calDay);
		if(i == -1) {
			return;
		}
		else {
			calDay = dayList.get(i);
			
			if(calDay.getNotes().size() < index) {
				return;
			}
			calDay.removeNote(index);
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
			addDay(calDay);
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
	 * @purpose adds a calday object to the list in order
	 * @param Calday Object
	 */
	public void addDay(Day calDay) {
		Calendar newDay = new GregorianCalendar(calDay.getYear(), calDay.getMonth(), calDay.getDay(), 0, 0);
		
		for(int i = 0;i < dayList.size();i++) {
			Day temp = dayList.get(i);
			Calendar oldDay = new GregorianCalendar(temp.getYear(), temp.getMonth(), temp.getDay(), 0, 0);
			if(oldDay.after(newDay)) {
				dayList.add(i, calDay);
				return;
			}
		}
		dayList.add(calDay);
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
	/*
	 * @purpose removes days 2 weeks out
	 * 
	 */
	public void cleanCalendar() {
		Calendar limit = new GregorianCalendar();
		limit.add(Calendar.DATE, -14);
		for(int i = 0;i < dayList.size();i++) {
			Day d = dayList.get(i);
			Calendar day = new GregorianCalendar(d.getYear(), d.getMonth(), d.getDay(), 0, 0);
			
			if(day.before(limit)) {
				dayList.remove(i);
			}
			else {
				return;
			}	
		}
	}
	/*
	 * @purpose - gets the public appointements for a date
	 * @returns - the appointment that are public
	 */
	public ArrayList<Appointment> getPublicAppointmentsSpecifiedDate(int day, int month, int year) {
		Day calDay = new Day(day, month, year);
		Day tempDay = null;
		for(int i = 0;i<dayList.size();i++) {
			tempDay = dayList.get(i);
			if(tempDay.equals(calDay)) {
				return tempDay.getPublicAppointments();
			}
		}
		return calDay.getPublicAppointments();
	}
	/*
	 * @purpose get the all notes for specific day
	 * @param int day, int month, int year
	 * @returns array list of notes
	 */
	public ArrayList<Appointment> getUserPublicSchedule(String username,  LocalDate date){
		MyCalendar userCal = Persistence.load(username);
		
		if(userCal == null)
			return null;
		
		ArrayList<Appointment> scheduleList =userCal.getPublicAppointmentsSpecifiedDate(date.getDayOfMonth(), date.getMonthValue(), date.getYear());

		int size = scheduleList.size();
		
		if(size < 1)
			return null;
		
		// add Free-Time slot
		if(size > 1) {
			for(int i = 0; i < size; i+=2) {
				Appointment appt1 = scheduleList.get(i);
				Appointment appt2 = scheduleList.get(i+1);
				
				if(appt1.getEndHour() == appt2.getStartHour() && appt2.getEndMinute() == appt2.getEndMinute())
					continue;
				
				Appointment freeTime = new Appointment(appt1.getEndHour(), appt1.getEndMinute(), appt2.getStartHour(), appt2.getStartMinute());
				scheduleList.add( i+1, freeTime);
			}
		}
				
		if(!(scheduleList.get(0).getStartHour() == 0 && scheduleList.get(0).getStartMinute() == 0))
			scheduleList.add( 0, new Appointment(0, 0, scheduleList.get(0).getStartHour(), scheduleList.get(0).getStartMinute()));
		size = scheduleList.size();
		
		if(!(scheduleList.get(size-1).getEndHour() == 23 && scheduleList.get(size-1).getEndMinute()== 59))
			scheduleList.add(size, new Appointment(scheduleList.get(size-1).getEndHour(), scheduleList.get(size-1).getEndMinute(), 23, 59));
		
		return scheduleList;
	}
}