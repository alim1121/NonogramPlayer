package edu.ou.cs2334.project5.models;

/**
 * Used to Create A cell state with 3 values, either empty, filled or marked. 
 * @author Muhammad Ali
 *
 */
public enum CellState {
	/**
	 * EMPTY if the cell state is empty.
	 */
	EMPTY,
	/**
	 * FILLED if the cell is filled.
	 */
	FILLED, 
	/**
	 * MARKED if the cell is not filled.
	 */
	MARKED;
	
	/**
	 * Converts the given state to boolean, true if state is FILLED
	 * @param state the state to convert to boolean
	 * @return true if state is filled, false if otherwise
	 */
	public static boolean toBoolean(CellState state) {
		if(state == FILLED)
			return true;
		return false;
	}

}
