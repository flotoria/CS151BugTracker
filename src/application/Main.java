	package application;
	
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;

public class Main extends Application {
	
	/*
	 * When the program is ran, the start function is initially ran.
	 * 
	 * @param primaryStage the JavaFX stage
	 */ 
	@Override
	public void start(Stage primaryStage) {
		try {
			HBox root = (HBox) FXMLLoader.load(getClass().getClassLoader().getResource("view/Main.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getClassLoader().getResource("css/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
