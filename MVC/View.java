

public class View {

  private static Markdown singleton = new Markdown();

  private View() {
    //to prevent making seperate instances
  }

  public static View getInstance() {
    return singleton;
  }

  public void display(String data){
    String[] lines = string.split(System.getProperty("line.seprator"));
    System.out.println("*********************************************");
    for (line : lines) {
      System.out.println("**   " + line + "   **");
    }
    System.out.println("*********************************************");
  }

}