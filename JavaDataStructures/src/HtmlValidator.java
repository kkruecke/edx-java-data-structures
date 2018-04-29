	
import java.io.IOException;
import java.util.Queue;
import java.util.Stack;

/*
 * SD2x Homework #2
 * Implement the method below according to the specification in the assignment description.
 * Please be sure not to change the method signature!
 *
 * Kurt : See http://introcs.cs.princeton.edu/java/43stack/ for help.
 */


public class HtmlValidator {
    
     public static <T> void show_queue(Queue<T> queue)
     {
         System.out.print("Queue of " + queue.size() + " values: ");
         
         for (T v : queue) {
             
             System.out.print(v);
         }
     }
     
     public static <T> void show_stack(Stack<T> stack)
     {
         if (stack == null) {
             System.out.println("Stack is null");
             return;
         }
         
         System.out.print("Stack of " + stack.size() + " values: ");
         
         for (T v : stack) {
             
             System.out.print(v);
         }
     }
     
    /* 
      Implement isValidHtml() which determines whether an HTML file is well formatted using a stack. Every time it code encounters an open tag, it should be pushed it onto the stack;
      when it encounters a close tag, the tag should be popped off the top of the stack, and if they don’t match, then you’ll know the file is not well formatted.

      Some tags are self closing. Most are open-close pairs, in which the most recently opened tag type must match the type of the next closed tag.
    
      Input: Return value of getTagsFromHtmlFile(String filename)
     */
	
	public static Stack<HtmlTag> isValidHtml(Queue<HtmlTag> tags_queue) 
        {
           Stack<HtmlTag> stack_openTags = new Stack<HtmlTag>(); // Create stack        

           for(;!tags_queue.isEmpty(); tags_queue.remove()) { 
        	       
              HtmlTag tag = tags_queue.peek(); // Don't remove tag from queue just yet...
                     
              if (tag.isOpenTag()) { // Push open tags onto stack.

                  stack_openTags.push(tag); 
                  
              }  else if (!tag.isSelfClosing()) {  
                  
                  if (stack_openTags.empty()) {

                     break;
                  }
                                   
                  HtmlTag popped_tag = stack_openTags.peek(); // Peek top of stack for closed tag match 

                  String peeked_tag_string = popped_tag.getElement();
                                                          
                  String tag_string = tag.getElement();
                  
                  if (tag_string.equalsIgnoreCase(peeked_tag_string)) {

                      stack_openTags.pop(); // If we found a match, 
                     
                  }  else {
                      
                     break;
                  }
              } 
    } 
    
    if (!tags_queue.isEmpty() && stack_openTags.empty())  { // We have a closing tag with no open tag.
       
          return null;
     }      
            
     return stack_openTags; 
    }

   /* 
    public static void main(String[] args)
    {
       String files[] = { "test1.html", "test2.html", "test3.html", "test4.html", "test5.html", "test6.html", "test7.html" };
       
       for (String file : files)  {
           try { 
               System.out.println("\nTest Validating " + file);
               
               Queue<HtmlTag> tag_queue = HtmlReader.getTagsFromHtmlFile(file);  
           
               Stack<HtmlTag> open_tag_stack = HtmlValidator.isValidHtml(tag_queue);
                              
            } catch (IOException e) {
                
                System.out.println("Exception occurred calling HtmlReader.getTagsFromHtmlFile(" + file + ") " + e.getMessage());
           } 
        }   
    }
    */
}

