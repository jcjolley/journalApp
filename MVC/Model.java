import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Model {

  private static Model singleton = new Model();

  private Model() {
    //to prevent making seperate instances
  }

  public String readFile(String filename) {
    String data = "";
    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
      int i = 1;
      String line = "";
      while ((line = br.readLine()) != null) {
        data += line + "\n";
      }
    } catch (IOException e) {
      e.getMessage();
      e.printStackTrace();
    }
    return data;
  }

  public static Model getInstance() {
    return singleton;
  }

}