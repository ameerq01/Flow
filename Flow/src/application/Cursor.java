package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
//Cursor class extends ImageView
public class Cursor extends ImageView {
//Declaring class variables
	int width, height;
	ImageView ivCursor;
	Image click, cursor;
	boolean clicked;
	/**
	 * Constructor that creates a cursor icon at the specified coordinates and dimensions
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public Cursor(double x, double y,int w,int h) {
		super.setLayoutX(x);
		super.setLayoutY(y);
		width=w;
		height=h;
		click = new Image("file:images/clicked.png", w, h, true,true);
		cursor=new Image("file:images/cursor.png",w,h,true,true);
		super.setImage(cursor);
		clicked =false;
	}
	/**
	 * Method that obtains width of location
	 * @return - int representing width of the location
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * Method that obtains height of the location
	 * @return - int representing the height of location
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * Method that sets the width of the location
	 * @param width - int representing desired width of the location
	 */
	public void setWidth(int width) {
		this.width = width;
	}
/**
 * Method that sets height of the location
 * @param height - int representing desired height of the location
 */
	public void setHeight(int height) {
		this.height = height;
	}
/**
 * Method that sets the cursor to clicked mode
 */
	public void click() {
		clicked = true;
		super.setImage(click);
	}
	/**
	 * Method that sets the cursor to unclicked mode
	 */
	public void unClick() {
		clicked = false;
		super.setImage(cursor);
	}
	/**
	 * Method to check if the cursor is clicked
	 * @return boolean representing cursor is clicked at that moment of time
	 */
	public boolean isClicked() {
		return clicked;
	}
}
