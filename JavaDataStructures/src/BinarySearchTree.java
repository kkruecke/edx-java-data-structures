import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BinarySearchTree<E extends Comparable<E>> {

    class Node {
        
        E value;
        Node leftChild = null;
        Node rightChild = null;
        
        Node(E value) 
        {
	        this.value = value;
        }
        
        @Override
        public boolean equals(Object obj) 
        {
	        if ((obj instanceof BinarySearchTree.Node) == false) return false;
		        
	        @SuppressWarnings("unchecked")
	        
	        Node other = (BinarySearchTree<E>.Node)obj;
	        
	        return other.value.compareTo(value) == 0 && other.leftChild == leftChild && other.rightChild == rightChild;
        }
    }

    class Pair {

        public Node node;
        public int  level;
        
        public Pair(Node n, int lvl)
        {
	        this.node = n;
	        this.level = lvl;
        }
    }
	
    protected Node root = null;
    protected int  size = 0;
    
    protected void visit(Node n) 
    {
        System.out.println(n.value);
    }
    
    public boolean contains(E val) 
    {
        return contains(root, val);
    }
    
    protected boolean contains(Node n, E val) 
    {
        if (n == null) return false;
        
        if (n.value.equals(val)) {
        
	    return true;
	        
        } else if (n.value.compareTo(val) > 0) {
    
	    return contains(n.leftChild, val);
	        
        } else {
        
	    return contains(n.rightChild, val);
	        
        }
    }
    
    public boolean add(E val)
    {
    
        if (root == null) {
        
	        root = new Node(val);
	        return true;
	        
        }
        
        return add(root, val);
    }
    
    protected boolean add(Node n, E val)
    {     
        if (n == null) {
        
	    return false;
        }
        
        int cmp = val.compareTo(n.value);
        
        if (cmp == 0) {
        
	    return false; // this ensures that the same value does not appear more than once
	        
        } else if (cmp < 0) {
        
	    if (n.leftChild == null) {
	        
		n.leftChild = new Node(val);
                ++size; 
		return true;
		        
	    } else {
	        
		return add(n.leftChild, val);
	    } 	
	        
        } else {
        
	    if (n.rightChild == null) {
	        
		n.rightChild = new Node(val);
                ++size; 
		return true;
		        
	    } else {
	        
	        return add(n.rightChild, val);
		        
	    } 	
        }
    }
	    
    int size() 
    {
        return size;
    }
    
    public boolean remove(E val) 
    {
	return remove(root, null, val);
    }
    
    protected boolean remove(Node n, Node parent, E val) // TODO: add '--size' 
    {
	if (n == null) return false;
    
	if (val.compareTo(n.value) == -1) {
	        
	    return remove(n.leftChild, n, val);
	    	    
	} else if (val.compareTo(n.value) == 1) {
	        
	    return remove(n.rightChild, n, val);
	        
	} else {
	        
	    if (n.leftChild != null && n.rightChild != null) {
	        
	        n.value = maxValue(n.leftChild);
	        
	        remove(n.leftChild, n, n.value);
	        
	    } else if (parent == null) {
	        
	        root = n.leftChild != null ? n.leftChild : n.rightChild;
	        
	    } else if (parent.leftChild == n) {
	        
	        parent.leftChild = n.leftChild != null ? n.leftChild : n.rightChild;
	        
	    } else {
	        
	        parent.rightChild = n.leftChild != null ? n.leftChild : n.rightChild;
	    }
	    --size; 
	    return true;
	}
    }

     // prints in level order print
    public void levelOrderPrint()  
    {
       System.out.println("\nPrinting tree:");
        
	   int height = height(root); // In terms of the display, assume full and balanced

        /* 
           num = (2 raised to the power of the height); // number in bottom level
           num_spaces = num + the space between (num - 1) elements + (extra space between each pair: there are num/2 pairs) <-- double check this.
           etc.
        */	    
        Node startNode = root;
        
        Queue<Pair> queue = new LinkedList<Pair>();
	    
        int level = 0;
	    
        queue.add(new Pair(startNode, level));

        int prior_level = -1;
        
        while(!queue.isEmpty())   {
        
            Pair pr = queue.poll();
            int current_level = pr.level;
   
            if (current_level != prior_level) { // If level changes, print indentation of spaces

               System.out.append('\n');
 
               for (int i = 0; i < height - current_level; ++i) // Print out spaces based on level.
                   System.out.append(' ');

               prior_level = current_level;
            }

            System.out.print(pr.node.value);
            System.out.print(' ');
            
            if(pr.node.leftChild != null)
               queue.add(new Pair(pr.node.leftChild, current_level + 1));
             
            if(pr.node.rightChild != null)
               queue.add(new Pair(pr.node.rightChild, current_level + 1));
        }
        System.out.println(' ');
    }
      
    protected E maxValue(Node n)
    {
       if (n.rightChild == null)  {
		    
	   return n.value;
		    
       } else {
    	    
           return maxValue(n.rightChild);
       }
    }
    
    /*********************************************
     * 
     * IMPLEMENT THE METHODS BELOW!
     *
     *********************************************/
    
    // Method #1.
    public Node findNode(E val)      /* IMPLEMENT THIS METHOD! */
    {
        if (val == null) return null;

        Node current = root;
    
        while (current != null) {
    
          int rc = current.value.compareTo(val);
    
          if (rc == 0) {
    
              break;
    
          } else if (rc > 0) {
    
              current = current.leftChild;
    
          } else {
    
              current = current.rightChild;
          }
        }
        return current; 
    }
    /*
      Depth and Height properties of a Node

      Depth

        The depth of a node is the number of edges from the node to the tree's root node.
        A root node will have a depth of 0.

      Height     

        The height of a node is the number of edges on the longest path from the node to a leaf.
        A leaf node will have a height of 0.
    
       Properties of a tree:
    
            The height of a tree would be the height of its root node,
            or equivalently, the depth of its deepest node.
        
            Note that depth doesn't make sense for a tree.
        
            The diameter (or width) of a tree is the number of nodes on the longest path between any two leaf nodes. The tree below has a diameter of 6 nodes.
    */	

    // Method #2.
    /* 
      Iterative version of depth() 
      If val is null or val not found, return -1
      If val is in root, return 0; if in 2nd level, return 1; third, return 2, and so on...
     */
    protected int depth(E val) 
    {
        System.out.println("Calling: depth(" + val + ")"); 
    	levelOrderPrint();
    	
        if (val == null) return -1;

        int depth = 0;
          
        for (Node current = root; current != null; ++depth) {
    
          int rc = current.value.compareTo(val);
    
          if (rc == 0) {
   
              return depth;
    
          } else if (rc > 0) {
    
              current = current.leftChild;
    
          } else {
    
              current = current.rightChild;
          }
        }

        return -1; // not found
    }
    
    // Method #3.
    protected int height(E val)  /* IMPLEMENT THIS METHOD! */
    {
       if (val == null)  return -1;

       Node node = findNode(val);

       if (node == null) return -1; // If node does not exist return -1.
    
       return height(node); 
    }

    public int height()
    {
       return height(root);
    }
    
    // The height of a node is the number of edges from the node to the deepest leaf.  
    // input: node must exist in tree
    private int height(Node node)
    {
       if (node == null) { 
    
          return -1;
    
       }  else {
    
          return 1 + Math.max( height(node.leftChild), height(node.rightChild) );
       }
    }
   
    // Method #4.
    protected boolean isBalanced(Node n) /* IMPLEMENT THIS METHOD! */
    {
    	if (n == null || findNode(n.value) == null) return false; // If n is null or if n is not in tree, return false.
    	
        int leftHeight = height(n.leftChild);

        int rightHeight = height(n.rightChild);

        int diff = Math.abs( leftHeight - rightHeight);

        return (diff == 1 || diff ==0) ? true : false; // return true is absolute value is 0 or 1.
    }
    
    // Method #5. .
    public boolean isBalanced() // Visits Nodes in pre order, testing whether it is balanced. Return false if any node is not balanced.
    {
        Stack<Node> nodes = new Stack<>();
    
        nodes.push(root);
    
        while (!nodes.isEmpty()) {
    
          Node current = nodes.pop();
    
          boolean b = isBalanced(current);
    
          if (b == false)  // If not balanced, return false
              return false; 
    
          if (current.rightChild != null) 
              nodes.push(current.rightChild);
     
          if (current.leftChild != null) 
              nodes.push(current.leftChild);
        }
    
        return true; // No Nodes were not balanced, return true.
    }

    public static void main(String[] args)
    {
       BinarySearchTree<String> tree = new BinarySearchTree<String>();
       
       String arr[] = {"dog", "cat", "pig"};

       for(int i = 0; i < arr.length; i++) {
           tree.add(arr[i]);
           
       }
       
       for(int i = 0; i < arr.length; i++) {
           
           tree.levelOrderPrint();
           
           int depth = tree.depth(arr[i]);
           System.out.println("depth(" + arr[i] + ") = " + depth);
        }
     }

}
