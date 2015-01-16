import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * This is the primary level class for StickyBox.
 *
 * @author Callie Mao
 */
class BoxWorld {
    private static final int PLAYER_SPEED = 10;
    protected static final int OBSTACLE_SIZE = 40;
    protected static final int NUM_FRAMES_PER_SECOND = 60;

    private static final int RIGHT = 1;
    private static final int LEFT = -1;
    private static final int UP = 1;
    private static final int DOWN = -1;

    private Scene myScene;
    private Group myRoot;
    private ImageView myExit;
    private Timeline myAnimation;
    private Stage myStage;
    private StickyBox myPlayer;

    private int[] obstacleXCoordinates = { 640, 600, 160, 200, 40, 80, 360,
                                          200, 720, 0, 560, 720, 480, 80, 640 };
    private int[] obstacleYCoordinates = { 0, 240, 200, 480, 440, 640, 600, 80,
                                          160, 320, 480, 560, 120, 40, 640 };
    private ImageView[] obstacleArray;

    protected int myWidth;
    protected int myHeight;

    // coordinates of the final exit
    private static final int EXIT_COORDINATES = 320;
    // the number of obstacles revealed in the hint
    private static final int NUM_HINT_OBSTACLES = 7;

    /**
     * Create the game's scene
     */
    public Scene init (Stage s, int width, int height) {

        myStage = s;
        myWidth = width;
        myHeight = height;

        myRoot = new Group();
        addStarterObjects();

        KeyFrame frame = start(NUM_FRAMES_PER_SECOND);
        myAnimation = new Timeline();
        myAnimation.setCycleCount(Animation.INDEFINITE);
        myAnimation.getKeyFrames().add(frame);
        myAnimation.play();

        return createScene(width, height);
    }

    protected Scene createScene (int width, int height) {
        myScene = new Scene(myRoot, width, height, Color.BLACK);
        myScene.setOnKeyPressed(e -> handleKeyInput(e));
        myScene.setOnKeyReleased(e -> handleKeyRelease(e));
        return myScene;
    }

    /**
     * Adds sticky box, obstacles, and the final exit to the game screen
     *
     */
    protected void addStarterObjects () {

        // create the player (Sticky Box)
        myPlayer = new StickyBox(0, 0, OBSTACLE_SIZE, OBSTACLE_SIZE,
                                 PLAYER_SPEED, 0, 0, false);
        myPlayer.setFill(Color.WHITE);
        myRoot.getChildren().add(myPlayer);

        // create the obstacles
        obstacleArray = new ImageView[obstacleXCoordinates.length];
        createObstacleArray(obstacleXCoordinates, obstacleYCoordinates);

        // create the final exit
        myExit = new ImageView(new Image(getClass().getResourceAsStream("images/finish.png")));
        myExit.setFitWidth(OBSTACLE_SIZE);
        myExit.setFitHeight(OBSTACLE_SIZE);
        myExit.setX(EXIT_COORDINATES);
        myExit.setY(EXIT_COORDINATES);
        myRoot.getChildren().add(myExit);
    }

    /**
     * Uses the given x-coordinates and y-coordinates to construct and place
     * obstacles onto the game screen
     *
     */
    public void createObstacleArray (int[] xCoord, int[] yCoord) {
        for (int i = 0; i < xCoord.length; i++) {
            obstacleArray[i] = new ImageView(new Image(getClass()
                    .getResourceAsStream("images/rock.png")));
            obstacleArray[i].setX(xCoord[i]); // make this into an item
            obstacleArray[i].setY(yCoord[i]);
            obstacleArray[i].setFitWidth(OBSTACLE_SIZE);
            myRoot.getChildren().add(obstacleArray[i]);
        }
    }

    /**
     * Create the game's frame
     */
    public KeyFrame start (int frameRate) {
        return new KeyFrame(Duration.millis(1000 / frameRate),
                            e -> updateSprites());
    }

    /**
     * What to do each game frame: checks for collisions and moves the player
     *
     */
    public void updateSprites () {
        if (checkAllCollisions() && !myPlayer.isStuck()) {
            myPlayer.setStuck(true);
            myPlayer.setPreviousDirection(myPlayer.getXDirection(),
                                          myPlayer.getYDirection());
            myPlayer.setDirection(0, 0);
        }
        if (!checkAllCollisions() && myPlayer.isStuck()) {
            myPlayer.setStuck(false);
        }
        myPlayer.move();
    }

    /**
     * Checks if the passed in object parameter is within the game screen's
     * bounds
     *
     * @return true if object is on the screen and false if otherwise
     */
    public boolean onScreen (Node object) {
        return object.getTranslateX() <= myScene.getWidth()
               && object.getTranslateY() <= myScene.getHeight()
               && object.getTranslateX() >= 0 && object.getTranslateY() >= 0;
    }

    /**
     * Iterates through all obstacles and checks if the player has collided with
     * any. Advances to win screen if player collides with the exit and to the
     * lose screen if player is not one screen
     *
     * @return true if player has collided with any other obstacles and false
     *         otherwise
     */
    protected boolean checkAllCollisions () {
        if (!onScreen(myPlayer)) {
            loseGame();
        }
        if (checkCollide(myExit)) {
            winLevel();
        }
        for (int i = 0; i < obstacleXCoordinates.length; i++) {
            if (checkCollide(obstacleArray[i])) { return true; }
        }
        return false;
    }

    protected void winLevel () {
        myAnimation.stop();
        ItemBoxWorld nextLevel = new ItemBoxWorld();
        Scene scene = nextLevel.init(myStage, myWidth, myHeight);
        myStage.setScene(scene);
        myStage.show();
        System.out.println("you won");
    }

    protected void loseGame () {
        myAnimation.stop();
        LoseScreen lose = new LoseScreen();
        Scene scene = lose.init(myStage, myWidth, myHeight);
        myStage.setScene(scene);
        myStage.show();
    }

    /**
     * Checks if myPlayer has collided with a specific obstacle based on
     * myPlayer's movement direction and its x-coordinates and y-coordinates
     * relative to the obstacle's
     *
     * @return true if player has collided with obstacle and false otherwise
     */

    protected boolean checkCollide (ImageView obstacle) {

        if (myPlayer.getXDirection() == RIGHT && myPlayer.getYDirection() == 0
            && myPlayer.getTranslateX() + OBSTACLE_SIZE == obstacle.getX()
            && myPlayer.getTranslateY() == obstacle.getY()) {
            return true;
        }
        else if (myPlayer.getXDirection() == LEFT
                 && myPlayer.getYDirection() == 0
                 && myPlayer.getTranslateX() - OBSTACLE_SIZE == obstacle.getX()
                 && myPlayer.getTranslateY() == obstacle.getY()) {
            return true;
        }
        else if (myPlayer.getXDirection() == 0
                 && myPlayer.getYDirection() == UP
                 && myPlayer.getTranslateY() - OBSTACLE_SIZE == obstacle.getY()
                 && myPlayer.getTranslateX() == obstacle.getX()) {
            return true;
        }
        else if (myPlayer.getXDirection() == 0
                 && myPlayer.getYDirection() == DOWN
                 && myPlayer.getTranslateY() + OBSTACLE_SIZE == obstacle.getY()
                 && myPlayer.getTranslateX() == obstacle.getX()) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * What to do each time a key is pressed
     */
    protected void handleKeyInput (KeyEvent e) {
        KeyCode keyCode = e.getCode();
        if (keyCode == KeyCode.RIGHT && allowMovement(RIGHT, 0)) {
            myPlayer.setDirection(RIGHT, 0);
        }
        else if (keyCode == KeyCode.LEFT && allowMovement(LEFT, 0)) {
            myPlayer.setDirection(LEFT, 0);
        }
        else if (keyCode == KeyCode.UP && allowMovement(0, UP)) {
            myPlayer.setDirection(0, UP);
        }
        else if (keyCode == KeyCode.DOWN && allowMovement(0, DOWN)) {
            myPlayer.setDirection(0, DOWN);
        }
        else if (keyCode == KeyCode.R) {
            reset();
        }
        else if (keyCode == KeyCode.S) {
            winLevel();
        }
        else if (keyCode == KeyCode.H) {
            for (int i = 0; i < NUM_HINT_OBSTACLES; i++) {
                obstacleArray[i].setImage(new Image(getClass()
                        .getResourceAsStream("images/star.png")));
                obstacleArray[i].setFitHeight(OBSTACLE_SIZE);
            }
        }
    }

    /**
     *
     * Resets the level by returning StickyBox back to its initial location in
     * the top left corner
     */

    protected void reset () {
        myPlayer.setDirection(0, 0);
        myPlayer.setPreviousDirection(0, 0);
        myPlayer.setTranslateX(0);
        myPlayer.setTranslateY(0);
    }

    /**
     * Checks if Sticky Box can respond to a specific user input.
     *
     * @return true when Sticky Box is not moving and if the current direction
     *         input is different from that of the previous input (to ensure the
     *         box does not go through an obstacle); returns false otherwise
     */
    private boolean allowMovement (int x, int y) {
        if (x == 0) { return myPlayer.getYDirection() == 0
                             && myPlayer.getXDirection() == 0
                             && myPlayer.getYPrev() != y; }
        if (y == 0) {
            return myPlayer.getYDirection() == 0
                   && myPlayer.getXDirection() == 0
                   && myPlayer.getXPrev() != x;
        }
        else {
            return false;
        }
    }

    protected void handleKeyRelease (KeyEvent e) {
        KeyCode keyCode = e.getCode();
        if (keyCode == KeyCode.H) {
            for (int i = 0; i < NUM_HINT_OBSTACLES; i++) {
                obstacleArray[i].setImage(new Image(getClass()
                        .getResourceAsStream("images/rock.png")));
                obstacleArray[i].setFitWidth(OBSTACLE_SIZE);
            }
        }
    }

    protected void setRoot (Group group) {
        myRoot = group;
    }

    protected Group getRoot () {
        return myRoot;
    }

    protected void setObstacleYCoordinates (int[] yCoords) {
        obstacleYCoordinates = yCoords;
    }

    protected void setObstacleXCoordinates (int[] xCoords) {
        obstacleXCoordinates = xCoords;
    }

    protected void setStage (Stage s) {
        myStage = s;
    }

    protected Stage getStage () {
        return myStage;
    }

    protected StickyBox getPlayer () {
        return myPlayer;
    }

    protected Timeline getAnimation () {
        return myAnimation;
    }

    protected void setAnimation (Timeline animate) {
        myAnimation = animate;

    }
}
