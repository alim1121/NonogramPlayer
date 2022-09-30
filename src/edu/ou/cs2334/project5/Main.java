package edu.ou.cs2334.project5;

import edu.ou.cs2334.project5.presenters.NonogramPresenter;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class will serve to launch the application
 * @author Muhammad Ali
 *
 */
public class Main extends Application{
	
	private static final int IDX_CELL_SIZE = 0; //what is this for?
	private static final int DEFAULT_CELL_SIZE = 30;
	
	/**
	 * Launches the application.
	 * @param args The arguments given to the application.
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Starts the application and creates a nonogram with the cell size(if given).
	 */
	@Override
	public void start(Stage mainStage) throws Exception {
		int cellSize;
		if( getParameters().getUnnamed().size() != 0)
			cellSize = Integer.parseInt(getParameters().getUnnamed().get(IDX_CELL_SIZE));
		else cellSize = DEFAULT_CELL_SIZE;
		NonogramPresenter presenter = new NonogramPresenter(cellSize);
		mainStage.setTitle("Nonogram++");
		Scene scene = new Scene(presenter.getPane());
		scene.getStylesheets().add("/style.css");
		mainStage.setScene(scene);
		mainStage.setResizable(false);
		mainStage.show();
		
	}

}
