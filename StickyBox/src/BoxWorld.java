import java.util.Arrays;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * This represents the primary class for a game/animation.
 *
 * @author Callie Mao
 */
class BoxWorld {
	private static final int PLAYER_SPEED = 10;
    protected static final int OBSTACLE_SIZE = 40;
    protected static final int NUM_FRAMES_PER_SECOND = 60;
    
	protected static final int RIGHT = 1;
	protected static final int LEFT = -1;
	protected static final int UP = 1;
	protected static final int DOWN = -1;
    
  //  protected int[] xCoords; 
  //  protected int[] yCoords;
    private Scene myScene;
    protected Group myRoot; 
    protected StickyBox myPlayer;
 //   private Rectangle[] obstacleArray;
    private ImageView[] obstacleArray;
    private ImageView myExit;
    protected int[] obstacleXCoordinates;
    protected int[] obstacleYCoordinates; 
    private ItemBoxWorld myNextLevel;
    protected Stage myStage;
    
    protected int myWidth;
    protected int myHeight;
   
    //coordinates of the final exit
    private static final int EXIT_COORDINATES = 320;
    //the number of obstacles needed for the potential solution given in the hint (by pressing 'h')
    private static final int NUM_REAL_OBSTACLES = 7;
    //coordinates for obstacles in the puzzle. the first NUM_REAL_OBSTACLES of the coordinates will be revealed in the hint and are the solution to the puzzle. 
   // private static final int[] obstacleXCoordinates = {640,600,160,200,40, 80, 360,200,720,0, 560, 720,480,80,640,EXIT_COORDINATES}; 
    //private static final int[] obstacleYCoordinates = {0, 240, 200,480,440,640,600,80, 160,320,480,560,120,40,640,EXIT_COORDINATES};
    
    /**
     * Create the game's scene
     */
    public Scene init (Stage s, int width, int height) {
        //set obstacle coordinates for the level
    	
     //   setObstacleCoordinates(xCoords, yCoords);
    	myStage = s;
    	myWidth = width;
    	myHeight = height;
    	obstacleXCoordinates = new int[]{640,600,160,200,40, 80, 360,200,720,0, 560, 720,480,80,640};
    	obstacleYCoordinates = new int[]{0, 240, 200,480,440,640,600,80, 160,320,480,560,120,40,640};

    	myRoot = new Group();
    	addStarterObjects();
    	return createScene(width, height);  
    }
    
    protected Scene createScene(int width, int height){
    	myScene = new Scene(myRoot, width, height, Color.BLACK);
        myScene.setOnKeyPressed(e -> handleKeyInput(e));
        myScene.setOnKeyReleased(e -> handleKeyRelease(e));
        return myScene;
    }
    
    //
 /*   for use by methods that extend this in order to change the map
    protected void setObstacleCoordinates(int[] xCoords,int[] yCoords){
    	obstacleXCoordinates = xCoords;
    	obstacleYCoordinates = yCoords;
    }*/
    
   /* public void addObstaclesToRoot(int[] xCoords, int[] yCoords){
    	myRoot.getChildren().add(myPlayer);
    	obstacleArray = new Rectangle[obstacleXCoordinates.length]; //LILA is this inefficient?
        createObstacleArray(obstacleXCoordinates, obstacleYCoordinates);
    }*/
    
    protected void addStarterObjects(){
        
        // create the player (Sticky Box)        
        myPlayer = new StickyBox(0,0,OBSTACLE_SIZE,OBSTACLE_SIZE, PLAYER_SPEED,0,0, false);
        myPlayer.setFill(Color.RED);        
        myRoot.getChildren().add(myPlayer);
        System.out.println("player created");

        //create the obstacles
        obstacleArray = new ImageView[obstacleXCoordinates.length];
        createObstacleArray(obstacleXCoordinates, obstacleYCoordinates);

        //create the final exit
        //myExit = new Rectangle(EXIT_COORDINATES,EXIT_COORDINATES,OBSTACLE_SIZE,OBSTACLE_SIZE);
        myExit = new ImageView(new Image(getClass().getResourceAsStream("images/finish.png")));
        myExit.setFitWidth(OBSTACLE_SIZE);
        myExit.setFitHeight(OBSTACLE_SIZE);
        myExit.setX(EXIT_COORDINATES);
        myExit.setY(EXIT_COORDINATES);
        
        //myExit.setFill(Color.WHITE); //set the exit spot to a special color
    	Text finish = new Text(EXIT_COORDINATES+8,EXIT_COORDINATES+25,"EXIT");
        myRoot.getChildren().add(myExit);
        myRoot.getChildren().add(finish);

    }
    
    //use the given rectangle coordinates to create an array of obstacles (Rectangle objects)  
    public void createObstacleArray(int[] xCoord, int[] yCoord){ 
    	for (int i = 0; i < xCoord.length; i++){
    		//obstacleArray[i] = new Rectangle(xCoord[i],yCoord[i],OBSTACLE_SIZE,OBSTACLE_SIZE);
    		obstacleArray[i] = new ImageView(new Image(getClass().getResourceAsStream("images/rock.png")));
    		obstacleArray[i].setX(xCoord[i]); //make this into an item
    		obstacleArray[i].setY(yCoord[i]);
            obstacleArray[i].setFitWidth(OBSTACLE_SIZE);


     		//obstacleArray[i].setFill(Color.GREY);
	        myRoot.getChildren().add(obstacleArray[i]);   		
	    }	   
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
    //how to make it private LILA
    public void updateSprites() {
		//if the player has just collided with an obstacle but was not stuck to it in the previous frame
    	if (checkAllCollisions() && !myPlayer.isStuck()){
    		myPlayer.setStuck(true);
    		myPlayer.setPreviousDirection(myPlayer.getXDirection(),myPlayer.getYDirection());
    		myPlayer.setDirection(0, 0);
    	}
    	//if player is no longer in contact with another object but was stuck to it in the previous frame
    	if (!checkAllCollisions() && myPlayer.isStuck()){
    		myPlayer.setStuck(false);
    	}
    	myPlayer.move();
    }

/*
     	//if player is still in contact with another object and was stuck to it in the previous frame
//    	if (checkAllCollisions() && myPlayer.isStuck()){
 //   		myPlayer.move();
 //   	}
    	//if player is no longer in contact with another object but was stuck to it in the previous frame
    	if (!checkAllCollisions() && myPlayer.isStuck()){
    		myPlayer.setStuck(false);
 //   		myPlayer.move();
    	}
 //   	else
    	
    	myPlayer.move();*/
    
    protected boolean checkAllCollisions(){
	    boolean hasCollided = true;
	    //check if the exit has been reached 
	    if (checkCollide(myExit)){
	    	//you won the game screen; LILA
	    	System.out.println("you won!");
	    	winLevel();
	    }
	    
	    //iterate through all obstacles and check if player has collided with any of them. return true if so and false otherwise
	    for (int i = 0; i < obstacleXCoordinates.length; i ++){
	    //	if (checkCollide(myPlayer, obstacleArray[i])){ //LILA erase the bad one 
	    	if (checkCollide(obstacleArray[i])){
	        	hasCollided = true;
	    		break;
	    	}
	    	else{ 
	    		hasCollided = false;
	    	}
		}
		return hasCollided;
    }

    protected void winLevel(){
      myNextLevel = new ItemBoxWorld();
	  Scene scene = myNextLevel.init(myStage, myWidth, myHeight);
	  myStage.setScene(scene);
	  myStage.show();

	  // setup the game's loop
	  KeyFrame frame = myNextLevel.start(NUM_FRAMES_PER_SECOND);
	  Timeline animation = new Timeline();
	  animation.setCycleCount(Animation.INDEFINITE);
	  animation.getKeyFrames().add(frame);
	  animation.play();
      System.out.println("you won!");
    }
    
    /**
     * Checks to see if myPlayer has collided with a specific obstacle based on 
     * myPlayer's movement direction and its x-coordinates and y-coordinates from the object
     * returns true if player has collided with obstacle 
     */
 
    //want to make rectangle Node but cannot because javafx not robust enough
    protected boolean checkCollide(ImageView obstacle){  
    	if (myPlayer.getXDirection() == RIGHT && myPlayer.getYDirection() == 0 
    			&& myPlayer.getTranslateX() + OBSTACLE_SIZE == obstacle.getX()
    			&& myPlayer.getTranslateY() == obstacle.getY())
    		return true;
    	else if (myPlayer.getXDirection() == LEFT && myPlayer.getYDirection() == 0 
    			&& myPlayer.getTranslateX() - OBSTACLE_SIZE == obstacle.getX()
    			&& myPlayer.getTranslateY() == obstacle.getY())
    		return true;
    	else if (myPlayer.getXDirection() == 0 && myPlayer.getYDirection() == UP 
    			&& myPlayer.getTranslateY() - OBSTACLE_SIZE == obstacle.getY()
    			&& myPlayer.getTranslateX() == obstacle.getX())
    		return true;
    	else if (myPlayer.getXDirection() == 0 && myPlayer.getYDirection() == DOWN 
    			&& myPlayer.getTranslateY() + OBSTACLE_SIZE == obstacle.getY()
    			&& myPlayer.getTranslateX() == obstacle.getX())
    		return true;
    	else
    		return false;
    }

   // public boolean registerCollision (int xDirect, int yDirect,  , )
    //plane would be which plane it is moving in: x, y, or z plane
  /*  public boolean registerCollision (int xDirect, int yDirect, char plane){
    	if (plane = 'x'){
    		if (myPlayer.getXDirection() == xDirect && myPlayer.getYDirection() == yDirect 
	    			&& myPlayer.getTranslateX() + OBSTACLE_SIZE == obstacle.getX()
	    			&& myPlayer.getTranslateY() == obstacle.getY()){
    			return true;
    	}
    	if (plane = 'y'){
    		if (myPlayer.getXDirection() == xDirect && myPlayer.getYDirection() == yDirect 
        			&& myPlayer.getTranslateY() + OBSTACLE_SIZE == obstacle.getY()
        			&& myPlayer.getTranslateX() == obstacle.getX())
    			return y;
    		}
    	}
    	
    }*/
    
    
   
    //delete if above works LILA
	
	//this method does not work well since even a pixel of collision
    //(diagonals colliding with diagonals) will register
/*
    private boolean checkCollide(Node player, Node obstacle){  
    	if (player.getBoundsInParent().intersects(obstacle.getBoundsInParent()))
    		return true;
    	return false;
    }
    */
    /**
     * What to do each time a key is pressed
     */
    protected void handleKeyInput (KeyEvent e) {
        KeyCode keyCode = e.getCode();
        if (keyCode == KeyCode.RIGHT && allowMovement(1,0)) {
        	myPlayer.setDirection(1,0);
        }
        else if (keyCode == KeyCode.LEFT && allowMovement(-1,0)) {
        	myPlayer.setDirection(-1,0);
        }
        else if (keyCode == KeyCode.UP && allowMovement(0,1)) {
        	myPlayer.setDirection(0,1);
        }
        else if (keyCode == KeyCode.DOWN && allowMovement(0,-1)) {
        	myPlayer.setDirection(0,-1);
        }
        else if (keyCode == KeyCode.R){
        	reset();
        }
        else if (keyCode == KeyCode.H){ 
        	for (int i = 0; i < NUM_REAL_OBSTACLES; i ++){
	        	obstacleArray[i].setImage(new Image(getClass().getResourceAsStream("images/star.png")));
	            obstacleArray[i].setFitHeight(OBSTACLE_SIZE);

        	}
        }
    }
    
    protected void reset(){
    	myPlayer.setDirection(0,0);
    	myPlayer.setPreviousDirection(0,0);
    	myPlayer.setTranslateX(0);
    	myPlayer.setTranslateY(0);
    }
    
    //check if player is currently moving 
    //return true if player is not moving and if it previous was not travellin
    private boolean allowMovement(int x, int y){
    	if (x == 0)
    		return myPlayer.getYDirection() == 0 && myPlayer.getXDirection() == 0 && myPlayer.getYPrev() != y;
    	if (y == 0)
    		return myPlayer.getYDirection() == 0 && myPlayer.getXDirection() == 0 && myPlayer.getXPrev() != x;
    	else
    		return false;
    }
    
    protected void handleKeyRelease (KeyEvent e) {
    	KeyCode keyCode = e.getCode();
    	if (keyCode == KeyCode.H){
    		for (int i = 0; i < NUM_REAL_OBSTACLES; i ++){
	        	obstacleArray[i].setImage(new Image(getClass().getResourceAsStream("images/rock.png")));
	            obstacleArray[i].setFitWidth(OBSTACLE_SIZE);

	    	}
	    }
    }   
    
  
    /*You Lost! play again?
Box will slide off the screen since it has nothing else to stick to so make sure to do a boundary check*/

    /* add onto the readme to lead it back to it's final resting spot at a hole. or find a flag gif*/
    
}
