import javafx.scene.shape.Rectangle;


/**
 * This is the class that defines the properties of StickyBox
 *
 * @author Callie Mao
 */
public class StickyBox extends Rectangle {
    private int xMove;
    private int yMove;
    private int mySpeed;
    private int xPrev; // previous x-direction
    private int yPrev; // previous y-direction

    public StickyBox (int xPos, int yPos, int xSize, int ySize, int speed,
                      int movingXDirect, int movingYDirect) {
        super(xPos, yPos, xSize, ySize);
        mySpeed = speed;
        xMove = movingXDirect;
        yMove = movingYDirect;

        xPrev = 0;
        yPrev = 0;
    }

    public void setDirection (int x, int y) {
        xMove = x;
        yMove = y;
    }

    public void setPreviousDirection (int x, int y) {
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

    public void move () {
        if (xMove == 0 && yMove == 0) {
            return;
        }
        else if (xMove == 0 && yMove > 0) {
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

    public int getSpeed () {
        return mySpeed;
    }

}
