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
	 * @purpose - add an appointment to the array list
	 * @param - Appointment appt
	 */
	public void addAppointment(Appointment appt) {
		appointmentList.add(appt);
	}
	/*
	 * @purpose - remove an appt from the arraylist
	 * @param - String task
	 */
	public void removeAppointment(Appointment appt) {
		for(int i = 0;i<appointmentList.size();i++) {
			/*create temp appointment obj*/
			Appointment temp = appointmentList.get(i);
			/*checking to see if tasks are the same*/
			if(appt.equals(temp)) {
				appointmentList.remove(i);
				return;
			}
		}
	} 
	/*
	 * @purpose - edit an appt from the arraylist
	 * @param - String task, String newTask
	 */
	public void editAppointmentTask(Appointment appt, String newTask, String newStartTime, String newEndTime) {
		for(int i = 0;i<appointmentList.size();i++) {
			Appointment temp = appointmentList.get(i);
			if(appt.equals(temp)) {
				temp.setTask(newTask);
				temp.setstartTime(newStartTime);
				temp.setEndTime(newEndTime);
				return;
			}
		}
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
	public void removeNote(String note) {
		if(noteList.contains(note)) {
			noteList.remove(note);
		}
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
	public String dateToString() {
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
