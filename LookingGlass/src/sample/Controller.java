package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;
import static sample.Main.cal;
import static sample.Main.date1;
import static sample.Main.date2;
import static sample.Main.date3;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.io.*;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    @FXML
    public Button closeButton, arrowLeft, arrowRight;
    @FXML
    AnchorPane top1, top2, top3;
    @FXML
    Label label1, label2, label3, day1, day2, day3, username;
    @FXML
    TextFlow textflow1, textflow2, textflow3, task1, task2, task3;
    @FXML
    ScrollPane flow1;
    @FXML
    AppointCont appointCont;
    @FXML
    ImageView weather1, weather2, weather3;
    
	// Button event handlers
    public void handleCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
    /*
     * @purpose handles left arrow button decreasing date
     */
    public void leftClick(ActionEvent event) { 
    	date1.add(date1.DATE, -3);
    	date2.add(date2.DATE, -3);
    	date3.add(date3.DATE, -3);
    	loadInfo(date1, date2, date3);
    	
    	try {
			loadWeathers(date1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    /*
     * @purpose handles right arrow button increasing date
     */
    public void rightClick(ActionEvent event) {
    	date1.add(date1.DATE, 3);
    	date2.add(date2.DATE, 3);
    	date3.add(date3.DATE, 3);
    	loadInfo(date1, date2, date3);
    	
    	try {
			loadWeathers(date1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public void handleAddNewTask(ActionEvent event) {

        StackPane addTask = new StackPane();

        Scene taskScene = new Scene(addTask, 230, 100);
        Stage taskStage = new Stage();
        try {
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("appoint.fxml"));
            Pane mask = loader.load();
            addTask.getChildren().add(mask);
            
            //getting the controller from main.fxml
            AppointCont controller = (AppointCont) loader.getController();
            
            // pass the mainController to the AppointmentController 
            controller.init(this);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        taskStage.setMinHeight(450);
        taskStage.setMinWidth(550);
        taskStage.initModality(Modality.APPLICATION_MODAL);
        taskStage.setTitle("Add New Task...");
        taskStage.setScene(taskScene);
        taskStage.show();
    }

    public void handleMonthView(ActionEvent event) {

        StackPane addTask = new StackPane();

        Scene taskScene = new Scene(addTask, 230, 100);
        Stage taskStage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("monthView.fxml"));
            Pane mask = loader.load();
            addTask.getChildren().add(mask);
            
            //getting the controller from main.fxml
            MonthCont controller = (MonthCont) loader.getController();
            
            // pass the mainController to the MonthViewController 
            controller.init(this);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        taskStage.setMinHeight(470);
        taskStage.setMinWidth(540);
        taskStage.initModality(Modality.APPLICATION_MODAL);
        taskStage.setTitle("Month View");
        taskStage.setScene(taskScene);
        taskStage.show();
    }
    // Menu bar event handlers
    public void newUser(ActionEvent event) {
        // Create the new user dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("New User");
        dialog.setHeaderText("Choose name and password for new user:");
        ButtonType createButtonType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField username = new TextField();
        username.setPromptText("Username");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        grid.add(new Label("Username:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(password, 1, 1);


        // Enable/Disable login button depending on whether a username was entered.
        Node createButton = dialog.getDialogPane().lookupButton(createButtonType);
        createButton.setDisable(true);

        // Text validation
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            createButton.setDisable(newValue.trim().isEmpty());

        });
        
        dialog.getDialogPane().setContent(grid);
        // Convert the result to a username-password-pair when the create button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == createButtonType) {
                return new Pair<>(username.getText(), password.getText());
            }
            return null;
        });
    
        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(usernamePassword -> {
            // Code for retrieving entered text.
           // System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());
        	
        	ArrayList<String> users = Persistence.getAvailableUsers();
        	if(!users.contains(usernamePassword.getKey())) {
        		cal = new MyCalendar(usernamePassword.getKey(), usernamePassword.getValue());
               	Persistence.save(cal);  
               	loadInfo(date1, date2, date3);
        	}
        	
        });
    }
    public void loadUser(ActionEvent event) {
        // Handles user login.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Load User");
        dialog.setHeaderText("Please enter username and password:");
        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField username = new TextField();
        username.setPromptText("Username");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        grid.add(new Label("Username:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(password, 1, 1);

        // Enable/Disable login button depending on whether a username was entered.
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        // Text validation
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });
        dialog.getDialogPane().setContent(grid);
        // Convert the result to a username-password-pair when the create button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(username.getText(), password.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(usernamePassword -> {
            // Code for retrieving entered text.
           // System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());
        	ArrayList<String> users = Persistence.getAvailableUsers();
        	if(users.contains(usernamePassword.getKey())) {
        		MyCalendar temp = Persistence.load(usernamePassword.getKey());
        		if(temp.getPassword().equals(usernamePassword.getValue())) {
        			cal = temp;
        			loadInfo(date1, date2, date3);
        		}
        	}
        });
    }

    /*public void deleteTask(ActionEvent event) {

    }

    public void findNextFree(ActionEvent event) {
    // Find the user's next available block of free time between 8 AM - 8 PM
    }

    public void findUsersFree(ActionEvent event) {
    // Find next free time between multiple users
    }*/

    public void helpInfo(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("About");
        alert.setContentText("Looking Glass is freeware available at GitHub.com");
        TextArea textArea = new TextArea();
        try {
            FileReader reader = new FileReader( "readme.txt" );
            BufferedReader br = new BufferedReader(reader);
            String message = br.toString();
            textArea.setText(message);
            br.close();
            textArea.requestFocus();
        }
        catch(FileNotFoundException e) {
            System.out.println("File not found!");
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);
        
        GridPane readmeCont = new GridPane();
        readmeCont.setMaxWidth(Double.MAX_VALUE);
        readmeCont.add(textArea, 0, 1);

        alert.getDialogPane().setExpandableContent(readmeCont);

        alert.showAndWait();
    }
  
    public void loadInfo(GregorianCalendar date1, GregorianCalendar date2, GregorianCalendar date3) {

    	textflow1.getChildren().clear();
    	textflow2.getChildren().clear();
    	textflow3.getChildren().clear();
    	task1.getChildren().clear();
    	task2.getChildren().clear();
    	task3.getChildren().clear();
    	
    	loadLoginStatus();

    	
    	//setting the labels at the top of the pane to the appropriate date
    	day1.setText(MyCalendar.days[date1.get(date1.DAY_OF_WEEK)] + " " + (date1.get(date1.MONTH) + 1) + "/" + date1.get(date1.DATE) + "/" + date1.get(date1.YEAR));
		
    	day2.setText(MyCalendar.days[date2.get(date2.DAY_OF_WEEK)] + " " + (date2.get(date2.MONTH) + 1) + "/" + date2.get(date2.DATE) + "/" + date2.get(date2.YEAR));
		
    	day3.setText(MyCalendar.days[date3.get(date3.DAY_OF_WEEK)] + " " + (date3.get(date3.MONTH) + 1) + "/" + date3.get(date3.DATE) + "/" + date3.get(date3.YEAR));
		
    	//getting the appointments for the specified  date
		ArrayList<Appointment> appts = cal.getAppointmentsSpecifiedDate(date1.get(date1.DATE), date1.get(date1.MONTH) + 1, date1.get(date1.YEAR));

		//check if list is empty
		if(!appts.isEmpty()) {
			for(Appointment appt : appts) { 
				Text text = new Text(appt.toString() + "\n\n");
				
				textflow1.getChildren().add(text);
			}
		}
		ArrayList<String> notes = cal.getNotesSpecifiedDate(date1.get(date1.DATE), date1.get(date1.MONTH) + 1, date1.get(date1.YEAR));
		if(!notes.isEmpty()) {
			for(String note : notes) {
				Text text = new Text(note + "\n\n");
				
				ObservableList list = task1.getChildren(); 
				list.addAll(text);
				
			}
		}
		
		
		appts = cal.getAppointmentsSpecifiedDate(date2.get(date2.DATE), date2.get(date2.MONTH) + 1, date2.get(date2.YEAR));
		if(!appts.isEmpty()) {
			for(Appointment appt : appts) { 
				Text text = new Text(appt.toString() + "\n\n");
				
				ObservableList list = textflow2.getChildren(); 
				list.addAll(text);
			}
		}
		notes = cal.getNotesSpecifiedDate(date2.get(date2.DATE), date2.get(date2.MONTH) + 1, date2.get(date2.YEAR));
		if(!notes.isEmpty()) {
			for(String note : notes) {
				Text text = new Text(note + "\n\n");
				
				ObservableList list = task2.getChildren(); 
				list.addAll(text);
				
			}
		}
		appts = cal.getAppointmentsSpecifiedDate(date3.get(date3.DATE), date1.get(date3.MONTH) + 1, date3.get(date3.YEAR));
		if(!appts.isEmpty()) {
			for(Appointment appt : appts) { 
				Text text = new Text(appt.toString() + "\n\n");
				
				ObservableList list = textflow3.getChildren(); 
				list.addAll(text);
			}
		}
		notes = cal.getNotesSpecifiedDate(date3.get(date3.DATE), date3.get(date3.MONTH) + 1, date3.get(date3.YEAR));
		if(!notes.isEmpty()) {
			for(String note : notes) {
				Text text = new Text(note + "\n\n");
				
				ObservableList list = task3.getChildren(); 
				list.addAll(text);
				
			}
		}
    }
    
    // giving date1, load all remaining weathers
    public void loadWeathers(GregorianCalendar date) throws IOException
    {
    	Date date1 = date.getTime();
    			
    	int diff=  (int) (date1.getTime()- new Date().getTime())/86400000;
    	
    	if(diff < 0 ) {
    		System.out.println("weather history is currently not supported\n");
    		diff=0;
    	}
    	
		weather1.setImage(Weather.getIcon(diff));
		int weather[] = Weather.getWeather(diff);
		label1.setText(String.valueOf(weather[1]) + "°/" + String.valueOf(weather[0]) + "°");
	
		weather2.setImage(Weather.getIcon(diff+1));
		weather = Weather.getWeather(diff+1);
		label2.setText(String.valueOf(weather[1]) + "°/" + String.valueOf(weather[0]) + "°");

		weather3.setImage(Weather.getIcon(diff+2));
		weather = Weather.getWeather(diff+2);
		label3.setText(String.valueOf(weather[1]) + "°/" + String.valueOf(weather[0]) + "°");
    }
    
    
    public void loadLoginStatus() {
		if(cal.getUser() == null)
			username.setText("Not logged in");
		else
			username.setText("Hello, " + cal.getUser() );
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub		
		
		loadLoginStatus();
		
		day1.setText(MyCalendar.days[date1.get(date1.DAY_OF_WEEK)] + " " + (date1.get(date1.MONTH) + 1) + "/" + date1.get(date1.DATE) + "/" + date1.get(date1.YEAR));
		
    	day2.setText(MyCalendar.days[date2.get(date2.DAY_OF_WEEK)] + " " + (date2.get(date2.MONTH) + 1) + "/" + date2.get(date2.DATE) + "/" + date2.get(date2.YEAR));
		
    	day3.setText(MyCalendar.days[date3.get(date3.DAY_OF_WEEK)] + " " + (date3.get(date3.MONTH) + 1) + "/" + date3.get(date3.DATE) + "/" + date3.get(date3.YEAR));

    	try {
			loadWeathers(date1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  		
	}
}
