/*
 * SD2x Homework #5
 * Implement the method below according to the specification in the assignment description.
 * Please be sure not to change the method signature!
 */

import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class MovieRatingsParser {
	
	int year;
	
	/*
	MovieRatingsParser(int yr)
	{
		year = yr;
	}
	
    public int comapreTo(MovieRatingsParser m)
    {
    	return year - m.year;
    }
    */
	
	public static TreeMap<String, PriorityQueue<Integer>> parseMovieRatings(List<UserMovieRating> allUsersRatings) 
	{
		
		/* IMPLEMENT THIS METHOD! */
		
		return null; // this line is here only so this code will compile if you don't modify it
	}
	
	// Sample output from compareTo()
    public static void main(String[] args)
    {
    	int rc = new String("ally").compareTo(new String("ally"));  // rc = 0

        System.out.print(rc + "  = new String(\"ally\").compareTo(new String(\"ally\"))" + "\n");

    	rc = new String("ally").compareTo(new String("belinda")); // rc = -1

        System.out.print(rc + " = new String(\"ally\").compareTo(new String(\"belinda\"))" + "\n");

    	rc = new String("jane").compareTo(new String("ally"));  // rc = 0

        System.out.print(rc + "  = new String(\"jane\").compareTo(new Stinrg(\"ally\"))" + "\n");
        
        MovieRatingsParser m1 = new MovieRatingsParser(1977);
        MovieRatingsParser m2 = new MovieRatingsParser(2017);
        
        rc = m1.comapreTo(m2);
        
        System.out.print(rc + "  = new MovieRatingsParser(1977).compareTo(new MovieRatingsParser(2017)) " + "\n"); // -40
        
        rc = m2.comapreTo(m1);
        
        System.out.print(rc + "  = new MovieRatingsParser(2017).compareTo(new MovieRatingsParser(1977)) " + "\n"); // 40
    } 

}

