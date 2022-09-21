package application;

import javafx.scene.control.Button;

public class ButtonClass {
	Button startGame = new Button("Start Game");
	Button instructions = new Button("Instructions");
	Button backBtn = new Button("Back");
	Button submit = new Button("Submit");

	public ButtonClass(int screenHeight, int screenWidth) {
		startGame.setPrefWidth(100);
		startGame.setPrefHeight(50);
		startGame.setLayoutY(screenHeight / 4);
		startGame.setLayoutX((screenWidth / 2) + 50);
		instructions.setLayoutY((screenHeight / 4) * 2);
		instructions.setLayoutX((screenWidth / 2) + 50);
		instructions.setPrefWidth(100);
		instructions.setPrefHeight(50);
		backBtn.setPrefWidth(100);
		backBtn.setPrefHeight(50);
		submit.setPrefWidth(100);
		submit.setPrefHeight(50);
		
		
		

	}

	public Button getStartButton() {
		return startGame;

	}
	public Button getInstructions() {
		return instructions;

	}
	
	public Button getBack() {
		return backBtn;
	}
	
	public Button getSubmit() {
		return this.submit;
	}

}
