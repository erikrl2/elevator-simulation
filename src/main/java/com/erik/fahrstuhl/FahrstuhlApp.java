package com.erik.fahrstuhl;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class FahrstuhlApp extends Application {
	@Override
	public void start(Stage stage) throws Exception {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Scene.fxml"));
			stage.setScene(new Scene(loader.load()));
			stage.getIcons().add(new Image("/textures/icon1.png"));
			stage.setTitle("Fahrstuhl-Simulation");
			stage.setResizable(false);
			stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
