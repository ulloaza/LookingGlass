import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

public class Controller {
    @FXML
    public Button closeButton;
    public Button addTaskBtn;

    public void handleCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void handleAddNewTask(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add New Event");
        // Spawn popup
        ButtonType AddTask = new ButtonType("Add Task", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(AddTask, ButtonType.CANCEL);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        // Create Text fields
        TextField task = new TextField();
        grid.add(new Label("Title:"), 0, 0);
        grid.add(task, 1, 0);
        TextArea desc = new TextArea();
        grid.add(new Label("Description:"), 0, 0);
        grid.add(desc, 1, 0);
        dialog.getDialogPane().setContent(grid);
    }
}
