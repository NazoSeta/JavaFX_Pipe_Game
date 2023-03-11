package application;
	
import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class Main extends Application {

	// First Programmer ->  Name: Niyazi Ozan    Surname: Ateþ      no: 150121991
	// Second Programmer -> Name: Hasan  	     Surname: Özeren    no: 150121036
	
	/* In this program we aimed to create a game named 'Pipe Game'.
	 * The purpose of this game is to let a ball roll across some pipes to reach a end from a start point.
	 * We did this by using an another class called 'Tile'.
	 * This 'Tile' class helped us to hold some information about the tiles in the game such as images, types, property, id, etc... */
	
	// Here we create some data fields and an ArrayList that we will use.
	// The reason that it is outside the start class is because it will be used in multiple methods.
	// The tiles ArrayList is to create tiles that we will use in the game.
	private ArrayList<Tile> tiles = new ArrayList<Tile>();
	private int moves = 0;
	private int currentLevel = 0;
	private int unlockedLevel = 1;
	private boolean locked = false;
	private GridPane gamePane = new GridPane();
	private Label tf1 = new Label("Level Not Completed");
	private Label tf2 = new Label("Number Of Moves:   " + moves);
	
	@Override
	public void start(Stage primaryStage) throws FileNotFoundException {
		
		// Here we will create 2 data fields for the file we use that will help us further in the program.
		File fileLevels = new File("src/Levels");
		int fileSize = fileLevels.list().length;
		
		// This will be our main pane that set all the other panes together.
		BorderPane mainPane = new BorderPane();
		gamePane.setAlignment(Pos.CENTER);
		gamePane.setHgap(4);
		gamePane.setVgap(4);
		
		// This is our HBox that we will set at the bottom of our mainPane which will hold some inforamtion about the current level.
		HBox hBox = new HBox(20);
		hBox.setPadding(new Insets(15, 0, 15, 0));
		hBox.setAlignment(Pos.CENTER);
		hBox.setStyle("-fx-border-color: black");
		hBox.getChildren().add(tf1);
		hBox.getChildren().add(tf2);
		
		mainPane.setBottom(hBox);
		
		// In this part of our program we create an ArrayList called levelNames.
		// We fill it with the number of levels and show if  they are locked/unlocked.
		// After we did this we put the String that are in the levelNames into a ComboBox.
		// The ComboBox is called 'levels' which will be put to the right of the mainPane.
		ArrayList<String> levelNames = new ArrayList<String>();
		levelNames.add("Level: " + 1 + " is unlocked!");
		for (int i = 2 ; i <= fileSize ; i++) {
			levelNames.add("Level: " + i + " is locked!");
		}
		ComboBox<String> levels = new ComboBox<>(FXCollections.observableArrayList(levelNames));
		levels.setValue("Levels");
		mainPane.setRight(levels);
		
		
		
		// This part helps us to switch and create one level to another by using the ComboBox 'levels'.
		// The level will be created by using the showLevel(int levelIndex) method.
		// This part will also reset the moves.
		levels.setOnAction(e -> {
			moves = 0;
			String comboValue = levels.getValue();
			
			if (comboValue.contains("un")){
				locked = false;
				if (comboValue.charAt(8) == ' ') {
					currentLevel = (comboValue.charAt(7) - '0');
				}
				else if ('0' <= comboValue.charAt(8) && comboValue.charAt(8) <= '9') {
					currentLevel = (comboValue.charAt(7) - '0')*10 + (comboValue.charAt(8) - '0');
				}
				try {
					gamePane.getChildren().clear();
					showLevel(currentLevel);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		// We used nested loops here to create an empty playground for the pipes and ball.
		// After that we add the gamePane to the center of the mainPane. 
		for (int i = 0 ; i < 4 ; i++) {
			for (int j = 0 ; j < 4 ; j++) {
					ImageView imageView = new ImageView("Empty.png");
					imageView.setFitHeight(80);
					imageView.setFitWidth(80);
					gamePane.add(imageView, j, i);
				}
			}
		
		mainPane.setCenter(gamePane);
		
		// Here we control the tiles that are in the gamePane
		// We do this by pressing a tile (tile1) and releasing on an another tile (tile2).
		gamePane.setOnMousePressed(e -> {
            int x1 = (int)e.getSceneX();
            int y1 = (int)e.getSceneY();

            Tile tile1 = location(x1, y1);

            gamePane.setOnMouseReleased(e2 -> {
                int x2 = (int)e2.getSceneX();
                int y2 = (int)e2.getSceneY();

                Tile tile2 = location(x2, y2);
         // We check if we locked the level, if not we check if the tiles are good to move by using the isTileMoveable(Tile, Tile) method.
                if (!locked) {
                if (isTileMoveable(tile1, tile2)) {
         // If they are movable then we use the moveTile(Tile, Tile) method the switch them from position.
         // We update the 'Number Of Moves x' text and check if the level is completed with the levelCompleted() method.
                    moveTile(tile1, tile2);
                    tf2.setText("Number Of Moves:   " + ++moves);
                    if (levelCompleted()) {
         // If the level is completed, we update the 'Level Not Completed' text and let a ball roll from the start to end pipe by using the method called ballAnimation().
         // After that we will lock the level by using the statement locked = true;
                    	 tf1.setText("Level Completed");
                    	 ballAnimation();
                    	 locked = true;
         // We clear some ArrayList that we filled in the levelCompleted() part. We also do this if the level is not completed. We do this for the next check.
                         pointsx.clear();
                         pointsy.clear();
                         properties.clear();
        // And then at last we update the next text in the ComboBox, so the next level is updated and can be played.
                         if (currentLevel == unlockedLevel) {
                        	 unlockedLevel++;
                        	 ObservableList<String> list = levels.getItems();
                        	 if (unlockedLevel <= fileSize) {
                        	 list.set(unlockedLevel - 1, "Level: " + unlockedLevel + " is unlocked!");
                        	 }
                        } 
                    }
                    else {
                    	pointsx.clear();
                    	pointsy.clear();
                    	properties.clear();
                    }
                }
                }
            });
        }); 
		
		// This is were the Scene and Stage are created and shown.
		Scene scene = new Scene(mainPane);
		primaryStage.setResizable(false);
		primaryStage.setTitle("Pipe Game");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public Tile location(int x, int y) {
        // The purpose of this method is to determine the index of the tiles and we will add ids accordingly.
        // We create some variables
        int tileRow = 0;
        int tileColumn = 0;
        int id = 0;
        // Since each of our images is 80 pixels, we can calculate the number of rows and columns here.
        if (x <= 335 && y <= 335) {
            tileRow = y/80;
            tileColumn = x/80;
        }
        // Here we find the tile id and return the tile id
        for (int i = 0 ; i < 16 ; i++) {
            if (tiles.get(i).getId() == (tileRow*4 + tileColumn + 1)) {
                id = i;
            }
        }
        return tiles.get(id);
    }
	
	// This method is used to create whenever two tile is movable to one other. An important note is that the second tile should be empty and free.
	public boolean isTileMoveable (Tile tile1, Tile tile2) {
		if (tile1.isisMoveable() && tile2.isisMoveable() && tile2.getProperty().equals("Free") && ((Math.abs(tile1.getId() - tile2.getId()) == 1) || (Math.abs(tile1.getId() - tile2.getId()) == 4))) {
			return true;
		}
		else
			return false;
	}
	
	// This method is used to switch two tiles in the gamePane.
	public void moveTile(Tile tile1, Tile tile2) {
		
			int tile1ID = 0;
			int tile2ID = 0;
			
			// Here we find the ids of the tiles so we can use them from the tiles ArrayList.
			for (int i = 0 ; i < 16 ; i++) {
				if (tiles.get(i).getId() == tile1.getId())
					tile1ID = i;
				if (tiles.get(i).getId() == tile2.getId())
					tile2ID = i;
			}
			
			// In this next part we switch the type, property and images of the tiles that are represented to switch.
			String tempType = tiles.get(tile1ID).getType();
			tiles.get(tile1ID).setType(tiles.get(tile2ID).getType());
			tiles.get(tile2ID).setType(tempType);
			
			String tempProperty = tiles.get(tile1ID).getProperty();
			tiles.get(tile1ID).setProperty(tiles.get(tile2ID).getProperty());
			tiles.get(tile2ID).setProperty(tempProperty);
			
			// We switch the images thanks to the setTile(String, String) method defined in the 'Tile' class.
			tiles.get(tile1ID).setTile(tiles.get(tile1ID).getType(), tiles.get(tile1ID).getProperty());
			tiles.get(tile2ID).setTile(tiles.get(tile2ID).getType(), tiles.get(tile2ID).getProperty());
			
			// The switched tiles are refresed in the gamePane thanks to the x and y coordinates found below.
			int rowIndex = (tiles.get(tile1ID).getId() - 1) / 4;
			int ColumnIndex = (tiles.get(tile1ID).getId() - 1) % 4;
			int rowIndex2 = (tiles.get(tile2ID).getId() - 1) / 4;
			int ColumnIndex2 = (tiles.get(tile2ID).getId() - 1) % 4;
			ImageView imageView = new ImageView(tiles.get(tile1ID).getImage());
			imageView.setFitHeight(80);
			imageView.setFitWidth(80);
			gamePane.add(imageView, ColumnIndex, rowIndex);
			
			ImageView imageView2 = new ImageView(tiles.get(tile2ID).getImage());
			imageView2.setFitHeight(80);
			imageView2.setFitWidth(80);
			gamePane.add(imageView2, ColumnIndex2, rowIndex2);
	}
	
	// This method checks whenever the level is completed or not.
	public boolean levelCompleted() {
		
		// Here we define some important data fields that we will use in this method.
		int startIndex = 0;
		int endIndex = 0;
		int nextIndex = 0;
		String startOutput = "";
		// These booleans show the direction of the ball.
		boolean goesUp = false;
		boolean goesRight = false;
		boolean goesDown = false;
		boolean goesLeft = false;
		
		// We first search for the start and end index. While doing this we also are interesed in finding the direction of the ball.
		for (int i = 0 ; i < 16 ; i++) {
			if (tiles.get(i).getType().equals("Starter")) {
				startIndex = i;
				if (tiles.get(i).getProperty().equals("Vertical")) {
					startOutput = "Vertical";
					goesDown = true;
				}
				else {
					startOutput = "Horizontal";
					goesLeft = true;
				}
			}
			if (tiles.get(i).getType().equals("End")) {
				endIndex = i;
			}
		}
		
		// These two if clauses is created to find create the first point of the path.
		// And find the nextIndex to create a path. And create the first path between the two points.
		// We create a path with the createPath(int, String) method.
			if (startOutput.equals("Vertical")) {
				createPath(startIndex, startOutput);
				nextIndex = startIndex + 4;
				createPath(nextIndex, tiles.get(nextIndex).getProperty());
			}
			if (startOutput.equals("Horizontal")) {
				createPath(startIndex, startOutput);
				nextIndex += startIndex - 1;
				createPath(nextIndex, tiles.get(nextIndex).getProperty());
			}
			
			// This for loop is used to check if the level is completed and to create the path while checking it.
			// We check with the direction of the ball and check whenever the pipe is open from the direction of the ball.
			// After that we check the type of the next tile.
			// then we create a path with createPath(int, String) method with the corresponding property of that tile.
			// And of course we will update the way of the ball and the nextIndex data field.
			// At last, we will check if the nextIndex is equal to the endIndex. If so, it will return true otherwise it continue the loop or return false;
				for (int i = 0 ; i < 16 ; i++) {
					if (goesUp && tiles.get(nextIndex).isDown()) {
						if (tiles.get(nextIndex).getType().equals("Pipe") || tiles.get(nextIndex).getType().equals("PipeStatic")) {
							if(tiles.get(nextIndex).getProperty().equals("Vertical")) {
								goesUp = true;
								nextIndex -= 4;
								createPath(nextIndex, tiles.get(nextIndex).getProperty());
								if (nextIndex == endIndex) {
									return true;
								}
							}
							else if (tiles.get(nextIndex).getProperty().equals("10")) {
								goesUp = false;
								goesLeft = true;
								nextIndex -= 1;
								createPath(nextIndex, tiles.get(nextIndex).getProperty());
								if (nextIndex == endIndex) {
									return false;
								}
							}
							else if (tiles.get(nextIndex).getProperty().equals("11")) {
								goesUp = false;
								goesRight = true;
								nextIndex += 1;
								createPath(nextIndex, tiles.get(nextIndex).getProperty());
								if (nextIndex == endIndex) {
									return true;
								}
							}
						}
					}
					else if (goesRight && tiles.get(nextIndex).isLeft()) {
						if (tiles.get(nextIndex).getType().equals("Pipe") || tiles.get(nextIndex).getType().equals("PipeStatic")) {
							if(tiles.get(nextIndex).getProperty().equals("Horizontal")) {
								goesRight = true;
								nextIndex += 1;
								createPath(nextIndex, tiles.get(nextIndex).getProperty());
								if (nextIndex == endIndex) {
									return true;
								}
							}
							else if (tiles.get(nextIndex).getProperty().equals("00")) {
								goesRight = false;
								goesUp = true;
								nextIndex -= 4;
								createPath(nextIndex, tiles.get(nextIndex).getProperty());
								if (nextIndex == endIndex) {
									return true;
								}
							}
							else if (tiles.get(nextIndex).getProperty().equals("10")) {
								goesRight = false;
								goesDown = true;
								nextIndex += 4;
								createPath(nextIndex, tiles.get(nextIndex).getProperty());
								if (nextIndex == endIndex) {
									return false;
								}
							}
						}
					}
					else if (goesDown && tiles.get(nextIndex).isUp()) {
							if (tiles.get(nextIndex).getType().equals("Pipe") || tiles.get(nextIndex).getType().equals("PipeStatic")) {
								if(tiles.get(nextIndex).getProperty().equals("Vertical")) {
									goesDown = true;
									nextIndex += 4;
									createPath(nextIndex, tiles.get(nextIndex).getProperty());
									if (nextIndex == endIndex) {
										return false;	
									}
								}
							else if (tiles.get(nextIndex).getProperty().equals("00")) {
								goesDown = false;
								goesLeft = true;
								nextIndex -= 1;
								createPath(nextIndex, tiles.get(nextIndex).getProperty());
								if (nextIndex == endIndex) {
									return false;
								}
							}
							else if (tiles.get(nextIndex).getProperty().equals("01")) {
								goesDown = false;
								goesRight = true;
								nextIndex += 1;
								createPath(nextIndex, tiles.get(nextIndex).getProperty());
								if (nextIndex == endIndex) {
									return true;
								}
							}
							}
					}
					else if (goesLeft && tiles.get(nextIndex).isRight()) {
						if (tiles.get(nextIndex).getType().equals("Pipe") || tiles.get(nextIndex).getType().equals("PipeStatic")) {
							if(tiles.get(nextIndex).getProperty().equals("Horizontal")) {
								goesLeft = true;
								nextIndex -= 1;
								createPath(nextIndex, tiles.get(nextIndex).getProperty());
								if (nextIndex == endIndex) {
									return false;
								}
							}
							else if (tiles.get(nextIndex).getProperty().equals("01")) {
								goesLeft = false;
								goesUp = true;
								nextIndex -= 4;
								createPath(nextIndex, tiles.get(nextIndex).getProperty());
								if (nextIndex == endIndex) {
									return true;
								}
							}
							else if (tiles.get(nextIndex).getProperty().equals("11")) {
								goesLeft = false;
								goesDown = true;
								nextIndex += 4;
								createPath(nextIndex, tiles.get(nextIndex).getProperty());
								if (nextIndex == endIndex) {
									return false;
								}
							}
						}
					}
			}
		return false;
	}
	
	public void showLevel(int levelIndex) throws FileNotFoundException {
		// We do our file reading operations through this method.
		File file = new File("src/Levels/CSE1242_spring2022_project_level" + levelIndex + ".txt");
		Scanner input = new Scanner(file);
		// When we are going to read a new file, we need to empty our tiles array.
		tiles.removeAll(tiles);
		
		// We get the properties we need from the file through the for loop and add the tiles array.
        // We are preparing our panel by creating our Tiles array.
		while (input.hasNext()) {
			String[] line = input.nextLine().split(",");
			if (line.length < 3) {
				continue;
			}
			String number = line[0];
			String type = line[1];
			String property = line[2];
		    tiles.add(new Tile(number, type, property));
		}
		// We add the tiles features we received in the tiles array to our grid pane through the for loop using our images.
		for (int i = 0 ; i < 4 ; i++) {
            for (int j = 0 ; j < 4 ; j++) {
                    ImageView imageView = new ImageView(tiles.get(4*i + j).getImage());
                    imageView.setFitHeight(80);
                    imageView.setFitWidth(80);
                    gamePane.add(imageView, j, i);
            }
        }
       // We update the bottom part of our border pane.
		tf1.setText("Level Not Completed");
		moves = 0;
		tf2.setText("Number Of Moves:   " + moves);
		input.close();
	}
	// We have defined these ArrayLists here because we will only use this ArrayList here specific.
	private ArrayList<Double> pointsx = new ArrayList<>();
	private ArrayList<Double> pointsy = new ArrayList<>();
	private ArrayList<String> properties = new ArrayList<>();
	public void createPath(int index, String property) {
		// While making the path, we take the coordinates of the path through this method and add them to an ArrayList.
		double x1 = index%4*84 + 65;
		double y1 = index/4*84 + 43;
		pointsx.add(x1);
		pointsy.add(y1);
		properties.add(property);
	}
	
	public void ballAnimation() {
		// In this method, we create a ball and animate it.
        // We create a ball and add the ball grid pane.
        Circle circle = new Circle(40, 40, 10);
		circle.setStroke(Color.BLACK);
		circle.setFill(Color.AQUA);
		gamePane.getChildren().add(circle);
		// We will create a path here, specify the index of this path, and move the ball according to that path.
        // We take the unique feature of the road and assign an index accordingly and create our own path.
		Path path = new Path();
	    // We determine whether the starting point of the path is horizontal or vertical and we create the beginning of the path
		if (properties.get(0).equals("Vertical")) {
			MoveTo moveTo = new MoveTo(pointsx.get(0), pointsy.get(0));
			LineTo line = new LineTo(pointsx.get(0), pointsy.get(0) + 20);
			path.getElements().addAll(moveTo, line);
		}
		if (properties.get(0).equals("Horizontal")) {
			MoveTo moveTo = new MoveTo(pointsx.get(0), pointsy.get(0));
			LineTo line = new LineTo(pointsx.get(0) + 20, pointsy.get(0));
			path.getElements().addAll(moveTo, line);
		}
		// After determining our starting path, we take the paths in a for loop and add them to the path,
		// respectively, by looking at certain features.
		for (int i = 1 ; i < pointsx.size() ; i++) {
			// If our path is vertical, we take our points vertically and draw the path.
			if (properties.get(i).equals("Vertical")) {
				path.getElements().add(new LineTo(pointsx.get(i), pointsy.get(i) + 20));
			}
			// If our path is horizontal, we take our points vertically and draw the path.
			else if (properties.get(i).equals("Horizontal")) {
				path.getElements().add(new LineTo(pointsx.get(i) + 20, pointsy.get(i)));
			}
			// If our path is in a circle, we create our path in the form of a quarter circle using the arcTo method 
		    // and make a small correction in our index.
			else if (properties.get(i).equals("00")) {
			    ArcTo arcTo = new ArcTo();
			    arcTo.setX(pointsx.get(i) - 11);
			    arcTo.setY(pointsy.get(i) - 11);
			    arcTo.setRadiusX(40.0);
			    arcTo.setRadiusY(40.0);
				path.getElements().add(arcTo);
			}
			else if (properties.get(i).equals("01")) {
			    ArcTo arcTo = new ArcTo();
			    arcTo.setX(pointsx.get(i) + 11);
			    arcTo.setY(pointsy.get(i) - 11);
			    arcTo.setRadiusX(40.0);
			    arcTo.setRadiusY(40.0);
				path.getElements().add(arcTo);
			}
			else if (properties.get(i).equals("10")) {
			    ArcTo arcTo = new ArcTo();
			    arcTo.setX(pointsx.get(i) - 11);
			    arcTo.setY(pointsy.get(i) + 11);
			    arcTo.setRadiusX(40.0);
			    arcTo.setRadiusY(40.0);
			    arcTo.setSweepFlag(true);
				path.getElements().add(arcTo);
			}
			else if (properties.get(i).equals("11")) {
			    ArcTo arcTo = new ArcTo();
			    arcTo.setX(pointsx.get(i) + 11);
			    arcTo.setY(pointsy.get(i) + 11);
			    arcTo.setRadiusX(40.0);
			    arcTo.setRadiusY(40.0);
			    arcTo.setSweepFlag(true);
			    path.getElements().add(arcTo);
			}
		}
		// Here we solve an index shift problem that occurred on the road at the end.
		if (properties.get(properties.size() - 1).equals("Vertical")) {
			path.getElements().add(new LineTo(pointsx.get(pointsx.size() - 1) + 5, pointsy.get(pointsy.size() - 1) - 10));
		}
		// We do our animation operations here and animate the ball.
		PathTransition pt = new PathTransition();
		pt.setDuration(Duration.millis(5000));
		pt.setPath(path);
		pt.setNode(circle);
		pt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
		pt.setCycleCount(1);
		pt.setAutoReverse(false);
		pt.play();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
