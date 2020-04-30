package sample;

import java.util.GregorianCalendar;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/*
* "Looking Glass" - Class Main
* @purpose - Main class
* @class CS3443.003
* @author - Zack Ulloa, Yi He, Gilbert Ramirez
*/

public class Main extends Application {
	//creates a mycal instance set to null for invalid user
	public static MyCalendar cal = new MyCalendar();
	public static GregorianCalendar date1 = new GregorianCalendar();
    public static GregorianCalendar date2 = new GregorianCalendar();
    public static GregorianCalendar date3 = new GregorianCalendar();
    private static final int shadowSize = 50;
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) throws Exception{
        // Removes traditional header bar and handles making main panel style and colors.
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        StackPane stackPane = new StackPane(createShadowPane());
        stackPane.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.7);" +
                        "-fx-background-insets: " + shadowSize + ";"
        );

        primaryStage.setTitle("Looking Glass v 1.0");
        Pane maskPane =  FXMLLoader.load(getClass().getResource("main.fxml"));
        stackPane.getChildren().add(maskPane);
        stackPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        stackPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            }
        });

        Scene mainScene = new Scene(stackPane, 1250, 750);
        mainScene.setFill(Color.TRANSPARENT);
        maskPane.getStylesheets().add(getClass().getResource("AppStyle.css").toExternalForm());
        maskPane.setLayoutX(200);
        maskPane.setLayoutY(300);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }
    // Create glow for transparent panel.
    private Pane createShadowPane() {
        Pane shadowPane = new Pane();
        shadowPane.setStyle(
                "-fx-background-color: white;" +
                        "-fx-effect: dropshadow(gaussian, darkred, " + shadowSize + ", 0, 0, 0);" +
                        "-fx-background-insets: " + shadowSize + ";"
        );

        Rectangle innerRect = new Rectangle();
        Rectangle outerRect = new Rectangle();
        shadowPane.layoutBoundsProperty().addListener(
                (observable, oldBounds, newBounds) -> {
                    innerRect.relocate(
                            newBounds.getMinX() + shadowSize,
                            newBounds.getMinY() + shadowSize
                    );
                    innerRect.setWidth(newBounds.getWidth() - shadowSize * 2);
                    innerRect.setHeight(newBounds.getHeight() - shadowSize * 2);

                    outerRect.setWidth(newBounds.getWidth());
                    outerRect.setHeight(newBounds.getHeight());

                    Shape clip = Shape.subtract(outerRect, innerRect);
                    shadowPane.setClip(clip);
                }
        );

        return shadowPane;
    }

    public static void main(String[] args) {
    	//adds a day to dates for the middle and right column boxes
    	date2.add(date1.DATE, 1);
    	date3.add(date1.DATE, 2);
        launch(args);
    }
}