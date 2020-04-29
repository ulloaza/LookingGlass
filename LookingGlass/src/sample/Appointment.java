package sample;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/*
 * "Looking Glass" - Class Appointment
 * @purpose Models an appointment and it's attributes for a user's calendar
 * @class CS3443.003
 * @author Gilberto Ramirez vwz745
 */

public class Appointment implements Serializable {
	
	/*appointment content*/
	private String task;
	/*appointment description*/
	private String desc;
	/*time content*/
	private int startHour, startMinute, endHour, endMinute;
	/*privacty setting*/
	private boolean privacy;	
	/*appt date*/
	private int day, month, year;
	
	// a unique timestamp for an appoint
	private String id;

	

	/*Constructor
	 * @param - String task, String time, int day, int month, int year
	 * 
	 */
	public Appointment(String task, String desc, int startHour, int startMinute, int endHour, int endMinute, int day, int month, int year, boolean privacy) {
		this.task = task;
		this.desc = desc;
		this.startHour = startHour;
		this.startMinute = startMinute;
		this.endHour = endHour;
		this.endMinute = endMinute;
		this.day = day;
		this.month = month;
		this.year = year;
		this.privacy = privacy;
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		this.id = ""+timestamp.getTime();
	}
	public Appointment(int startHour, int startMinute, int endHour, int endMinute, int day, int month, int year) {
		this.startHour = startHour;
		this.startMinute = startMinute;
		this.endHour = endHour;
		this.endMinute = endMinute;
		this.day = day;
		this.month = month;
		this.year = year;
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		this.id = ""+timestamp.getTime();
	}
	//  constructor for Note template
	public Appointment(String task, String desc, int day, int month, int year) {

		this.day = day;
		this.month = month;
		this.year = year;
		
		this.task = task;
		this.desc = desc;
		this.privacy = true;
	}
	/*
	 * @purpose get task info
	 * @returns task
	 */
	public String getTask() {
		return task;
	}
	
	
	/*
	 * @purpose get task description
	 * @returns desc
	 */
	public String getDesc() {
		return desc;
	}
	/*
	 * @purpose get time info
	 * @returns starthour
	 */
	public int getStartHour() {
		return startHour;
	}
	/*
	 * @purpose get time info
	 * @returns startminute
	 */
	public int getStartMinute() {
		return startMinute;
	}
	/*
	 * @purpose get time info
	 * @returns endhour
	 */
	public int getEndHour() {
		return endHour;
	}
	/*
	 * @purpose get time info
	 * @returns endminute
	 */
	public int getEndMinute() {
		return endMinute;
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
	
	public String getId() {
		return this.id;
	}
	/*
	 * @purpose - get privacy setting 
	 * @returns privacy
	 */
	public boolean isPrivate() {
		return privacy;
	}
	/*
	 * @purpose - set privacy setting 
	 */
	public void setPrivacy(boolean privacy) {
		this.privacy = privacy;
	}
	/*
	 * @purpose - set task description
	 */
	public void setDesc(String desc) {
		this.desc = desc;
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
	public void setStartHour(int startHour) { 
		this.startHour = startHour;
	}
	public void setStartMinute(int startMinute) {
		this.startMinute = startMinute;
	}
	/*
	 * @purpose - set time info 
	 */
	public void setEndHour(int endHour) { 
		this.endHour = endHour;
	}
	public void setEndMinute(int endMinute) {
		this.endMinute = endMinute;
	}
	/*
	 * @purpose - toString method format: 9:00: Task info
	 */
	public String toString() {
		String start = AppointCont.convertTo12(startHour + ":" + startMinute);
		String end = AppointCont.convertTo12(endHour + ":" + endMinute);

		return start + "-" + end + " -- "  + task + "\n" + desc;
	}
	
	
	/*
	 * @purpose - Compares appointment classes to check if the task is the same
	 * @param - Appointment appt
	 * @returns boolean
	 */
	public boolean equals(Appointment appt) {
//		if(this.startHour == appt.getStartHour() && this.startMinute == appt.getEndMinute()) {
//			return true;
//		}
//		else {
//			return false;
//		}
		// Avoid conflict by using their unique id
		return this.id.equals(appt.id);
	}
	
	// return the date(mm/dd/yyyy) as string
	public LocalDate getLocalDate() {
		
		return LocalDate.of(this.year, this.month, this.day);
	}
	


}
