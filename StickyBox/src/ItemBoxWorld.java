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
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * This is the a class for a StickyBox level with items
 *
 * @author Callie Mao
 */
public class ItemBoxWorld extends BoxWorld {

    private Text myWarning;
    private int itemCounter;
    private ImageView myItem1;
    private ImageView myItem2;

    private static final int NUM_OF_ITEMS = 2;

    @Override
    public Scene init (Stage s, int width, int height) {

        setStage(s);
        setObstacleXCoordinates(new int[] { 0, 120, 80, 320, 280, 520, 480,
                                           440, 520, 480, 280, 200, 240, 520 });
        setObstacleYCoordinates(new int[] { 240, 200, 480, 440, 40, 80, 360,
                                           40, 640, 520, 600, 560, 280, 200 });

        myWidth = width;
        myHeight = height;

        setRoot(new Group());
        addStarterObjects();
        addAllItems();

        KeyFrame frame = start(NUM_FRAMES_PER_SECOND);
        setAnimation(new Timeline());
        // myAnimation = new Timeline();
        getAnimation().setCycleCount(Animation.INDEFINITE);
        getAnimation().getKeyFrames().add(frame);
        getAnimation().play();
        return createScene(width, height);
    }

    /**
     * Puts all items onto the game screen
     */
    private void addAllItems () {
        itemCounter = NUM_OF_ITEMS;
        if (!itemExists(myItem1)) {
            myItem1 = addSingleItem(280, 320);
        }
        if (!itemExists(myItem2)) {
            myItem2 = addSingleItem(480, 300);
        }
    }

    private ImageView addSingleItem (int xCoord, int yCoord) {
        ImageView item = new ImageView(new Image(getClass()
                .getResourceAsStream("images/coin.png")));
        item.setX(xCoord);
        item.setY(yCoord);
        item.setFitWidth(OBSTACLE_SIZE);
        getRoot().getChildren().add(item);
        return item;
    }

    /**
     * Reduces item counter if StickyBox and removes item from game screen if
     * StickyBox collides with it
     */
    @Override
    protected boolean checkAllCollisions () {
        if (itemExists(myItem1) && checkCollide(myItem1)) {
            getRoot().getChildren().remove(myItem1);
            itemCounter--;
        }
        if (itemExists(myItem2) && checkCollide(myItem2)) {
            getRoot().getChildren().remove(myItem2);
            itemCounter--;
        }
        return super.checkAllCollisions();
    }

    /**
     * @return true if item is located on the game screen
     */
    private boolean itemExists (Node item) {

        for (Node node : getRoot().getChildren()) {
            if (node == item) { return true; }
        }
        return false;
    }

    @Override
    protected void winLevel () {
        if (itemCounter == 0) {
            getAnimation().stop();
            EndScreen endGame = new EndScreen();

            Scene scene = endGame.init(getStage(), myWidth, myHeight);
            getStage().setScene(scene);
            getStage().show();
        }
        else {
            if (!itemExists(myWarning)) {
                myWarning = new Text(
                                     "You must collect all items first before you can exit!");
                myWarning.setX(200);
                myWarning.setY(200);
                myWarning.setFill(Color.WHITE);
                getRoot().getChildren().add(myWarning);
            }
        }
    }

    @Override
    protected void handleKeyInput (KeyEvent e) {
        if (e.getCode() == KeyCode.S) {
            itemCounter = 0;
            winLevel();
        }
        super.handleKeyInput(e);
    }

    @Override
    protected void reset () {
        super.reset();
        addAllItems();

    }
}
