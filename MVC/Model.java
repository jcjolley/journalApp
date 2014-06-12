import java.util.BufferedReader;
import java.util.FileReader;
import java.io.IOException;

public class Model {

  private static Markdown singleton = new Markdown();

  private Model() {
    //to prevent making seperate instances
  }

  public String readFile(String filename) {
    try (Buffered Reader br = new BufferedReader(new FileReader(filename))) {
      String data = "";
      int i = 1;
      
      while ((data += br.readLine()) != null) {
        System.out.println("Read line " + i;)
        i++;
      }
    } catch (IOException e) {
      e.getMessage();
      e.printStackTrace();
    }
  } 

  public static Model getInstance() {
    return singleton;
  }

}