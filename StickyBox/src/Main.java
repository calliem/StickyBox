import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * This is the main program, it is basically boilerplate to create an animated scene.
 *
 * @author Callie Mao
 */
public class Main extends Application {
    private static final int NUM_FRAMES_PER_SECOND = 60;
    private Menu myMenu;

    /**
     * Set things up at the beginning.
     */
    @Override
    public void start (Stage s) {
        s.setTitle("BoxWorld");
        
        myMenu = new Menu();
        Scene scene = myMenu.init(s,720,720);
        s.setScene(scene);
        s.show();
    }

    //public void clear??
    
    /**
     * Start the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
}
