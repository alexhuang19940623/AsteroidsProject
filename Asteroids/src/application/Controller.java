package application;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Controller {
	// TODO figure out how to handle multiple buttons pressed simultaneously
	void show(KeyEvent event, Player player) {
		if (event.getCode() == KeyCode.LEFT){
			player.setRotateLeft(true);
		}
		else if (event.getCode() == KeyCode.RIGHT) {
			player.setRotateRight(true);			
		}
		if (event.getCode() == KeyCode.UP) {
			player.setThrusting(true);
			player.thrust();
		}
		if (event.getCode() == KeyCode.TAB) {
			player.hyperJump();
		}
		if (event.getCode() == KeyCode.SPACE) {
			player.setShooting(true);
		}
	}
	// Below we can see when a key is released. I think this can be used to make our inputs feel better

	void keyReleased(KeyEvent event, Player player) {
		if (event.getCode() == KeyCode.LEFT) {
			player.setRotateLeft(false);
		}
		if (event.getCode() == KeyCode.RIGHT) {
			player.setRotateRight(false);
		}
		if (event.getCode() == KeyCode.UP) {
			player.setThrusting(false);
		}
		if (event.getCode() == KeyCode.SPACE) {
			player.setShooting(false);
		}
	}
}