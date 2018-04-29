import java.util.ListIterator;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Collections; 

/*
 * SD2x Homework #1
 * Implement the methods below according to the specification in the assignment description.
 * Please be sure not to change the signature of any of the methods!
 */

public class LinkedListUtils {

   

    // Utilities
    public static <T> void show_array(T[] arr)    
    {
        for (T value : arr) {
          System.out.print(value + ", ");
        }
        
        System.out.print("\n");
    }
      
    public static <T> void show_list(List<T> list) // generic method to display a list       
    {
       int sz = list.size();
       System.out.print("list size is " + sz + ". Elements: ");
       
       if (sz != 0) {
          for (T value : list) {
           
            System.out.print(value + ", ");
          }
       }
        
        System.out.print("\n");
    }
   
    static protected boolean reverse_binary_search(String arr[], String str_value, int low, int high) 
    {
        while(low <= high ) {

            int middle = (low+high) /2;
            
            int comp_rc = -arr[middle].compareTo(str_value); 
            
            if (comp_rc < 0) { 

                low = middle + 1;

            } else if (comp_rc > 0) { 

                high = middle - 1;

            } else { // The element has been found

                return true;
            }
        }

        return false;
   }

   static boolean compare_string_lists(ListIterator<String> iter, ListIterator<String> sub_iter) 
   {
      boolean rc = true;
   
      while (sub_iter.hasNext()) {
          
          rc = rc && iter.next().compareTo(sub_iter.next()) == 0;

          if (rc == false) break;
          
      }
      return rc;
   }
   
   static boolean compare_int_lists(ListIterator<Integer> iter, ListIterator<Integer> sub_iter) 
   {
      boolean rc = true;
   
      while (sub_iter.hasNext()) {
                    
          rc = rc && iter.next() == sub_iter.next();

          if (rc == false) break;
          
      }
      return rc;
   }
   
   /*
   This method assumes the input LinkedList is already sorted in non-descending order, i.e., such that each element is greater than or equal to the one
   that is before it, and it inserts the Integer value into the correct location of the list. Note that the method does not return anything, but rather
   modifies the input LinkedList as a side effect. If the input LinkedList is null, this method should simply terminate.
  */                 
   public static void insertSorted(LinkedList<Integer> list, int value) 
   {
     if (list == null)  
         return;
     
     if (list.size() == 0) {
    	    list.add(value); 
    	    return;
     }
      
     for (ListIterator<Integer> iter = list.listIterator(); iter.hasNext(); ) {
         
        Integer current = iter.next(); 
        
        if (value < current) {

            iter.previous(); // previous() is needed because add() will insert following the current element.
            iter.add(value);
            return;
            
        } else if (value == current) {
            
            iter.add(value);
            return;        
        }
     } 
    
     list.addLast(value); 
  }

   /* 
    removeMaximumValues:

      This method removes all instances of the N largest values in the LinkedList. Because the values are Strings, you will need to use the String class’ compareTo method
      to find the largest elements; see the Java API for help with that method. If the input LinkedList is null or if N is non-positive, this method should simply return
      without any modifications to the input LinkedList. Keep in mind that if any of the N largest values appear more than once in the LinkedList, this method should return
      remove all instances, so it may remove more than N elements overall. The other elements in the LinkedList should not be modified and their order must not be changed.
    */ 
    // BUG: We need to remove the N largest elements. If there is more than once occurance of an element, removing both does not affect N; for example, if there are N + 1 elements, say,
    //"zzz", then we still need to remove the N - 1 next immediately smaller elements. 
    public static void removeMaximumValues(LinkedList<String> list, int N)
    {
        if (list == null) return;
        
        if (N <= 0 || list.size() == 0) return;

         // Convert the list to an array and sort it in descending-order.
         String[] arr = list.toArray(new String[list.size()]);

         Arrays.sort(arr, Collections.reverseOrder());

         // Copy the first N unique values into unique_value[].
         String unique_values[] = new String[N]; 

         int unique_index = 0; 

         unique_values[unique_index++] = arr[0];

         // Note: Since arr is already in sorted order, it is sufficient to copy arr[index] to unique_values if arr[index] < arr[index - 1], lexiographically.
         for (int index = 1; index < arr.length  && unique_index < N; ++index) {  
             
              int rc = arr[index].compareTo(arr[index - 1]);
              
              if (rc < 0) { //  Negative value => arr[index - 1] is lex. less than. index[index], so copy it. 
        
                  unique_values[unique_index++] = arr[index];
              }
         }
       
        /* 
         * Now, iterate over the list element and search for each value in unique_value[]. If found, remove it from the list. We know only the largest N values are those in unique_values. 
         */
                
        ListIterator<String> iter = list.listIterator();
        
        do {
           
           String str_value = iter.next(); 
           
           boolean rc = LinkedListUtils.reverse_binary_search(unique_values, str_value, 0, unique_index - 1);  
                   
           if (rc == true) { 

              iter.remove(); // We maintain the current order of the list.
           }
           
        } while (iter.hasNext());
    }
    /*
      containsSubsequence:
      This method determines whether any part of the first LinkedList contains all elements of the second in the same order with no other elements in the sequence, i.e. it should return true if the second LinkedList is a subsequence of the first,
      and false if it is not. The method should return false if either input is null or empty.
    */

    public static boolean containsSubsequence(LinkedList<Integer> one, LinkedList<Integer> two) 
    {
    	    if (one == null || two == null) return false;
        int one_sz = one.size();
        int two_sz = two.size();

        if (one_sz == 0 || two_sz == 0) return false;

        if (one_sz < two_sz) return false;         

        ListIterator<Integer> one_iter = one.listIterator();

        //  Once we reach the point where the distance to the end of the list is < two.size, we exit the loop.

        while(one.size() - one_iter.nextIndex() >= two.size()) {
          
          if (LinkedListUtils.compare_int_lists(one_iter, two.listIterator() )) return true;

       }      
       return false;       
    }

    static protected void test_removeMaximumValues()
    {         
       String arr[] = { "a", "a", "b", "b", "c", "c", "e", "e", "dd", "e", "e", "0", "0"};
       
       LinkedList<String> list = new LinkedList<String>(Arrays.asList(arr));
       
       System.out.print("The list:\n");
       
       show_list(list);
       
       System.out.print("The list in Descending order:\n");
       
       String desc_arr[] = list.toArray(new String[4]);
       
       Arrays.sort(desc_arr, Collections.reverseOrder());
       
       show_array(desc_arr);
              
       LinkedList<String> list_copy = new LinkedList<String>();
              
       for (int i = list.size() - 1; i >= 0 ;--i) {
           
          list_copy.clear();
          
          for(String v : list) {
             list_copy.add(v);
          }
         
         System.out.print("The list with the " + i + " lexicographically largest strings removed: ");
       
         LinkedListUtils.removeMaximumValues(list_copy, i);
        
         show_list(list_copy);
       }  
             
    }

    public static void test_insertSorted()
    {
        LinkedList<Integer> list = new LinkedList<Integer>();

        for(int i = 1; i < 10; ++i) {
              list.addLast(i * 2);
        } 

        System.out.println("LinkedList<Integer> before insertSorted(list, v):");        
        
        LinkedListUtils.show_list(list);

        System.out.print("Inserting sorted value of: " ); 

        for(int i = 0; i < 10; ++i) {

           int insert_value = i + 1;  
           System.out.print(insert_value + ", "); 

           LinkedListUtils.insertSorted(list, i + 1); 
           
           System.out.println("LinkedList<Integer> after inserting " + insert_value + ": "); 
           LinkedListUtils.show_list(list);
           
        } 
        
        System.out.println("LinkedList<Integer> after inserting " + 200 + ": "); 
        
        LinkedListUtils.insertSorted(list, 200); 
        
        LinkedListUtils.show_list(list); 
        
        System.out.println("\nLinkedList<Integer> after insertSorted(list, v):");        
        LinkedListUtils.show_list(list);
    } 
 
    public static void test_containsSubsequence()
    {
        LinkedList<Integer> one = new LinkedList<Integer>();
        
        for(int i = 0; i < 10; ++i) {
          one.add(i);
        }

        System.out.print("Main list is: ");
        show_list(one); 
        
        LinkedList<Integer> two = new LinkedList<Integer>();
        
        Integer arr[] = one.toArray(new Integer[one.size()]);
        
        for(int i = one.size() - 1; i > 0; --i) {
            
            two.clear();
            
            for (int j = i; j < one.size(); ++j) {
              two.add(arr[j]);
            }  
            
            System.out.print("sequence list is: ");
            
            show_list(two);
            
            boolean b = LinkedListUtils.containsSubsequence(one, two);
            
            System.out.println("Result of containsSubsequence(one, two) is: " + b);
       }  
    }

    public static boolean test_compare()
    {
       Integer arr[] = { 1, 1, 2, 2, 3, 4, 5, 6, 7 };
       
       LinkedList<Integer> list = new LinkedList<Integer>(Arrays.asList(arr));
       
       System.out.print("The list:\n");
       
       show_list(list);
       Integer arr2[] = { 2, 3, 4 };

       List<Integer> sub = Arrays.asList(arr2);
       
       System.out.print("The sub_sequence list:\n");
       
       show_list(sub);
              
       ListIterator<Integer> list_iter = list.listIterator();

       while (list_iter.hasNext()) {
          
          boolean b = compare_int_lists(list_iter, sub.listIterator());

          if (b) {

             System.out.println("It worked.\n");
             return b;
          }
       } 
        
       System.out.println("It failed.\n");
       return false;
    }   
 
    
    public static void show_strings(LinkedList<String> list)    
    {
        for (String str : list) {
          System.out.print(str + ", ");
        }
        
        System.out.print("\n");
    }
    
    /*
    public static void main(String[] args)
    {
       LinkedListUtils.test_insertSorted(); // works
        
       LinkedListUtils.test_removeMaximumValues(); // works 

       LinkedListUtils.test_containsSubsequence(); // works
    }
    */
}
