package mancala;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class GoalMancala implements Hole{

	private int x;
	private int y;
	private int width;
	private int height;
	private int totalStones;
	private final int STONE_SIZE = 10;
	private int id;

	private Color color;
	private Color stoneColor;
	private Shape shape;
	private ArrayList<Ellipse2D.Double> stonesInPit;

	/**
	 * creates the goal for the mancala game
	 * @param x horizontal value
	 * @param y vertical value
	 * @param w how wide it is
	 * @param h how tall it is
	 */
	public GoalMancala(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
		totalStones = 0;
		stonesInPit = new ArrayList<>();
		id = -1;
	}
	
	/**
	 * gets the id
	 * @return the id of the board
	 */
	public int getId() {
		return id;
	}

	/**
	 * sets the id 
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * @return - x value of goal
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * @return - y value of goal
	 */
	public int getY() {
		return this.y;
	}
	
	/*
	 * @return - width of mancala
	 */
	public int getWidth() {
		return this.width;
	}

	/*
	 * @return - height of mancala
	 */
	public int getHeight() {
		return this.height;
	}
	
	/**
	 * adds a stone to the total
	 */
	public void addStone() {
		totalStones += 1;
	}
	
	/**
	 * gets the total amount of stones in the goal
	 */
	public int getStones() {
		return totalStones;
	}
	
	/**
	 * sets the total amount of stones in goal
	 */
	public void setStones(int num) {
		totalStones = num;
	}
	
	/**
	 * adds a certain amount of stones
	 * @param amount the amount to add
	 */
	public void addStones(int amount) {
		totalStones += amount;
	}

	/**
	 * gets the shape of the mancala
	 */
	public Shape getShape() {
		return shape;
	}

	/**
	 * sets the shape of the goal
	 * shape - the given shape to set
	 */
	public void setShape(Shape shape) {
		this.shape = shape;
		this.setColor(color);
	}
	
	/**
	 * set the color of the goal
	 * color - the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	
	/**
	 * sets the stone color
	 * @param stoneColor - color of stone to set
	 */
	public void setStoneColor(Color stoneColor) {
		this.stoneColor = stoneColor;
	}

	/**
	 * draw interface of the goal
	 */
	public void draw(Graphics2D g2) {
		g2.setColor(color);
		g2.draw(shape);
		
		//Display goal mancala label
		if(this.x == MancalaView.BOARD_WIDTH-50) {
			g2.drawString("Mancala A", x-2, y-5);
			g2.setColor(color);
		}
		else{
			g2.drawString("Mancala B", x-2, y-5);
			g2.setColor(color);
		}
		
		//Display number of stones in pit
		if(this.x == MancalaView.BOARD_WIDTH-50) {
			g2.drawString(" " + totalStones, x+20, y+MancalaView.GOAL_HEIGHT+15); 
		}
		if(this.x == 40) {
			g2.drawString(" " + totalStones, x+20, y+MancalaView.GOAL_HEIGHT+15);
		}
		
		//Display stones in pit 
		double xcoord = this.x + 20;
		double ycoord = this.y + 20;
		int counter = 1;
		stonesInPit.clear();
			for(int i = 0; i < totalStones; i++) {
				if(counter > 11) {
					return;
				}
				if(ycoord >= this.y + MancalaView.GOAL_HEIGHT) {
					ycoord = this.y;
				}
				if(counter % 2 == 0) {
					xcoord = this.x + 20;
					ycoord = this.y + 20 * counter/1.5;
				}
				else if(counter % 2 == 1) {
					xcoord = this.x + 10;
					ycoord = this.y + 10 * counter/1.5;
				}
				Ellipse2D.Double stone = new Ellipse2D.Double(xcoord, ycoord, STONE_SIZE, STONE_SIZE);
				stonesInPit.add(stone);
				g2.setColor(stoneColor);
				g2.fill(stone);
				g2.setColor(color);
				g2.draw(stone);
				counter++;
			}
	}

	/**
	 * if this goal contains the point (x,y)
	 * @point p - the point it is checking
	 * @return - false because we dont need to check for it
	 */
	public boolean contains(Point p) {
		return false;
	}

}
