package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class HighScore {
	public String[] names = {"----", "----", "----"};
	public int[] scores = {0,0,0};
	
	public HighScore() {
		getHighScores();
	}

	public int getScoreAmount(int level) {
		return this.scores[level];
	}

	public String getScoreName(int level) {
		return this.names[level];
	}

	public boolean isNewHighScore(int score) {
		for (int i = 0; i < scores.length; i++) {
			if (scores[i] < score) {
				return true;	
			}
		}
		return false;
	}

	public void updateHighScore(String newName, int newScore) {
		if (newScore > scores[0]) {
			scores[2] = scores[1];
			names[2] = names[1];
			scores[1] = scores[0];
			names[1] = names[0];
			scores[0] = newScore;
			names[0] = newName;

		}
		else if (newScore > scores[1]) {
			scores[2] = scores[1];
			names[2] = names[1];
			scores[1] = newScore;
			names[1] = newName;
		}
		else if (newScore > scores[2]) {
			scores[2] = newScore;
			names[2] = newName;
		}
		
		createHighScoreCsv();


	}

	public Text topScoreToText(int index, int screenHeight, int screenWidth) {
		String playerscore = Integer.toString(this.scores[index]);
		String newString = String.format("No. %1$d %2$s: %3$s", index+1, this.names[index], playerscore);
		Text text = new Text();
		text.setText(newString);
		text.setFill(Color.WHITE);

		text.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
		text.setTextAlignment(TextAlignment.JUSTIFY);

		return text;
	}
	
	public String topScoreToString(int index, int screenHeight, int screenWidth) {
		String playerscore = Integer.toString(this.scores[index]);
		String newString = String.format("%1$d %2$s: %3$s", index+1, this.names[index], playerscore);
		

		return newString;
	}
	
	public TextField getTextField(int screenWidth,int screenHeight) {
		TextField field = new TextField("Enter your name:");
		field.setMaxWidth(300);
		field.setPrefHeight(30);

		return field;
	}
	
	public void createHighScoreCsv() {
		String filePath = "highScore.csv";
		File file = new File(filePath);
		try {
			FileWriter CSV = new FileWriter(file);
			CSV.append(String.format("%s,%d\n", this.names[0], this.scores[0]));
			CSV.append(String.format("%s,%d\n", this.names[1], this.scores[1]));
			CSV.append(String.format("%s,%d\n", this.names[2], this.scores[2]));
			CSV.flush();
			CSV.close();
			
			
		}
	    catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	// https://stackabuse.com/reading-and-writing-csvs-in-java/
	public void getHighScores() {
		String filePath = "highScore.csv";
//		File file = new File(filePath);
		try {
			Scanner sc = new Scanner(new File(filePath));
			sc.useDelimiter(",");
			
//			for (int i = 0; i < 6; i++) {
//				System.out.println(sc.next());
//			}
			int index = 0;
			 while(sc.hasNext() || index <= 3)
			    {
			        final String input = sc.nextLine();
			        String[] scoreInfo = input.split(",");
			        this.names[index] = scoreInfo[0];
			        this.scores[index] = Integer.parseInt(scoreInfo[1]);
			        index++;
			        
			    }
			
			
			sc.close();
			
		}
		catch (Exception e) {
			
		    }
	}
	
	public Text congrats() {
		Text congrats = new Text("Congratulations!");
		congrats.setFill(Color.WHITE);
		congrats.setFont(Font.font("Verdana", FontWeight.BOLD, 80));
		return congrats;
		
		
	}
	
	public Text congratsBody() {
		Text cBody = new Text("You've set a new high score!");
		cBody.setFill(Color.WHITE);
		cBody.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
		return cBody;
	}







}
