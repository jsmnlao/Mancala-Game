package mancala;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

/**
 * Fall 2022: CS 151 Team Project - Mancala Game GUI
 * @author Jasmine Lao, Zachary Hobbs
 * @version 1.0 October 25, 2022
 */

/**
 * Pit class is a Hole that represents each pit.
 */
public class Pit implements Hole{

	private int x;
	private int y;
	private int width;
	private int height;
	private int totalStones;
	private final int STONE_SIZE = 10;
	private int id;
	
	private Shape shape;
	private Color color;
	private Color stoneColor;
	private ArrayList<Ellipse2D.Double> stonesInPit;
	
	/**
	 * creates the pit for the mancala game
	 * @param x horizontal value
	 * @param y vertical value
	 * @param w how wide it is
	 * @param h how tall it is
	 */
	public Pit(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
		stonesInPit = new ArrayList<>();
		totalStones = 0;
		id = -1;
	}
	
	/**
	 * Returns string representation of pit
	 * @return String
	 */
	public String toString() {
		String letter = "";
		if(this.y == MancalaView.TOP_Y) {
			letter = "B"; 
		}
		else if(this.y == MancalaView.BOTTOM_Y){
			letter = "A"; 
		}
		return "Pit ID: " + this.getId() + "\n" + "Pit Label: " + letter + (id+1);
	}
	
	/**
	 * gets the id
	 * @return the id of the pit
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Sets the id of the pit
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**Returns the x coord
	 * @return int
	 */
	public int getX() {
		return this.x;
	}

	/**Returns the y coord
	 * @return int
	 */
	public int getY() {
		return this.y;
	}

	/**Returns the width of pit
	 * @return int
	 */
	public int getWidth() {
		return this.width;
	}

	/**Returns the height of pit
	 * @return int
	 */
	public int getHeight() {
		return this.height;
	}
	
	/**
	 * Adds one stone to the number of total stones
	 */
	public void addStone() {
		totalStones += 1;
	}
	
	/**Returns the number stones in pit
	 * @return int
	 */
	public int getStones() {
		return totalStones;
	}
	
	/**
	 * Sets the number of stones in pit
	 * @param num
	 */
	public void removeStone() {
		totalStones -= 1;
	}
	
	/**
	 * Adds an amount of stones in pit
	 * @param amount
	 */
	public void setStones(int amount) {
		totalStones = amount;
	}

	/**
	 * Returns the shape of pit
	 * @return Shape
	 */
	public Shape getShape() {
		return shape;
	}

	/**
	 * Sets the shape of the pit
	 * @param shape
	 */
	public void setShape(Shape shape) {
		this.shape = shape;
	}
	
	/**
	 * Sets the color of the pit
	 * @param color 
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	
	/**
	 * Sets the color of stone in the pit
	 * @param stoneColor
	 */
	public void setStoneColor(Color stoneColor) {
		this.stoneColor = stoneColor;
	}

	/**
	 * Displays the pit in GUI
	 * @param g2
	 */
	public void draw(Graphics2D g2) {
		g2.setColor(color);
		g2.draw(shape);
		
		//Display pit label
		int temp = MancalaView.PIT_WIDTH*2 + MancalaView.PIT_WIDTH/5;
		if(this.y == MancalaView.TOP_Y) {
			if(this.x == temp) 		g2.drawString("B1", x+20, y-10);
			if(this.x == temp+55) 	g2.drawString("B2", x+20, y-10);
			if(this.x == temp+55*2) g2.drawString("B3", x+20, y-10);
			if(this.x == temp+55*3) g2.drawString("B4", x+20, y-10);
			if(this.x == temp+55*4) g2.drawString("B5", x+20, y-10);
			if(this.x == temp+55*5) g2.drawString("B6", x+20, y-10);
		}
		if(this.y == MancalaView.BOTTOM_Y) {
			if(this.x == temp) 		g2.drawString("A1", x+20, y+MancalaView.PIT_HEIGHT+15);
			if(this.x == temp+55) 	g2.drawString("A2", x+20, y+MancalaView.PIT_HEIGHT+15);
			if(this.x == temp+55*2) g2.drawString("A3", x+20, y+MancalaView.PIT_HEIGHT+15);
			if(this.x == temp+55*3) g2.drawString("A4", x+20, y+MancalaView.PIT_HEIGHT+15);
			if(this.x == temp+55*4) g2.drawString("A5", x+20, y+MancalaView.PIT_HEIGHT+15);
			if(this.x == temp+55*5) g2.drawString("A6", x+20, y+MancalaView.PIT_HEIGHT+15);
		}
		
		//Display number of stones in pit
		if(this.y == MancalaView.TOP_Y) {
			g2.drawString(" " + totalStones, x+20, y+MancalaView.PIT_HEIGHT+15); 
		}
		if(this.y == MancalaView.BOTTOM_Y) {
			g2.drawString(" " + totalStones, x+20, y-10);
		}
		
		//Display stones in pit 
		int counter = 1; //counter for number of stones already displayed in pit
		double xcoord = this.x;
		double ycoord = this.y;
		stonesInPit.clear();
		for(int i = 0; i < totalStones; i++) {
			if(counter > 8) { //stop displaying stones over 8
				return;
			}
			if(counter % 2 == 0) { 		//EVEN STONE
				xcoord = this.x + 30;
				ycoord = (this.y + 10) + 6 * counter/1.8;
			}
			else if(counter % 2 == 1) { //ODD STONE
				xcoord = this.x + 20;
				ycoord = (this.y + 10) + 6 * counter/1.8;
			}

			Ellipse2D.Double stone = new Ellipse2D.Double(xcoord, ycoord, STONE_SIZE, STONE_SIZE);
			
			stonesInPit.add(stone);
			g2.setColor(stoneColor);
			g2.fill(stone);
			g2.setColor(color);
			g2.draw(stone);
			counter++;
//			xcoord = this.x + 20;
//			ycoord = this.y + 20;
		}	
	}

	/**
	 * Returns true if point is contained in shape, false otherwise
	 * @return boolean value
	 */
	public boolean contains(Point p) {
		return shape.contains(p);
	}
	
}
