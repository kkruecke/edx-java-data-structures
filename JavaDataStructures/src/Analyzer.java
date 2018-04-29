import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

// Kurt:
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
import java.io.IOException;
import java.util.LinkedList;
import java.io.File;
import java.util.HashMap;
import java.util.regex.*;
// BreakIterator needs:
import java.util.*;
import java.text.*;

/*
 * SD2x Homework #3
 * Implement the methods below according to the specification in the assignment description.
 * Please be sure not to change the method signatures!
 */
public class Analyzer {
    
    private static void show_hash_set(HashSet<Word> set)
    {
       System.out.print("Printing HashSet<Word> of size " + set.size() + "\n");

       for (Word entry : set) {

           System.out.print("[ " + entry.getText() + ", "); 
           
           System.out.print("{ " + entry.getCount() + ", " + entry.getTotal() + "}] ,"); 
        }
    }

    private static void show_hash_map(Map<String, Word> map)
    {
       System.out.print("Printing HashMap<String. Word> of size " + map.size() + "\n");

        for (Map.Entry<String, Word> entry : map.entrySet()) {
             
           System.out.print("[ " + entry.getKey() + ", "); 
           
           System.out.print("{ " + entry.getValue().getCount() + ", " + entry.getValue().getTotal() + "}] ,"); 
        }
        System.out.println(" "); 
    }

    private static void show_sentences(List<Sentence> list)
    {
       System.out.print("Printing List<Sentence> of size " + list.size() + "\n");

       for (Sentence s : list) {
            System.out.println("Score is: " + s.getScore() + " AND The Sentence is: " + s.getText());
       }
    } 
    private static void show_list(List<String> list)
    {
       System.out.print("Printing List<String> of size " + list.size() + "\n");

       for (String str : list) {
           
            System.out.println(str);
       }
    } 
    
    /*
      Regex from https://stackoverflow.com/questions/20320719/constructing-regex-pattern-to-match-sentence
      private static final Pattern pattern = Pattern.compile("^-?[210]\\s+[A-Za-z,;'\"\\s]+[.?!]$");
     */
    
    private static final Pattern pattern = Pattern.compile("^(-?[210])\\s+((?:[a-zA-Z]+[,; .!?]?)+)$"); // My version that captures the number and the rest of the sentence following the space.

/*
   Implement this method in Part 1

   Implementations Details: 

   For a valid sentence such as: “2 I am learning a lot .”

the score field of the Sentence object should be set to 2, and the text field should be “I am learning a lot .” Your code should ignore (and not create a Sentence object for) any line that is not well-formatted,
that is, does not start with an int between -2 and 2 (inclusive), has a single whitespace, and then is followed by more text.” 

However, if the file cannot be opened for reading or if the input filename is null, this method should return an empty List.

Note that it is up to you to determine which List implementation to return; any decision is fine, as long as it implements the List interface and you do not change the signature of this method.
         
	 */
         /*
    See reading text files in Java 8:
    
    1. http://javarevisited.blogspot.com/2015/07/3-ways-to-read-file-line-by-line-in.html
    2. http://www.onlinetutorialspoint.com/java8/java-8-read-file-line-by-line-example.html
    3. https://www.mkyong.com/java8/java-8-stream-read-a-file-line-by-line/
    
    Regex help:
        
        https://docs.oracle.com/javase/tutorial/essential/regex/
        https://www.javacodegeeks.com/2012/11/java-regular-expression-tutorial-with-examples.html
    */
    public static List<Sentence> readFile(String filename) 
    {   
        LinkedList<Sentence> list = new LinkedList<Sentence>();
        
        if (filename == null) return list;
        
        try (Scanner scanner = new Scanner(new File(filename))) {
    
	    while (scanner.hasNext()){
                
               String line = scanner.nextLine();
               //--System.out.println(line); debug
     	       Matcher matcher = pattern.matcher(line);
    
               if (!matcher.matches()) continue;
               
               System.out.println(matcher.group(1));
               System.out.println(matcher.group(2));
               
               list.add( new Sentence(Integer.valueOf(matcher.group(1)), matcher.group(2)) ); 
            }
    
        } catch (IOException e) {
              
            System.out.println("There was an error either opening or reading from " + filename + "."); 
        }
        
        System.out.println("This is LinkedList<Sentence>: "); 
        show_sentences(list);
        
        return list; // this line is here only so this code will compile if you don't modify it
    }
     
    /*
     * Implement this method in Part 2
       Criteria:

     * If the input List of Sentences is null or is empty, the allWords method should return an empty Set.
     * If a Sentence object in the input List is null, this method should ignore it and process the non-null Sentences.
     * your program should ignore any token that does not start with a letter
BUGS:

#2. allWords contains incorrect number of elements when some words start with character that is not a letter
     */  
    public static Set<Word> allWords(List<Sentence> sentences) 
    {
      HashSet<Word> words_set = new HashSet<Word>();
    
      if (sentences == null || sentences.size() == 0) return words_set;  
    
      HashMap<String, Word> hash_map = new HashMap<String, Word>(); // Set of unique words in a given sentence.
    
      for (Sentence sentence : sentences) {
    
         if (sentence == null) {
        	     continue;
         }
    
         int current_score = sentence.getScore();   
         
         System.out.println("This is the sentence: " + sentence);
         
         List<String> word_list = extractWords(sentence.getText());
         
         System.out.println("List<String> output of extractWords() is: ");
         
         Analyzer.show_list(word_list);
    
         for (String str_word : word_list) {
    
             Word word = hash_map.get(str_word);
    
             if (word == null) {

                 Word new_word = new Word(str_word);

                 new_word.increaseTotal(current_score);

                 hash_map.put (str_word, new_word);
    
             } else {
    
                word.increaseTotal(current_score); 
             }

             System.out.println("\nShowing HashMap<String, Word> within allWords(): ");
             show_hash_map(hash_map);
         }
       } 
       words_set = buildWordSet(hash_map);
      
      return words_set; 
    }
    /*
    See:
    https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/i18n/text/examples/BreakIteratorDemo.java
    http://tutorials.jenkov.com/java-internationalization/breakiterator.html

     Returns the list of words in the target after converting all such words to lowercase. A word being defined as consisting of A-Za-z. 
  */ 	    
     
    private static List<String> extractWords(String target) 
    {
         LinkedList<String> words = new LinkedList<String>();
         // new  code
         
         String [] prosp_words = target.split("\\s");
         for (String word : prosp_words) {
        	 
         
        	   if (Character.isLetter( word.charAt(0) )) {
        		   
        		   words.add(word.toLowerCase());
        	   }
         }
         
        return words;
    } 
        
    
    private static HashSet<Word>  buildWordSet(Map<String, Word> map)
    {
        HashSet<Word> set = new HashSet<Word>();

        for (Map.Entry<String, Word> entry : map.entrySet()) {

            Word word = entry.getValue();
            set.add(word);
        }
        return set;
    } 

     /*
     * Implement this method in Part 3
     */
    public static Map<String, Double> calculateScores(Set<Word> words) 
    {
        HashMap<String, Double> hash_map = new HashMap<String, Double>();

        if (words == null || words.size() == 0) return hash_map; 

        for (Word word : words) {

           if (word == null) continue;
           
           hash_map.put(word.getText(), word.calculateScore());
        }
	    
	return hash_map; 
    }  

    /*
     * Implement this method in Part 4
     * Input: All words in the Map consist only of lowercase letters.
     * TODO: The errors are:
33 tests passed.
4 tests failed.
#1. calculateSentenceScore does not return correct value when input sentence contains invalid words
#4. readFile throws java.lang.NullPointerException when input filename is null
     */
    public static double calculateSentenceScore(Map<String, Double> wordScores, String str_sentence)
    {
         if (wordScores == null || wordScores.size() == 0)  return 0;

         else if (str_sentence == null || str_sentence.length() == 0) return 0;
         
         // but if it starts with a letter and is not present in the Map, assign it a score of 0. 
         List<String> word_list = Analyzer.extractWords(str_sentence); 

         double total_score = 0;

         int cnt = 0;

         for (String str_word : word_list) {

        	   // "If a word in the sentence does not start with a letter, then you can ignore it,...
        	   if (!Character.isLetter( str_word.charAt(0) )) continue; 

           // "... but if it starts with a letter and is not present in the Map, assign it a score of 0.
           double score = wordScores.containsKey(str_word) ? wordScores.get(str_word) : 0;
           
           total_score += score; 
           ++cnt;
         }  
         double avg_score = (cnt != 0) ? (total_score / cnt) : 0;  
         	    
        return avg_score; // this line is here only so this code will compile if you don't modify it
    }
   
    // Utility methods 
   /*
    * This method is here to help you run your program. Y
    * You may modify it as needed.
    */
    public static void main(String[] args) 
    {
	    if (args.length == 0) {
		    System.out.println("Please specify the name of the input file");
		    System.exit(0);
	    }
	    
	    String filename = args[0];
	    
	    System.out.print("Please enter a sentence: ");
	    
	    Scanner in = new Scanner(System.in);
	    
	    String sentence = in.nextLine();
	    
	    in.close();
	    
	    List<Sentence> sentences = Analyzer.readFile(filename);
	    
	    Set<Word> words = Analyzer.allWords(sentences);
            
        System.out.println("\nThe Set<Word> returned by Analyzer.calculateScores(words) is below:");
        System.out.print("\t"); 
	    
	    
	    Map<String, Double> wordScores = Analyzer.calculateScores(words);

        System.out.println("\nThe Map<String, Double> returned by Analyzer.calculateScores(words) is below:");
        System.out.print("\t"); 
	    
	    double score = Analyzer.calculateSentenceScore(wordScores, sentence);
	    
	    System.out.println("\nThe sentiment score is " + score);
        System.out.print("\t"); 
    }
    /*
    public static void main(String[] args) 
    {
	    if (args.length == 0) {
		    System.out.println("Please specify the name of the input file");
		    System.exit(0);
	    }
	    
	    String filename = args[0];
	    
	    System.out.print("Please enter a sentence: ");
	    
	    Scanner in = new Scanner(System.in);
	    
	    String sentence = in.nextLine();
	    
	    in.close();
	    
	    List<Sentence> sentences = Analyzer.readFile(filename);
	    
	    Set<Word> words = Analyzer.allWords(sentences);
            
        System.out.println("\nThe Set<Word> returned by Analyzer.calculateScores(words) is below:");
        System.out.print("\t"); 
	    
	    
	    Map<String, Double> wordScores = Analyzer.calculateScores(words);

        System.out.println("\nThe Map<String, Double> returned by Analyzer.calculateScores(words) is below:");
        System.out.print("\t"); 
	    
	    double score = Analyzer.calculateSentenceScore(wordScores, sentence);
	    
	    System.out.println("\nThe sentiment score is " + score);
        System.out.print("\t"); 
    }
    */
}
