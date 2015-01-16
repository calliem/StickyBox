import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * This is the main program of StickyBox that starts up the game.
 *
 * @author Callie Mao
 */
public class Main extends Application {
    private Menu myMenu;

    /**
     * Set things up at the beginning.
     */
    @Override
    public void start (Stage s) {
        s.setTitle("BoxWorld");

        myMenu = new Menu();
        Scene scene = myMenu.init(s, 720, 720);
        s.setScene(scene);
        s.show();
    }

    /**
     * Start the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
}
