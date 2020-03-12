package schedulingApp;

import java.io.*;
import java.util.ArrayList;

public class Day implements java.io.Serializable{
	
	private int day;
	private int month;
	private int year;
	
	/*keep count of notes/appts*/
	private int numOfAppointments = 0;
	private int numOfNotes = 0;
	
	/*store objects in dynamic array list for easy add/del*/
	private ArrayList<Appointment> appointments = new ArrayList<Appointment>();	
	/*creates array list of notes for caldender day*/
	private ArrayList<String> notes = new ArrayList<String>();
	
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
		appointments.add(appt);
		numOfAppointments += 1;
	}
	/*
	 * @purpose get appointments list
	 * @returns ArrayList appointments
	 */
	public ArrayList<Appointment> getAppointments() {
		return appointments;
	}
	/*
	 * @purpose get notes
	 * @returns - ArrayList notes
	 */
	public ArrayList<String> getNotes() {
		return notes;
	}
	/*
	 * @purpose - adds a String note to the note arraylist
	 * @param - notes
	 */
	public void addNote(String note) {
		notes.add(note);
		numOfNotes += 1;
	}
	/*
	 * @purpose - get the number of appts
	 * @returns - numOfAppointments
	 */
	public int getNumOfAppointments() {
		return numOfAppointments;
	}
	/*
	 * @purpose - get the number of notes
	 * @returns - numOfNotes
	 */
	public int getNumOfNotes() {
		return numOfNotes;
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
}
