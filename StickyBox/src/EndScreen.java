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

    public Scene init (Stage s, int width, int height) {
        myRoot = new Group();

        Text closer = new Text(130, 350, "You Won!");
        closer.setFont(new Font(100));
        closer.setFill(Color.WHITE);

        Button play = new Button("Play Again?");
        play.setTranslateX(295);
        play.setTranslateY(400);

        play.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle (ActionEvent e) {
                myMenu = new Menu();
                Scene scene = myMenu.init(s, width, height);
                s.setScene(scene);
                s.show();
            }
        });

        myRoot.getChildren().add(closer);
        myRoot.getChildren().add(play);
        myScene = new Scene(myRoot, width, height, Color.BLACK);
        return myScene;
    }
}
