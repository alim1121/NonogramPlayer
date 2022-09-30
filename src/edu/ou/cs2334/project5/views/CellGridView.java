package edu.ou.cs2334.project5.views;

import edu.ou.cs2334.project5.models.CellState;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

/**
 * This is a grid representation of the cell views.
 * 
 * @author Muhammad Ali
 *
 */
public class CellGridView extends GridPane {
	
	private static final String STYLE_CLASS = "cell-grid-view";
	private CellView[][] cellViews;
	private int cellLength;
	
	/**
	 * Creates a 2d array of cell views.
	 * @param numRows The number of rows for the 2d array.
	 * @param numCols The number of columns for the 2d array.
	 * @param cellLength The length of each cell in the grid.
	 */
	public CellGridView(int numRows, int numCols, int cellLength) {
		initCells(numRows, numCols, cellLength);
		this.cellLength = cellLength;
		this.getStyleClass().add(STYLE_CLASS);
		
	}
	
	/**
	 * Initializes each cell.
	 * @param numRows The number of rows in the grid.
	 * @param numCols the number of columns in the grid.
	 * @param cellLength the length of each cell in a grid.
	 */
	public void initCells(int numRows, int numCols, int cellLength) {
		//Clear the children before the ctor?
		getChildren().clear();
		cellViews = new CellView[numRows][numCols];
		
		//Create a button on the grid for each element
		for(int rowIdx = 0; rowIdx < numRows; ++rowIdx) {
			for(int colIdx = 0; colIdx < numCols; ++colIdx) { 
				CellView nc = new CellView(cellLength);
				this.add(nc, rowIdx, colIdx);
				cellViews[rowIdx][colIdx] = nc;
			}
		}
		
		this.cellLength = cellLength;
	}
	
	/**
	 * Returns the cell view at the given indices.
	 * @param rowIdx the row to get the cell view from.
	 * @param colIdx the column to get the cell view from.
	 * @return the cell view at the given indices.
	 */
	public CellView getCellView(int rowIdx, int colIdx) {
		return cellViews[rowIdx][colIdx];
	}
	
	/**
	 * Sets the cell at the given indices.
	 * @param rowIdx the row to get the view from.
	 * @param colIdx the column to get the view from.
	 * @param state the state to set the indices value to.
	 */
	public void setCellView(int rowIdx, int colIdx, CellState state) {
		//CellView toAdd = new CellView(cellLength);
		//toAdd.setState(state);
		cellViews[rowIdx][colIdx].setState(state);
	}

}
