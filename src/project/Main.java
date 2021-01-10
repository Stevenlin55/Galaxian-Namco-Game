package project;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import edu.princeton.cs.introcs.StdDraw;



public class Main {
	/**
	 * Draws a box at the top of the game showing the time and score
	 * @param time the seconds that have passed
	 * @param points number of points the player has
	 */
	public static void scoreBox(int points) {
		StdDraw.setPenColor(Color.WHITE);
		StdDraw.text(0.5, 0.94, "Press A to move left, D to move right, J to shoot missle");
		StdDraw.setPenColor(Color.RED);
		StdDraw.text(0.5, 0.86, "Score: " + points);
	}

	/**
	 * Returns a double within the start and stop
	 * @param start the starting value
	 * @param stop the ending value
	 * @return a random number between the start and stop
	 */
	public static double randomInRange(double start, double stop) {		
		return (Math.random()*(stop-start)) +start;
	}


	/**
	 * Creates random alien locations
	 * @param n number of 
	 * @return 2D array of alien locations
	 */
	public static double[][] createRandomAlienLocations(int n) {
		double[][] alienLocations = new double[n][2];
		for (int i = 0; i<n; i++) {
			alienLocations[i][0] = randomInRange(0.3,0.97);
			alienLocations[i][1] = randomInRange(0.5,0.7);
		}
		return alienLocations;

	}

	/**
	 * Moves all aliens in a certain way 
	 * @param alienLocations array of alien locations
	 * @param nonFollowingAliensX array of the specific aliens that will not follow the rocket
	 * @param alienThatFollows1 the first alien that follows rocket
	 * @param alienThatFollows2 the second alien that follows the rocket
	 * @param rocketX
	 * @param rocketY
	 */
	public static void advanceGreenAliens(double[][] alienLocations, double[] nonFollowingAliensX, int alienThatFollows1,int alienThatFollows2, double rocketX,double rocketY) {


		double movingAlienY1 = randomInRange(-0.005,0);
		double movingAlienX1 = 0; 
		double movingAlienY2 = randomInRange(-0.005,0);
		double movingAlienX2 = 0; 

		for(int i = 0; i < 15; i++) {
			if (i == alienThatFollows1 || i == alienThatFollows2) { //if it is the alien that is moving, do nothing
			}							  //otherwise move the other aliens
			else if (alienLocations[i][0] <= 0.03) { 
				nonFollowingAliensX[i] *= -1;
				alienLocations[i][0] += nonFollowingAliensX[i];
			}
			else if (alienLocations[i][0] >= 0.97){
				nonFollowingAliensX[i] *= -1;
				alienLocations[i][0] += nonFollowingAliensX[i];
			}
			else {
				alienLocations[i][0] += nonFollowingAliensX[i]; //this moves them in the x direction 
			}
		}


		if (alienLocations[alienThatFollows1][1] > 1 || alienLocations[alienThatFollows2][1] > 1) { //if it is moved off screen, let it stay off screen
			return;
		}

		if (rocketX >alienLocations[alienThatFollows1][0]) { //if rocket is to the right of alien1, alien will move in positive direction
			movingAlienX1 = (Math.sqrt(Math.pow((alienLocations[alienThatFollows1][0]-rocketX), 2) + Math.pow((alienLocations[alienThatFollows1][1]-rocketY),2)))/90.0;
		}
		else if (rocketX <alienLocations[alienThatFollows1][0]) { //if rocket is to the left of alien1, alien will move in negative direction
			movingAlienX1 = -(Math.sqrt(Math.pow((alienLocations[alienThatFollows1][0]-rocketX), 2) + Math.pow((alienLocations[alienThatFollows1][1]-rocketY),2)))/90.0;
		}

		if (rocketX >alienLocations[alienThatFollows2][0]) { //if rocket is to the right of alien2, alien will move in positive direction
			movingAlienX2 = (Math.sqrt(Math.pow((alienLocations[alienThatFollows2][0]-rocketX), 2) + Math.pow((alienLocations[alienThatFollows2][1]-rocketY),2)))/90.0;
		}
		else if (rocketX <alienLocations[alienThatFollows2][0]) { //if rocket is to the left of alien2, alien will move in negative direction
			movingAlienX2 = -(Math.sqrt(Math.pow((alienLocations[alienThatFollows2][0]-rocketX), 2) + Math.pow((alienLocations[alienThatFollows2][1]-rocketY),2)))/90.0;
		}


		//next few lines of code will ensure that the alien1 will not move off screen in the x direction
		if (alienLocations[alienThatFollows1][0] <= 0.03) {
			alienLocations[alienThatFollows1][0] -= movingAlienX1;
		}
		else if (alienLocations[alienThatFollows1][0] >= 0.97){
			alienLocations[alienThatFollows1][0] -= movingAlienX1;
		}
		else {
			alienLocations[alienThatFollows1][0] += movingAlienX1; //this moves alien1 in the x direction 
		}

		alienLocations[alienThatFollows1][1] = alienLocations[alienThatFollows1][1] + movingAlienY1; //moves alien1 in y direction

		if (alienLocations[alienThatFollows1][1] <=0) {
			alienLocations[alienThatFollows1][1] = -1.2;  //move alien1 that follows off screen
		}

		//next few lines of code will ensure that the alien1 will not move off screen in the x direction
		if (alienLocations[alienThatFollows2][0] <= 0.03) {
			alienLocations[alienThatFollows2][0] -= movingAlienX2;
		}
		else if (alienLocations[alienThatFollows2][0] >= 0.97){
			alienLocations[alienThatFollows2][0] -= movingAlienX2;
		}
		else {
			alienLocations[alienThatFollows2][0] += (movingAlienX2-0.001); //this moves alien2 in the x direction 
		}

		alienLocations[alienThatFollows2][1] = alienLocations[alienThatFollows2][1] + movingAlienY2; //moves alien2 in y direction

		if (alienLocations[alienThatFollows2][1] <=0) {
			alienLocations[alienThatFollows2][1] = -1.2;  //move alien2 that follows off screen
		}

	}
	/**
	 * Draws aliens at locations defined in array
	 * @param alienLocations array of alien locations
	 */
	public static void drawGreenAliensAt(double[][] alienLocations) {

		for (int i=0; i<15; i++) {
			StdDraw.picture(alienLocations[i][0], alienLocations[i][1], "images/greenAlien.png", 0.08, 0.08);
		}
	}

	/**
	 * Draws missles at locations defined by x and y coordinates of rocket
	 * @param x x-coordinate of rocket
	 * @param y y-coordinate of rocket
	 */
	public static void drawMissleAt(double x, double y) {
		StdDraw.picture(x, y, "images/missle.png", 0.07, 0.07);
	}

	/**
	 * Whether or not the rocket hit the obstacle
	 * @param rocketX x-coordinate of rocket
	 * @param rocketY y-coordinate of rocket
	 * @param greenAlienLocations array of alien locations
	 * @return true if there is a collision, false if there isn't
	 */
	public static boolean alienrocketCollision(double rocketX,double rocketY, double[][] greenAlienLocations) {
		for (int i = 0; i < 15; i++) {
			if (Math.sqrt(Math.pow((greenAlienLocations[i][0]-rocketX), 2) + Math.pow((greenAlienLocations[i][1]-rocketY),2)) <= 0.035+0.03) {
				return true;
			}
		}
		return false;

	}

	/**
	 * Whether or not the missle hit the obstacle
	 * @param rocketX x-coordinate of missle
	 * @param rocketY y-coordinate of missle
	 * @param alienLocations array of alien locations
	 * @return true if there is a collision, false if there isn't
	 */
	public static boolean alienMissleCollision(double missleX, double missleY, double[][] alienLocations) {
		for (int i = 0; i < 15; i++) {
			if (Math.sqrt(Math.pow((alienLocations[i][0]-missleX), 2) + Math.pow((alienLocations[i][1]-missleY),2)) <= 0.035+0.007) {
				return true;
			}
		}
		return false; 
	}

	/**
	 * Draws the 3 slimes at specified coordinates
	 * @param slimeX1 
	 * @param slimeY1
	 * @param slimeX2
	 * @param slimeY2
	 * @param slimeX3
	 * @param slimeY3
	 */
	public static void drawSlimeAt(double slimeX1, double slimeY1, double slimeX2, double slimeY2,double slimeX3, double slimeY3) {
		StdDraw.picture(slimeX1, slimeY1, "images/slime.png", 0.07, 0.07);
		StdDraw.picture(slimeX2, slimeY2, "images/slime.png", 0.07, 0.07);
		StdDraw.picture(slimeX3, slimeY3, "images/slime.png", 0.07, 0.07);
	}

	/**
	 * Whether or not there is a collision bewteen a slime and the rocket
	 * @param slimeX1
	 * @param slimeY1
	 * @param slimeX2
	 * @param slimeY2
	 * @param slimeX3
	 * @param slimeY3
	 * @param rocketX
	 * @param rocketY
	 * @return
	 */
	public static boolean rocketSlimeCollision(double slimeX1, double slimeY1,double slimeX2, double slimeY2, double slimeX3, double slimeY3, double rocketX, double rocketY) {

		if (Math.sqrt(Math.pow((rocketX-slimeX1), 2) + Math.pow((rocketY-slimeY1),2)) <= 0.035+0.007) {
			return true;
		}
		if (Math.sqrt(Math.pow((rocketX-slimeX2), 2) + Math.pow((rocketY-slimeY2),2)) <= 0.035+0.007) {
			return true;
		}
		if (Math.sqrt(Math.pow((rocketX-slimeX3), 2) + Math.pow((rocketY-slimeY3),2)) <= 0.035+0.007) {
			return true;
		}
		if (Math.sqrt(Math.pow((rocketX-slimeX3), 2) + Math.pow((rocketY-slimeY3),2)) <= 0.035+0.007) {
			return true;
		}
		return false; 
	}




	public static void main(String[] args) {

		StdDraw.enableDoubleBuffering();

		double rocketX = 0.5;  // x location of the rocket
		double rocketY = 0.1;  // y location of the rocket
		double missleX = rocketX; //initial x location of missle 
		double missleY = rocketY+0.06; //initial y location of missle
		double missleFiredX = rocketX; //x location of where missle is fired

		//slimes will be off screen first
		double slimeX1 = -1; 
		double slimeY1 = -1.2; 
		double slimeX2 = -1;
		double slimeY2 = -1.2;
		double slimeX3 = -1;
		double slimeY3 = -1.2;


		int points = 0;


		double[][] alienLocations = createRandomAlienLocations(15); //there will be 15 aliens
		double[] nonFollowingAliensX = new double[15]; //all the aliens will have a starting horizontal velocity 
		for (int j = 0; j < 15; j++) {
			nonFollowingAliensX[j] =  0.005; //all aliens will have this initial velocity 
		} 


		boolean missleFired = false; //whether or not J was pressed
		boolean hasMissle = true;
		boolean rocketLife1 = true; //the player will have two lives available
		boolean rocketLife2= true;
		boolean gameOver = false;



		int alienThatFollows1 = (int)randomInRange(0,15); 
		int alienThatFollows2 = (int)randomInRange(0,15);
		int alienThatShoots1 = (int)randomInRange(0,15);
		int alienThatShoots2 = (int)randomInRange(0,15);
		int alienThatShoots3 = (int)randomInRange(0,15);

		while (gameOver == false) {

			StdDraw.clear();

			StdDraw.picture(0.5, 0.5, "images/space.jpg", 1.2, 1.2);
			StdDraw.setPenColor(Color.WHITE);
			StdDraw.text(0.08, 0.14, "Lives left");

			if (rocketLife1) StdDraw.picture(0.06, 0.09, "images/rocket.png", 0.06, 0.06);

			if(rocketLife1 == false && rocketLife2 == false) gameOver = true; //instead of having it quit right away, lets have an explosino and then game over with score. This can be tracked with a timer too
			if (rocketLife2) StdDraw.picture(0.1, 0.09, "images/rocket.png", 0.06, 0.06);

			scoreBox(points);//keeps track of scores


			if (checkFor(KeyEvent.VK_A) && rocketX>=0.03) { //move left if A is pressed and rocket is not too far left
				rocketX = rocketX - 0.005;
				missleX= rocketX; //missle moves with rocket
			} 
			if (checkFor(KeyEvent.VK_D) && rocketX<=0.97) { //move right if D is pressed and rocket is not too far right
				rocketX = rocketX + 0.005;
				missleX= rocketX; //missle moves with rocket
			}


			//the rocket
			StdDraw.picture(rocketX, rocketY, "images/rocket.png", 0.1, 0.1);


			if (checkFor(KeyEvent.VK_J) && missleY == rocketY+0.06) { //if j is pressed, missle will be fired only if rocket has missle
				missleFiredX= missleX;
				missleFired = true; 
				hasMissle = false;
				drawMissleAt(missleX,missleY);

			}

			if (missleFired) {
				missleX = missleFiredX; //makes sure the missle doesn't move with the rocket after being fired
				missleY += 0.02; //the missle fired will be moving up at constant speed if the missle was fired

			}

			if (missleY >= 1) { //when the fired missle is off screen, it'll remain off screen until j is pressed again
				missleFired = false;
				hasMissle= true;
				missleX = rocketX; 
				missleY = rocketY+0.06;
			}
			drawMissleAt(missleX,missleY);

			if (alienLocations[alienThatShoots1][1]<0) { //helps slime appear more often
				alienThatShoots1 = (int) randomInRange(0,15);
				if (alienThatShoots1 == alienThatShoots2) {
					alienThatShoots1 = (int) randomInRange(0,15);
				}
			}
			if (alienLocations[alienThatShoots2][1] <0) { //helps have slimes appear more often
				alienThatShoots2 = (int) randomInRange(0,15);
				if (alienThatShoots1 == alienThatShoots2) {
					alienThatShoots2 = (int) randomInRange(0,15);
				}

			}

			drawSlimeAt(slimeX1,slimeY1,slimeX2,slimeY2,slimeX3,slimeY3);
			if (slimeY1 <= -1) { //if slime1 is off screen, a new alien will shoot it
				slimeX1 = alienLocations[alienThatShoots1][0];
				slimeY1 = alienLocations[alienThatShoots1][1];
			}
			if (slimeY2 <= -1) { //if slime2 is off screen, a new alien will shoot it
				slimeX2 = alienLocations[alienThatShoots2][0];
				slimeY2 = alienLocations[alienThatShoots2][1];
			}
			if (slimeY3 <= -1) { //if slime2 is off screen, a new alien will shoot it
				slimeX3 = alienLocations[alienThatShoots3][0];
				slimeY3 = alienLocations[alienThatShoots3][1];
			}
			//velocites of the slimes
			slimeY1 -= 0.01; 
			slimeY2 -= 0.01;
			slimeY3 -= 0.01;
			//check if there's a collision between rocket and alien
			if (alienrocketCollision(rocketX,rocketY,alienLocations)) {

				//the for loop below will go through the alienLocations array and check which alien was involved in collision and moves them off screen
				for (int i = 0; i < 15; i++) {
					if (Math.sqrt(Math.pow((alienLocations[i][0]-rocketX), 2) + Math.pow((alienLocations[i][1]-rocketY),2)) <= 0.035+0.03) {
						alienLocations[i][0] = -1;
						alienLocations[i][1] = -1;
					}
				}
				if (rocketLife2 == false) rocketLife1 = false; //if it only has one life left, lose its last life
				rocketLife2 = false; //lose its first life first
			}

			if (alienMissleCollision(missleX,missleY,alienLocations)) {
				if (hasMissle) {
					for (int i = 0; i < 15; i++) {
						if (Math.sqrt(Math.pow((alienLocations[i][0]-missleX), 2) + Math.pow((alienLocations[i][1]-missleY),2)) <= 0.035+0.007) {
							alienLocations[i][0] = -1;
							alienLocations[i][1] = -1;
						}
					}
					if (rocketLife2 == false) rocketLife1 = false; //if it only has one life left, lose its last life
					rocketLife2 = false; //lose its first life first
				}
				//the for loop below will go through the alienLocations array and check which alien was involved in collision and moves them off screen, along with returning missle back to the rocket
				else {	for (int i = 0; i < 15; i++) {
					if (Math.sqrt(Math.pow((alienLocations[i][0]-missleX), 2) + Math.pow((alienLocations[i][1]-missleY),2)) <= 0.035+0.007) {
						missleFired = false;
						hasMissle = true;
						missleX = rocketX;
						missleY = rocketY+0.06;
						alienLocations[i][0] = -1;
						alienLocations[i][1] = -1;

					}

				}
					points++;
				}
			}
			if (rocketSlimeCollision(slimeX1,slimeY1,slimeX2,slimeY2,slimeX3,slimeY3, rocketX,rocketY)) {
				if (Math.sqrt(Math.pow((rocketX-slimeX1), 2) + Math.pow((rocketY-slimeY1),2)) <= 0.035+0.007) { //if collision with slime1, move it off screen 
					slimeX1 = -1;
					slimeY1 = -1.2;
				}
				if (Math.sqrt(Math.pow((rocketX-slimeX2), 2) + Math.pow((rocketY-slimeY2),2)) <= 0.035+0.007) {//if collision with slime2, move it off screen
					slimeX2 = -1;
					slimeY2 = -1.2;
				}
				if (Math.sqrt(Math.pow((rocketX-slimeX3), 2) + Math.pow((rocketY-slimeY3),2)) <= 0.035+0.007) {//if collision with slime3, move it off screen
					slimeX3 = -1;
					slimeY3 = -1.2;
				}
				if (rocketLife2 == false) rocketLife1 = false; //if it only has one life left, lose its last life
				rocketLife2 = false; //lose its first life first
			}



			drawGreenAliensAt(alienLocations);
			advanceGreenAliens(alienLocations, nonFollowingAliensX, alienThatFollows1, alienThatFollows2,rocketX, rocketY);

			if (alienLocations[alienThatFollows1][1] < 0) alienThatFollows1 = (int) randomInRange(0,15); //if the previous alien that was moving is off screen, then choose a different alien
			if (alienLocations[alienThatFollows2][1] < 0) alienThatFollows2 = (int) randomInRange(0,15);
			int index=0; 

			//this loop will check if each alien is offscreen
			while(alienLocations[index][1] < 0) { 

				if (index == 14 ) { //if it reaches the last alien, and they are all offscreen, then respawn them onto screen by creating new random locations
					alienLocations = createRandomAlienLocations(15);
					break;
				}
				else {
					index++; 
				}

			}


			StdDraw.show();  
			StdDraw.pause(10);   // 1/100 of a second


		}

		StdDraw.clear();

		//the game over screen that also displays the score
		StdDraw.picture(0.5, 0.5, "images/space.jpg", 1.2, 1.2);
		StdDraw.setPenColor(Color.WHITE);
		StdDraw.text(0.5, 0.7, "GAME OVER");
		StdDraw.setPenColor(Color.RED);
		StdDraw.text(0.5, 0.5, "Score: " + points);
		StdDraw.show();
	}

	/**
	 * Check to see if a given key is pressed at the moment.  This method does not
	 *   wait for a key to be pressed, so if nothing is pressed, it returns
	 *   false right away.
	 *   
	 * The event constants are found at:
	 *   https://docs.oracle.com/javase/7/docs/api/java/awt/event/KeyEvent.html
	 * @param key the integer code of the key
	 * @return true if that key is down, false otherwise
	 */
	private static boolean checkFor(int key) {
		if (StdDraw.isKeyPressed(key)) {
			return true;
		}
		else {
			return false;
		}
	}

}
