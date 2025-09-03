package atlas.ui;

import atlas.Atlas;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Atlas atlas;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/person.png"));
    private Image atlasImage = new Image(this.getClass().getResourceAsStream("/images/atlas.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Atlas instance */
    public void setAtlas(Atlas a) {
        atlas = a;
        showWelcome();
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Atlas's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = atlas.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getAtlasDialog(response, atlasImage)
        );
        userInput.clear();
    }

    private void showWelcome() {
        String welcomeMessage = "Hello! I'm Atlas.Atlas\nWhat can I do for you?";
        String informationMessage = "Type commands in the following format:\n"
                + "- todo <task>\n- deadline <task> /by <datetime>\n- event <task> /from <datetime> /to <datetime>\n"
                + "Note that <datetime> must be in the form of <dd/MM/yyyy HHmm> where HHmm is time in 24-hour format\n";
        dialogContainer.getChildren().addAll(
                DialogBox.getAtlasDialog(welcomeMessage, atlasImage),
                DialogBox.getAtlasDialog(informationMessage, atlasImage)
        );
    }

}
