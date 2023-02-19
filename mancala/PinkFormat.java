package mancala;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

/**
 * Fall 2022: CS 151 Team Project - Mancala Game GUI
 * @author Jasmine Lao, Zachary Hobbs
 * @version 1.0 October 25, 2022
 */

/**
 * The PinkFormat class is a Strategy pattern that styles the board with a pink theme. 
 */

public class PinkFormat implements MancalaFormatter{

	/**
	 * gets the color format for the stones
	 * @ return color format
	 */
	public Color formatStoneColor() {
		return Color.GRAY;
	}

	/**
	 * gets the color format for the outline
	 * @ return color format
	 */
	public Color formatOutlineColor() {
		return Color.MAGENTA;
	}
	
	/**
	 * gets the color format for board background
	 * @ return color format
	 */
	public Color formatBoardBackground() {
		return Color.WHITE;
	}

	/**
	 * formats the board with the colors
	 * @return the shape of the board
	 */
	public Shape formatBoardShape() {
		return new Rectangle2D.Double(10, 10, MancalaView.BOARD_WIDTH, MancalaView.BOARD_HEIGHT);
	}

	/**
	 * formats the pit with its given color
	 * @return the shape of the pit
	 */
	public Shape formatPitShape(Pit pit) {
		return new Ellipse2D.Double(pit.getX(), pit.getY(), pit.getWidth(), pit.getHeight());
	}

	/**
	 * formats the mancala with its given goals
	 * @return the shape of the goals
	 */
	public Shape formatGoalMancalaShape(GoalMancala goal) {
		return new RoundRectangle2D.Double(goal.getX(), goal.getY(), goal.getWidth(), goal.getHeight(), 20, 20);
	}

	/**
	 * gets the formatter
	 * @return the mancalaformatter created through this class
	 */
	public MancalaFormatter getFormatter(){
		return this;
	}

}
