import java.util.regex.*;
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
    while (line.charAt(i) == '#') {
      i++;
    }

    return i;
  }

  /**
  * This will replace characters surrounding somthing with a HTML tag
  * @arg oldChars - the characters that will surround some text
  *      eg: "_" for "_italic_"
  * @arg newTag - the tag name
  *       like "div id='coolness'" for "<div id='coolness'>italic</div>"
  * @arg leftOffset/rightOffset - where the character(s) to be replaced are in text.
  *       eg: __bold is so cold__
  *           ^-leftOffset     ^-rightOffset (note it points to the left most char in both)
  * @arg text the body of text in which this all resides
  * @return newly changed text
  */
  private StringBuffer replaceSurrounding(String oldChars, String newTag, int leftOffset,
                                          int rightOffset, StringBuffer text) {
    int oldWidth = oldChars.length();
    String pulled = text.substring(leftOffset + oldWidth, rightOffset);
    text = text.delete(leftOffset, rightOffset + oldWidth);
    text = text.insert(leftOffset, tagWrap(pulled, newTag));
    return text;
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

    result = parseChars(result);

    return result;
  }

  public void test() {
    String wrapMe = "Wrap this in tags?";
    System.out.println(tagWrap(wrapMe, "p"));
    System.out.println(tagWrap(wrapMe, "div"));
    System.out.println(tagWrap(wrapMe, "a href=\"#awesome\""));

    String parseMe = "#this is a H1 tag\nthis is a __body that has cool things\non multiple__ lines\n##double h2!\nanother line.";
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
