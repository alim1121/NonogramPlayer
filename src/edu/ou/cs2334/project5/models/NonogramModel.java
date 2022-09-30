package edu.ou.cs2334.project5.models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class creates a 2d array that represents rows and columns of a nonogram.
 * @author Muhammad Ali
 *
 */
public class NonogramModel {

	private static final String DELIMITER = " ";
	private static final int IDX_NUM_ROWS = 0;
	private static final int IDX_NUM_COLS = 1;

	private int[][] rowClues;
	private int[][] colClues;
	private CellState[][] cellStates;
	private int numRows;
	private int numCols;
	
	/**
	 * This ctor makes a model and clues for the rows and columns with the 2d arrays given.
	 * @param rowClues Contains int values that show how many consecutive elements are true in the nonogram for each row.
	 * @param colClues Contains int values that show how many consecutive elements are true in the nonogram for each column.
	 */
	public NonogramModel(int[][] rowClues, int[][] colClues) {
		this.rowClues = deepCopy(rowClues);
		this.colClues = deepCopy(colClues);
		numRows = rowClues.length; 
		numCols = colClues.length;
		cellStates = initCellStates(rowClues.length, colClues.length);
		
	}
	
	/**
	 * This ctor makes a model and clues for the rows and columns with the given txt file.
	 * @param file Gives rowClues and colClues.
	 * @throws IOException If file does not exist.
	 */
	public NonogramModel(File file) throws IOException {
		// Number of rows and columns
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String header = reader.readLine();
		String[] fields = header.split(DELIMITER);
		numRows = Integer.parseInt(fields[IDX_NUM_ROWS]);
		numCols = Integer.parseInt(fields[IDX_NUM_COLS]);

		cellStates = initCellStates(numRows, numCols);
		
		rowClues = readClueLines(reader, numRows);
		
		colClues = readClueLines(reader, numCols);

		reader.close();
	}

	/**
	 * Calls File ctor with given filename,  makes a model and clues for the rows and columns with the given txt file.
	 * @param filename The file to get row and column clues from. 
	 * @throws IOException If file does not exist.
	 */
	public NonogramModel(String filename) throws IOException {
		// TODO: Fix this constructor
		// This is simple, and you should not ask about this on Discord.
		this(new File(filename));
	}
	
	/**
	 * Returns the number of rows.
	 * @return The number of rows.
	 */
	public int getNumRows() {
		return numRows;
	}
	
	/**
	 * Returns the number of columns.
	 * @return Returns the number of columns.
	 */
	public int getNumCols() {
		return numCols;
	}
	
	/**
	 * Converts the cellstate to a boolean, true only if filled.
	 * @param rowIdx The row index of the cellstate to convert.
	 * @param colIdx The column index of the cellstate to convert.
	 * @return True if state is filled.
	 */
	public boolean getCellStateAsBoolean(int rowIdx, int colIdx) {
		return CellState.toBoolean(cellStates[rowIdx][colIdx]);
	}
	
	/**
	 * Returns cellState at given index.
	 * @param rowIdx Row Index to retrieve cellstate from.
	 * @param colIdx Column Index to retrieve cellstate from.
	 * @return The cellstate at the given indices.
	 */
	public CellState getCellState(int rowIdx, int colIdx) {
		return cellStates[rowIdx][colIdx];
	}
	
	/**
	 * Sets cellstate at given index to given state.
	 * @param rowIdx Row index to set cellstate to.
	 * @param colIdx Column index of cellstate to set.
	 * @param state the state to set the given indices to.
	 * @return true if change was made.
	 */
	public boolean setCellState(int rowIdx, int colIdx, CellState state) {
		if(state == null || cellStates[rowIdx][colIdx] == state || isSolved())
			return false;

		cellStates[rowIdx][colIdx] = state;
		return true;
	}
	
	/**
	 * Gives the 2d array of row clues for the puzzle.
	 * @return The 2d array of row clues for the puzzle.
	 */
	public int[][] getRowClues(){
		return deepCopy(rowClues);
	}
	
	/**
	 * Gives the 2d array of column clues for the puzzle.
	 * @return The 2d array of column clues for the puzzle.
	 */
	public int[][] getColClues(){
		return deepCopy(colClues);
	}
	
	/**
	 * Returns the row clue at the given row.
	 * @param rowIdx the row to get the clue from.
	 * @return the row clue array at the given index.
	 */
	public int[] getRowClue(int rowIdx) {
		return getRowClues()[rowIdx];
	}
	
	/**
	 * Returns the column clue at the given column.
	 * @param colIdx the column to get the clue from.
	 * @return the column clue at the given column.
	 */
	public int[] getColClue(int colIdx) {
		return getColClues()[colIdx];
	}
	
	/**
	 * Contains Integer values that show how many consecutive elements are true in the array.
	 * @param cells The array representation of cells to project.
	 * @return Integer values that show how many consecutive elements are true in the array.
	 */
	public static List<Integer> project(boolean[] cells){
		List<Integer> toReturn = new ArrayList<Integer>();
		
		int count = 0;
		for(int i = 0; i < cells.length; ++i) {
			if(cells[i] == true) {
				++count;
				while((i + count) < cells.length && cells[i + count] == true) {
					++count;
				}
				toReturn.add(count);
				i += count;
				count = 0;
			}
		}
		
		if(toReturn.size() == 0)
			toReturn.add(0);
		return toReturn;
		
	}
	
	/**
	 * Resets all cells to CellState EMPTY.
	 */
	public void resetCells() {
		for(int i = 0; i < numRows; ++i) {
			for(int j = 0; j < numCols; ++j) {
				cellStates[i][j] = CellState.EMPTY;
			}
		}
	}
	
	/**
	 * Calls the project method on the given row.
	 * @param rowIdx the row to project.
	 * @return Integer values that show how many consecutive elements are true in the row.
	 */
	public int[] projectCellStatesRow(int rowIdx) {
		//Convert the row to boolean array, then continue same as project 4
		boolean[] row = new boolean[numCols];
		
		for(int i = 0; i < numCols; ++i) {
			row[i] = CellState.toBoolean(cellStates[rowIdx][i]);
		}
		
		
		ArrayList<Integer> projection = (ArrayList<Integer>) project(row);
		int[] toReturn = new int[projection.size()];
		int count = 0;
		for(int proj: projection) {
			toReturn[count] = proj;
			++count;
		}
		return toReturn;
	}

	/**
	 * Calls the project method on the given column.
	 * @param colIdx the column to project.
	 * @return Integer values that show how many consecutive elements are true in the column.
	 */
	public int[] projectCellStatesCol(int colIdx) {
		boolean[] col = new boolean[getNumRows()];
		for(int i = 0; i < col.length; ++i) {
			col[i] = CellState.toBoolean(cellStates[i][colIdx]);
		}
		
		ArrayList<Integer> projection = (ArrayList<Integer>) project(col);
		int[] toReturn = new int[projection.size()];
		int count = 0;
		for(int proj: projection) {
			toReturn[count] = proj;
			++count;
		}
		return toReturn;
	}
	
	/**
	 * Returns true if puzzle matches row clues at row index.
	 * @param rowIdx The row to determine if solved.
	 * @return True if puzzle at given row is solved.
	 */
	public boolean isRowSolved(int rowIdx) {
		return Arrays.equals(getRowClue(rowIdx), projectCellStatesRow(rowIdx));
			
	}
	
	/**
	 * Returns true if puzzle matches column clues at column index.
	 * @param colIdx The column to determine if solved.
	 * @return True if puzzle at given row is solved.
	 */
	public boolean isColSolved(int colIdx) {
		return Arrays.equals(getColClue(colIdx), projectCellStatesCol(colIdx));
			
	}
	
	/**
	 * Returns true if puzzle is solved.
	 * @return True if puzzle is solved.
	 */
	public boolean isSolved() {
		boolean rowTruth = true;
		boolean colTruth = true;
		
		
		for(int i = 0; i < numRows; ++i) {
			if(!isRowSolved(i)) {
				rowTruth = false;
				break;
			}
		}
		for(int j = 0; j < numCols; ++j) {
			if(!isColSolved(j)) {
				colTruth = false;
				break;
			}
		}
		
		if(rowTruth == true && colTruth == true )
			return true;
		else
			return false;
	}
	
	/* Helper methods */
	
	// This is implemented for you
	private static CellState[][] initCellStates(int numRows, int numCols) {
		// Create a 2D array to store numRows * numCols elements
		CellState[][] cellStates = new CellState[numRows][numCols];
		
		// Set each element of the array to empty
		for (int rowIdx = 0; rowIdx < numRows; ++rowIdx) {
			for (int colIdx = 0; colIdx < numCols; ++colIdx) {
				cellStates[rowIdx][colIdx] = CellState.EMPTY;
			}
		}
		
		// Return the result
		return cellStates;
	}
	
	// TODO: Implement this method
	private static int[][] deepCopy(int[][] array) {
		// You can do this in under 10 lines of code. If you ask the internet
		// "how do I do a deep copy of a 2d array in Java," be sure to cite
		// your source.
		// Note that if we used a 1-dimensional array to store our arrays,
		// we could simply use Arrays.copyOf directly without this helper
		// method.
		// Do not ask about this on Discord. You can do this on your own. :)
		int[][] toReturn = new int[array.length][];
		for(int i = 0; i < array.length; ++i) {
			toReturn[i] = array[i].clone();
		}
		return toReturn;
		//Source: https://www.techiedelight.com/create-copy-of-2d-array-java/
	}
	
	// This method is implemented for you. You need to figure out it is useful.
	private static int[][] readClueLines(BufferedReader reader, int numLines)
			throws IOException {
		// Create a new 2D array to store the clues
		int[][] clueLines = new int[numLines][];
		
		// Read in clues line-by-line and add them to the array
		for (int lineNum = 0; lineNum < numLines; ++lineNum) {
			// Read in a line
			String line = reader.readLine();
			
			// Split the line according to the delimiter character
			String[] tokens = line.split(DELIMITER);
			
			// Create new int array to store the clues in
			int[] clues = new int[tokens.length];
			for (int idx = 0; idx < tokens.length; ++idx) {
				clues[idx] = Integer.parseInt(tokens[idx]);
			}
			
			// Store the processed clues in the resulting 2D array
			clueLines[lineNum] = clues;
		}
		
		// Return the result
		return clueLines;
	}
	
}
