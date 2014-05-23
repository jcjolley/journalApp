import java.lang.Character;
import java.lang.StringBuffer;
/**
* @singleton Class that parses markdown
*/
public class Markdown {

  /**
  * @return Header level (eg: 1) if no header it will be 0
  */

  static Markdown singleton = new Markdown();

  private Markdown() {
    // just to stop us from making it
  }

  public static Markdown getInstance() {
    return singleton;
  }

  public int parseHeader(String line) {
    int i = 0;

    while (i < line.length() && line.charAt(i) == '#') {
      i++;
    }

    return i;
  }

 
  public String parseChars(String allText) {
    char cursor;
    int lastEm = -1;
    int lastStrong = -1;
    StringBuffer text = new StringBuffer(allText);

    for (int i = 0; i < text.length(); i++) {
      cursor = text.charAt(i);

      switch (cursor) {
      case '_':
        //checks if previous was whitespace
        // is there two? (strong)
        if (text.charAt(i + 1) == '_') {
          // is this the left one?
          if (lastStrong == -1) {
            if (i - 1 < 0 || Character.isWhitespace(text.charAt(i - 1))) {
              lastStrong = i;
              // increment to get past double "__"
              i++;
            }
          } else {
            // i is on the left "_" of the closing "__"
            if (i + 2 > text.length() || Character.isWhitespace(text.charAt(i + 2))) {

              text = replaceSurrounding("__", "strong", lastStrong, i, text);

              lastStrong = -1;
            }
          }
        }

      }
    }

    return text.toString();
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
      } else {
        // do this?
        // line = tagWrap(line, "p");
        line = line + "<br />";
      }

      result += line += "\n";
    }

    result = parseChars(result);

    return result;
  }

  public void test() {
    String wrapMe = "Wrap this in tags?";
    System.out.println(tagWrap(wrapMe, "p"));
    System.out.println(tagWrap(wrapMe, "div"));
    System.out.println(tagWrap(wrapMe, "a href=\"#awesome\""));

    String parseMe = "#this is a __H1__ tag\nthis is a __body that has cool things\non multiple__ lines\n##double h2!\nanother line.";
    System.out.println(Parse(parseMe));
    System.out.println(Parse("this_is_underscores not __bold__"));
    System.out.println(Parse("__double__ __boldness__")); // fails
    System.out.println(Parse("__single__boldness__With____Toomuch_underscore__")); // fails
  }

  /**
   *
   */
  public static void main(String[] args) {
    Markdown md = Markdown.getInstance();
    md.test();
  }
}

