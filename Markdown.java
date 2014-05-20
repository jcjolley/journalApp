import java.util.regex.*;
import java.lang.Character;
/**
* @singleton Class that parses markdown
*/
public class Markdown {

  /**
  * @return Header level (eg: 1) if no header it will be 0
  */

  static Markdown singleton;

  private Markdown() {
    if (singleton != null) {
      singleton = new Markdown();
    }
  }

  public static Markdown getInstance() {
    return singleton;
  }

  public int parseHeader(String line) {
    int i = 0;
    while (line.charAt(i) == '#') {
      i++;
    }

    return i;
  }

  public String parseChars(String allText) {
    char cursor;
    int lastEm = -1;
    int lastStrong = -1;

    for (int i = 0; i < allText.length(); i++) {
      cursor = allText.charAt(i);
      switch (cursor) {
      case '_':
        //checks if previous was whitespace
        if (i - 1 < 0 || Character.isWhitespace(allText.charAt(i - 1))) {
          if (allText.charAt(i + 1) == '_') {
            if (lastStrong != -1) {

            }
          }

        }
      }
    }

    return allText;
  }


  /**
   * This function will easily wrap any string in a tag.
   *    toWrap - text to be wrapped in a tag. "wrapped"
   *    tag - tag text "div id="content""
   * @return wrapped text (eg: <div id="conent">wrapped</div>)
   */
  public String tagWrap(String toWrap, String tag) {
    return "<" + tag + ">" + toWrap + "</" + tag.split(" ")[0] + ">";
  }

  /**
   * Will run the parser, line per line
   * @return {String} HTML.
   */
  public String Parse(String md) {
    String lines[] = md.split("\n");
    int oneUnderscore[] = null;
    boolean oneStar = false;

    String result = "";

    for (int i = 0; i < lines.length; i++) {
      String line = lines[i];

      // Find headers and repalce them.
      int h = parseHeader(line);
      if (h > 0 && h <= 6) {
        line = tagWrap(line.substring(h), "h" + h);
      }

      result += line += "\n";
    }


    return result;
  }

  public void test() {
    String wrapMe = "Wrap this in tags?";
    System.out.println(tagWrap(wrapMe, "p"));
    System.out.println(tagWrap(wrapMe, "div"));
    System.out.println(tagWrap(wrapMe, "a href=\"#awesome\""));

    String parseMe = "#this is a H1 tag\nthis is a body\n##double h2!\nanother line.";
    System.out.println(Parse(parseMe));
  }

  /**
   *
   */
  public static void main(String[] args) {
    Markdown.getInstance().test();
  }
}
