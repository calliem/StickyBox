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


/**
 * This is the game menu
 *
 * @author Callie Mao
 */
public class Menu {

    private Group myRoot;
    private BoxWorld myGame;
    public Scene myScene;

    public Scene init (Stage s, int width, int height) {
        myRoot = new Group();

        addIntroText();
        Button play = new Button("Play");
        play.setTranslateX(330);
        play.setTranslateY(400);

        play.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle (ActionEvent e) {
                myGame = new BoxWorld();
                Scene scene = myGame.init(s, width, height);
                s.setScene(scene);
                s.show();
            }
        });

        myRoot.getChildren().add(play);
        myScene = new Scene(myRoot, width, height, Color.BLACK);
        return myScene;
    }

    public void addIntroText () {
        Text opener = new Text(100, 300, "Sticky Box");
        opener.setFont(new Font(100));
        opener.setFill(Color.WHITE);
        Text instructions =
                new Text(
                         150,
                         350,
                         "Guide sticky box to the white exit. "
                                 + "Sticky box can only move in a straight line but can stick to obstacles along the way. "
                                 + "Use the arrow keys to move. Press 'r' to restart the level, 's' to skip the level, and 'h' for a hint.");
        instructions.setTextAlignment(TextAlignment.CENTER);
        instructions.setWrappingWidth(450);
        instructions.setFill(Color.WHITE);

        myRoot.getChildren().add(opener);
        myRoot.getChildren().add(instructions);
    }
}