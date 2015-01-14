import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;


public class ItemBoxWorld extends BoxWorld {
	
	private Text myWarning;
	private int itemCounter;
	private ImageView myItem1;
	private ImageView myItem2;
	private Stage myStage;
	
	private static final int NUM_OF_ITEMS = 2;
	
    @Override
    public Scene init (Stage s, int width, int height) {
    	myStage = s;
    	obstacleXCoordinates = new int[]{0, 120, 80, 320,280,520,480,440,520,480,280,200,240,520}; 
    	obstacleYCoordinates = new int[]{240,200,480,440,40, 80, 360, 40,640,520,600,560,280,200};
    	
    	myWidth = width;
    	myHeight = height;
     
    	myRoot = new Group();
      	addStarterObjects();
      	addAllItems();  
        return createScene(width, height);
    }
    
    private void addAllItems(){
    	System.out.println("222");
      	itemCounter = NUM_OF_ITEMS;
      	if (!itemExists(myItem1)){
      		myItem1 = addSingleItem(280,320);	
      	}
    	if (!itemExists(myItem2)){
    		myItem2 = addSingleItem(480,300);
    	}
    }
    
    private ImageView addSingleItem(int xCoord, int yCoord){
    	ImageView item = new ImageView(new Image(getClass().getResourceAsStream("images/coin.png")));
		item.setX(xCoord); //make this into an item
		item.setY(yCoord);
        item.setFitWidth(OBSTACLE_SIZE);
        myRoot.getChildren().add(item);
        return item;
    	
    }

	protected boolean checkAllCollisions(){
    	if (itemExists(myItem1) && checkCollide(myItem1)){
    		myRoot.getChildren().remove(myItem1);
    		itemCounter--;
    	}
    	if (itemExists(myItem2) && checkCollide(myItem2)){
    		myRoot.getChildren().remove(myItem2);
    		itemCounter --;
    	}
    	return super.checkAllCollisions();
    }
	
	private boolean itemExists(Node item){
		
		for (Node node: myRoot.getChildren()){
			if (node == item){
				return true;
			}
		}
		return false;
	}
	
	 protected void winLevel(){
		 if(itemCounter == 0){
		      System.out.println("end game");
			  EndScreen endGame = new EndScreen();
			  
			  Scene scene = endGame.init(myStage, myWidth, myHeight);
			  myStage.setScene(scene);
			  myStage.show();	
	    }
		 else{
			 myWarning = new Text("You must collect all items first before you can exit!");
			 myWarning.setX(200);
			 myWarning.setY(200);
			 myWarning.setFill(Color.WHITE);
			 myRoot.getChildren().add(myWarning);
		 }	 
	 }
	
	 protected void reset(){
		 super.reset();
		 addAllItems(); 
		 
	 }
}
    

    
    


