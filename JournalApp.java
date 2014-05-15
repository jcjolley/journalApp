//javaFX stuff
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;

//file stuff
import java.io.File;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;


/**
* Class JournalApp
* Description: Opens a javaFX window and displays a text area
* with a save button and console.  --Now with actual saving!
*/
public class JournalApp extends Application {
  private Scene scene;
  private TextArea textArea;
  private TextField myConsole;

  /**
  * method writeFile
  * A generic method to write to a file.
  */
  public void writeFile(String text, String filename, boolean fAppend) {
    //try with resources closes my Writers/Files for me
    try(PrintWriter fout = new PrintWriter(new BufferedWriter(new FileWriter(filename, fAppend)))) {
      fout.println(text);
    } catch (IOException e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
  }

  /**
  * Method start
  * Initializes the GUI
  */
  @Override
  public void start(Stage stage) {
    Button saveBtn = new Button();
    Label label = new Label("What's on your mind?");
    textArea = new TextArea();
    myConsole = new TextField();

    //we dont want users to edit the console area
    myConsole.setEditable(false);
    myConsole.setText("Welcome to Journal App v0.04");
    //save button!
    saveBtn.setText("Save entry");

    //this method handles what happens when the button is clicked
    saveBtn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        //FileChooser is the save prompt object
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Journal Entry");

        //this brings up the actual save prompt object and returns where you pick
        File file = fileChooser.showSaveDialog(stage);
        writeFile( textArea.getText(), file.getAbsolutePath(), false);
        myConsole.setText("Journal entry saved to " + file.getAbsolutePath());
      }
    });

    //a vertical box that stacks each element in it.
    VBox root = new VBox();

    //add all the children in the order we want them to appear
    root.getChildren().add(label);
    root.getChildren().add(textArea);
    root.getChildren().add(saveBtn);
    root.getChildren().add(myConsole);

    //make the textArea the one that moves around
    VBox.setVgrow(textArea, Priority.ALWAYS);

    //Set the title of the window
    stage.setTitle("Journal App v0.04");

    //Create a new scene of type Browser (which we create below)
    //and with the background color specified
    scene = new Scene(root, 750, 500);

    //Set this scene as the current scene on stage
    stage.setScene(scene);

    //load our CSS file for this stage
    scene.getStylesheets().add("mycss.css");

    //Show the stage to the world!  (make it visible)
    stage.show();
  }



  /**
  * Method main
  * Our driver program.  Gadzooks! Robin, why don't you drive for once?
  */
  public static void main(String args[]) {
    launch(args);
  }
}

