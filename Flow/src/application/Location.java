package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
//Location class used for the actual grid of boxes
public class Location{
	//Declaring class variables
	Color color,backgroundColor;
	boolean occupied, circle;
	int width,height;
	double xPos, yPos;
	int row, column;
	/**
	 * Constructor that takes a color, booleans representing the occupation status and whether their is a circle to be drawn, and the coordinates
	 * @param c - Color object for the color of the location
	 * @param o - boolean representing if it is occupied. true for occupied. 
	 * @param circ - boolean representing if a circle is to be drawn at this position. true if it is to be drawn
	 * @param r - int representing row number
	 * @param col - int representing column number
	 */
	public Location(Color c, boolean o, boolean circ, int r, int col ){
		backgroundColor = Color.BLACK;
		color = c;
		occupied =o;
		circle =circ;
		width= 50;
		height=50;
		xPos =0;
		yPos=0;
		row = r;
		column = col;
	}
	/**
	 * Constructor that allows to set the dimensions as well as takes a color, booleans representing the occupation status and whether their is a circle to be drawn, and the coordinates
	 * @param w - int representing width of the location
	 * @param h - int representing height of the location
	 * @param c - Color object for the color of the location
	 * @param o - boolean representing if it is occupied. true for occupied. 
	 * @param circ - boolean representing if a circle is to be drawn at this position. true if it is to be drawn
	 * @param r - int representing row number
	 * @param col - int representing column number
	 */
	public Location(int w,int h,Color c, boolean o, boolean circ, int r, int col ){
		backgroundColor = Color.BLACK;
		color = c;
		occupied =o;
		circle =circ;
		width= w;
		height=w;
		xPos =0;
		yPos=0;
		row = r;
		column = col;
	}
	/**
	 * Method that returns the coordinates
	 * @return - String representing the coordinates
	 */
	public String getCoordinates() {
		return row+column+"";
	}
	/**
	 * Method to obtain the row number
	 * @return - int representing the row number
	 */
	public int getRow() {
		return row;
	}
	/**
	 * Method representing the row number
	 * @param row - int for desired row number
	 */
	public void setRow(int row) {
		this.row = row;
	}
	/**
	 * Method to obtain column number
	 * @return - int representing column number
	 */
	public int getColumn() {
		return column;
	}
	/**
	 * Method that sets column number
	 * @param column - int representing desired column number
	 */
	public void setColumn(int column) {
		this.column = column;
	}
	/**
	 * Method that obtains the Color
	 * @return -Color representing the background color 
	 */
	public Color getBackgroundColor() {
		return backgroundColor;
	}
	/**
	 * Method that sets the background color
	 * @param backgroundColor - Color representing the desired background color
	 */
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	/**
	 * Method that obtains a rectangle with dimensions and coordinates of the location 
	 * @return - Rectangle object at dimensions and coordinates
	 */
	public Rectangle getBounds() {
		return new Rectangle(xPos,yPos,width,height);
	}
	/**
	 * Method that obtains a boolean for if there is a circle or not
	 * @return - boolean representing if there is a circle
	 */
	public boolean getCircle(){
		return circle;
	}
	/**
	 * Method that obtains the color 
	 * @return - Color object representing the color of the location
	 */
	public Color getColor() {
		return color;
	}
	/**
	 * Method that represents the occupation stated
	 * @return - boolean representing if it is occupied. true for occupied.
	 */
	public boolean isOccupied() {
		return occupied;
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
	 * Method that obtains the xPosition of the location
	 * @return - double representing the x position
	 */
	public double getxPos() {
		return xPos;
	}
	/**
	 * Method that obtains the yPosition of the location
	 * @return - double representing the y position
	 */
	public double getyPos() {
		return yPos;
	}
	/**
	 * Method that sets color of the location
	 * @param color - Color object representing the desired color 
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	/**
	 * Method that sets occupation state
	 * @param occupied  - boolean to represent the occupation state. true for occupied
	 */
	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}
	/**
	 * Method that sets if there is a circle
	 * @param circle - boolean to represent if there is a circle. true for circle
	 */
	public void setCircle(boolean circle) {
		this.circle = circle;
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
	 * Method that sets x position of the location
	 * @param xPos - double representing desired x position
	 */
	public void setxPos(double xPos) {
		this.xPos = xPos;
	}
	/**
	 * Method that sets the yPosition
	 * @param yPos - double representing desired y pos
	 */
	public void setyPos(double yPos) {
		this.yPos = yPos;
	}
	/**
	 * Method that sets the color and circle 
	 * @param c - Color object representing color 
	 * @param circ - boolean representing if their is a circle or not
	 */
	public void setPoint(Color c, boolean circ) {
		color =c;
		circle =circ;
	}
}
