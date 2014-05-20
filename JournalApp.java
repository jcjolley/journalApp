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
import javafx.scene.input.KeyEvent;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.scene.layout.Region;

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
  private Browser browser;

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
    Region spacer = new Region();
    Button saveBtn = new Button();
    Button htmlBtn = new Button();
    Label label = new Label("What's on your mind?");
    textArea = new TextArea();
    myConsole = new TextField();
    browser = new Browser();


    spacer.setMinHeight(20);
    textArea.setPrefRowCount(25);
    //we dont want users to edit the console area
    myConsole.setEditable(false);
    myConsole.setText("Welcome to Journal App v0.04");
    //save button!
    saveBtn.setText("Save entry as text");
    htmlBtn.setText("Save entry as html");
    //this method handles what happens when the button is clicked
    saveBtn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        //FileChooser is the save prompt object
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Journal Entry as Text");

        //this brings up the actual save prompt object and returns where you pick
        File file = fileChooser.showSaveDialog(stage);
        writeFile( textArea.getText(), file.getAbsolutePath(), false);
        myConsole.setText("Journal entry saved to " + file.getAbsolutePath() + " as text.");
      }
    });

    htmlBtn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        //FileChooser is the save prompt object
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Journal Entry as HTML");

        FileChooser.ExtensionFilter htmlFilter = new FileChooser.ExtensionFilter("html file (*.html)", "html");
        fileChooser.getExtensionFilters().add(htmlFilter);

        //this brings up the actual save prompt object and returns where you pick
        File file = fileChooser.showSaveDialog(stage);
        writeFile( "<!DOCTYPE html>" + browser.getText(), file.getAbsolutePath(), false);
        myConsole.setText("Journal entry saved to " + file.getAbsolutePath() + " as html.");
      }
    });
    //This watches the text area for changes in the text
    textArea.setOnKeyReleased(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        myConsole.setText("A key was pressed");
        browser.interpret("<div>" + textArea.getText() + "</div>");
        //This will be where I send info to the markdown interpreter
        //for displaying
      }
    });

    //a vertical box that stacks each element in it.
    VBox root = new VBox();

    //add all the children in the order we want them to appear
    root.getChildren().add(label);
    root.getChildren().add(textArea);
    root.getChildren().add(spacer);
    root.getChildren().add(browser);
    HBox hbox = new HBox();
    hbox.setAlignment(Pos.TOP_RIGHT);
    hbox.getChildren().add(saveBtn);
    hbox.getChildren().add(htmlBtn);
    root.getChildren().add(hbox);

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

/**
* Class Browser
* It appears this class is like a web browser, with it's own Web View and
* Web engine
*/
class Browser extends Region {
  final WebView browser = new WebView();
  final WebEngine webEngine = browser.getEngine();

  public Browser() {
    //apply the styles ... how I'm not sure.
    getStyleClass().add("browser");

    String myHTML = "<div><h1>Start typing, your text will be interpreted here.</div>";    //load the web page
    //add my HTML it to the webEngine
    interpret(myHTML);

    //add the web view to the scene ... how does getChildren work?
    getChildren().add(browser);
  }

  /**
  * Method createSpacer
  * I just do what I'm told... I'll have to research this too
  */
  private Node createSpacer() {
    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    return spacer;
  }

  /**
  * Method layoutChildren()
  * Again, I'm not sure what this does, or what is being implemented.
  */
  @Override
  protected void layoutChildren() {
    double w = getWidth();
    double h = getHeight();
    layoutInArea(browser, 0, 0, w, h, 0, HPos.CENTER, VPos.CENTER);
  }

  /**
  * Method computePrefWidth
  * Again, I'm not sure what this does, or what is being implemented.
  * Why does it take a height as a parameter... and do nothing with it?
  */
  @Override protected double computePrefWidth(double height) {
    return 750;
  }

  /**
  * Method computePrefHeight
  * Again, I'm not sure what this does, or what is being implemented.
  * Why does it take a width as a parameter... and do nothing with it?
  */
  @Override protected double computePrefHeight(double width) {
    return 500;
  }


  /**
  * method interpret
  * interprets markdown into HTML
  * (At least that's what it will do someday.) Currently it loads an HTML string
  * into the browser's web engine.
  */
  public void interpret(String htmlText) {
    webEngine.loadContent(htmlText, "text/html");

  }

  public String getText() {
    return (String) webEngine.executeScript("document.documentElement.outerHTML");
  }
}
