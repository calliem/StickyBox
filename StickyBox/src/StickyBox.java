// This entire file is part of my masterpiece.
// Callie Mao

import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;


/**
 * This is the class that defines the properties of StickyBox
 *
 * @author Callie Mao
 */
public class StickyBox extends Rectangle {
    private int xMove; // specifies current x-direction of movement
    private int yMove; // specifies current y-direction of movement
    private int mySpeed;
    private int xPrev; // previous x-direction travelled
    private int yPrev; // previous y-direction travelled

    private static final int RIGHT = 1;
    private static final int LEFT = -1;
    private static final int UP = 1;
    private static final int DOWN = -1;

    public StickyBox (int xPos, int yPos, int xSize, int ySize, int speed,
                      int movingXDirect, int movingYDirect) {
        super(xPos, yPos, xSize, ySize);
        mySpeed = speed;
        xMove = movingXDirect;
        yMove = movingYDirect;

        xPrev = 0;
        yPrev = 0;
    }

    public void move () {
        if (xMove == 0 && yMove > 0) {
            setTranslateY(getTranslateY() - mySpeed);
        }
        else if (xMove == 0 && yMove < 0) {
            setTranslateY(getTranslateY() + mySpeed);
        }
        else if (yMove == 0 && xMove > 0) {
            setTranslateX(getTranslateX() + mySpeed);
        }
        else if (yMove == 0 && xMove < 0) {
            setTranslateX(getTranslateX() - mySpeed);
        }
    }

    /**
     * Checks if myPlayer has collided with a specific obstacle based on
     * myPlayer's movement direction and its x-coordinates and y-coordinates
     * relative to the obstacle's
     *
     * @return true if player has collided with obstacle and false otherwise
     */
    public boolean checkCollide (ImageView obstacle) {
        if (checkCollideConditions(obstacle, RIGHT, 0) ||
            checkCollideConditions(obstacle, LEFT, 0) || checkCollideConditions(obstacle, 0, UP) ||
            checkCollideConditions(obstacle, 0, DOWN)) { return true; }
        return false;
    }

    /**
     * Check if collision conditions have been met. Conditions check to see if StickyBox has touched
     * the object
     * and if StickyBox's x- and y-coordinates are aligned with the object's depending on what
     * direction
     * StickyBox is travelling in
     *
     * @param obstacle is the object that is being checked to see if StickyBox has collided with
     * @param xDirect is the x-direction that is being checked to see if StickyBox is travelling in
     * @param yDirect is the y-direction that is being checked to see if StickyBox is travelling in
     * @return true if conditions are met, false if otherwise
     */
    private boolean checkCollideConditions (ImageView obstacle, int xDirect, int yDirect) {
        if (yMove == 0) { return xMove == xDirect &&
                                 getTranslateX() + xDirect * getWidth() == obstacle.getX() &&
                                 getTranslateY() == obstacle.getY(); }
        if (xMove == 0) { return yMove == yDirect &&
                                 getTranslateY() - yDirect * getHeight() == obstacle.getY() &&
                                 getTranslateX() == obstacle.getX(); }
        return false;
    }

    /**
     * Stops StickyBox so that it will no longer move
     *
     */
    public void stop () {
        setPreviousDirection(xMove, yMove);
        setDirection(0, 0);
    }

    /**
     * Resets the level by returning StickyBox back to its initial location in
     * the top left corner
     */
    public void resetLocation () {
        setDirection(0, 0);
        setPreviousDirection(0, 0);
        setTranslateX(0);
        setTranslateY(0);
    }

    public void setDirection (int x, int y) {
        xMove = x;
        yMove = y;
    }

    private void setPreviousDirection (int x, int y) {
        xPrev = x;
        yPrev = y;
    }

    public int getXPrev () {
        return xPrev;
    }

    public int getYPrev () {
        return yPrev;
    }

    public int getXDirection () {
        return xMove;
    }

    public int getYDirection () {
        return yMove;
    }

    public int getSpeed () {
        return mySpeed;
    }

}
