import java.util.regex.Pattern;

class Rememberer {
  private String leftToken;
  private String rightToken;

  private String tag;
  private int leftOffset;

  /**
  * Create's a rememberer
  * @arg tokens - the left and right tokens if they are the same then just "_"
  *         if they are different sperate with a | like so "{|}"
  */
  public Rememberer(String tokens, String newTag) {

    if (tokens.indexOf(" OR ") > -1) {
      String[] tempTokens = tokens.split(" OR ");
      leftToken = tempTokens[0];
      rightToken = tempTokens[1];
    } else {
      leftToken = rightToken = tokens;
    }

    tag = newTag;
    leftOffset = -1;

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
  * This will replace characters surrounding somthing with a HTML tag
  * @arg oldChars - the characters that will surround some text
  *      eg: "_" for "_italic_"
  * @arg newTag - the tag name
  *       like "div id='coolness'" for "<div id='coolness'>italic</div>"
  * @arg  curOffset - where the character to be replaced are in text.
  *       eg: __bold is so cold__
  *           ^-leftOffset     ^-curOffset (note it points to the left most char in both)
  * @arg text the body of text in which this all resides
  * @return newly changed text
  */
  private void maybeReplace(int curOffset, StringBuffer text) {

    String oldChars = text.substring(curOffset, curOffset+5);

    if (leftOffset == -1 && oldChars.matches(leftToken)) {
      leftOffset = curOffset;
    }

    if (leftOffset != -1 && oldChars.matches(rightToken)) {
      int oldWidth = oldChars.length();
      String pulled = text.substring(leftOffset + oldWidth, curOffset);
      text = text.delete(leftOffset, curOffset + oldWidth);
      text = text.insert(leftOffset, tagWrap(pulled, tag));
    }
  }

  public void test() {
    System.out.println("leftToken:" + leftToken);
    System.out.println("rightToken:" + rightToken);
    //test of tag wrapper
    System.out.println(tagWrap("this is a sentance", "p"));
    System.out.println(tagWrap("this is a sentance", "div tag='coolness'"));
    String match = "$__\\w*";
    System.out.println(Pattern.matches(match, " __asdf"));
    System.out.println(Pattern.matches(match, "__asdf"));
    System.out.println(Pattern.matches(match, "__ asdf"));

    // test of matching
    StringBuffer boldMe = new StringBuffer(" __THIS IS COOL__ **this is cool too**");

    maybeReplace(0, boldMe);
    System.out.println("left:" + leftOffset);
    maybeReplace(13, boldMe);
    System.out.println("left:" + leftOffset);
    System.out.println(boldMe);

  }

  public static void main(String[] args) {
    Rememberer tester = new Rememberer(".*\\W__ OR __\\s.*", "strong");
    tester.test();
  }
}
