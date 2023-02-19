package mancala;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;

/**
 * Fall 2022: CS 151 Team Project - Mancala Game GUI
 * @author Jasmine Lao, Zachary Hobbs
 * @version 1.0 October 25, 2022
 */

/**
 * Hole interface contains all required methods of a hole (ie. GoalMancala, Pit) 
 */
public interface Hole {
	
	int getX();
	int getY();
	int getWidth();
	int getHeight();
	void addStone();
	int getStones();
	void setStones(int num);
	void setShape(Shape s);
	void setColor(Color s);
	Shape getShape();
	void draw(Graphics2D g2);
	boolean contains(Point p);
	void setId(int id);
	int getId();
}
