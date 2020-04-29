package sample;

/*
 * "Looking Glass" - Class AppointController
 * @purpose controller for the appointcont.fxml
 * @class CS3443.003
 * @author Gilberto Ramirez vwz745
 */

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import static sample.Main.cal;
import static sample.Main.date1;
import static sample.Main.date2;
import static sample.Main.date3;

public class AppointCont {
	
	@FXML 
	private AnchorPane AppointPane;
	@FXML
    public Button closeButton;
	@FXML
    private TextField appTitle, appDate, appStartTime, appEndTime;
    @FXML
    private TextArea appDesc;
    @FXML
    private ToggleGroup toggleGroup;
    @FXML
    private RadioButton todo, appt;
    @FXML
    private CheckBox privacy;
    @FXML
    private Label message;
    
    private String action = "Create";
    private int currentApptIndex;
    
	private Controller mainController;
    /*
     * @purpose button handler for closing event
     */
    public void handleCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
        
    }
    /*
     * @purpose button handler for create button; creates an appointment or todo 
     */
    public void handleCreate(ActionEvent event) throws IOException {
    	
    	String title = appTitle.getText();
    	String desc = appDesc.getText();
    	String date = appDate.getText();
    	String start = appStartTime.getText();
    	String end = appEndTime.getText();
        	
    	if(toggleGroup.getSelectedToggle() == appt) {
    		
    		if (start.matches("[0-9]+:[0-9]{2} (AM|PM)") && end.matches("[0-9]+:[0-9]{2} (AM|PM)") && date.matches("[0-9]+/[0-9]+/[0-9]{4}")) {
    			String dateArr[] = date.split("/");
        		String startMilitary = convertToMilitary(start);
        		String endMilitary = convertToMilitary(end);
        		String startArr[] = startMilitary.split(":");
        		String endArr[] = endMilitary.split(":");
        					
        		int startHour = Integer.parseInt(startArr[0]);
        		int startMin = Integer.parseInt(startArr[1]);
        		int endHour = Integer.parseInt(endArr[0]);
        		int endMin = Integer.parseInt(endArr[1]);
        		int day = Integer.parseInt(dateArr[1]);
        		int month = Integer.parseInt(dateArr[0]);
        		int year = Integer.parseInt(dateArr[2]);
        		
        		if(this.action.equals("Create"))
        			cal.createAppointment(title, desc, startHour, startMin, endHour, endMin, day, month, year, privacy.isSelected());	
        		else if(this.action.equals("Edit")) {
        			System.out.println("Debug" + currentApptIndex);
        			cal.editAppointment(currentApptIndex, title, desc, startHour, startMin, endHour, endMin, day, month, year, privacy.isSelected());
        		}
    		}
    		else
    		{
    			message.setText("Incorrect input format");
    			return;
    		}
    	}
    	else if(toggleGroup.getSelectedToggle() == todo) {
    		
    		if(date.matches("[0-9]+/[0-9]+/[0-9]{4}")) {
    			
    			String dateArr[] = date.split("/");
    			int day = Integer.parseInt(dateArr[1]);
        		int month = Integer.parseInt(dateArr[0]);
        		int year = Integer.parseInt(dateArr[2]);
        		
    			cal.createNote(title + ":\n" + desc, day, month, year);	
    		}
    	}
    	
    	//save calendar
    	Persistence.save(cal);

        mainController.loadInfo(date1, date2, date3);
        // close window after creation
        Window window =   ((Node)(event.getSource())).getScene().getWindow(); 
        if (window instanceof Stage){
            ((Stage) window).close();
        }
        message.setText("");
    }
    
    public void handleEdit(int index, Appointment old_appt)
    {
    	this.action = "Edit";
    	this.currentApptIndex = index;
    	
    	setStageTitle("Edit task...");
    	
    	appTitle.setText(old_appt.getTask());
    	appDesc.setText(old_appt.getDesc());
    	appDate.setText(old_appt.getDate());
    	
    	appt.setSelected(true);
    	
    	appStartTime.setText(convertTo12(old_appt.getStartHour() + ":" + old_appt.getStartMinute()));
    	appEndTime.setText(convertTo12(old_appt.getEndHour() + ":" + old_appt.getEndMinute()));
    	
    	privacy.setSelected(old_appt.isPrivate());
    }
    
    
    /*
     * @purpose - convert input time to military for easy collision detection
     * @param - Time input "9:00 AM"
     */
    public static String convertToMilitary(String input) {
    	//input format
    	DateFormat df = new SimpleDateFormat("hh:mm aa");
        //24hr output format
        DateFormat outputformat = new SimpleDateFormat("HH:mm");
        Date date = null;
        String output = null;
        try{
        	//Converting the input String to Date
        	date= df.parse(input);
            //Changing the format of date and storing it in String
        	output = outputformat.format(date);
        }
        catch(ParseException pe) {
        	pe.printStackTrace();
        }
        return output;
    }
    /*
     * @purpose convert time back to standard 12hr time for output
     * @param - input time "24:00"
     */
    public static String convertTo12(String input) {
    	
        DateFormat df = new SimpleDateFormat("HH:mm");
        DateFormat outputformat = new SimpleDateFormat("h:mm aa");
        Date date = null;
        String output = null;
        try{
        	//Converting the input String to Date
        	date= df.parse(input);
            //Changing the format of date and storing it in String
        	output = outputformat.format(date);
            //Displaying the date
        	}
        catch(ParseException pe) {
        	pe.printStackTrace();
        }
        return output;
    }
    
	public void init(Controller mainController)
	{
		this.mainController = mainController;
	}
	
	public void setStageTitle(String title){
		Stage stage = (Stage) AppointPane.getScene().getWindow();
		stage.setTitle(title);
	}
}
