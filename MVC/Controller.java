import java.util.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

//the view will handle
//the controller will handle
//the model will handle

public class Controller implements Runnable {

  View view = View.getInstance();
  Model model = Model.getInstance();

  public String getFilename() {
    System.out.println("What file would you like to display?");
    String filename = ""
    try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
      filename = br.readLine();
    } catch (IOException e) {
      e.getMessage();
      e.printStackTrace();
    }
    return filename;
  }

  @Override
  public void run() {

    String filename = getFilename();
    String file = model.readFile(filename);
    view.display(file);
  }

  public static void main(String[] args) {
    Controller controller = new Controller();
    controller.run();
  }
}

