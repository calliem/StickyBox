import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

//You Won! Play Again? button
public class EndScreen {
	
	private Group myRoot;
    private Scene myScene;
    private Menu myMenu;

    private static final int NUM_FRAMES_PER_SECOND = 60;

    public Scene init (Stage s, int width, int height) {
    	  myRoot = new Group();
          
          Text closer = new Text(140,300,"You Won!");
      	  closer.setFont(new Font(100));
      	  closer.setFill(Color.WHITE);
      	  
    	  Button play = new Button("Play Again?");
    	  play.setTranslateX(295); 
    	  play.setTranslateY(400);
    	  
    	  play.setOnAction(new EventHandler<ActionEvent>() {
    		  public void handle(ActionEvent e){
	    		  myMenu = new Menu();
	    		  // attach game to the stage and display it
	    		  Scene scene = myMenu.init(s, width, height);
	    		  s.setScene(scene);
	    		  s.show();
		
	    		  // setup the game's loop
	    		 
    		  }
    	  }); 
    	  
          myRoot.getChildren().add(closer);
    	  myRoot.getChildren().add(play);
          
          myScene = new Scene(myRoot, width, height, Color.BLACK);
          return myScene;
    }
    
/*	 public KeyFrame start (int frameRate) {
        return new KeyFrame(Duration.millis(1000 / frameRate), e -> updateSprites());
    }*/
    
    
}
