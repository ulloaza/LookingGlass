import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    @FXML
    public Button closeButton;
    public Button addTaskBtn;

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
        taskStage.setTitle("Add New Task...");
        taskStage.setScene(taskScene);
        taskStage.show();
    }
}
