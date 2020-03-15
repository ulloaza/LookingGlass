package sample;

public class Appointment implements java.io.Serializable{
	
	/*appointment content*/
	private String task;
	/*note content/ also temporary, I want to see if a simple arraylist of String of notes will work in Day Class*/
	private String note;
	/*time content*/
	private String time;

	
	/*appt date*/
	private int day, month, year;
	
	/*default constructor
	 * @purpose creates an invalid appt object
	 */
	public Appointment() {
		
	}
	/*2nd constuctor
	 * @param - String task, String time, int day, int month, int year
	 * 
	 */
	public Appointment(String task, String time, int day, int month, int year) {
		this.task = task;
		this.time = time;
		this.day = day;
		this.month = month;
		this.year = year;
	}
	/*
	 * @purpose get task info
	 * @returns task
	 */
	public String getTask() {
		return task;
	}
	/*
	 * @purpose get note information
	 * @returns - note
	 */
	public String getNote() {
		return note;
	}
	/*
	 * @purpose get time info
	 * @returns time
	 */
	public String getTime() {
		return time;
	}
	/*
	 * @purpose - get day 
	 * @returns day
	 */
	public int getDay() {
		return day;
	}
	/*
	 * @purpose - get month 
	 * @returns month
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
	 * @purpose - set task info 
	 */
	public void setTask(String task) {
		this.task = task;
	}
	/*
	 * @purpose - set Note info 
	 */
	public void setNote(String note) {
		this.note = note;
	}
	/*
	 * @purpose - set time info 
	 */
	public void setTime(String time) { 
		this.time = time;
	}
	/*
	 * @purpose - toString method format: 9:00: Task info
	 */
	public String toString() {
		return time + ": " + task;
	}

}
