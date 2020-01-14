/*
Flow Program (CPT) 
 Name:Ameer Qamar
Class:ICS4U
Date: January 18, 2019
 */
//Importing all necessary java packages
package application;
import java.io.*;
import java.util.ArrayList;
import java.util.Optional;

import javax.swing.JOptionPane;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Main extends Application {
	//Declaring global variables, arrays, and ArrayLists
	boolean eof,necessary;
	String nam,pas;
	Location[][] grid;
	GridPane root ;
	Button[] menuButtons , btnLevels;
	Button btnMain;
	VBox vBox;
	BorderPane gamePane;
	Color[]colors,backgroundColors;
	Color selectedColor, backColor;
	GraphicsContext gc;
	Canvas canvas;
	Scene scene ;
	Player tempPlayer;
	boolean[]levelCompleted;
	ArrayList<Location>  selectedBoxes;
	ArrayList<Player> playerList;
	boolean selected;
	Cursor cursor;
	public void start(Stage primaryStage) {
		try {
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent e) {
					//Declaring and initializing alertExit, a confirmation box
					Alert alertExit = new Alert(AlertType.CONFIRMATION);
					//Setting the properties of alertExit
					alertExit.setContentText("Are you sure you would like to exit? Take a moment to\n save your progress if you haven't already done so!");
					alertExit.setTitle("Exit");
					alertExit.setGraphic(new ImageView(new Image("file:images/Question-Mark.png", 150,150,true,true)));
					alertExit.setHeaderText(null);
					alertExit.getButtonTypes().clear();
					//Adding yes or no options
					alertExit.getButtonTypes().addAll(ButtonType.YES, ButtonType.CANCEL);
					//Creating a ButtonType
					Optional<ButtonType> result = alertExit.showAndWait();
					//If the user chose no
					if (result.get() == ButtonType.CANCEL)
					{
						//Close the alertExit and end program
						e.consume();
					}
					//If the user chose yes
					else if(result.get()== ButtonType.YES){
						//Thanking them for using the program and exiting it 
						Alert thanks = new Alert(AlertType.INFORMATION);
						thanks.setTitle("Thanks");
						thanks.setHeaderText(null);
						thanks.setGraphic(new ImageView(new Image("file:images/goodbye.gif",150,150,true,true)));
						thanks.setContentText("Thank you for playing Flow! We hope you enjoyed our levels.\nGoodbye!");
						thanks.showAndWait();
						System.exit(0);
					}
				}
			});
			//Initializing global variables, arrays, and ArrayLists 
			playerList= new ArrayList<Player>();
			//To track how many levels they have completed
			levelCompleted = new boolean[5];
			for(int i =0; i< levelCompleted.length;i++) {
				levelCompleted[i] = false;
			}
			btnLevels =new Button[5];
			for(int j=0;j<btnLevels.length;j++) {
				btnLevels[j] = new Button("X");
				btnLevels[j].setMinSize(100, 50);
				btnLevels[j].setTextFill(Color.WHITE);
			}
			//Keeps track of the selected boxes
			selectedBoxes= new ArrayList<Location>();
			//Initializing selectedColor and backColor which will later be used to represent the selected color and background color
			selectedColor=Color.HOTPINK;
			backColor =Color.BURLYWOOD;
			//Initializing the GridPane and setting its properties
			root = new GridPane();
			root.setStyle("-fx-background-color:black");
			root.setAlignment(Pos.CENTER);
			//Array of colors and corresponding background colors (green, pink, red, blue, yellow)
			colors = new Color[]{Color.valueOf("#81e681"),Color.valueOf("#ffdba4"), Color.valueOf("#ff2903"),
					Color.AQUA,Color.valueOf("#fffb00"),Color.valueOf("#b566ff")};
			backgroundColors = new Color[] {Color.valueOf("#1d6132"),Color.valueOf("#fc4bff"),
					Color.valueOf("#800a0a"),Color.valueOf("#5980ff"),Color.valueOf("#ff8c00"), Color.valueOf("#7131a3")};
			//Initializing menuButtons and setting properties
			menuButtons = new Button[5];
			for(int i=0; i<menuButtons.length;i++){
				menuButtons[i] = new Button();
				menuButtons[i].setMinSize(100, 50);
				menuButtons[i].setTextFill(Color.WHITE);
			}
			menuButtons[1].setText("Instructions");
			menuButtons[2].setText("Save Progress");
			menuButtons[3].setText("Load Game");
			menuButtons[4].setText("Exit");
			menuButtons[4].setOnAction(e->{
				//Declaring and initializing alertExit, a confirmation box
				Alert alertExit = new Alert(AlertType.CONFIRMATION);
				//Setting the properties of alertExit
				alertExit.setContentText("Are you sure you would like to exit? Take a moment to\n save your progress if you haven't already done so!");
				alertExit.setTitle("Exit");
				alertExit.setGraphic(new ImageView(new Image("file:images/Question-Mark.png", 150,150,true,true)));
				alertExit.setHeaderText(null);
				alertExit.getButtonTypes().clear();
				//Adding yes or no options
				alertExit.getButtonTypes().addAll(ButtonType.YES, ButtonType.CANCEL);
				//Creating a ButtonType
				Optional<ButtonType> result = alertExit.showAndWait();
				//If the user chose no
				if (result.get() == ButtonType.CANCEL)
				{
					//Close the alertExit and end program
					e.consume();
				}
				//If the user chose yes
				else if(result.get()== ButtonType.YES){
					//Thanking them for using the program and exiting it 
					Alert thanks = new Alert(AlertType.INFORMATION);
					thanks.setTitle("Thanks");
					thanks.setHeaderText(null);
					thanks.setGraphic(new ImageView(new Image("file:images/goodbye.gif",150,150,true,true)));
					thanks.setContentText("Thank you for playing Flow! We hope you enjoyed our levels.\nGoodbye!");
					thanks.showAndWait();
					System.exit(0);
				}
			});
			//Handling actions for menuButtons
			menuButtons[0].setText("Levels");
			menuButtons[0].setOnAction(e->{
				//Open the levels page
				levelsPage();
			});
			//Handling actions for the load button
			menuButtons[3].setOnAction(e->{
				File f	 =	new File("progress.dat");
				try
				{
					necessary = true;
					boolean empty= true;
					nam="";
					pas="";
					// Prompt user for name and password
					do {
						TextInputDialog inputA = new TextInputDialog();
						inputA.setContentText("Please enter a username:");
						inputA.setHeaderText(null);
						inputA.setTitle("Flow");
						inputA.setOnCloseRequest(null);
						nam = inputA.showAndWait().get();
						if (!nam.trim().equals("")) {
							empty= false;
						}
						else {
							empty=true;
							Alert emptyAlert =new Alert(AlertType.ERROR);
							emptyAlert.setTitle("Flow");
							emptyAlert.setHeaderText(null);
							emptyAlert.setContentText("Please do not leave username blank!");
							emptyAlert.showAndWait();
						}
						//Continuously asking until they enter a username
					}
					while(empty==true);
					empty=true;
					do {
						TextInputDialog inputB = new TextInputDialog();
						inputB.setContentText("Please enter your password:");
						inputB.setHeaderText(null);
						inputB.setTitle("Flow");
						inputB.setOnCloseRequest(null);
						pas = inputB.showAndWait().get();
						if (!pas.trim().equals("")) {
							empty= false;
						}
						else {
							empty=true;
							Alert emptyAlert =new Alert(AlertType.ERROR);
							emptyAlert.setTitle("Flow");
							emptyAlert.setHeaderText(null);
							emptyAlert.setContentText("Please do not leave password blank!");
							emptyAlert.showAndWait();
						}
						//Continuously asking until they enter a password
					}while(empty==true);
					// Declare and initialize DataOutputStream object
					DataInputStream in = new DataInputStream(new
							BufferedInputStream(new FileInputStream(f)));
					eof = false;
					//while we have not reached the end of the file
					while (!eof)
					{		
						//Read and store the name, password, and a boolean array
						String name = in.readUTF();
						String password = in.readUTF();

						boolean[] tempLevel = levelCompleted.clone();
						for(int i =0; i<levelCompleted.length;i++) {
							tempLevel[i] = in.readBoolean();

						}
						//Adds the player to playerList
						playerList.add(new Player(name,password, tempLevel));
					}
					//Close the reader
					in.close();
				}
				//If the file is all read
				catch (EOFException ex)
				{
					eof = true;
				}
				//if another exception has occurred
				catch (IOException ex2)
				{
					JOptionPane.showMessageDialog(null, "An IOExcpetion has"
							+"	occurred!", "Error!", JOptionPane.ERROR_MESSAGE);
				}
				catch(Exception ex3) {
					necessary =false;
				}
			
				if(necessary==true) {
					//Checking if there are multiple occurrences of a user name and password. If there are, take the most recent one.
					Player temp = new Player("yeet","lmnop",null);
					for(int i=0; i<playerList.size();i++) {
						if (playerList.get(i).getUsername().equals(nam)&&playerList.get(i).getPassword().equals(pas)) {
							//setting levelCompleted to the most recent occurrence of this username and password
							levelCompleted = playerList.get(i).getLevelCompleted();
							temp.setUsername(playerList.get(i).getUsername());
							temp.setPassword(playerList.get(i).getPassword());
						}
						//if the loop has ended & they have been on the list; let them now their progress has been loaded
						if(i ==playerList.size()-1 &&!temp.getUsername().equals("yeet")&&!temp.getUsername().equals("lmnop")) {
							Alert a =new Alert(AlertType.INFORMATION);
							a.setTitle("Flow");
							a.setHeaderText(null);
							a.setContentText("Welcome back "+nam+"! Your progress has been restored.");
							a.setGraphic(new ImageView(new Image(("file:images/welcomeback.gif"), 200,200,true,true)));
							a.showAndWait();
						}
						//if the loop has ended& they have not been on the list; let them know
						else if(i ==playerList.size()-1 ) {
							Alert sorry = new Alert(AlertType.ERROR);
							sorry.setTitle("Flow");
							sorry.setHeaderText(null);
							sorry.setContentText("There was no account found with this username and password!");
							sorry.showAndWait();
						}
					}
					//update the levels
					levelCheck();
				}
			});

			//Handling events to the save progress button
			menuButtons[2].setOnAction(e->{
				File f	 =	new File("progress.dat");
				try
				{
					boolean empty= true;
					String name="";
					String password="";
					do {
						//Declaring, initializing, and setting properties of textinputdialogs for obtaining the username and password
						TextInputDialog inputA = new TextInputDialog();
						inputA.setContentText("Please enter a username:");
						inputA.setHeaderText(null);
						inputA.setTitle("Flow");
						inputA.setOnCloseRequest(null);
						name = inputA.showAndWait().get();
						if (!name.trim().equals("")) {
							empty= false;
						}
						else {
							empty=true;
							Alert emptyAlert =new Alert(AlertType.ERROR);
							emptyAlert.setTitle("Flow");
							emptyAlert.setHeaderText(null);
							emptyAlert.setContentText("Please do not leave username blank!");
							emptyAlert.showAndWait();
						}
						//Continue to ask as long as they leave the username blank
					}while(empty== true);
					empty =true;
					do {
						TextInputDialog inputB = new TextInputDialog();
						inputB.setContentText("Please enter your password:");
						inputB.setHeaderText(null);
						inputB.setTitle("Flow");
						inputB.setOnCloseRequest(null);
						password = inputB.showAndWait().get();
						if (!password.trim().equals("")) {
							empty= false;
						}
						else {
							empty=true;
							Alert emptyAlert =new Alert(AlertType.ERROR);
							emptyAlert.setTitle("Flow");
							emptyAlert.setHeaderText(null);
							emptyAlert.setContentText("Please do not leave password blank!");
							emptyAlert.showAndWait();
						}
						//
					}while(empty==true) ;
					// Declare and initialize DataOutputStream object
					DataOutputStream out = new DataOutputStream(new
							BufferedOutputStream(new FileOutputStream(f, true)));
					// Prompt user for name and password
					out.writeUTF(name);
					out.writeUTF(password);
					//writing the boolean values of levelCompleted to the binary file
					for(int i =0 ; i<levelCompleted.length;i++) {
						out.writeBoolean(levelCompleted[i]);
					}
					//closing the output stream
					out.close();
					out.flush();
				}
				catch(IOException ex)
				{
					// Output error message if exception is thrown
					JOptionPane.showMessageDialog(null, ex.getMessage() + "!",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
				catch(Exception ex2) {

				}

			});

			//Handling events to the instructions button
			menuButtons[1].setOnAction(e->{
				//Creating an alert with an ImageView and written instructions
				Image imgClick= new Image("file:images/mousclick.gif",200,200,true,true);
				ImageView ivClicked = new ImageView(imgClick);
				Alert i = new Alert(AlertType.INFORMATION);
				i.setTitle("Flow");
				i.setHeaderText(null);
				i.setContentText("Your objective is to connect circles of the same color and fill up the board!   "
						+ "\nCONTROLS:\n Click the left mouse button to select a circle. Release the button and drag the mouse within"
						+ " the grid to start drawing. \n Leaving the grid, going to an illegal box,  or clicking the right  mouse button while"
						+ "\n drawing will erase the path being drawn\n"
						+ "Click on a circle that is already shaded to erase the corresponding path. \n. Have Fun!");
				i.setGraphic(ivClicked);
				i.showAndWait();
			});

			scene= new Scene(root,300,500);
			scene.setFill(Color.BLACK);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			mainMenu();
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
	/**
	 * Method that opens the page with the different levels 
	 */
	private void  levelsPage() {
		levelCheck();
		//Declaring, initializing, and setting properties for an alert for when they click a locked level
		Alert alertError = new Alert(AlertType.ERROR);
		alertError.setTitle("Flow");
		alertError.setGraphic(new ImageView(new Image(("file:images/lock.png"),50,50,true,true)));
		alertError.setHeaderText(null);
		alertError.setContentText("You haven't unlocked this level yet!");
		alertError.setHeaderText(null);
		//Clearing the gridPane and setting it's properties
		root.getChildren().clear();
		root.setAlignment(Pos.TOP_LEFT);
		root.setVgap(20);
		root.setHgap(25);
		//Initializing and setting properties of btnMain
		ImageView main = new ImageView(new Image ("file:images/return.png",30,30,true,true));
		btnMain= new Button();
		btnMain.setGraphic(main);
		btnMain.setMinSize(40, 40);
		btnMain.setTextFill(Color.WHITE);
		root.add(btnMain,0, 0 );
		//Handling events to btnMain
		btnMain.setOnAction(e->{
			//Opening the mainMenu
			mainMenu();
		});
		//Setting the text of the Level 1 button as they can always access it
		btnLevels[0].setText("Level 1");
		//Creating level 1 when they click it
		btnLevels[0].setOnAction(e->{
			int[] coordinates = new int[] {0,0,2,4,1,0,2,3,1,1,1,3,3,0,3,3,4,0,3,4};
			newLevel(1,5,coordinates);
		});
		//Handling events to the buttons for the levels. If the previous level has been completed; allow them to access the level
		//otherwise show an error message
		btnLevels[1].setOnAction(e->{
			if(levelCompleted[0]==true){
				int[] coordinates = new int[] {0,1,1,4,1,1,0,4,2,4,3,0,3,1,3,3};
				newLevel(2,5,coordinates);
			}
			else{
				alertError.showAndWait();
			}
		});

		btnLevels[2].setOnAction(e->{
			if(levelCompleted[1]==true){
				int[] coordinates = new int[] {0,4,4,4,1,1,4,2,1,2,3,2,4,0,4,3};
				newLevel(3,5,coordinates);
			}
			else{
				alertError.showAndWait();
			}
		});

		btnLevels[3].setOnAction(e->{
			if(levelCompleted[2] == true) {
				int[] coordinates = new int[] {0,0,4,0,0,1,5,0,0,2,2,2,3,2,0,4,4,2,1,4,0,5,5,2};
				newLevel(4,6,coordinates);
			}
			else {
				alertError.showAndWait();
			}
		});

		btnLevels[4].setOnAction(e->{
			if(levelCompleted[3] == true) {
				int[] coordinates = new int[] {1,0,3,5,5,0,3,1,2,1,4,1,1,3,4,4,1,1,4,5};
				newLevel(5,6,coordinates);
			}
			else {
				alertError.showAndWait();
			}
		});
		//Declaring, initializing, and setting properties of labels to describe the dimensions of the levels underneath
		Label[] lblLevel = new Label[2] ;
		for(int i =0;i <lblLevel.length;i++) {
			lblLevel[i] = new Label();
			lblLevel[i].setMinSize(50, 20);
			lblLevel[i].setStyle("-fx-font-size:20;");
		}
		lblLevel[0].setTextFill(colors[3]);
		lblLevel[0].setText("5 x 5");
		lblLevel[1].setTextFill(colors[5]);
		lblLevel[1].setText("6 x 6");
		//Adding the items to the gridPane
		root.add(lblLevel[0],2, 1);
		root.add(btnLevels[0], 2, 2);
		root.add(btnLevels[1], 2, 3);
		root.add(btnLevels[2],2, 4);
		root.add(lblLevel[1],2, 5);
		root.add(btnLevels[3], 2, 6);
		root.add(btnLevels[4], 2, 7);
	}
	/**
	 * Method that makes the colored flow tile and scence for the main menu
	 */
	private void mainMenu() {
		root.getChildren().clear();
		root.setAlignment(Pos.CENTER);
		vBox = new VBox(10);
		vBox.setAlignment(Pos.BOTTOM_CENTER);
		vBox.setPadding(new Insets(40));
		vBox.setStyle("-fx-background-color:black");
		HBox flow = new HBox();
		flow.setAlignment(Pos.CENTER);
		String[] letters = new String[]{"f","l","o","w"};
		Label[] title = new Label[4];
		for(int i =0; i<title.length;i++){
			title[i]= new Label();
		}
		title[0].setTextFill(Color.RED);
		title[1].setTextFill(Color.GREENYELLOW);
		title[2].setTextFill(Color.AQUA);
		title[3].setTextFill(Color.YELLOW);
		for(int i =0; i<title.length;i++){
			title[i].setStyle("-fx-font-family:Arial; -fx-font-size:50");
			title[i].setText(letters[i]);
			flow.getChildren().add(title[i]);
		}
		Label blank = new Label();
		blank.setMinHeight(20);
		vBox.getChildren().addAll(flow);
		vBox.getChildren().add(blank);
		vBox.getChildren().addAll(menuButtons);
		root.add(vBox,0,0);
	}
	/**
	 * Method that creates a new level 
	 * @param level - the level number
	 * @param dimensions - the dimensions of the grid to be used on this level
	 * @param coordinates - ints representing the coordinates of the circles in the form x1,y1,x2,y2.. 
	 */
	private void newLevel(int level, int dimensions, int[] coordinates) {
		btnMain.setLayoutX(0);
		btnMain.setLayoutY(0);
		cursor = new Cursor(0, 0 ,40,40);
		gamePane = new BorderPane();
		gamePane.setMinWidth(300);
		gamePane.setMinHeight(500);
		root.getChildren().clear();
		menuButtons[1].setLayoutX(300-menuButtons[1].getWidth());
		menuButtons[1].setLayoutY(0);
		
		canvas = new Canvas(300, 500);
		gc = canvas.getGraphicsContext2D();
		gc.setStroke(Color.WHITE);
		int x=25, y=160;
		//Creating a grid of desired dimensions
		if(dimensions ==5) {
			grid = create5by5(x,y);
		}
		else if(dimensions==6) {
			grid = create6by6(x,y);
		}
		//Adding circles and setting their color to the given coordinates
		grid[coordinates[0]][coordinates[1]] .setPoint(colors[0],true);
		grid[coordinates[2]][coordinates[3]].setPoint(colors[0],true);
		grid[coordinates[4]][coordinates[5]].setPoint(colors[1],true);
		grid[coordinates[6]][coordinates[7]].setPoint(colors[1],true);
		grid[coordinates[8]][coordinates[9]].setPoint(colors[2],true);
		grid[coordinates[10]][coordinates[11]]  .setPoint (colors[2],true);
		grid[coordinates[12]][coordinates[13]].setPoint  (colors[3],true);
		grid[coordinates[14]][coordinates[15]].setPoint (colors[3],true);
		if(coordinates.length>16) {
			grid[coordinates[16]][coordinates[17]].setPoint (colors[4],true);
			grid[coordinates[18]][coordinates[19]] .setPoint (colors[4],true);
		}
		if(coordinates.length>20) {
			grid[coordinates[20]][coordinates[21]].setPoint (colors[5],true);
			grid[coordinates[22]][coordinates[23]] .setPoint (colors[5],true);
		}
		//Drawing the circle at all locations where there should be a circle
		for(int i =0;i<grid.length;i++) {
			for(int j =0; j< grid[i].length;j++){			
				if(grid[i][j].getCircle() == true) {
					drawCircle(grid[i][j]);
				}
			}
		}
		//if they click the return icon, send them to the levels page
		btnMain.setOnAction(e->{
			gamePane.getChildren().clear();
			levelsPage();
		});



		//Handling mouse clicks
		scene.setOnMouseClicked(e->{
			//if they click the right mouse button
			if(e.getButton()== MouseButton.SECONDARY)
			{
				//Clear the line they have drawn so far if cursor is clicked
				if(cursor.isClicked()){
					cursor.unClick();
					clearBoxes(selectedBoxes, backColor);
				}
			}
			//if they click the left mouse button
			else {
				//if the cursor isn't clicked
				if(cursor.isClicked() == false) {
					//Cycle through the grid
					for(int i =0;i<grid.length;i++) {
						for(int j =0; j< grid[i].length;j++){
							//if they click in an occupied circle
							if(grid[i][j].getBounds().contains(e.getSceneX(), e.getSceneY())&& grid[i][j].getCircle()==true &&
									grid[i][j].isOccupied()) {
								//clear any boxes of the color they clicked on
								String temp = grid[i][j].getBackgroundColor().toString();
								for(int l=0;l <grid.length; l++) {
									for(int m =0;  m< grid[l].length ; m++) {
										if(grid[l][m].getBackgroundColor().toString().equals(temp)) {
											emptyBox(grid[l][m]);
										}
									}
								}
							}
							//if they landed on an unoccupied circle; start drawing
							else if(grid[i][j].getBounds().contains(e.getSceneX(), e.getSceneY())&& grid[i][j].getCircle()==true) {
								//set cursor to clicked
								cursor.click();
								//store the background color to be used
								for(int k =0; k < colors.length;k++) {
									if(colors[k].toString().equals(grid[i][j].getColor().toString())) {
										backColor = backgroundColors[k];
										grid[i][j].setBackgroundColor(backgroundColors[k]);
									}
								}
								//set the location to occupied and draw a circle again (updated properties)
								grid[i][j].setOccupied(true);
								grid[i][j].setCircle(true);
								drawCircle(grid[i][j]);
								selectedColor= grid[i][j].getColor();
								//Add the location to selectedBoxes if it hasn't already been added
								if(!selectedBoxes.contains(grid[i][j])) {
									selectedBoxes.add(grid[i][j]);
									grid[i][j].setBackgroundColor(backColor);	
								}
							}
						}
					}
				}
			}
		});
		//Handling the mouse moving
		scene.setOnMouseMoved(e->{
			//If the game is running
			if(root.getChildren().contains(gamePane)) {
				//declare and initalize gameWon to true
				boolean gameWon=true;
				//if any of the grids are unoccupied, the game is not over yet
				for(int i =0; i< grid.length;i++){
					for(int j =0; j< grid[i].length;j++) {
						if(grid[i][j].isOccupied()==false) {
							gameWon=false;
						}
					}
				}
				//Prevent cursor icon from going on top of main menu button
				if(btnMain.getBoundsInLocal().contains(e.getSceneX(),e.getSceneY())||
						(menuButtons[1].getBoundsInLocal().contains(e.getSceneX(),e.getSceneY()))){
				cursor.setLayoutX(cursor.getLayoutX());
				cursor.setLayoutY(cursor.getLayoutY());
				}
				else {
					cursor.setLayoutX(e.getSceneX()-cursor.getWidth()/2 +3 );
					cursor.setLayoutY(e.getSceneY()-10);
				}
				
				//If the game hasn't been won yet and the game is running
				if(gameWon==false &&root.getChildren().contains(gamePane)) {
					//if the mouse leaves the grid
					if(e.getSceneX()<=grid[0][0].getxPos() || e.getSceneX()>=grid[0][grid.length-1].getxPos()+grid[0][grid.length-1].getWidth()||
							e.getSceneY()<=grid[0][0].getyPos() || e.getSceneY()>=grid[grid.length-1][0].getyPos()+grid[grid.length-1][0].getHeight()) {
						//if selectedBoxes has a size greater than or equal to 1
						if(selectedBoxes.size()>=1) {
							//clear the first circle
							selectedBoxes.get(0).setBackgroundColor(Color.BLACK);
							drawCircle(selectedBoxes.get(0));
							selectedBoxes.remove(selectedBoxes.get(0));
						}
						//clear all boxes and unclick
						clearBoxes(selectedBoxes, backColor);
						cursor.unClick();
					}
					//if the cursor is clicked
					if(cursor.isClicked()) {
						//cycle through the grid
						for(int i =0;i<grid.length;i++) {
							for(int j =0; j< grid[i].length;j++){	
								//if the cursor goes into a location
								if(grid[i][j].getBounds().contains(e.getSceneX(), e.getSceneY())) {
									//if the location has no circle and hasn't been selected yet 
									if(grid[i][j].getCircle()==false && !grid[i][j].getColor().toString().equals(selectedColor.toString())
											&&selectedBoxes.contains(grid[i][j]) ==false) {
										//add it
										selectedBoxes.add(grid[i][j]);
										break;
									}
									//if the location has a circle of the same color and more than 1 has already been selected
									else if(grid[i][j].getCircle()==true&& grid[i][j].getColor().toString().equals(selectedColor.toString())&&
											(selectedBoxes.size()>1)) {
										//add it
										selectedBoxes.add(grid[i][j]);
										break;
									}
								}
							}
						}
					}
					//Cycling through selectedBoxes starting at index 1
					for(int i =1; i<selectedBoxes.size();i++) {
						//storing the current and previous rows, columns and current color that is being drawn
						Color curColor = selectedBoxes.get(i-1).getBackgroundColor();
						int curRow = selectedBoxes.get(i).getRow();
						int curCol = selectedBoxes.get(i).getColumn();
						int prevRow = selectedBoxes.get(i-1).getRow();
						int prevCol = selectedBoxes.get(i-1).getColumn();
						//Checking if the consecutively chosen boxes are right next to each other vertically or horizontally
						if(! (((Math.abs((prevRow-curRow))==0)&&
								(Math.abs(((prevCol-curCol)))==1))||
								((Math.abs(((prevRow)-curRow))==1&&
								(Math.abs(((prevCol)-curCol))==0)))))
						{
							//unclick the cursor and clear the boxes
							cursor.unClick();
							clearBoxes(selectedBoxes, curColor);
							break;
						}

						//	If the selected box is to override an occupied box
						if(!selectedBoxes.get(i).getBackgroundColor().toString().equals(backColor.toString())){
							//store the color of the box that was to be removed
							String temp = selectedBoxes.get(i).getBackgroundColor().toString();
							for(int l=0;l <grid.length; l++) {
								for(int m =0;  m< grid[l].length ; m++) {
									//empty the box if it is the same as the one that is overwritten
									if(grid[l][m].getBackgroundColor().toString().equals(temp)) {
										emptyBox(grid[l][m]);
									}
								}
							}
						}	
						//set the boxes to occupied and set their color to backColor
						selectedBoxes.get(i).setOccupied(true);
						selectedBoxes.get(i).setBackgroundColor(backColor);
						if(selectedBoxes.get(i).getCircle()==false){
							//Drawing the X and filling the background of the box at the index
							drawLine(selectedColor,backColor,selectedBoxes.get(i).getxPos(),
									selectedBoxes.get(i).getyPos(),selectedBoxes.get(i).getWidth(),selectedBoxes.get(i).getHeight());
						}
						//or drawing a circle if there was a circle at the box
						else{
							drawCircle(selectedBoxes.get(i));
						}
					}
					//storing the coordinates of the box
					String coor ="" ;
					if(selectedBoxes.size()>=1) {
						coor= selectedBoxes.get(selectedBoxes.size()-1).getCoordinates();
					}
					//if they find the correctly colored unique circle to complete the line
					if((selectedBoxes.size()>=1)&&(((selectedBoxes.get(selectedBoxes.size()-1).getColor()).toString()).equals
							(selectedColor.toString())&&
							!(selectedBoxes.get(0).getCoordinates().equals(coor)))){
						//Draw the final circle, unclick, and set gameWon to true
						selectedBoxes.get(selectedBoxes.size()-1).setBackgroundColor(backColor);
						selectedBoxes.get(selectedBoxes.size()-1).setOccupied(true);
						drawCircle( selectedBoxes.get(selectedBoxes.size()-1));
						cursor.unClick();
						selectedBoxes.clear();
						gameWon=true;
						//if the grid is not full set gameWon to false, otherwise keep it to true
						for(int i =0; i< grid.length;i++){
							for(int j =0; j< grid[i].length;j++) {
								if(grid[i][j].isOccupied()==false) {
									gameWon=false;
								}
							}
						}
						//if game has been won
						if(gameWon == true) {
							//Alert them that they've won and of their progress
							Alert congrats = new Alert(AlertType.INFORMATION);
							congrats.setTitle("Flow");
							congrats.setHeaderText(null);
							if(level<5) {
								congrats.setGraphic(new ImageView(new Image(("file:images/unlock.png"),50,50,true,true)));
								congrats.setContentText("You successfully connected the right colors. \nLevel "
										+ (level+1)+" is now unlocked. Well done!");
							}
							else {
								congrats.setGraphic(new ImageView(new Image(("file:images/win.png"),50,50,true,true)));
								congrats.setContentText("You successfully connected the right colors. You have finished all available"
										+ " levels. We hope you enjoyed our game. Well done!");

							}
							congrats.showAndWait();
							//Set levelCompleted at the corresponding index to true and set the text of the level button
							levelCompleted[level-1] = true;
							btnLevels[level-1].setText("Level "+ (level-1));
							gamePane.getChildren().clear();
							//open the levels page
							root.getChildren().clear();
							levelsPage();
						}
					}
				}
			}
		});
		//Adding items to the scene
		gamePane.getChildren().add(canvas);

		gamePane.getChildren().add(menuButtons[1]);
		gamePane.getChildren().addAll(btnMain,cursor);
		root.add(gamePane, 0, 0);
	}
	/**
	 * Method that checks which levels have been completed and set the corresponding button's properties accordingly
	 */
	private void levelCheck(){
		for(int k =1; k< levelCompleted.length;k++) {
			if(levelCompleted[k-1] ==true) {
				btnLevels[k].setText("Level "+ (k+1));
				btnLevels[k-1].setStyle("-fx-border-color:green");
				btnLevels[k].setStyle("-fx-border-color:white");
			}
			else {
				btnLevels[k].setText("X");
				btnLevels[k].setStyle("-fx-border-color:red;");
			}
		}
		if(levelCompleted[4]==true) {
			btnLevels[4].setStyle("-fx-border-color:green");
		}
	}
	/**
	 * Method that creates a 5 by 5 grid
	 * @param x - xPosition of the top left corner
	 * @param y - yPosition of top left corner
	 * @return -Location[][] that representing the boxes for the level
	 */
	private Location[][] create5by5(int x, int y) {
		grid= new Location[5][5]; 
		for(int i =0; i< grid.length;i++){
			for(int j =0; j< grid[i].length;j++){			
				grid[i][j] =new Location(Color.BLACK,false,false,i,j);	
				grid[i][j].setxPos(x);
				grid[i][j].setyPos(y);
				x+=grid[i][j].getWidth();
				gc.strokeRect(grid[i][j].getxPos(), grid[i][j].getyPos(),grid[i][j].getWidth() , grid[i][j].getHeight());
			}
			x=25;
			y+=grid[0][0].getHeight();
		}
		return grid;
	}
	/**
	 * Method that creates a 5 by 5 grid
	 * @param x - xPosition of the top left corner
	 * @param y - yPosition of top left corner
	 * @return -Location[][] that representing the boxes for the level
	 */
	private Location[][] create6by6(int xPos, int y) {
		grid= new Location[6][6]; 
		int x=xPos;
		for(int i =0; i< grid.length;i++){
			x=xPos;
			for(int j =0; j< grid[i].length;j++){			
				grid[i][j] =new Location(42,42, Color.BLACK,false,false,i,j);	
				grid[i][j].setxPos(x);
				grid[i][j].setyPos(y);
				x+=grid[i][j].getWidth();
				gc.strokeRect(grid[i][j].getxPos(), grid[i][j].getyPos(),grid[i][j].getWidth() , grid[i][j].getHeight());
			}
			y+=grid[0][0].getHeight();
		}
		return grid;
	}
	/**
	 * Method that empties a given Location object (resets it to its default)
	 * @param l - Location object to be reset
	 */
	private void emptyBox(Location l) {
		gc.setFill(Color.BLACK);
		l.setBackgroundColor(Color.BLACK);
		l.setOccupied(false);
		gc.fillRect(l.getxPos()+2,l.getyPos()+2, l.getWidth()-4, l.getHeight()-4); 
		//If there is a circle, draw one
		if(l.getCircle()==true) {
			drawCircle(l);
		}
	}
	/**
	 * Method used to draw the circle at a give Location object
	 * @param l - Location object where the circle is to be draw
	 */
	private void drawCircle(Location l) {
		Color color= l .getColor();Color back = l.getBackgroundColor();
		double xPos = l.getxPos(); double yPos=l.getyPos(); int width = l.getWidth(); int height=l.getHeight();
		gc.setFill(back);
		gc.fillRect(xPos+2, yPos+2, width-4, height-4);
		gc.setFill(color);
		gc.fillOval(xPos+width/2-20,yPos+height/2-20 ,40, 40);
	}
	/**
	 * Method used to draw an X at the given spot
	 * @param color - Color of the x
	 * @param back - Color representing background color
	 * @param xPos - double representing xPos
	 * @param yPos - double representing yPos
	 * @param width - int representing width
	 * @param height - int representing height
	 */
	private void drawLine(Color color, Color back, double xPos, double yPos, int width, int height) {
		gc.setFill(back);
		gc.fillRect(xPos+2, yPos+2, width-4, height-4);
		gc.setStroke(color);
		gc.setLineWidth(10);
		gc.strokeLine(xPos+width/4,yPos+height/4,xPos+3*width/4,yPos+3*height/4);
		gc.strokeLine(xPos+width/4,yPos+3*height/4,xPos+3*width/4,yPos+height/4);
	}
	//Method that clears the arrayList of boxes of a given color
	private void clearBoxes(ArrayList<Location> selectedBoxes, Color curColor) {
		for(int i =0; i<selectedBoxes.size();i++) {
			if(selectedBoxes.get(i).getBackgroundColor().toString().equals(curColor.toString())) {
				selectedBoxes.get(i).setBackgroundColor(Color.BLACK);
				selectedBoxes.get(i).setOccupied(false);
				emptyBox(selectedBoxes.get(i));
			}
		}
		selectedBoxes.clear();
	}
}