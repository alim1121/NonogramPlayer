package edu.ou.cs2334.project5.interfaces;

import java.io.File;

/**
 * Used to specify that a class has a method to open a file.
 * @author Muhammad Ali
 *
 */
public interface Openable {
	/**
	 * This method opens the given file.
	 * @param file The file to be opened.
	 */
	 void open(File file);

}
