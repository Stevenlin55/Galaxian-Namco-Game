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
	 * Double array of bubble locations. The rows are the bubbles, the columns are the x or y position of the bubbles
	 * @param n number of bubbles
	 * @return returns a double array of bubble locations
	 */
	public static double[][] createRandomBubbleLocations(int n) {
		double[][] bubbleLocations = new double[n][2];
		for (int i = 0; i<n; i++) {
			bubbleLocations[i][0] = randomInRange(0,1);
			bubbleLocations[i][1] = randomInRange(-0.4,0);
		}
		return bubbleLocations;
	}

	/**
	 * Takes array of bubbleLocations and changes their y positions only
	 * @param bubbleLocations bubbleLocations array
	 */
	public static void advanceBubbles(double[][] bubbleLocations) {
		for (int i = 0; i < 20; i++) {
			double y = randomInRange(0,0.01);
			bubbleLocations[i][1] = bubbleLocations[i][1] + y;
			if (bubbleLocations[i][1] >=1) {
				bubbleLocations[i][1] = -0.2;
			}
		}
	}

	/**
	 * Draws the bubbles at the position defined in bubbleLocations array
	 * @param bubbleLocations bubbleLocations array
	 */
	public static void drawBubblesAt(double[][] bubbleLocations) {

		for (int i = 0; i < 20; i++) {
			drawBubbles(bubbleLocations[i][0], bubbleLocations[i][1], 0.015);
		}

	}
	/**
	 * Draws the bubbles
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param radius radius of bubble
	 */
	public static void drawBubbles(double x, double y, double radius) {
		StdDraw.setPenColor((int)randomInRange(0,255),(int)randomInRange(0,255),(int)randomInRange(0,255));
		StdDraw.filledCircle(x, y,radius);
	}

	/**
	 * Creates random locations for the treasures
	 * @param n number of treasure chests
	 * @return 2D array of treasure locations
	 */
	public static double[][] createRandomTreasureLocations(int n) {
		double[][] treasureLocations = new double[n][2];
		for (int i = 0; i<n; i++) {
			treasureLocations[i][0] = randomInRange(0,1);
			treasureLocations[i][1] = randomInRange(0.2,0.7);
		}
		return treasureLocations;
	}

	/**
	 * Creates random locations for the diamonds
	 * @param n number of diamonds
	 * @return 2D array of diamond locations
	 */
	public static double[][] createRandomDiamondLocations(int n) {
		double[][] diamondLocations = new double[n][2];
		for (int i = 0; i<n; i++) {
			diamondLocations[i][0] = randomInRange(0,1);
			diamondLocations[i][1] = randomInRange(0.2,0.7);
		}
		return diamondLocations;
	}

	/**
	 * Creates random rock locations
	 * @param n number of rocks
	 * @return 2D array of rock locations
	 */
	public static double[][] createRandomRockLocations(int n) {
		double[][] rockLocations = new double[n][2];
		for (int i = 0; i<n; i++) {
			rockLocations[i][0] = randomInRange(0,1);
			rockLocations[i][1] = randomInRange(0.2,0.7);
		}
		return rockLocations;

	}

	/**
	 * Draws the treasure chests at locations defined in array
	 * @param treasureLocations array of treasure locations
	 */
	public static void drawTreasuresAt(double[][] treasureLocations) {
		for (int i = 0 ; i < 4 ; i++) {
			StdDraw.picture(treasureLocations[i][0], treasureLocations[i][1], "images/treasure.png", 0.1, 0.1);
		}
	}
	/**
	 * Draws the diamonds at locations defined in array
	 * @param diamondLocations array of diamond locations
	 */
	public static void drawDiamondsAt(double[][] diamondLocations) {
		for (int i = 0 ; i < 2 ; i++) {
			StdDraw.picture(diamondLocations[i][0], diamondLocations[i][1], "images/diamond.png", 0.1, 0.1);
		}
	}
	/**
	 * Draws rocks at locations defined in array
	 * @param rockLocations array of rock locations
	 */
	public static void drawRocksAt(double[][] rockLocations) {

		for (int i=0; i<12; i++) {
			StdDraw.picture(rockLocations[i][0], rockLocations[i][1], "images/rock.png", 0.12, 0.12);
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
	 * @param rockLocations array of rock locations
	 * @return true if there is a collision, false if there isn't
	 */
	public static boolean rockPirateCollision(double pirateX,double pirateY, double[][] rockLocations) {
		for (int i = 0; i < 12; i++) {
			if (Math.sqrt(Math.pow((rockLocations[i][0]-pirateX), 2) + Math.pow((rockLocations[i][1]-pirateY),2)) <= 0.045+0.03) {
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
	public static boolean rockMissleCollision(double missleX, double missleY, double[][] rockLocations) {
		for (int i = 0; i < 12; i++) {
			if (Math.sqrt(Math.pow((rockLocations[i][0]-missleX), 2) + Math.pow((rockLocations[i][1]-missleY),2)) <= 0.045+0.007) {
				return true;
			}
		}
		return false; 
	}

	/**
	 * Whether or not the pirate hit the treaure
	 * @param pirateX x-coordinate of pirate
	 * @param pirateY y-coordinate of pirate
	 * @param rockLocations array of treasure locations
	 * @return true if there is a collision, false if there isn't
	 */
	public static boolean treasureCollision(double pirateX,double pirateY, double[][] treasureLocations) {
		for (int i = 0; i < 4; i++) {
			if (Math.sqrt(Math.pow((treasureLocations[i][0]-pirateX), 2) + Math.pow((treasureLocations[i][1]-pirateY),2)) <= 0.045+0.03) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Whether or not the pirate hit the diamond
	 * @param pirateX x-coordinate of pirate
	 * @param pirateY y-coordinate of pirate
	 * @param rockLocations array of diamond locations
	 * @return true if there is a collision, false if there isn't
	 */
	public static boolean diamondCollision(double pirateX,double pirateY, double[][] diamondLocations) {
		for (int i = 0; i < 2; i++) {
			if (Math.sqrt(Math.pow((diamondLocations[i][0]-pirateX), 2) + Math.pow((diamondLocations[i][1]-pirateY),2)) <= 0.02+0.03) {
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

		double[][] bubbleLocations = createRandomBubbleLocations(20);
		double[][] rockLocations = createRandomRockLocations(12);
		double[][] treasureLocations = createRandomTreasureLocations(4);
		double[][] diamondLocations = createRandomDiamondLocations(2);

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

			if (checkFor(KeyEvent.VK_W)) { //move left
				rocketY = rocketY + 0.005;
				missleX= rocketX; //missle moves with rocket
			} 

			if (checkFor(KeyEvent.VK_A)) { //move left
				rocketX = rocketX - 0.005;
				missleX= rocketX; //missle moves with rocket
			} 
			if (checkFor(KeyEvent.VK_D)) { //move right
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
			if (rockPirateCollision(rocketX,rocketY,rockLocations)) {

				//the for loop below will go through the rockLocations array and check which rock was involved in collision and moves them off screen
				for (int i = 0; i < 12; i++) {
					if (Math.sqrt(Math.pow((rockLocations[i][0]-rocketX), 2) + Math.pow((rockLocations[i][1]-rocketY),2)) <= 0.045+0.03) {
						rockLocations[i][0] = -1;
						rockLocations[i][1] = -1;
					}
				}
				--points; //decrease points by 1 every time the pirate hits a rock
				if (rocketLife2 == false) rocketLife1 = false; //if it only has one life left, lose its last life
				rocketLife2 = false; //lose its first life first
			}

			if (rockMissleCollision(missleX,missleY,rockLocations)) {

				//the for loop below will go through the rockLocations array and check which rock was involved in collision and moves them off screen, along with missle off screen
				for (int i = 0; i < 12; i++) {
					if (Math.sqrt(Math.pow((rockLocations[i][0]-missleX), 2) + Math.pow((rockLocations[i][1]-missleY),2)) <= 0.045+0.007) {
						missleFired = false;
						missleX = rocketX;
						missleY = rocketY+0.06;
						rockLocations[i][0] = -1;
						rockLocations[i][1] = -1;

					}
				}
				points++;
			}

			//check if there's a collision between pirate and treasure
			if (treasureCollision(rocketX,rocketY,treasureLocations)) {

				//the for loop below will go through the treasureLocations array and check which treasure was involved in collision and moves them off screen
				for (int i = 0; i < 4; i++) {
					if (Math.sqrt(Math.pow((treasureLocations[i][0]-rocketX), 2) + Math.pow((treasureLocations[i][1]-rocketY),2)) <= 0.045+0.03) {
						treasureLocations[i][0] = -1;
						treasureLocations[i][1] = -1;
					}
				}
				++points; //increase points by 1 when pirate gets a treasure chest
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
			//check if there's a collision between pirate and diamond
			if (diamondCollision(rocketX,rocketY,diamondLocations)) {

				//the for loop below will go through the diamondLocations array and check which diamond was involved in collision and moves them off screen
				for (int i = 0; i < 2; i++) {
					if (Math.sqrt(Math.pow((diamondLocations[i][0]-rocketX), 2) + Math.pow((diamondLocations[i][1]-rocketY),2)) <= 0.02+0.03) {
						diamondLocations[i][0] = -1;
						diamondLocations[i][1] = -1;
					}
				}
				points+=2; //increase points by 2 when pirate gets a diamond

			}
			
		
			drawTreasuresAt(treasureLocations);
			drawDiamondsAt(diamondLocations);
			drawRocksAt(rockLocations);
			drawBubblesAt(bubbleLocations);
			advanceBubbles(bubbleLocations);

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
