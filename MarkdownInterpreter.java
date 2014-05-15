// This entire file adapted from
// http://docs.oracle.com/javafx/2/webview/jfxpub-webview.htm

//javaFX stuff
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;


/***
* class MarkdownInterpreter
* This class will interpret markdown into HTML for displaying.
* JFrame elements are purely for debug at this point.  The
* Final result will just feed the HTML to a different window for display
*/
public class MarkdownInterpreter extends Application {
  private Scene scene;

  /**
  * Method start
  * Initializes the GUI
  */
  @Override
  public void start(Stage stage) {
    //Set the title of the window
    stage.setTitle("MarkdownInterpreter v0.02");

    //Create a new scene of type Browser (which we create below)
    //and with the background color specified
    scene = new Scene(new Browser(), 750, 500, Color.web("#666970"));

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

    String myHTML = "<html><head></head><body><div><h1> Testing </h1> <br/><h2> Testing </h2> <br/><h3> Testing </h3> <br/><h4> Testing </h4> <br/><h5> Testing </h5> <br/><h6> Testing </h6> <br/></div></body></html>";    //load the web page
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
}