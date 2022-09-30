package edu.ou.cs2334.project5.views;

import edu.ou.cs2334.project5.models.CellState;
import edu.ou.cs2334.project5.views.clues.LeftCluesView;
import edu.ou.cs2334.project5.views.clues.TopCluesView;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * This class extends borderpane and serves as the GUI of the nonogram.
 * @author Muhammad Ali
 *
 */
public class NonogramView extends BorderPane{
	private static final String STYLE_CLASS = "nonogram-view";
	private final static String SOLVED_STYLE_CLASS = "nonogram-view-solved";
	private LeftCluesView leftCluesView;
	private TopCluesView topCluesView;
	private CellGridView cellGridView;
	private HBox bottomHBox;
	private Button loadBtn;
	private Button resetBtn;
	
	/**
	 * Constructs view with a default style class.
	 */
	public NonogramView() {
		getStyleClass().add(STYLE_CLASS);
	}
	
	/**
	 * Initializes the view with row clues, column clues, and cell length.
	 * @param rowClues the row Clues to be displayed on the top of the cell grid.
	 * @param colClues the column clues to be displayed on the left of the cell grid.
	 * @param cellLength The size of the cells in the cell grid.
	 */
	public void initialize(int[][] rowClues, int[][] colClues, int cellLength) {
		leftCluesView = new LeftCluesView(rowClues, cellLength, calcWidthHeight(rowClues));
		topCluesView = new TopCluesView(colClues, cellLength, calcWidthHeight(colClues));
		cellGridView = new CellGridView(rowClues.length, colClues.length, cellLength);
		
		setLeft(leftCluesView);
		setTop(topCluesView);
		setAlignment(getTop(), Pos.TOP_RIGHT);
		setCenter(cellGridView);
		
		initBottomHBox();
		setBottom(bottomHBox);
	}

	private void initBottomHBox() {
		bottomHBox = new HBox(6);
		bottomHBox.setAlignment(Pos.CENTER);
		loadBtn = new Button("Load");
		resetBtn = new Button("Reset");
		bottomHBox.getChildren().addAll(loadBtn, resetBtn);
	}
	
	//Helper method to calculate the width and height in initialize method
	private int calcWidthHeight(int[][] rClues) {
		int max = rClues[0].length;
		for(int i = 1; i < rClues.length ; ++i) {
			if(rClues[i].length > max)
				max = rClues[i].length;
		}
		return max;
	}
		
	/**
	 * Returns the cell view at the given indices.	
	 * @param rowIdx the row to get the cell from.
	 * @param colIdx the column to get the cell view from.
	 * @return The cell view at the given indices.
	 */
	public CellView getCellView(int rowIdx, int colIdx) {
		return cellGridView.getCellView(rowIdx, colIdx);
	}
	
	/**
	 * Sets the cell state at the given indices.
	 * @param rowIdx the row of the cell state to change.
	 * @param colIdx the column of the cell state to change.
	 * @param state The state to change the element at the given indices to.
	 */
	public void setCellState(int rowIdx, int colIdx, CellState state) {
		cellGridView.setCellView(rowIdx, colIdx, state);
	}
	
	/**
	 * Changes the row clue state to solved.
	 * @param rowIdx The row to set.
	 * @param solved The value to set.
	 */
	public void setRowClueState(int rowIdx, boolean solved) {
		leftCluesView.setState(rowIdx, solved);
	}
	
	/**
	 * Changes the column clue state to solved.
	 * @param colIdx The column to set.
	 * @param sovled The value to set.
	 */
	public void setColClueState(int colIdx, boolean sovled) {
		topCluesView.setState(colIdx, sovled);
	}
	
	/**
	 * Changes the state of the puzzle to solved value.
	 * @param solved True if the puzzle is sovled.
	 */
	public void setPuzzleState(boolean solved) {
		if(solved == true)
			getStyleClass().add(SOLVED_STYLE_CLASS);
		else {
			getStyleClass().removeAll(SOLVED_STYLE_CLASS);
		}
	}
	
	/**
	 * Returns the load button to load in a puzzle.
	 * @return The load button to lead in a puzzle.
	 */
	public Button getLoadButton() {
		return loadBtn;
	}
	
	/**
	 * The Reset Button to reset a puzzle.
	 * @return the reset button to reset the puzzle.
	 */
	public Button getResetButton() {
		return resetBtn;
	}
	
	/**
	 * Displays an alert if the puzzle was solved.
	 */
	public void showVictoryAlert() {
		Alert victory = new Alert(Alert.AlertType.INFORMATION);
		victory.setHeaderText("Congratulations!");
		victory.setTitle("Puzzle Solved");
		//For action when clicked single button, clear all marked cells to empty
		victory.setContentText("You Win!");
		victory.show();
	}
}
