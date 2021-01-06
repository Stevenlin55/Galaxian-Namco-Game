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
	 * Creates random rock locations
	 * @param n number of 
	 * @return 2D array of rock locations
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
	 * Takes array of alienLocations and changes their y positions only
	 * @param bubbleLocations bubbleLocations array
	 */
	public static void advanceGreenAliens(double[][] alienLocations) {
		for (int i = 0; i < 12; i++) {
			double y = randomInRange(-0.005,0);
			double x = randomInRange(-0.015,0.015);
		
			if (alienLocations[i][0] <= 0.03) {
				alienLocations[i][0] -= x;
			}
			else if (alienLocations[i][0] >= 0.97){
				alienLocations[i][0] -= x;
			}
			else {
				alienLocations[i][0] += x;
			}
			alienLocations[i][1] = alienLocations[i][1] + y;
			if (alienLocations[i][1] <=0) {
				alienLocations[i][1] = 1.2;
			}
			
		}
	}
	/**
	 * Draws aliens at locations defined in array
	 * @param greenAlienLocations array of rock locations
	 */
	public static void drawGreenAliensAt(double[][] greenAlienLocations) {

		for (int i=0; i<12; i++) {
			StdDraw.picture(greenAlienLocations[i][0], greenAlienLocations[i][1], "images/greenAlien.png", 0.08, 0.08);
		}
	}

	/**
	 * Draws missles at locations defined by x and y coordinates of pirate
	 * @param x x-coordinate of pirate
	 * @param y y-coordinate of pirate
	 */
	public static void drawMissleAt(double x, double y) {
		StdDraw.picture(x, y, "images/missle.png", 0.07, 0.07);
	}

	/**
	 * Whether or not the pirate hit the obstacle
	 * @param pirateX x-coordinate of pirate
	 * @param pirateY y-coordinate of pirate
	 * @param greenAlienLocations array of rock locations
	 * @return true if there is a collision, false if there isn't
	 */
	public static boolean alienRocketCollision(double pirateX,double pirateY, double[][] greenAlienLocations) {
		for (int i = 0; i < 12; i++) {
			if (Math.sqrt(Math.pow((greenAlienLocations[i][0]-pirateX), 2) + Math.pow((greenAlienLocations[i][1]-pirateY),2)) <= 0.035+0.03) {
				return true;
			}
		}
		return false;

	}

	/**
	 * Whether or not the missle hit the obstacle
	 * @param pirateX x-coordinate of missle
	 * @param pirateY y-coordinate of missle
	 * @param rockLocations array of rock locations
	 * @return true if there is a collision, false if there isn't
	 */
	public static boolean alienMissleCollision(double missleX, double missleY, double[][] rockLocations) {
		for (int i = 0; i < 12; i++) {
			if (Math.sqrt(Math.pow((rockLocations[i][0]-missleX), 2) + Math.pow((rockLocations[i][1]-missleY),2)) <= 0.035+0.007) {
				return true;
			}
		}
		return false; 
	}

	
	public static void main(String[] args) {

		StdDraw.enableDoubleBuffering();
	
		double rocketX = 0.5;  // x location of the rocket
		double rocketY = 0.1;  // y location of the rocket
		double missleX = rocketX; //initial x location of missle 
		double missleY = rocketY+0.06; //initial y location of missle
		double missleFiredX = rocketX;
		//
		// This song will play in the background allowing your other work
		//   to proceed. 
		// If annoyed, comment this out
		// If you want more, change playOnce() to playAlways()
		//
		//		BackgroundSong sbsp = new BackgroundSong("SpongeBobSquarePants.wav");
		//		sbsp.playOn1=ce();

		int time = 1; //this will be used to help track how many seconds has passed
		int frozenTime = 0; //time that will be used to keep track of how long the pirate has been frozen for
		int points = 0;

	
		double[][] greenAlienLocations = createRandomAlienLocations(12);
		
		boolean rocketFrozen = false; //whether or not pirate has hit an obstacle
		boolean missleFired = false; //whether or not J was pressed
		boolean rocketLife1 = true;
		boolean rocketLife2= true;
		boolean gameOver = false;
		
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


			//
			// The pirate
			//
			StdDraw.setPenColor(Color.BLACK);
			StdDraw.picture(rocketX, rocketY, "images/rocket.png", 0.1, 0.1);


			if (checkFor(KeyEvent.VK_J) && missleY == rocketY+0.06) { //if j is pressed, missle will be fired only if rocket has missle
				missleFiredX= missleX;
				missleFired = true; 
				drawMissleAt(missleX,missleY);
				
			}

			if (missleFired) {
				missleX = missleFiredX; //makes sure the missle doesn't move with the rocket after being fired
				missleY += 0.03; //the missle fired will be moving up at constant speed if the missle was fired
				
			}

			if (missleY >= 1) { //when the fired missle is off screen, it'll remain off screen until j is pressed again
				missleFired = false;
				missleX = rocketX; 
				missleY = rocketY+0.06;
			}
			drawMissleAt(missleX,missleY);

			//check if there's a collision between pirate and obstacle
			if (alienRocketCollision(rocketX,rocketY,greenAlienLocations)) {

				//the for loop below will go through the rockLocations array and check which rock was involved in collision and moves them off screen
				for (int i = 0; i < 12; i++) {
					if (Math.sqrt(Math.pow((greenAlienLocations[i][0]-rocketX), 2) + Math.pow((greenAlienLocations[i][1]-rocketY),2)) <= 0.045+0.03) {
						greenAlienLocations[i][0] = -1;
						greenAlienLocations[i][1] = -1;
					}
				}
				--points; //decrease points by 1 every time the pirate hits a rock
				if (rocketLife2 == false) rocketLife1 = false; //if it only has one life left, lose its last life
				rocketLife2 = false; //lose its first life first
			}

			if (alienMissleCollision(missleX,missleY,greenAlienLocations)) {

				//the for loop below will go through the rockLocations array and check which rock was involved in collision and moves them off screen, along with missle off screen
				for (int i = 0; i < 12; i++) {
					if (Math.sqrt(Math.pow((greenAlienLocations[i][0]-missleX), 2) + Math.pow((greenAlienLocations[i][1]-missleY),2)) <= 0.045+0.007) {
						missleFired = false;
						missleX = rocketX;
						missleY = rocketY+0.06;
						greenAlienLocations[i][0] = -1;
						greenAlienLocations[i][1] = -1;

					}
				}
				points++;
			}

		
			if (rocketFrozen){ //if the pirate is frozen after hitting obstacle
				if ((frozenTime/33+1) == 2) { //after two seconds, the pirate will start moving again
					rocketFrozen = false;
					frozenTime = 0; //reset frozenTime to 0 because not frozen anymore
				}
				time+=1;
				frozenTime+=1;
			}
			if(rocketFrozen == false) {
				
				time+=1;
			}
		
			
		
			drawGreenAliensAt(greenAlienLocations);
			advanceGreenAliens(greenAlienLocations);
			
			StdDraw.show();  
			StdDraw.pause(10);   // 1/100 of a second
			
		}

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
