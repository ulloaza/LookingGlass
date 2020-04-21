import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.*;
import java.util.Optional;

public class Controller {
    @FXML
    public Button closeButton;
    // Button event handlers
    public void handleCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void handleAddNewTask(ActionEvent event) {

        StackPane addTask = new StackPane();

        Scene taskScene = new Scene(addTask, 230, 100);
        Stage taskStage = new Stage();
        try {
            Pane mask = FXMLLoader.load(getClass().getResource("appoint.fxml"));
            addTask.getChildren().add(mask);
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
            Pane mask = FXMLLoader.load(getClass().getResource("monthView.fxml"));
            addTask.getChildren().add(mask);
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
            System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());
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
            System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());
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
}
