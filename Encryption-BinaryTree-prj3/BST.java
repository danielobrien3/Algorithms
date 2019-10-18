import java.util.Scanner;

/* A BSTNode represents a node in a binary search tree. Each BSTNode object
 * stores a single item (called "data"). Each object also has left and right
 * pointers, which point to the left and right subtrees.
 *
 * The BST can be seen as superclass of the BSTNode class, so that the BST 
 * may make changes to the internals of a BSTNode.
 *
 * The constructor is provided for you; read it carefully.
 *
 * The getLeft(), getRight(), and getData() methods are useful for the
 * EncryptionTree class (or any class that wants to have read-only access to the
 * nodes of a BST).
 *
 * The printPreorder() traverses this node and its children recursively in
 * pre-order and prints each node it visits to standard output (i.e.
 * System.in). It presumes that "data" can be printed; that is, 
 * "System.out.print(this.data)" is a statement that makes sense. At each 
 * level of the tree it adds two spaces of indentation to show the structure 
 * of the tree. The run-time of printPreorder() is O(n). Can you figure out 
 * why?  Could it be made more efficient?
 *
 * The minNode() and maxNode() methods are useful in verifySearchOrder(). They
 * should find the leftmost and rightmost node at or below the node they are
 * called on. These can be implemented recursively or iteratively.
 *
 * The function verifySearchOrder() can be used to do verifications of the
 * binary search tree's order. It can and should be used for testing purposes.
 * If you implement minNode() and maxNode() efficiently, the run-time of
 * verifySearchOrder() is O(n^2) for this (potentially unbalanced) tree. Can you
 * figure out why?  Could it be made more efficient using different techniques?
 *
 * No one may call the copy constructor on a BSTNode, it is hereby forbidden,
 * so it is protected and will crash the program if called.
 */

class BSTNode {
  protected  BSTNode(BSTNode t) { assert(false); }

  protected  String data;
  protected  BSTNode left;
  protected  BSTNode right;

  public BSTNode(String d, BSTNode l, BSTNode r) {
    data = d; left = l; right = r;
  }

  public BSTNode getLeft()  { return left;  }
  public BSTNode getRight()  { return right; }
  public String getData()    { return data;  }
  
  public void printPreorder(String indent) {
    System.out.println(indent + this.data);
    indent += "  ";

    if(this.getLeft() == null)
      System.out.println(indent + "NULL");
    
    else if(this.getLeft().getData()==null)
      System.out.println(indent + "NULL");
    
    else 
      getLeft().printPreorder(indent);
    
    if(this.getRight() == null)
        System.out.println(indent + "NULL");
    
    else if(this.getRight().getData()==null)
      System.out.println(indent + "NULL");

    else 
      getRight().printPreorder(indent);
  }
   
  public BSTNode minNode() { 
    //base case, no left children
    if (getLeft() == null)
      return this;
    else if(getLeft().getData() == null)
      return this;

    //recursive case
    else return getLeft().minNode();
  }


  public BSTNode maxNode() { 
    //base case, no right children
    if(this.getRight() == null)
      return this;
    //recursive case 
    else return this.getRight().maxNode();
  }

  /* professor's implementation of verifySearchOrder(); don't change it */
  public void verifySearchOrder() {
    if (getLeft() != null) {
      assert(getLeft().maxNode().data.compareTo(data) == -1);
      getLeft().verifySearchOrder();
    }
    if (right != null) {
      assert(data.compareTo(right.minNode().data) == -1);
      right.verifySearchOrder();
    }
  }
}

/* A BST is a String-based class, but could easily be coded as a generic-type 
 * type class (e.g. with T), that represents a binary search tree. It has one
 * data member, "root", which is a pointer to the root of the tree.
 *
 * The constructor is provided for you.
 *
 * The insert() method places the given item in the tree, unless the item is
 * already in the tree. The insertion should follow the algorithm we discuss in
 * class.
 *
 * The remove() method removes the given item from the tree, if it is in the
 * tree. The remove should follow the algorithm we discuss in class.
 *
 * The printPreorder() and verifySearchOrder() methods simply calls the relevant
 * per-node methods on the root.
 *
 * No one may call the copy constructor on a BST, it is hereby forbidden, so
 * it is protected and will crash the program if called.
 */
class BST {
  protected BST(BST t) { assert(false); }
  protected BST isEqual(BST t) { assert(false); return this; }
  
  protected BSTNode root;

  public BST() {
    root = null; 
  }

  public void insert(String item) { 
    if(root == null){
      root = new BSTNode(item, null, null);
    }
    else{
      BSTNode pointer = root;
      BSTNode trail = pointer;

      //find bottom of tree
      while(pointer!=null){
        trail = pointer;

        //if item is found in tree, exit loop. set trail to null
        if(trail.getData().equals(item)){
          pointer = null;
          trail = null;
        }
        else if(item.compareTo(trail.getData())<0)
          pointer = pointer.left;
        else if(item.compareTo(trail.getData())>0)
          pointer = pointer.right;
      }

      //if trail is not null (item not found in tree), find where to insert item
      if(trail != null){
        if(item.compareTo(trail.getData())<0){
          trail.left = new BSTNode(item, null, null);
        }
        else if(item.compareTo(trail.getData())>0){
          trail.right = new BSTNode(item, null, null);
        }
      }
    }
  }
  
  

  public void remove(String item) { 
    if (root != null){
      BSTNode pointer = root;
      BSTNode trail = pointer;

      //find node to be removed
      while(!trail.getData().equals(item) && pointer!=null){
        trail = pointer;
        if(item.compareTo(pointer.getData())>0){
          pointer = pointer.getRight();
        }
        else if(item.compareTo(pointer.getData())<0){
          pointer = pointer.getLeft();
        }
      }

      //remove item if found
      if(trail.getData().equals(item)){
        //Node has two children
        if(trail.getRight() != null){
          if(trail.getLeft() != null){
            //set trail data = to right min node
            trail.data = trail.getRight().minNode().getData();
            if(trail.getRight() == trail.getRight().minNode())
              trail.right = trail.getRight().minNode().right;

            /* if trail.right is trail.right.minNode(), 
            set trail.right to null */
            /*if(trail.getRight() == trail.getRight().minNode()){
              trail.right = null;
            }*/
            //else find parent of minNode
            else{
              pointer = trail.right;
              BSTNode minTrail = pointer;

              //while pointer.left is not equal to minNode, pointer = pointer.left
              while(pointer != trail.getRight().minNode()){
                minTrail = pointer;
                pointer = pointer.left;
              }

              //set minTrail.left(aka minNode) to minNode right tree
              minTrail.left = minTrail.left.right;
            }    
          }

          //Node has one child (right). Replace node with child.
          else{
            trail.data = trail.right.getData();
            trail.left = trail.right.getLeft();
            trail.right = trail.right.getRight();
          }
        }
        //Node has one child (left). Replace node with child
        else if(trail.getLeft() != null){
          trail.data = trail.left.getData();
          trail.right = trail.left.getRight();
          trail.left = trail.left.getLeft();
        }

        //Node has no children, remove node
        else{
          //if node is the root, set root to null
          if(root.data.equals(item)){
            root = null;
          }
          else{
            pointer = trail;
            BSTNode current = root; 
            boolean parent = false;
            String direction = "";

            //search through tree to find the node's parent
            while(parent==false){
              //if item is smaller than current
              if(item.compareTo(current.data)<0){
                //check if current is node parent
                if(current.left == pointer){
                  parent = true;
                  direction = "left";
                }
                else 
                  current = current.left;
              }
              else if(item.compareTo(current.data)>0){
                //check if current is node parent
                if(current.right == pointer){
                  parent = true;
                  direction = "right";
                }
                else 
                  current = current.right;
              }
            }

            //use direction to set correct node to null
            switch(direction){
              case "left": 
                current.left = null;
                break;
              case "right":
                current.right = null;
                break;
            }
          }
        }
      }
    }
  }

  public void printPreorder() { 
    if (root != null) root.printPreorder(""); 
    else System.out.println("");
  }

  public void verifySearchOrder() { if (root != null) root.verifySearchOrder(); }

}

/* An EncryptionTree is a special type of BST which knows how to encrypt a
 * String object (e.g. word) into a string that represents the path to the 
 * object in the tree, and decrypt a path into the String object (e.g. word) 
 * it leads to.
 *
 * The constructor method is provided for you.
 *
 * The encrypt() method takes a String object and returns a string of the form 
 * "rX" where "r" is a literal letter r, and X is a sequence of 0 and 1 
 * characters (which may be empty). The r stands for "root", and each 0 and 1 
 * represent the getLeft()/right path from the root to the given object, with 0 
 * indicating a getLeft()-branch and 1 indicating a right-branch. If the object is 
 * not in the dictionary, an empty string (or the string "?") should be 
 * returned.
 *
 * The decrypt() method takes an encrypted string (or path through the tree) in
 * the form provided by encrypt(). It should return a pointer to the associated
 * string for the given path (or NULL if the path is invalid).
 */
class EncryptionTree extends BST {
  public EncryptionTree() {}
  
  public String encrypt(String item) {
    if(root == null)
      return "?";
    
    else{
      String path = "r";
      BSTNode pointer = root;

      while(!item.equals(pointer.data)){
        if(item.compareTo(pointer.data) < 0){
          path +='0';
          pointer = pointer.getLeft();
        }
        else if(item.compareTo(pointer.data) > 0){
          path +='1';
          pointer = pointer.getRight();
        }
        if(pointer == null)
          return "?";
      }
      return path;
    }
  }

  public String decrypt(String path) { 
    //only accept paths that start with the root.
    if(path.charAt(0) == 'r'){
      BSTNode pointer = root;

      for(int i=1; i<path.length(); i++){
        if(pointer != null){
          char move = path.charAt(i);
          if(move=='0')
           pointer = pointer.getLeft();
          if(move=='1')
           pointer = pointer.getRight();
        }
      }

      if(pointer == null)
       return "?";
      else return (pointer.data);
    }
    else 
      return "?";
  }
    
}


