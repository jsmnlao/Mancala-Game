package mancala;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * This MancalaModel provides the underlying data structures and holds subjects in which the event occurs.
 */

public class MancalaModel{

	public JLabel whosTurn = new JLabel("Player A's turn");
	private ArrayList<ChangeListener> listeners;
	private int initialStones;
	private MancalaFormatter format;
	static String winner = "";
	private String playerTurn = "Player A";
	private String previousPlayer = "Player A";
	
	private int[] allPits;
	private int[] previousPits;
	private int numberOfUndos = 0;
	
	/**
	 * the model for this board, putting the structure together
	 */
	public MancalaModel() {
		listeners = new ArrayList<>();
		allPits = new int[14];
		previousPits = new int[14];
	}
	
	/**
	 * gets the pits values
	 * @return the values within the pits
	 */
	public int[] getViewPits() {
		return MancalaView.getTotalStonesPits();
	}
	
	/**
	 * starts the game, asking for the color format, starting stones and then displays game
	 * postcondition: java window is displayed
	 */
	public void startGame() {
		JDialog popup = new JDialog();
		
		JLabel theme = new JLabel("Choose a theme!");
		Object[] optionsForTheme = {"Blue Format", "Pink Format"};
		String initialThemeSelection = "Blue Format";
		Object askForTheme = JOptionPane.showInputDialog(null, theme, "Menu", JOptionPane.QUESTION_MESSAGE, null, optionsForTheme, initialThemeSelection); 		
		JOptionPane selectionTheme = new JOptionPane(askForTheme);
		String selectedTheme = askForTheme.toString();
		if(selectedTheme.equals("Blue Format")) {
			format = new BlueFormat();
		}
		else if (selectedTheme.equals("Pink Format")) {
			format = new PinkFormat();
		}

		JLabel stones = new JLabel("Choose the number of initial stones!");
		Object[] optionsForStones = {"1", "2", "3", "4"};
		String initialStoneSelection = "4";
		Object askForStones = JOptionPane.showInputDialog(null, stones, "Menu", JOptionPane.QUESTION_MESSAGE, null, optionsForStones, initialStoneSelection); 		
		JOptionPane selectionStones = new JOptionPane(askForTheme);
		String selectedStones = askForStones.toString();
		if(selectedStones.equals("4")) {
			initialStones = 4;
		}
		else if(selectedStones.equals("3")) {
			initialStones = 3;
		}
		else if(selectedStones.equals("2")) {
			initialStones = 2;
		}
		else if(selectedStones.equals("1")) {
			initialStones = 1;
		}
		
		for(int i = 0; i < allPits.length; i++) {
			if(i != 6 && i != 13)
			allPits[i] = initialStones;
		}

		popup.add(selectionTheme);
		popup.add(selectionStones);
		popup.setVisible(true);
	}
	
	/**
	 * Attaches change listener to listeners array list.
	 * Serves as ATTACHER.
	 * @param listener, listener to be added to ArrayList
	 */
	public void addChangeListener(ChangeListener listener) {
		listeners.add(listener);
	}
	
	/**
	 * updates the listeners within the pits
	 */
	public void updateListeners() {
		for(ChangeListener listener : listeners) {
			listener.stateChanged(new ChangeEvent(this));
		}
	}
	
	/**
	 * sets the format to the board
	 * @param f - the format
	 */
	public void setFormat(MancalaFormatter f) {
		this.format = f;
	}

	/**
	 * gets the format of the mancala from the mancalaformatter class
	 * @return the format in the form of mancalaformatter
	 */
	public MancalaFormatter getFormat() {
		return format;
	}
	
	/**
	 * gets the initial stones amount
	 * @return the integer value of the stones starting with
	 */
	public int getInitialStones() {
		return initialStones;
	}

	/**
	 * sets the initial part of the stones
	 * @param initialStones - the number of stones to set
	 */
	public void setInitialStones(int initialStones) {
		this.initialStones = initialStones;
	}
	
	/**
	 * gets all the pits within the mancala board
	 * @return the array of pits
	 */
	public int[] getAllPits(){
		return this.allPits;
	}
	
	/**
	 * moves the stone according to the game rules
	 * @param index - the index at which we starting moving the stones
	 * postcondition: stones will change pits
	 */
		public void moveStones(int index) {
//		changePlayers();
		previousPits =  getViewPits().clone();
		previousPlayer = playerTurn;
		allPits = getViewPits();
		int[] stonesInPits = MancalaView.getTotalStonesPits();
		numberOfUndos = 0;
		
		if(playerTurn == "Player A" && index >= 7) {
			return;
		}
		if(playerTurn == "Player B" && index <= 5) {
			return;
		}
		
		int numberStones = stonesInPits[index]; //number of stones in THIS pit
		stonesInPits[index] = 0; //reset this pit to 0 stones
		int temp = index;
		while(numberStones > 0) { //while number of stones > 0
			temp++;
			temp = temp % allPits.length;
			if(numberStones != 1) {
				if(index <= 5 && temp == 13) { //Don't add in MancalaB if Player 1
					temp = 0;
				}
				if(index >= 7 && temp == 6) { //Don't add in MancalaA if Player 2
					temp = 7;
				}
			}
			stonesInPits[temp] = stonesInPits[temp] + 1;
			if(numberStones == 1) {
				if(playerTurn == "Player A" && temp == 6) {
					playerTurn = "Player A";
					previousPlayer = "Player A";
				}
				else if(playerTurn == "Player B" && temp == 13) {
					playerTurn = "Player B";
					previousPlayer = "Player B";
				}
				else {
				changePlayers();
				}
			}

			numberStones--;
		}
		if(stonesInPits[temp] == 1) {
			if(temp <= 5 && temp >= 0) { //Don't add in MancalaB if Player 1
				if(playerTurn == "Player B")
				giveOppositeA(temp);
			}
			if(temp <= 12 && temp >= 7) { //Don't add in MancalaA if Player 2
				if(playerTurn == "Player A")
				giveOppositeB(temp);
			}
		}
		if(getEmptyRow() == "Row A") {
			giveAllToMancalaB();
			changePlayers();
		}
		else if(getEmptyRow() == "Row B") {
			giveAllToMancalaA();
			changePlayers();
		}
		// start here with if statement
		//if last pile stones = 1, give all opposite pile to placer
		
		this.updateListeners();

		if(checkIfGameEnds() == true) {
			displayWinner();
		}
//		changePlayers();
	}
		/**
		 * changes who turn it is in the label
		 * @param temp - the variable used to decide which person's turn it is
		 * @postcondition: changes the JLabel
		 */
		public void changeTurnLabel(int temp) {
			if(temp == 0) {
				whosTurn.setText("Player A's turn!");
			}
			if(temp == 1) {
				whosTurn.setText("Player B's turn!");
			}
			whosTurn.repaint();
			whosTurn.validate();
			
		}
	
	/**
	 * Changes the player's turn
	 * Postcondition: playerTurn is changed
	 */
	public void changePlayers() {
		if(playerTurn == "Player A") {
			playerTurn = "Player B";
			changeTurnLabel(1);
		}
		else if (playerTurn == "Player B"){
			playerTurn = "Player A";
			changeTurnLabel(0);
		}
	}
	
	/**
	 * gives the opponents stones across from them - edge case
	 * @param index - the index at which we start from
	 */
	public void giveOppositeA(int index) {
		if(index == 0) 
			moveToA(0,12);
		if(index == 1)
			moveToA(1,11);
		if(index == 2) 
			moveToA(2,10);
		if(index == 3) 
			moveToA(3,9);
		if(index == 4) 
			moveToA(4,8);
		if(index == 5) 
			moveToA(5,7);
	}
	
	/**
	 * gives the opponents stones across from them - edge case
	 * @param index - the index at which we start from
	 */
	public void giveOppositeB(int index) {
		if(index == 12) 
			moveToB(0,12);
		if(index == 11)
			moveToB(1,11);
		if(index == 10) 
			moveToB(2,10);
		if(index == 9) 
			moveToB(3,9);
		if(index == 8) 
			moveToB(4,8);
		if(index == 7) 
			moveToB(5,7);
	}
	
	/**
	 * moves the stones to panel a
	 * @param a the index of player a
	 * @param b the index of player b
	 */
	public void moveToA(int a, int b) {
		int stonesCollected = 0;
		stonesCollected += allPits[a];
		stonesCollected += allPits[b];
		allPits[a] = 0;
		allPits[b] = 0;
		allPits[6] += stonesCollected;
		this.updateListeners();
	}
	
	/**
	 * moves the stones to panel b
	 * @param a the index of player a
	 * @param b the index of player b
	 */
	public void moveToB(int a, int b) {
		int stonesCollected = 0;
		stonesCollected += allPits[a];
		stonesCollected += allPits[b];
		allPits[a] = 0;
		allPits[b] = 0;
		allPits[13] += stonesCollected;
		this.updateListeners();
	}
	
	/**
	 * checks if the game ends
	 * @return the win value of the game
	 * postcondition: game is not playable anymore
	 */
	public boolean checkIfGameEnds() {
		boolean allEmptyPits = true;
		int[] stonesInPits = MancalaView.getTotalStonesPits();
		for(int i = 0; i < stonesInPits.length; i++) {
			if(i != 6 && i != 13) {
				if(stonesInPits[i] != 0) {
					allEmptyPits = false;
				}
			}
		}
		return allEmptyPits;
	}
	
	/**
	 * displays the winner with the extra window
	 * postcondition: game is finished
	 */
	public void displayWinner() {
		if(allPits[6] > allPits[13]) {
			winner = "Player A";
		}
		else if(allPits[6] < allPits[13]) {
			winner = "Player B";
		}
		else {
			winner = "It's a tie!";
		}
		MancalaView.signalGameEnd();
	}

	/**
	 * undos the past move of the player
	 * postcondition: resets the play and attachs actionlisteners again
	 */
	public void undoMove() { 
		numberOfUndos++;
		playerTurn = previousPlayer; 
		allPits = previousPits.clone();
		this.updateListeners();
		if(playerTurn == "Player A") {
			playerTurn = "Player A";
			changeTurnLabel(0);
		}
		else if (playerTurn == "Player B"){
			playerTurn = "Player B";
			changeTurnLabel(1);
		}
	}
	
	/**
	 * the button for the undo function
	 * @return the Jbutton for undo
	 */
	public JButton getUndoButton() {
		JButton undoButton = new JButton("UNDO");
		undoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(numberOfUndos > 3) {
					return;
				}
				undoMove();
			}
		});
		return undoButton;
	}

	/**
	 * finds an empty row within the board
	 * @return where that empty row is, player a or b
	 */
	public String getEmptyRow() {
		boolean rowBEmpty = true;
		boolean rowAEmpty = true;
		for(int i = 0; i < 6; i++) { //Check ROW A
			if(allPits[i] != 0) { 
				rowAEmpty = false;
			}
		}
		for(int i = 7; i < 13; i++) { //Check ROW B
			if(allPits[i] != 0) {
				rowBEmpty = false;
			}
		}
		if(rowBEmpty == true && rowAEmpty == false) { //Only Row B is empty
			return "Row B"; //all stones should go to Mancala A
		}
		if(rowBEmpty == false && rowAEmpty == true) { //Only Row A is empty
			return "Row A"; //all stones should go to Mancala B
		}
		return "Both aren't empty"; //Both rows are empty
	}
	
	/**
	 * gives all the leftover stones to player A
	 * postcondition: all extra stones left to mancala A
	 */
	public void giveAllToMancalaA() {
		int stonesLeftInRowA = 0;
		for(int i = 0; i < 6; i++) {
			stonesLeftInRowA += allPits[i];
			allPits[i] = 0;
		}
		allPits[6] += stonesLeftInRowA;
		this.updateListeners();
	}
	
	/**
	 * gives all the leftover stones to player B
	 * postcondition: all extra stones left to mancala B
	 */
	public void giveAllToMancalaB() {
		int stonesLeftInRowB = 0;
		for(int i = 7; i < 13; i++) {
			stonesLeftInRowB += allPits[i];
			allPits[i] = 0;
		}
		allPits[13] += stonesLeftInRowB; //add all REMAINING stones to Mancala B
		this.updateListeners();
	}
	
		/**
	 * returns a JButton that styles the board with blue format
	 * @return JButton
	 */
	public JButton getBlueFormat() {
		JButton blueFormat = new JButton("Blue Theme!");
		blueFormat.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				BlueFormat blue = new BlueFormat();
				setFormat(blue);
				updateListeners();
			}
			
		});
		return blueFormat;
	}

	/**
	 * returns a JButton that styles the board with pink format
	 * @return JButton
	 */
	public JButton getPinkFormat() {
		JButton pinkFormat = new JButton("Pink Theme!");
		pinkFormat.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				PinkFormat pink = new PinkFormat();
				setFormat(pink);
				updateListeners();
			}
			
		});
		return pinkFormat;
	}

}
