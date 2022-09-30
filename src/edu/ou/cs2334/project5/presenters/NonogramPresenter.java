package edu.ou.cs2334.project5.presenters;
import java.io.File;
import java.io.IOException;

import edu.ou.cs2334.project5.handlers.OpenHandler;
import edu.ou.cs2334.project5.interfaces.Openable;
import edu.ou.cs2334.project5.models.CellState;
import edu.ou.cs2334.project5.models.NonogramModel;
import edu.ou.cs2334.project5.views.NonogramView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;

/**
 * This class serves as the connection between the NonogramView and NonogramModel.
 * @author Muhammad Ali
 *
 */
public class NonogramPresenter implements Openable {
	private NonogramView view;
	private NonogramModel model;
	private int cellLength;
	private final static String DEFAULT_PUZZLE = "puzzles/space-invader.txt";
	
	/**
	 * This constructor makes a model and a view, and passes the cellLength on to serve as the "button" sizes.
	 * @param cellLength the size of each cell.
	 * @throws IOException if initializePresenter() passes a file exception.
	 */
	public NonogramPresenter(int cellLength) throws IOException {
		model = new NonogramModel(DEFAULT_PUZZLE);
		view = new NonogramView();
		this.cellLength = cellLength;
		initializePresenter();
	}

	private void initializePresenter() {
		initializeView();
		synchronize();
		bindCellViews();
		synchronize();
		configureButtons();
	}

	private void synchronize() {
		for(int rowIdx = 0; rowIdx < model.getNumRows(); ++rowIdx) {
			for(int colIdx = 0; colIdx < model.getNumCols(); ++ colIdx) {
				view.setCellState(rowIdx, colIdx, model.getCellState(rowIdx, colIdx));
				view.setRowClueState(rowIdx, model.isRowSolved(rowIdx));
				view.setColClueState(colIdx, model.isColSolved(colIdx));
			}
		}
		
		view.setPuzzleState(model.isSolved());
		
		if(model.isSolved()) processVictory();
		
		
		
	}

	private void configureButtons() {
		//Credit to "Not a TA" Ethan Discord
		FileChooser fc = new FileChooser();
		fc.setTitle("Open");
		fc.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"));
		view.getLoadButton().setOnAction(new OpenHandler(getWindow(), fc, this));
		if(view.getResetButton() != null)
			view.getResetButton().setOnAction((event) -> {
				resetPuzzle();
			});
		
	}

	private void resetPuzzle() {
		model.resetCells();
		synchronize();
		
	}

	private void bindCellViews() {
		for(int row = 0; row < model.getNumRows(); ++row) {
			for(int col = 0; col < model.getNumCols(); ++col) {
				int tempR = row;
				int tempC = col; //These temp variables are used to prevent error in calling handleClick methods 
				view.getCellView(row, col).setOnMouseClicked(event -> {
					if(event.getButton() == MouseButton.PRIMARY) {
						handleLeftClick(tempR, tempC);
					} else if(event.getButton() == MouseButton.SECONDARY) {
						handleRightClick(tempR, tempC);
					}
				}); //Source: https://stackoverflow.com/questions/1515547/right-click-in-javafx
			}
		}
	}
		
	
	private void  handleRightClick(int rowIdx, int colIdx) {
		if(model.getCellState(rowIdx, colIdx).equals(CellState.MARKED))
			updateCellState(rowIdx, colIdx, CellState.EMPTY);
		else
			updateCellState(rowIdx, colIdx, CellState.MARKED);
		
	}

	private void updateCellState(int rowIdx, int colIdx, CellState state) {
		if(model.setCellState(rowIdx, colIdx, state)) {
			view.setCellState(rowIdx, colIdx, state);
			view.setRowClueState(rowIdx, CellState.toBoolean(state));
			view.setColClueState(colIdx, CellState.toBoolean(state));
		}
		if(model.isSolved()) processVictory();
	}
	
	
	private void handleLeftClick(int rowIdx, int colIdx) {
		if(model.getCellState(rowIdx, colIdx).equals(CellState.FILLED))
			updateCellState(rowIdx, colIdx, CellState.EMPTY);
		else
			updateCellState(rowIdx, colIdx, CellState.FILLED);
	}
	
	

	private void processVictory() {
		removeCellViewMarks();
		view.showVictoryAlert(); 
	}

	private void removeCellViewMarks() {
		for(int rowIdx = 0; rowIdx < model.getNumRows(); ++rowIdx) {
			for(int colIdx = 0; colIdx < model.getNumCols(); ++colIdx) {
				if(model.getCellState(rowIdx, colIdx) == CellState.MARKED) {
					view.setCellState(rowIdx, colIdx, CellState.EMPTY);
				}
			}
		}
	}

	private void initializeView() {
		view.initialize(model.getRowClues(), model.getColClues(), cellLength);
		if(getWindow() != null) {
			getWindow().sizeToScene();
		}
	}
	
	/**
	 * Gives the pane of the presenter's view.
	 * @return The pane of the presenter's view.
	 */
	public Pane getPane() {
		return view; 
	}

	/**
	 * Gives the window of the presenter's view.
	 * @return The window of the presenter's view.
	 */
	public Window getWindow() {
		//Helped by Blake H
		try {
			return view.getScene().getWindow();
		}
		catch(NullPointerException e) {
			return null;
		}
	}

	/**
	 * Opens the given file
	 */
	@Override
	public void open(File file) {
		try {
			model = new NonogramModel(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		initializePresenter();
		
	}
	
	

}
