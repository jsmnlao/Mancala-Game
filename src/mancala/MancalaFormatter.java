package mancala;

import java.awt.Color;
import java.awt.Shape;

/**
 * Fall 2022: CS 151 Team Project - Mancala Game GUI
 * @author Jasmine Lao, Zachary Hobbs
 * @version 1.0 October 25, 2022
 */

/**
 * The MancalaFormatter interface contains requirements for different styles of the board.
 */

public interface MancalaFormatter {
	
	Color formatStoneColor();
	Color formatOutlineColor();
	Color formatBoardBackground();
	Shape formatBoardShape();
	Shape formatPitShape(Pit pit);
	Shape formatGoalMancalaShape(GoalMancala goal);
	MancalaFormatter getFormatter();
}
