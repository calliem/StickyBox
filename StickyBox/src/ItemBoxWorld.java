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
	
	private int itemCounter;
	private ImageView myItem1;
	private ImageView myItem2;
	private Stage myStage;
	
	private static final int NUM_OF_ITEMS = 2;
	
    @Override
    public Scene init (Stage s, int width, int height) {
        //set obstacle coordinates for the level
    	myStage = s;
    	obstacleXCoordinates = new int[]{0, 120, 80, 320,280,520,480,440,520,480,280,200,240}; 
    	obstacleYCoordinates = new int[]{240,200,480,440,40, 80, 360, 40,640,520,600,560,280};
    	
    	//don't need these instructions LILA. can add these and then make the first input activate this
    	Text instructions = new Text(40,10,"Collect the items before leading Sticky Box to the exit");
      	instructions.setWrappingWidth(400);
      	instructions.setFill(Color.WHITE);
      	
    	myRoot = new Group();
      	myRoot.getChildren().add(instructions);
      	System.out.println(itemCounter);

      	addStarterObjects();
      	addAllItems(); //itemXcoordinates, itemYcoordinates as parameters LILA 
        return createScene(width, height);
    }
    
    private void addAllItems(){
      	itemCounter = NUM_OF_ITEMS;
      	
      	Rectangle rect = new Rectangle(0,0);
 
      	if (!itemExists(myItem1)){
      		addSingleItem(myItem1,280,320);
      		
      	}
    	if (!itemExists(myItem2)){
    		addSingleItem(myItem2,480,300);
    	}
    }
    
    private void addSingleItem(ImageView item, int xCoord, int yCoord){
    	item = new ImageView(new Image(getClass().getResourceAsStream("images/coin.png")));
		item.setX(xCoord); //make this into an item
		item.setY(yCoord);
        item.setFitWidth(OBSTACLE_SIZE);
        myRoot.getChildren().add(item);
    	
    }

	protected boolean checkAllCollisions(){
		System.out.println(itemCounter);
		/*if (checkCollide(myItem1)){
			System.out.println("collide item 1 first if statement");
			System.out.println(itemExists(myItem1));
		}*/
		System.out.println("itemExists" + itemExists(myItem1));
		//System.out.println("checkCollide" + checkCollide(myItem1));
    	if (itemExists(myItem1) && checkCollide(myItem1)){
    		myRoot.getChildren().remove(myItem1);
    		itemCounter--;
    		System.out.println("collide item 1 second if statement");
    		//most likely it goes through a box and gets stuck
    	}
    	if (itemExists(myItem2) && checkCollide(myItem2)){
    		myRoot.getChildren().remove(myItem2);
    		itemCounter --;
    	}
    	return super.checkAllCollisions();
    }
	
	private boolean itemExists(Node item){
		System.out.println("check starts");
		
		
		for (Node node: myRoot.getChildren()){
			System.out.println("node " + node);
			System.out.println("item " + item);
			if (node == item){
				System.out.println("item still exists!");
				return true;
			}
		}
		return false;
	}
	
	 protected void winLevel(){
		 if(itemCounter == 0){
		    
			  EndScreen endGame = new EndScreen();
			  Scene scene = endGame.init(myStage, myWidth, myHeight);
			  myStage.setScene(scene);
			  myStage.show();
	
			  // setup the game's loop
			/*  KeyFrame frame = endGame.start(NUM_FRAMES_PER_SECOND);
			  Timeline animation = new Timeline();
			  animation.setCycleCount(Animation.INDEFINITE);
			  animation.getKeyFrames().add(frame);
			  animation.play();*/
	    }
		 else{
			 System.out.println("nope");
			 Text warning = new Text("You must collect all items first! Press 'r' to restart.");
			 warning.setX(200);
			 warning.setY(200);
			 warning.setFill(Color.WHITE);
			 myRoot.getChildren().add(warning);
		 }	 
	 }
	 
	 protected void reset(){
		 super.reset();
		 addAllItems(); //might add too many items if i didn't collect all the items first
		 
	 }
}
    

    
    
    

//to do: restart level must put the items back. call init again instead? go back to original and create a restartLevel() method


