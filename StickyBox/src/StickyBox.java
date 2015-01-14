import javafx.animation.KeyFrame;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class StickyBox extends Rectangle{
	protected int xMove;
	protected int yMove; 
	protected int mySpeed;
	protected boolean isStuck;
	protected int xPrev; //previous x-direction
	protected int yPrev; //previous y-direction
	
	public StickyBox(int xPos, int yPos, int xSize, int ySize, 
			int speed, int movingXDirect, int movingYDirect, boolean stuck){ 
		super(xPos, yPos, xSize, ySize);
		mySpeed = speed;
		xMove = movingXDirect;
		yMove = movingYDirect;
		isStuck = stuck;
		
		xPrev = 0;
		yPrev = 0;
	}

	public boolean isStuck(){
		return isStuck;
	}

	public void setStuck(boolean stuck){
		isStuck = stuck;
	}
	
	public void setDirection(int x, int y){
		xMove = x;
		yMove = y;
	}
	
	public void setPreviousDirection(int x, int y){
		xPrev = x;
		yPrev = y;
	}
	
	public int getXPrev(){
		return xPrev;
	}
	
	public int getYPrev(){
		return yPrev;
	} 
		
	public int getXDirection(){
		return xMove;
	}
	
	public int getYDirection(){
		return yMove;
	}
	
	public void move(){
		if (xMove == 0 && yMove == 0)
			return; 
		else if (xMove == 0 && yMove > 0)
    		this.setTranslateY(this.getTranslateY() - mySpeed);
		else if (xMove == 0 && yMove < 0)
    		this.setTranslateY(this.getTranslateY() + mySpeed);
		else if (yMove == 0 && xMove > 0)
    		this.setTranslateX(this.getTranslateX() + mySpeed);
		else if (yMove == 0 && xMove < 0)
    		this.setTranslateX(this.getTranslateX() - mySpeed);
	}
	
	public int getSpeed(){
		return mySpeed;
	}
	

}
