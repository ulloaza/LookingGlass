package sample;

import java.io.Serializable;
import java.util.ArrayList;

/*
 * "Looking Glass" - Class Day
 * @purpose Models an a day for a user calendar
 * @class CS3443.003
 * @author Gilberto Ramirez vwz745
 */

public class Day implements Serializable {
	
	/*date information*/
	private int day, month, year;
	
	/*store objects in dynamic array list for easy add/del*/
	private ArrayList<Appointment> appointmentList = new ArrayList<Appointment>();	
	/*creates array list of notes for caldender day*/
	private ArrayList<String> noteList = new ArrayList<String>();
	
	/*
	 * Constructor for Day Class
	 * @param - int day, int month, int year
	 */
	public Day(int day, int month, int year) {
		this.day = day;
		this.month = month;
		this.year = year;
	}
	/*
	 * @purpose - adds appt to list in order from earliest to latest
	 * @param - Appointment appt
	 */
	public void addAppointment(Appointment appt) {
		for(int i = 0;i<appointmentList.size();i++) {
			if(appointmentList.get(i).getStartHour() > appt.getStartHour()) {
				appointmentList.add(i, appt);
				return;
			}
			else if(appointmentList.get(i).getStartHour() == appt.getStartHour() && appointmentList.get(i).getStartMinute() > appt.getStartMinute()) {
				appointmentList.add(i, appt);
				return;
			}
		}
		appointmentList.add(appt);
		
	}
	public void removeAppointment(int index) {
		appointmentList.remove(index);
	}
	
	public Appointment getAppointment(int index) {
		return appointmentList.get(index);
	}
		
	/*
	 * @purpose - edit an appt from the arraylist
	 * @param - String task, String newTask
	 */
	public void editAppointment(int oldIndex, String newTask, String newDesc, int newStartHour, int newStartMinute, int newEndHour, int newEndMinute, boolean privacy) {
		Appointment old = getAppointment(oldIndex);
	
		old.setTask(newTask);
		old.setDesc(newDesc);
		old.setStartHour(newStartHour);
		old.setStartMinute(newStartMinute);
		old.setEndHour(newEndHour);
		old.setEndMinute(newEndMinute);
		old.setPrivacy(privacy);
	}
	/*
	 * @purpose - edit an note from the arraylist
	 * @param - String note, String newNote
	 */
	public void editNote(String note, String newNote) {
		for(int i = 0;i<noteList.size();i++) {
			if(note.equals(noteList.get(i))) {
				noteList.set(i, newNote);
				return;
			}
		}
	}
	/*
	 * @purpose - remove an note from the arraylist
	 * @param - String note
	 */
	public void removeNote(int index) {
		noteList.remove(index);
	}
	/*
	 * @purpose get appointments list
	 * @returns ArrayList appointments
	 */
	public ArrayList<Appointment> getAppointments() {
		return appointmentList;
	}
	/*
	 * @purpose get notes
	 * @returns - ArrayList notes
	 */
	public ArrayList<String> getNotes() {
		return noteList;
	}
	/*
	 * @purpose - adds a String note to the note arraylist
	 * @param - notes
	 */
	public void addNote(String note) {
		System.out.printf("Debug: note was added %s\n", note);
		noteList.add(note);
	}
	/*
	 * @purpose - get the number of appts
	 * @returns - size of appt list
	 */
	public int getNumOfAppointments() {
		return appointmentList.size();
	}
	/*
	 * @purpose - get the number of notes
	 * @returns - size of note list
	 */
	public int getNumOfNotes() {
		return noteList.size();
	}
	/*
	 * @purpose - get day 
	 * @returns - day
	 */
	public int getDay() {
		return day;
	}
	/*
	 * @purpose - get month 
	 * @returns - month
	 */
	public int getMonth() {
		return month;
	}
	/*
	 * @purpose - get year 
	 * @returns year
	 */
	public int getYear() {
		return year;
	}
	/*
	 * @purpose check to see if notes are present
	 * @returns - true if there are, if none then return false
	 */
	public boolean hasNotes() {
		if (noteList.isEmpty()) {
			return false;
		}
		else {
			return true;
		}
	}
	/*
	 * @purpose check to see if appts are present
	 * @returns - true if there are, if none then return false
	 */
	public boolean hasAppointments() {
		if(appointmentList.isEmpty()) {
			return false;
		}
		else {
			return true;
		}
	}
	/*
	 * @purpose toString method for day class format: 03/16/20
	 * @returns String representation
	 */
	@Override
	public String toString() {
		return month + "/" + day + "/" + year;
	}
	/*
	 * @purpose equals method comparing Day Classes
	 * @returns boolean
	 */
	public boolean equals(Day day) {
		if (this.day == day.getDay() && this.month == day.getMonth() && this.year == day.getYear()) {
			return true;
		}
		else {
			return false;
		}
	}
}
