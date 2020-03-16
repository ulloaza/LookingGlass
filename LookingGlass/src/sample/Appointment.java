package sample;

public class Appointment implements java.io.Serializable{
	
	/*appointment content*/
	private String task;
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
	public boolean equals(Appointment appt) {
		if(this.task.equals(appt.getTask())) {
			return true;
		}
		else {
			return false;
		}
	}

}
