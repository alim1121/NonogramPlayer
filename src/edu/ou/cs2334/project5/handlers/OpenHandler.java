package edu.ou.cs2334.project5.handlers;


import java.io.File;

import edu.ou.cs2334.project5.interfaces.Openable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Window;

/**
 * This class serves to create open dialog and calls the openable method.
 * @author Muhammad Ali
 *
 */
public class OpenHandler extends AbstractBaseHandler implements EventHandler<ActionEvent> {
	private Openable opener;

	/**
	 * This creates object with the given Window, File, and opener.
	 * @param window Creates a window size.
	 * @param fileChooser Used to decide which file to open.
	 * @param opener Creates openable object to open the file given.
	 */
	public OpenHandler(Window window, FileChooser fileChooser, Openable opener) {
		super(window, fileChooser);
		this.opener = opener;
	}

	/**
	 * Shows view of files to open, and then opens the file.
	 */
	public void handle(ActionEvent arg0) { //Where is the parameter used?
		File file = super.fileChooser.showOpenDialog(super.window);
		if(file != null) {
			try {
				opener.open(file);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}//Helped by Keeton Fell
			
	}

}
