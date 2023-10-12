package application.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MainController {
	
	@FXML HBox mainBox;
	@FXML HBox newProject;
	@FXML DatePicker datePicker;
	
	/*
	 * If the back button is pressed, the function to show the homepage is run.
	 */
	@FXML public void showHomepage() {
		URL url = getClass().getClassLoader().getResource("view/Main.fxml");
		
		try {
			// Stage is fetched
			Stage stage = (Stage) newProject.getScene().getWindow(); 
			HBox pane1 = (HBox)FXMLLoader.load(url);
			Scene scene = new Scene(pane1);
			// Set scene
			stage.setScene(scene);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	/*
	 * If the back button is pressed, the function to show the new project page is run.
	 */
	@FXML public void showNewProjectPage() {
		
		URL url = getClass().getClassLoader().getResource("view/NewProject.fxml");
		
		try {
			// Stage is fetched
			Stage stage = (Stage) mainBox.getScene().getWindow(); 
			HBox pane1 = (HBox)FXMLLoader.load(url);
			Scene scene = new Scene(pane1);
			// Set scene
			stage.setScene(scene);
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML public void setCurrentDate() {
		datePicker.setValue(LocalDate.now());
	}
}
