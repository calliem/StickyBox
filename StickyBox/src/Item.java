import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class Item extends ImageView{ //would be better to extend imageview
	
	int myX;
	int myY;
	// if want to make more items in the future, can do so by extending item and changing the constructor of myimage. this can be the master class/interface that gets extended
	
	public Item(Image image, int xCoord, int yCoord, int size){
		myImage = new ImageView(new Image(getClass().getResourceAsStream("images/coin.png"))); 
		myImage.setX(xCoord);
		myImage.setY(yCoord);
	}
}
	
	

	

	
	
	
	

