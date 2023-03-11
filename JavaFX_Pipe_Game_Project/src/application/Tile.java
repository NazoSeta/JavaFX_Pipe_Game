package application;

import javafx.scene.image.Image;

public class Tile {
	
	// First Programmer ->  Name: Niyazi Ozan    Surname: Ateþ      no: 150121991
	// Second Programmer -> Name: Hasan  	     Surname: Özeren    no: 150121036
	
	// The purpose of this class is to hold some information about the pipe in a tile.
	
	// Here we create some data fields for the 'Tile' class to give some information about the pipe in the tile.
	private int id;
	private String type;
	private String property;
	private boolean isMoveable;
	private Image image;
	// The boolean data fields will show us which direction of the tile is open.
	private boolean up = false;
	private boolean down = false;
	private boolean left = false;
	private boolean right = false;
	
	// This is the constructor of the 'Tile' class.
	// We use the setTile(String, String) method to set some properties and  give the tile a pipe image.
	public Tile(String number, String type, String property) {
		id = Integer.parseInt(number);
		this.type = type;
		this.property = property;
		setTile(type, property);
	}
	
	// The setTile(String, String) method is created based on the type and property of the given String.
	// It will give the tile a image, check if it is movable and show which parts of the tile is open.
	public void setTile(String type, String property) {
		if (type.equals("Starter")) {
			if (property.equals("Vertical")) {
				image = new Image("StarterTileVertical.png");
				isMoveable = false;
				up = false;
				down = true;
				left = false;
				right = false;
			}
			else if (property.equals("Horizontal")) {
				image = new Image("StarterTileHorizontal.png");
			  	isMoveable = false;
			  	up = false;
			  	down = false;
				left = true;
				right = false;
			}
		}
		else if (type.equals("End")) {
			if (property.equals("Vertical")) {
				image = new Image("EndTileVertical.png");
				isMoveable = false;
				up = false;
				down = true;
				left = false;
				right = false;
			}
			else if (property.equals("Horizontal")) {
				image = new Image("EndTileHorizontal.png");
				isMoveable = false;
			  	up = false;
			  	down = false;
				left = true;
				right = false;
			}
		}
		else if (type.equals("Empty")) {
			if (property.equals("none")) {
				image = new Image("Empty.png");
				isMoveable = true;
				up = false;
				down = false;
				left = false;
				right = false;
			}
			else if (property.equals("Free")) {
				image = new Image("EmptyFree.png");
				isMoveable = true;
				up = false;
				down = false;
				left = false;
				right = false;
			}
		}
		else if (type.equals("Pipe")) {
			if (property.equals("Vertical")) {
				image = new Image("PipeTilesVertical.png");
				isMoveable = true;
				up = true;
				down = true;
				right = false;
				left = false;
			}
			else if (property.equals("Horizontal")) {
				image = new Image("PipeTilesHorizontal.png");
				isMoveable = true;
				left = true;
				right = true;
				up = false;
				down = false;
			}
			else if (property.equals("00")) {
				image = new Image("CurvedPipeTiles00.png");
				isMoveable = true;
				up = true;
				down = false;
				right = false;
				left = true;
			}
			else if (property.equals("01")) {
				image = new Image("CurvedPipeTiles01.png");
				isMoveable = true;
				up = true;
				down = false;
				right = true;
				left = false;
			}
			else if (property.equals("10")) {
				image = new Image("CurvedPipeTiles10.png");
				isMoveable = true;
				up = false;
				down = true;
				right = false;
				left = true;
			}
			else if (property.equals("11")) {
				image = new Image("CurvedPipeTiles11.png");
				isMoveable = true;
				up = false;
				down = true;
				right = true;
				left = false;
			}
		}
		else if (type.equals("PipeStatic")) {
			if (property.equals("Vertical")) {
				image = new Image("PipeStaticTilesVertical.png");
				isMoveable = false;
				up = true;
				down = true;
				right = false;
				left = false;
			}
			else if (property.equals("Horizontal")) {
				image = new Image("PipeStaticTilesHorizontal.png");
				isMoveable = false;
				up = false;
				down = false;
				left = true;
				right = true;
			}
			else if (property.equals("00")) {
				image = new Image("CurvedPipeStaticTiles00.png");
				isMoveable = false;
				up = true;
				down = false;
				right = false;
				left = true;
			}
			else if (property.equals("01")) {
				image = new Image("CurvedPipeStaticTiles01.png");
				isMoveable = false;
				up = true;
				down = false;
				right = true;
				left = false;
			}
			else if (property.equals("10")) {
				image = new Image("CurvedPipeStaticTiles10.png");
				isMoveable = false;
				up = false;
				down = true;
				right = false;
				left = true;
			}
			else if (property.equals("11")) {
				image = new Image("CurvedPipeStaticTiles11.png");
				isMoveable = false;
				up = false;
				down = true;
				right = true;
				left = false;
			}
		}
	}

	// Here we got our getter/setter methods.
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public boolean isisMoveable() {
		return isMoveable;
	}

	public void setisMoveable(boolean isMoveable) {
		this.isMoveable = isMoveable;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}
	
}
