package sample;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static sample.Main.cal;
import static sample.Main.date1;
import static sample.Main.date2;
import static sample.Main.date3;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import com.sun.javafx.scene.control.skin.DatePickerSkin;

public class MonthCont {
    @FXML
    public HBox calendarBox;
    @FXML
    private Label dateLabel;
    
    @FXML
    private ListView<Serializable> appointmentList, todoList;
    
	private Controller mainController;
	
	private DatePicker datePicker;
    
//    SimpleCalendar calWidget = new SimpleCalendar();
//    DatePicker calDateWidget = new DatePicker();
    
    
//    EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 
//        public void handle(ActionEvent e) 
//        { 
//        	
//        } 
//    }; 
	
	// load appts and notes for Month View
	public void loadInfo(LocalDate date)
	{
    	appointmentList.getItems().clear();
    	todoList.getItems().clear();
    	

    	int dom = date.getDayOfMonth();
    	int m = date.getMonthValue();
    	int y = date.getYear();
    	
    	dateLabel.setText(dom+"/"+m+"/"+y);
    	
    	ArrayList<Appointment> appts = cal.getAppointmentsSpecifiedDate(dom, m, y);
    	ArrayList<String> notes = cal.getNotesSpecifiedDate(dom, m, y);
    	
    	appointmentList.getItems().addAll(appts);
    	todoList.getItems().addAll(notes);
	}



    public void initialize() {
    	// initialize DatePicker
    	datePicker = new DatePicker(LocalDate.now());
    	DatePickerSkin datePickerSkin = new DatePickerSkin(datePicker);
    	
		 datePicker.setOnAction(new EventHandler<ActionEvent>() { 
		        public void handle(ActionEvent e) 
		        { 
		        	LocalDate date = datePicker.getValue();
		        	loadInfo(date);
		        } 
		    }); 
		 calendarBox.getChildren().add(datePickerSkin.getPopupContent());	
	    
		 // load today's appts and todos
	    loadInfo(LocalDate.now());
		 
		// Initialize context-menu for appointmentList and todoList
	    
	    ContextMenu apptMenu = new ContextMenu();
	    MenuItem editAppt = new MenuItem("Edit");
	    
	    // code for edit appointments
	    editAppt.setOnAction(event -> {
	    	LocalDate date = datePicker.getValue();
	    	int index = appointmentList.getSelectionModel().getSelectedIndex(); 
            mainController.handleAddNewTask(new ActionEvent());
            
            Appointment selectedAppt = cal.getAppointment(index, date.getDayOfMonth(), date.getMonthValue(), date.getYear());
            mainController.appointCont.handleEdit(index, selectedAppt);
            loadInfo(date);
        });
	    MenuItem deleteAppt = new MenuItem("Delete");
	    
	    // code for delete appointments
	    deleteAppt.setOnAction(event -> {
	    	LocalDate date = datePicker.getValue();
	    	int index = appointmentList.getSelectionModel().getSelectedIndex();
	    	cal.deleteAppointment(index, date.getDayOfMonth(), date.getMonthValue(), date.getYear());
            System.out.println("Delete Appts " + appointmentList.getSelectionModel().getSelectedItem() + "index "+ index); 
            
            // save new change then refresh
            Persistence.save(cal);
            loadInfo(date);
            mainController.loadInfo(date1, date2, date3);
        });
	    
	    apptMenu.getItems().addAll(editAppt, deleteAppt);
	    
	    ContextMenu noteMenu = new ContextMenu();
	    MenuItem editNote = new MenuItem("Edit");
	    
	    // code for edit notes
	    editNote.setOnAction(event -> {
            System.out.println("Edit Note");
        });
	    MenuItem deleteNote = new MenuItem("Delete");
	    
	    // code for delete notes
	    deleteNote.setOnAction(event -> {
	    	LocalDate date = datePicker.getValue();
	    	int index = todoList.getSelectionModel().getSelectedIndex();
	    	cal.deleteNote(index, date.getDayOfMonth(), date.getMonthValue(), date.getYear());
            System.out.println("Delete Notes " + todoList.getSelectionModel().getSelectedItem() + "index "+ index); 

            Persistence.save(cal);
            loadInfo(date);
            mainController.loadInfo(date1, date2, date3);
        });
	    
	    noteMenu.getItems().addAll(editNote, deleteNote);
	    
	    appointmentList.setContextMenu(apptMenu);
	    todoList.setContextMenu(noteMenu);   
    }

    public void handleAddNewTask(ActionEvent event) {

    	mainController.handleAddNewTask(new ActionEvent());
    	loadInfo(datePicker.getValue());
    }
    
	public void init(Controller mainController)
	{
		this.mainController = mainController;
	}
}
