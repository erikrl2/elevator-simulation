package com.erik.fahrstuhl;

import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class FahrstuhlController {

	@FXML
	private Pane pane, liftPane;
	@FXML
	private Canvas canvasDoors;
	@FXML
	private Rectangle seil;
	@FXML
	private Button eG, oG1, oG2;
	@FXML
	private Label text;
	private Fahrstuhl meinLift;
	private AudioClip ding;
	private GraphicsContext gDoors;
	private Image imgDoor;
	private int zustand;

	public FahrstuhlController() {
		meinLift = new Fahrstuhl();
	}

	@FXML
	private void ruf2OG() {
		if (zustand == 0) {
			move(2, 2);
		} else if (zustand == 1) {
			move(1, 2);
		}
		meinLift.setRuf2OG(true);
		zustand();
	}

	@FXML
	private void ruf1OG() {
		if (zustand == 0) {
			move(1, 1);
		} else if (zustand == 2) {
			move(-1, 1);
		}
		meinLift.setRuf1OG(true);
		zustand();
	}

	@FXML
	private void rufEG() {
		if (zustand == 1) {
			move(-1, 0);
		} else if (zustand == 2) {
			move(-2, 0);
		}
		meinLift.setRufEG(true);
		zustand();
	}

	private void zustand() {
		zustand = meinLift.folgezustand();
		switch (zustand) {
		case 0:
			text.setText("steht im EG");
			break;
		case 1:
			text.setText("steht im 1OG");
			break;
		case 2:
			text.setText("steht im 2OG");
			break;
		case 3:
			text.setText("fährt hoch");
			break;
		case 4:
			text.setText("fährt runter");
			break;
		}
	}

	private void move(int n, int etage) {
		eG.setDisable(true);
		oG1.setDisable(true);
		oG2.setDisable(true);
		close();
		TranslateTransition t = new TranslateTransition(Duration.seconds(Math.abs(n)), liftPane);
		t.setByY(-109 * n);
		t.setInterpolator(Interpolator.EASE_BOTH);
		t.setDelay(Duration.millis(400));
		t.play();
		translateRope(n);
		t.setOnFinished(e -> {
			open();
			switch (etage) {
			case 0:
				meinLift.setIstEG(true);
				break;
			case 1:
				meinLift.setIst1OG(true);
				break;
			case 2:
				meinLift.setIst2OG(true);
				break;
			}
			zustand();
			eG.setDisable(false);
			oG1.setDisable(false);
			oG2.setDisable(false);
		});
	}

	private void translateRope(int n) {
		TranslateTransition t = new TranslateTransition(Duration.seconds(Math.abs(n)), seil);
		t.setByY(-109 * n);
		t.setDelay(Duration.millis(400));
		t.play();
	}

	private double ppf, fps = 60, time = 0.333; // ppf = pixels per frame, fps = frames per second

	public void initialize() {
		scale();
		gDoors = canvasDoors.getGraphicsContext2D();
		imgDoor = new Image(getClass().getResource("/textures/ClosedDoor3.jpg").toExternalForm());
		ppf = (canvasDoors.getWidth() / 2) / fps / time;

		AudioClip bgm = new AudioClip(getClass().getResource("/sound/bgm.mp3").toExternalForm());
		bgm.setVolume(0.1);
		bgm.setCycleCount(AudioClip.INDEFINITE);
		bgm.play();
		ding = new AudioClip(getClass().getResource("/sound/Ding.mp3").toExternalForm());
		ding.setVolume(0.1);
		ding.play();
	}

	private void scale() {
		pane.setPrefSize(pane.getPrefWidth() * 1.5, pane.getPrefHeight() * 1.5);
		var st = new ScaleTransition(Duration.millis(1), pane);
		var tt = new TranslateTransition(Duration.millis(1), pane);
		st.setToX(1.5);
		st.setToY(1.5);
		tt.setByX(225);
		tt.setByY(150);
		st.play();
		tt.play();
	}

	private void open() {
		ding.play();

		AnimationTimer at = new AnimationTimer() {
			private double w = canvasDoors.getWidth() / 2;
			private double h = canvasDoors.getHeight();
			private double x = w;

			@Override
			public void handle(long arg0) {
				gDoors.clearRect(0, 0, canvasDoors.getWidth(), canvasDoors.getHeight());
				w -= ppf;
				x += ppf;
				gDoors.drawImage(imgDoor, 0, 0, w, h);
				gDoors.drawImage(imgDoor, x, 0, w, h);
				if (w <= 0)
					stop();
			}
		};
		at.start();
	}

	private void close() {
		AnimationTimer at = new AnimationTimer() {
			private double w = 0;
			private double h = canvasDoors.getHeight();
			private double x = canvasDoors.getWidth();

			@Override
			public void handle(long arg0) {
				gDoors.clearRect(0, 0, canvasDoors.getWidth(), canvasDoors.getHeight());
				w += ppf;
				x -= ppf;
				gDoors.drawImage(imgDoor, 0, 0, w, h);
				gDoors.drawImage(imgDoor, x, 0, w, h);
				if (w >= canvasDoors.getWidth() / 2)
					stop();
			}
		};
		at.start();
	}

}
