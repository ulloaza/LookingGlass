import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MonthCont {
    @FXML
    public HBox calendarBox;

    public void initialize() {
        simple_calendar.SimpleCalendar calWidget = new simple_calendar.SimpleCalendar();
        simple_calendar.DatePicker calDateWidget = new simple_calendar.DatePicker();
        calendarBox.getChildren().addAll(calDateWidget, calWidget);
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
}
