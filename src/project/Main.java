package project;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.Random;

import edu.princeton.cs.introcs.StdDraw;


public class Main {
	
	public static void scoreBox(int time, int points) {
		int sec = time/33 + 1;
		StdDraw.setPenColor(Color.RED);
		StdDraw.filledRectangle(0.5, 0.9, 0.47, 0.1);
		StdDraw.setPenColor(Color.WHITE);
		StdDraw.text(0.5, 0.98, sec + " seconds have passed");
		StdDraw.text(0.5, 0.94, "Press a to move left, d to move right, j to fire missles");
		StdDraw.text(0.5, 0.86, "Points: " + points);
	}
	
	
	public static double randomInRange(double start, double stop) {		
		return (Math.random()*(stop-start)) +start;
	}
	
	public static double[][] createRandomBubbleLocations(int n) {
		double[][] bubbleLocations = new double[n][2];
		for (int i = 0; i<n; i++) {
			bubbleLocations[i][0] = randomInRange(0,1);
			bubbleLocations[i][1] = randomInRange(-0.4,0);
		}
		return bubbleLocations;
	}
	
	public static void advanceBubbles(double[][] bubbleLocations) {
		for (int i = 0; i < 20; i++) {
			double y = randomInRange(0,0.01);
			bubbleLocations[i][1] = bubbleLocations[i][1] + y;
			if (bubbleLocations[i][1] >=1) {
				bubbleLocations[i][1] = -0.2;
			}
		}
	}
	public static void drawBubblesAt(double[][] bubbleLocations) {
		
		for (int i = 0; i < 20; i++) {
			drawBubbles(bubbleLocations[i][0], bubbleLocations[i][1], 0.015);
		}

	}
	public static void drawBubbles(double x, double y, double radius) {
		StdDraw.setPenColor((int)randomInRange(0,255),(int)randomInRange(0,255),(int)randomInRange(0,255));
		StdDraw.filledCircle(x, y,radius);
	}
	public static double[][] createRandomTreasureLocations(int n) {
		double[][] treasureLocations = new double[n][2];
		for (int i = 0; i<n; i++) {
			treasureLocations[i][0] = randomInRange(0,1);
			treasureLocations[i][1] = randomInRange(0.2,0.7);
		}
		return treasureLocations;
	}
	
	public static double[][] createRandomDiamondLocations(int n) {
		double[][] diamondLocations = new double[n][2];
		for (int i = 0; i<n; i++) {
			diamondLocations[i][0] = randomInRange(0,1);
			diamondLocations[i][1] = randomInRange(0.2,0.7);
		}
		return diamondLocations;
	}
	
	public static double[][] createRandomRockLocations(int n) {
		double[][] rockLocations = new double[n][2];
		for (int i = 0; i<n; i++) {
			rockLocations[i][0] = randomInRange(0,1);
			rockLocations[i][1] = randomInRange(0.2,0.7);
		}
		return rockLocations;
		
	}
	
	public static void drawTreasuresAt(double[][] treasureLocations) {
		for (int i = 0 ; i < 4 ; i++) {
			StdDraw.picture(treasureLocations[i][0], treasureLocations[i][1], "images/treasure.png", 0.1, 0.1);
		}
	}
		
	public static void drawDaimondsAt(double[][] diamondLocations) {
		for (int i = 0 ; i < 2 ; i++) {
			StdDraw.picture(diamondLocations[i][0], diamondLocations[i][1], "images/diamond.png", 0.1, 0.1);
		}
	}
	
	public static void drawRocksAt(double[][] rockLocations) {
	
		for (int i=0; i<12; i++) {
			StdDraw.picture(rockLocations[i][0], rockLocations[i][1], "images/rock.png", 0.12, 0.12);
		}
	}
	
	public static void drawMissleAt(double x, double y) {
		StdDraw.picture(x, y, "images/missle.png", 0.07, 0.07);
	}
	
	public static boolean rockPirateCollision(double pirateX,double pirateY, double[][] rockLocations) {
		for (int i = 0; i < 12; i++) {
			if (Math.sqrt(Math.pow((rockLocations[i][0]-pirateX), 2) + Math.pow((rockLocations[i][1]-pirateY),2)) <= 0.045+0.03) {
				return true;
			}
		}
		return false;
		
	}
	
	public static boolean rockMissleCollision() {
		return false;
	}
	
	public static boolean treasureCollision(double pirateX,double pirateY, double[][] treasureLocations) {
		for (int i = 0; i < 4; i++) {
			if (Math.sqrt(Math.pow((treasureLocations[i][0]-pirateX), 2) + Math.pow((treasureLocations[i][1]-pirateY),2)) <= 0.045+0.03) {
				return true;
			}
		}
		return false;
	}
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
		
		double px = 0.5;  // x location of the demo point
		double py = 0.05;  // y location of the demo point
		
		//
		// This song will play in the background allowing your other work
		//   to proceed. 
		// If annoyed, comment this out
		// If you want more, change playOnce() to playAlways()
		//
//		BackgroundSong sbsp = new BackgroundSong("SpongeBobSquarePants.wav");
//		sbsp.playOn1=ce();
		int time = 1; //this will be used to help track how many seconds has passed
		int points = 0;
		
		double[][] bubbleLocations = createRandomBubbleLocations(20);
		double[][] rockLocations = createRandomRockLocations(12);
		double[][] treasureLocations = createRandomTreasureLocations(4);
		double[][] diamondLocations = createRandomDiamondLocations(2);
		
		while (true) {
			StdDraw.clear();
			
			StdDraw.picture(0.5, 0.5, "images/underwater.jpg", 1.2, 1.2);
	
			scoreBox(time,points);//draws the box at the top and keeps track of scores

			
			py+= 0.001; //moves up the screen continuously at constant speed
			if (checkFor(KeyEvent.VK_A)) { //move left
				px = px - 0.005;
			}
			if (checkFor(KeyEvent.VK_D)) { //move right
				px = px + 0.005;
			}
			

			//
			// The pirate
			//
			StdDraw.setPenColor(Color.BLACK);
			StdDraw.filledCircle(px, py, .03);	
			
			//
			//The bubbles
			//
			double missleY = py;
			if (checkFor(KeyEvent.VK_J)) {
				drawMissleAt(px,missleY);//cannot figure out how to have the missles go on its own
			}
			
			
			//check if there's a collision between pirate and obstacle
			if (rockPirateCollision(px,py,rockLocations)) {
				
			//the for loop below will go through the rockLocations array and check which rock was involved in collision and moves them off screen
				for (int i = 0; i < 12; i++) {
					if (Math.sqrt(Math.pow((rockLocations[i][0]-px), 2) + Math.pow((rockLocations[i][1]-py),2)) <= 0.045+0.03) {
						rockLocations[i][0] = -1;
						rockLocations[i][1] = -1;
					}
				}
				--points; //decrease points by 1 every time the pirate hits a rock
							//freeze the pirate for 2 seconds
			}
			
			//check if there's a collision between pirate and treasure
			if (treasureCollision(px,py,treasureLocations)) {
				
			//the for loop below will go through the treasureLocations array and check which treasure was involved in collision and moves them off screen
				for (int i = 0; i < 4; i++) {
					if (Math.sqrt(Math.pow((treasureLocations[i][0]-px), 2) + Math.pow((treasureLocations[i][1]-py),2)) <= 0.045+0.03) {
						treasureLocations[i][0] = -1;
						treasureLocations[i][1] = -1;
					}
				}
				++points; //increase points by 1 when pirate gets a treasure chest
							
			}
			//check if there's a collision between pirate and diamond
			if (diamondCollision(px,py,diamondLocations)) {
				
			//the for loop below will go through the diamondLocations array and check which dimaond was involved in collision and moves them off screen
				for (int i = 0; i < 2; i++) {
					if (Math.sqrt(Math.pow((diamondLocations[i][0]-px), 2) + Math.pow((diamondLocations[i][1]-py),2)) <= 0.02+0.03) {
						diamondLocations[i][0] = -1;
						diamondLocations[i][1] = -1;
					}
				}
				points+=2; //increase points by 2 when pirate gets a diamond
						
			}
			drawTreasuresAt(treasureLocations);
			drawDaimondsAt(diamondLocations);
			drawRocksAt(rockLocations);
			drawBubblesAt(bubbleLocations);
			advanceBubbles(bubbleLocations);
			
			StdDraw.show();  
			StdDraw.pause(10);   // 1/100 of a second
			time+=1;
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
