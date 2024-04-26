/**
    Program to test Satellite class.
    Reads Satellite data from a website, constructs Satellite Objects
    and stores them in a List.

    Concepts used:
        Inheritance, Data Abstraction (List, ArrayList, Satellite class)
        Reading a url
        Template classes (List, ArrayList)

     To Do:
        implement Comparable/Comparator Interfaces
        implement additional Satellite class methods

     Websites:
        https://celestrak.org/NORAD/elements/
        https://en.wikipedia.org/wiki/Two-line_element_set
        https://www.programiz.com/java-programming/library/string/substring
        https://www.digitalocean.com/community/tutorials/java-convert-string-to-double
        http://www.satobs.org/seesat/Dec-2002/0197.html

*/
import java.net.*;  // URL
import java.io.*;   // BufferedReader, InputStreamReader
import java.util.*; // List, ArrayList

public class SatListURLReader
{
    public static List<Satellite> find_sats() throws Exception {

        URL url = new URL("https://celestrak.org/NORAD/elements/gp.php?GROUP=active&FORMAT=tle");
        BufferedReader in = new BufferedReader(
        new InputStreamReader(url.openStream()));

        List<Satellite> sat_list = new ArrayList<>();

        String name, line1, line2;
        while ((name = in.readLine()) != null)
        {
            line1 = in.readLine();
            line2 = in.readLine();
            sat_list.add(new Satellite(name, line1, line2));
        }
        in.close();

        return sat_list;
    }
}