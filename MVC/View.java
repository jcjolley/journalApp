

public class View {

  private static View singleton = new View();

  private View() {
    //to prevent making seperate instances
  }

  public static View getInstance() {
    return singleton;
  }

  public void display(String data){
    String[] lines = data.split("\n");
    System.out.println("********************************************************************************");
    System.out.println("********************************************************************************");
    
    for (String line : lines) {
      System.out.println(String.format("**   %-70s   **", line));
    }
    System.out.println("********************************************************************************");
    System.out.println("********************************************************************************");
    
  }

}