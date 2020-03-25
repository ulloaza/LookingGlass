package sample;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/*
 * "Looking Glass" - Class Persistance
 * @purpose Used to save/write Objects
 * @class CS3443.003
 * @author Gilberto Ramirez vwz745
 */
public class Persistence {
	
	/*
	 * @purpose - to save a Calendar object to the current working directory
	 * @param = MyCalendar calendar
	 */
	public static void save(MyCalendar calendar) {
		try {
			/*file name ex: gilbert.data*/
			String fileName = calendar.getUser() + ".data";
			FileOutputStream file = new FileOutputStream(fileName);
			ObjectOutputStream output = new ObjectOutputStream(file);
			output.writeObject(calendar);
			output.close();
			file.close();
		}
		catch(IOException e) {	
			
		}
	}
	/*
	 * @purpose = to load a particular users calendar 
	 * @param = the user's name
	 * @return the user's calendar object
	 */
	public static MyCalendar load(String user) {
		MyCalendar calendar = new MyCalendar();
		try {
			/*file name ex: gilbert.data*/
			String fileName = user + ".data";
			FileInputStream file = new FileInputStream(fileName);
			ObjectInputStream input = new ObjectInputStream(file);	
		    calendar = (MyCalendar) input.readObject();
			input.close();
			file.close();
			
		}
		catch(IOException e) {
			
		}
		catch(ClassNotFoundException e) {
			
		}
		return calendar;
	}
	/*
	 * @purpose get all the users that currently have save data = mostly to be used at startup of program
	 * @returns the array list of all the usernames with availalbe data to be loaded into a calendar object
	 */
	public static ArrayList<String> getAvailableUsers() {
		ArrayList<String> users = new ArrayList<String>();
		File curDir = new File(".");
        File[] fileList = curDir.listFiles();
        /*go through file list and find files that end with .data*/
        for(File file : fileList) {
            if(file.isFile() && file.getName().endsWith(".data")) {
            	/*remove the suffix and add to the userList*/
            	int index = file.getName().lastIndexOf(".");
            	String userName = file.getName().substring(0, index);
            	users.add(userName);
            }
        }
        return users;	
	}
}
