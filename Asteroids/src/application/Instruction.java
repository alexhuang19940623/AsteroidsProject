package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Instruction extends Application {
	public static int screenWidth = 600;
	public static int screenHeight = 400;
	
	@Override
	public void start(Stage instructionStage) {
		instructionStage.setTitle("Instruction Info");
        
        BorderPane instructRoot = new BorderPane();
        instructRoot.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        Scene instructScreen = new Scene(instructRoot,screenWidth,screenHeight,Color.BLACK);

        Text text = new Text();
        text.setX(20);
        text.setY(80);
        text.setText("Instructions: \n"
        		+"\n"
        		+ "Action is executed when Key is pressed\n"
        		+ "Action is interrupted when Key is released\n"
        		+ "\n"
        		+ "Key LEFT to turn left\n"
        		+ "Key RIGHT to turn right\n"
        		+ "Key UP to accelerate\n"
        		+ "Key TAB for Hyperjump\n"
        		+ "Key SPACE to shoot the bullet");
        text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        text.setFill(Color.SNOW); 
        instructRoot.getChildren().add(text);
	
		instructionStage.setScene(instructScreen);
		instructionStage.show();
  }    
}