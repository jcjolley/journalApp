public class Markdown {

   public static int parseHeader(String line) {
      int i = 0;
      while(line.charAt(i) == '#') {
         i++;
      }

      return i;
   }


   /**
    * This function will easily wrap any string in a tag.
    *    toWrap - text to be wrapped in a tag. "wrapped"
    *    tag - tag text "div id="content""
    * @return wrapped text (eg: <div id="conent">wrapped</div>)
    */
   public static String tagWrap(String toWrap, String tag) {
      return "<" + tag + ">" + toWrap + "</" + tag.split(" ")[0] + ">";
   }

   /**
    * Will run the parser, line per line
    */
   public static String Parse(String md) {
      String lines[] = md.split("\n");

      String result = "";

      for(String line : lines) {
         int h = parseHeader(line);

         

         if (h > 0 && h<= 6) {
            line = tagWrap(line.substring(h), "h" + h);
         }

         result += line +="\n";
      }

      
      return result;
   }

   public static void test() {
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
      test();
   }
}
