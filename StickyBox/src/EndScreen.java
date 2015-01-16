import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * This is the win screen for StickyBox when the player wins the game (collected
 * all the items and arrived at all the exits)
 *
 * @author Callie Mao
 */
public class EndScreen {

    private Group myRoot;
    private Scene myScene;
    private Menu myMenu;

    private static final int TEXT_X_COORD = 130;
    private static final int TEXT_Y_COORD = 350;
    private static final int FONT_SIZE = 100;
    private static final int BUTTON_X_COORD = 295;
    private static final int BUTTON_Y_COORD = 400;

    public Scene init (Stage s, int width, int height) {
        myRoot = new Group();

        Text youWon = new Text(TEXT_X_COORD, TEXT_Y_COORD, "You Won!");
        youWon.setFont(new Font(FONT_SIZE));
        youWon.setFill(Color.WHITE);

        Button play = new Button("Play Again?");
        play.setTranslateX(BUTTON_X_COORD);
        play.setTranslateY(BUTTON_Y_COORD);

        play.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle (ActionEvent e) {
                myMenu = new Menu();
                Scene scene = myMenu.init(s, width, height);
                s.setScene(scene);
                s.show();
            }
        });

        myRoot.getChildren().add(youWon);
        myRoot.getChildren().add(play);
        myScene = new Scene(myRoot, width, height, Color.BLACK);
        return myScene;
    }
}
