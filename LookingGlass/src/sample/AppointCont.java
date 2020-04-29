package sample;

/*
 * "Looking Glass" - Class AppointController
 * @purpose controller for the appointcont.fxml
 * @class CS3443.003
 * @author Gilberto Ramirez vwz745
 */

import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
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
    public Button closeButton, createButton;
	@FXML
    private TextField appTitle;
	@FXML
	private ListView<Serializable> userSchedule;
	@FXML
	private ComboBox<String> appStartTime, appEndTime, inviteBox;
	@FXML 
	private DatePicker appDate;
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
    
    private int currentSelectedIndex;
    
	private Controller mainController;
	private MonthCont monthController;
    /*
     * @purpose button handler for closing event
     */
    public void handleCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
        
    }
    
    /*
     * @purpose event handler for generate user schedule 
     */
    public void handleOnSelecedtInvitedMember(ActionEvent event) throws IOException {
    	userSchedule.getItems().clear();
    	String username = inviteBox.getSelectionModel().getSelectedItem().toString();
    	
    	ArrayList<Appointment> publicSchedule = cal.getUserPublicSchedule(username, appDate.getValue());
    	
    	if(publicSchedule != null)	userSchedule.getItems().addAll(publicSchedule);
    	else {
    		userSchedule.getItems().add("Available all day");
    		return;
    	}
    	
	    ContextMenu selectMenu = new ContextMenu();
	    MenuItem selectTime = new MenuItem("select");
	    
	    selectTime.setOnAction(e -> {
	    	handleOnSelectedTimeSlot();
        });
	    
	    selectMenu.getItems().add(selectTime);
	    userSchedule.setContextMenu(selectMenu);
    }
    
    public void handleOnSelectedTimeSlot(){
    	String content = (String) userSchedule.getSelectionModel().getSelectedItem().toString();
    	
    	String [] stringArr = (content).split(" -- ");
    	String dateInfo = stringArr[0];
    	stringArr = dateInfo.split("-");
    	appStartTime.setValue(stringArr[0]);
    	appEndTime.setValue(stringArr[1]);
    }
    /*
     * @purpose button handler for create button; creates an appointment or todo 
     */
    public void handleCreate(ActionEvent event) throws IOException {
    	
    	String title = appTitle.getText();
    	String desc = appDesc.getText();
    	String invitedMember = (String) inviteBox.getValue();
    	
    	LocalDate localDate = appDate.getValue();
    	String date = localDate.getMonthValue()+"/"+localDate.getDayOfMonth()+"/"+localDate.getYear();

    	String start = (String) appStartTime.getValue();
    	String end = (String) appEndTime.getValue();
//    	System.out.println(date + " " + start + " " + end);
        	
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
        			if(invitedMember == null)
        				cal.createAppointment(title, desc, startHour, startMin, endHour, endMin, day, month, year, privacy.isSelected());	
        			else
        			{
//        				System.out.println("AppointmentWith " + invitedMember);
        				boolean result = cal.createAppointmentWithInvite(title, desc, invitedMember, startHour, startMin, endHour, endMin, day, month, year, privacy.isSelected());
        				if(result==false) {
        					message.setText("Time Conflict");
        					return;
        				}
        			}
        		else if(this.action.equals("Edit")) 
        			cal.editAppointment(currentSelectedIndex, title, desc, startHour, startMin, endHour, endMin, day, month, year, privacy.isSelected());
        		
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
        		
        		if(this.action.equals("Create"))
        			cal.createNote(title + ":\n" + desc, day, month, year);	
        		else if(this.action.equals("Edit")) {
//        			System.out.println("Note edit");
        			cal.editNote(this.currentSelectedIndex, title + ":\n" + desc, day, month, year);
        		}
    		}
    	}
    	
    	//save calendar
    	Persistence.save(cal);

        mainController.loadInfo(date1, date2, date3);
        
        if(monthController != null)	monthController.refresh();
        
        // close window after creation
        Window window =   ((Node)(event.getSource())).getScene().getWindow(); 
        if (window instanceof Stage){
            ((Stage) window).close();
        }
        message.setText("");
        createButton.setText("Create");
    }
    
    public void switchToAppointment()  {
		todo.setSelected(false);
		privacy.setSelected(false);
		
		appStartTime.setDisable(false);
		appEndTime.setDisable(false);
		inviteBox.setDisable(false);
		appt.setDisable(false);
    }
    
    public void switchToNote()  {
		todo.setSelected(true);
		privacy.setSelected(true);
		
		appStartTime.setDisable(true);
		appEndTime.setDisable(true);
		inviteBox.setDisable(true);
    }
    
    public void handleSwitchToNote(ActionEvent event) throws IOException {
    	switchToNote();
    }
    
    public void handleSwitchToAppointment(ActionEvent event) throws IOException {
    	switchToAppointment();
    }
    
    public void handleEdit(int index, Appointment old_appt, String type)
    {
    	this.action = "Edit";
    	this.currentSelectedIndex = index;
    	
    	setStageTitle("Edit task...");
    	
    	appTitle.setText(old_appt.getTask());
    	appDesc.setText(old_appt.getDesc());
    	appDate.setValue(old_appt.getLocalDate());

    	if(type.equals("Appt")) {
    		switchToAppointment();
    		todo.setDisable(true);
        	
        	inviteBox.setValue(old_appt.getInvitedMember());
        	appStartTime.setValue(convertTo12(old_appt.getStartHour() + ":" + old_appt.getStartMinute()));
        	appEndTime.setValue(convertTo12(old_appt.getEndHour() + ":" + old_appt.getEndMinute()));
    	}
    	else if(type.equals("Note")) {
    		switchToNote();
    		appt.setDisable(true);
    	}
    	
    	createButton.setText("Save");
    	privacy.setSelected(old_appt.isPrivate());
    }
    
    public void initView() {
    	String timeOptions [] = {"12:00 AM", "12:30 AM","1:00 AM", "1:30 AM", "2:00 AM", "2:30 AM", "3:00 AM", "3:30 AM", "4:00 AM", "4:30 AM", "5:00 AM", "5:30 AM", "6:00 AM", "6:30 AM", "7:00 AM", "7:30 AM", "8:00 AM", "8:30 AM", "9:00 AM", "9:30 AM", "10:00 AM", "10:30 AM", "11:00 AM", "11:30 AM", "12:00 PM", "12:30 PM", "1:00 PM", "1:30 PM", "2:00 PM", "2:30 PM", "3:00 PM", "3:30 PM", "4:00 PM", "4:30 PM", "5:00 PM", "5:30 PM", "6:00 PM", "6:30 PM", "7:00 PM", "7:30 PM", "8:00 PM", "8:30 PM", "9:00 PM", "9:30 PM", "10:00 PM", "10:30 PM", "11:00 PM", "11:30 PM", "11:59 PM"};
    	
    	appDate.setValue(LocalDate.now());
    	appStartTime.setItems(FXCollections.observableArrayList(timeOptions));
    	appEndTime.setItems(FXCollections.observableArrayList(timeOptions));
    	
    	inviteBox.setItems(FXCollections.observableArrayList(cal.getUserList()));
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
    
	public void initMainController(Controller mainController)
	{
		this.mainController = mainController;
	}
	
	public void initMonthController(MonthCont monthCont)
	{
		this.monthController = monthCont;
	}
	public void setStageTitle(String title){
		Stage stage = (Stage) AppointPane.getScene().getWindow();
		stage.setTitle(title);
	}
}
