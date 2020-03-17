package sample;

import java.io.Serializable;

/*
 * "Looking Glass" - Class Appointment
 * @purpose Models an appointment and it's attributes for a user's calendar
 * @class CS3443.003
 * @author Gilberto Ramirez vwz745
 */

public class Appointment implements Serializable {
	
	/*appointment content*/
	private String task;
	/*time content*/
	private String startTime, endTime;
	/*privacty setting*/
	private boolean privacy;	
	/*appt date*/
	private int day, month, year;
	

	/*Constructor
	 * @param - String task, String time, int day, int month, int year
	 * 
	 */
	public Appointment(String task, String startTime, String endTime, int day, int month, int year) {
		this.task = task;
		this.startTime = startTime;
		this.endTime = endTime;
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
	 * @returns starttime
	 */
	public String getstartTime() {
		return startTime;
	}
	/*
	 * @purpose get time info
	 * @returns endtime
	 */
	public String getEndTime() {
		return endTime;
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
	 * @purpose - get privacy setting 
	 * @returns privacy
	 */
	public boolean getPrivacySetting() {
		return privacy;
	}
	/*
	 * @purpose - set privacy setting 
	 */
	public void setPrivacy(boolean privacy) {
		this.privacy = privacy;
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
	public void setstartTime(String startTime) { 
		this.startTime = startTime;
	}
	/*
	 * @purpose - set time info 
	 */
	public void setEndTime(String endTime) { 
		this.endTime = endTime;
	}
	/*
	 * @purpose - toString method format: 9:00: Task info
	 */
	public String toString() {
		return task + " " + startTime;
	}
	/*
	 * @purpose - Compares appointment classes to check if the task is the same
	 * @param - Appointment appt
	 * @returns boolean
	 */
	public boolean equals(Appointment appt) {
		if(this.task.equals(appt.getTask()) && this.startTime == appt.getstartTime() && this.endTime == appt.getEndTime()) {
			return true;
		}
		else {
			return false;
		}
	}

}
