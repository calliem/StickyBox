import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class Item extends Rectangle{ //would be better to extend imageview
	
	ImageView myImage;
	int myX;
	int myY;
	
	
	// if want to make more items in the future, can do so by extending item and changing the constructor of myimage. this can be the master class/interface that gets extended
	
	public Item(int xCoord, int yCoord, int width, int height){
		super (xCoord, yCoord, width, height);
		myImage = new ImageView(new Image(getClass().getResourceAsStream("images/coin.png"))); 
;
		myImage.setX(xCoord);
		myImage.setY(yCoord);
	}
	
	public Node getImageView(){
		return myImage;
	}
	
	

	

	
	
	
	

}
