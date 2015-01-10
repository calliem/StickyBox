import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * This represents the primary class for a game/animation.
 *
 * @author Callie Mao
 */
class BoxWorld {
    private static final double ENEMY_GROWTH_FACTOR = 1.1;
    private static final int OPPONENT_SIZE = 40;
    private static final int PLAYER_SPEED = 10;
    private static final int BOX_SIZE = 40;

    private Scene myScene;
    private Group myRoot;
    private Rectangle myPlayer;
    private Point2D myPlayerVelocity;
    private String playerDirection; // LILA this does not seem very efficient
    
    //this was originally in the init method
    private static final int exitCoord = 200;
    private static final int[] obstacleXCoordinates = {exitCoord, 320,280,80,120,40,80,240}; 
    private static final int[] obstacleYCoordinates = {exitCoord, 0,120,80,280,240,360,320};
   
    private static final int[] fakeObstacleXCoord = {120,360,0,240,280,360,320};
    private static final int[] fakeObstacleYCoord = {40,80,160,160,240,280,360};
    
	private static final int totalObstacles = obstacleXCoordinates.length + fakeObstacleXCoord.length;
	private static final Rectangle[] obstacleArray = new Rectangle[totalObstacles]; 


    /**
     * Create the game's scene
     */
    public Scene init (Stage s, int width, int height) {
        // create a scene graph to organize the scene
        myRoot = new Group();
        // make some shapes and set their properties
        createObstacleArray();
        
        myPlayer = new Rectangle(0,0,BOX_SIZE,BOX_SIZE);
        myPlayer.setTranslateX(0);
        myPlayer.setTranslateY(0);
        myPlayer.setFill(Color.RED);
        myPlayerVelocity = new Point2D(0, 0);
        
        myRoot.getChildren().add(myPlayer);

        // create a place to display the shapes and react to input
        //LILA can i move myScene.setOnKeyPressed to updateSprites
        myScene = new Scene(myRoot, width, height, Color.WHITE);
        myScene.setOnKeyPressed(e -> handleKeyInput(e));
        return myScene;
    }
    
    // first coordinate are the coordinates of the exit.
    public Rectangle[] createObstacleArray(){ //how to extend given rectangle class to give it a setColor (setfill?) LILA
    	for (int i = 0; i < totalObstacles; i++){
    		if (i == 0){
    			obstacleArray[i] = new Rectangle(obstacleXCoordinates[i],obstacleYCoordinates[i],BOX_SIZE,BOX_SIZE);
     			obstacleArray[i].setFill(Color.BLACK);
    		}
    		else if (i > 0 && i < obstacleXCoordinates.length){
		     	obstacleArray[i] = new Rectangle(obstacleXCoordinates[i],obstacleYCoordinates[i],BOX_SIZE,BOX_SIZE);
	     		obstacleArray[i].setFill(Color.GREY);
	     	}
	     	else{ //i == 0  or i >= obstacleXCoordinates.length
	     		obstacleArray[i] = new Rectangle(fakeObstacleXCoord[i-obstacleXCoordinates.length],fakeObstacleYCoord[i-obstacleXCoordinates.length],BOX_SIZE,BOX_SIZE); //this i here should start at i. 
	     		obstacleArray[i].setFill(Color.GREY);
	     	}
	        myRoot.getChildren().add(obstacleArray[i]);   		        	
	    }	   
    	return obstacleArray;
    }
    
    /**
     * Create the game's frame
     */
    public KeyFrame start (int frameRate) {
        return new KeyFrame(Duration.millis(1000 / frameRate), e -> updateSprites());
    }

    /**
     * What to do each game frame
     *
     * Change the sprite properties each frame by a tiny amount to animate them
     *
     * Note, there are more sophisticated ways to animate shapes, but these simple ways work too.
     */
    private void updateSprites () {
        for (int i = 0; i < obstacleXCoordinates.length; i ++){
        	System.out.println(myPlayerVelocity);
        	if (checkCollide(myPlayer, obstacleArray[i]) == true){ //true means there is a collision
        		System.out.print("stop ");
        		myPlayerVelocity = new Point2D(0,0);
        		myPlayer.setTranslateX(myPlayer.getTranslateX());
        		break;
        	}
        	else{
        		myPlayerVelocity = new Point2D(0.2,0.2);
        		myPlayer.setTranslateX(myPlayer.getTranslateX() + myPlayerVelocity.getX());
        	}
    	}		
    }

    /**
     * What to do each time a key is pressed
     */
    private void handleKeyInput (KeyEvent e) {
        KeyCode keyCode = e.getCode();
        //would a switch be better here? 
        if (keyCode == KeyCode.RIGHT) { // myPlayerVelocity == new Point2D(0,0)
//        	playerDirection = "right";
        	myPlayer.setTranslateX(myPlayer.getTranslateX() + PLAYER_SPEED);
        }
        else if (keyCode == KeyCode.LEFT) {
        	myPlayer.setTranslateX(myPlayer.getTranslateX() - PLAYER_SPEED);
        }
        else if (keyCode == KeyCode.UP) {
        	myPlayer.setTranslateY(myPlayer.getTranslateY() - PLAYER_SPEED);
        }
        else if (keyCode == KeyCode.DOWN) {
        	myPlayer.setTranslateY(myPlayer.getTranslateY() + PLAYER_SPEED);
        }
        else if (keyCode == KeyCode.R){
        	// reset the game
        }
        else if (keyCode == KeyCode.H){ 
        	for (int i = 0; i < obstacleXCoordinates.length; i ++){
	        	obstacleArray[i].setFill(Color.ORANGE);
        	}
        }
    }

    /**
     * What to do each time shapes collide
     */
    private boolean checkCollide(Node player, Node obstacles){ //how to make obstables an object? 
    	if (player.getBoundsInParent().intersects(obstacles.getBoundsInParent())){
    		System.out.println("Collide!");
    		return true;
    	}
    	return false; 
    }
}
