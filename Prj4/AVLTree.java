import java.util.Scanner;
import java.util.LinkedList;

/* An AVLNode represents a node in an AVL-balanced binary search tree. Each
 * AVLNode object stores a single item (called "data"). Each object also has
 * left and right references, which point to the left and right subtrees, and it
 * knows its own height (the path length to its deepest descendant).
 *
 * The AVLTree can be seen as superclass of the AVLNode class, so that the 
 * AVLTree may make changes to the internals of an AVLNode.
 *
 * Many of the methods in this class are virtually identical to those in the
 * BSTNode in the previous project (#3), including the constructor,
 * getLeft(), getRight(), getData(), printPreorder(), verifySearchOrder(),
 * minNode(), maxNode(), and the copy constructor.
 *
 * The function verifyBalance() can be used to do verifications that the AVL
 * balance property holds at each node. It also can and should be used for
 * testing purposes. What is its running time?
 *
 * The singleRotateLeft() and singleRotateRight() methods do a single rotation
 * on the node they are called on, and return a reference to the node that takes
 * its place (so that the node's parent's reference can be changed).  Note that
 * these methods should update the heights of some nodes as necessary.
 *
 * The doubleRotateLeftRight() and doubleRotateRightLeft() methods do a double
 * rotation on the node they are called on. This is really simple if you have
 * implemented the single rotation methods; my double rotation methods are two
 * lines each. These methods return a reference to the node which took the place
 * of the node the method was called on (so that the node's parent's reference 
 * can be changed).
 *
 * The getHeight() method is a static method which takes a reference to a node,
 * and returns the height of that node (or -1 if the reference is NULL). This
 * makes it easy to find the height of any node with a reference, without having
 * to check for NULL.
 *
 * The updateHeight() method calculates and updates the value of the height on
 * the node it's called on. It assumes that the height values for the two
 * children of this node are correct, and uses them.
 */
class AVLNode {
  AVLNode(AVLNode t) { assert(false); }

  AVLNode(String d, AVLNode l, AVLNode r, int h) {
    data = d; left = l; right = r; height = h; 
  }
  
  protected String data;
  protected AVLNode left, right;
  protected int height;
  
  protected AVLNode singleRotateLeft() {
    //MY IMPLEMENTATION
    //rotate original node left. Bring right node left subtree left as well.
    this.left = new AVLNode(this.data, this.left, this.right.left, 0);
    this.left.updateHeight();
    //rotate right node to og node
    this.data = right.data;
    this.right = right.right;
    if(this.right != null){
      this.right.updateHeight();
    }
    this.updateHeight();
    return this;

    //MY VERSION OF TEXTBOOKS IMPLENTATION
    /*
    AVLNode n = n2.right;
    n2.right = n2.left;
    n.left = n2;
    n.updateHeight();
    n2.updateHeight();
    return n2;
    */

  }

  protected AVLNode singleRotateRight() {
    //MY VERSION OF TEXTBOOK'S IMPLEMENTATION OF ROTATE RIGHT
    /*
    AVLNode n = n2.left;
    n2.left = n.right;
    n.right = n2;
    n.updateHeight();
    n2.updateHeight();
    return n2;
    */

    //MY IMPLEMENTATION OF ROTATE RIGHT
    //rotate original node right, bring left node right subtree right as well.
    this.right = new AVLNode(this.data, this.left.right, this.right, 0);
    this.right.updateHeight();
    //rotate left node to OG node.
    this.data = left.data;
    this.left = left.left;
    if(this.left !=null){
      this.left.updateHeight();
    }
    this.updateHeight();
    return this;
  }

  protected AVLNode doubleRotateLeftRight() {
    //MY VERSION OF TEXTBOOK's IMPLEMENTATION
    /*
    n.left = singleRotateLeft(n.left);
    return singleRotateRight(n);
    */


    //MY IMPLEMENTATION 
    this.left = this.left.singleRotateLeft();
    return this.singleRotateRight();
  }

  protected AVLNode doubleRotateRightLeft() {
    //MY VERSION OF TEXTBOOK'S IMPLEMENTATION
    /*
    n.right = singleRotateRight(n.right);
    return singleRotateLeft(n);
    */

    //MY IMPLEMENTATION 
    this.right = this.right.singleRotateRight();
    return this.singleRotateLeft();
  }

  protected static int getHeight(AVLNode n) { 
    return n != null ? n.height : -1; 
  }

  protected void updateHeight() {
    int lh = getHeight(left);
    int rh = getHeight(right);
    height = (lh > rh ? lh : rh) + 1;
  }


  public AVLNode getLeft()  { return left;  }
  public AVLNode getRight() { return right; }
  public String getData()   { return data;  }

  public void printPreorder(String indent) {
    System.out.println(indent + this.data);
    indent += "  ";

    if(this.left != null)
      left.printPreorder(indent);
    
    else 
      System.out.println(indent + "NULL");
    
    if(this.right != null)
      right.printPreorder(indent);
    
    else
      System.out.println(indent + "NULL");
    /*System.out.println(indent + this.data);
    indent += "  ";
    if(this.left == null){
      System.out.println(indent + "NULL");
    }
    else {
      left.printPreorder(indent);
    }
    if(this.right != null){
      right.printPreorder(indent);
    }
    else System.out.println(indent + "NULL");*/
  }

  /*Method to check if Node is null. Checks both Node and Node data.*/
  public Boolean isNull(){
    if(this == null){
      return true;
    }
    else if (this.data == null){
      return true;
    }
    else return false;
  }

  /* professor's implementation of verifySearchOrder(); don't change it */
  public void verifySearchOrder() {
    if (left != null) {
      assert(left.maxNode().data.compareTo(data) == -1);
      left.verifySearchOrder();
    }
    if (right != null) {
      assert(data.compareTo(right.minNode().data) == -1);
      right.verifySearchOrder();
    }
  }

  /* professor's implementation of verifyBalance(); don't change it */
  public void verifyBalance() {
    int heightDiff = Math.abs(getHeight(left) - getHeight(right));
    assert(heightDiff <= 1); 
    if (left  != null) left.verifyBalance();
    if (right != null) right.verifyBalance();
  }

  public AVLNode minNode() {
    return left == null ? this : this.left.minNode();
  }

  public AVLNode maxNode() {
    return right == null ? this : this.right.maxNode();
  }
}


/* An AVLTree is a String-based class that represents an AVL-balanced binary 
 * search tree. It has one data member, "root", which is a reference to the 
 * root of the tree.
 *
 * Many of the methods in this class are virtually identical to methods in the
 * BST from the previous project (#3), including the constructor,
 * printPreorder(), verifySearchOrder(), and copy constructor.
 *
 * The insert() and remove() methods behave as in the plain BST, but both
 * methods should rebalance the tree as necessary. This is best done by creating
 * an array of references to AVLNode objects as the insert/remove methods search
 * for the place to do their work.  This array of references represents the path
 * taken to get from the root to the place where a change occurs in the tree.
 * Note that for remove(), this path might go deeper than the node removed, in
 * the case of removing a node with two children (think carefully about this).
 * After insert/remove finish updating the tree, they can pass the path to
 * rebalancePathToRoot() which actually does the rebalancing. Think about how
 * large the array of references needs to be, at its largest. An AVL tree with
 * height 30 must have at least 3,524,577 nodes, and if it has height 50, it
 * must have at least 53,316,291,172 nodes -- probably more than we care to put
 * in the tree. These results come from the minimum size of an AVL tree of
 * height h, which is described in your book as: S(h) = S(h - 1) + S(h - 2) + 1
 * (and base cases S(0) = 1, S(1) = 2).
 *
 * The printLevelOrder() method prints out all the nodes in the tree in
 * level-order (root, then the root's children, then their children, etc.). This
 * is like performing a breadth-first search of the tree. The method should put
 * up to 20 nodes on each line, and use multiple lines as necessary. This method
 * should use a Java queue, and it is iterative (not recursive). This method is
 * useful if we want to transmit the information for building exactly the same
 * tree to our correspondent. If we were to take all the non-NULL nodes and
 * insert them in the order printed by this method, we would get the exact same
 * tree. We would not always be able to construct the exact same tree if we were
 * to use printPreorder() instead.
 *
 * The rebalancePathToRoot() method takes an array of references to AVLNode
 * objects, and the number of references that are on the array. This array should
 * represent the path that needs rebalancing after an insert or remove. It's
 * probably best to have the root at the start of the array. This method should
 * walk from the bottom of the path to the root, checking for imbalances, and
 * correcting any it finds by calling rotation methods as necessary to correct
 * imbalances.
 */
class AVLTree {
  protected AVLNode root;
  
  AVLTree(AVLTree t) { assert(false); }

  AVLTree() { root = null; }

  private int balanceFactor(AVLNode node){
    if(node.right == null){
      return (-1 * (node.getHeight(node)));
    }
    else if (node.left == null){
      return node.getHeight(node);
    }
    int rightHeight = node.getHeight(node.getRight());
    int leftHeight = node.getHeight(node.getLeft());

    return (rightHeight-leftHeight);
  }

  protected void rebalancePathToRoot(AVLNode[] path, int numOnPath) {
    //my version of textbook's implementation
    for(int i=numOnPath; i>=0; i--){
      if(path[i] != null){
        path[i].updateHeight();

        if(root.getHeight(path[i].left) - root.getHeight(path[i].right) > 1){
          if(root.getHeight(path[i].left.left) >= root.getHeight(path[i].left.right)){
            path[i] = path[i].singleRotateRight();
          }
          else {
            path[i] = path[i].doubleRotateLeftRight();
          }
        }
        else if(root.getHeight(path[i].right) - root.getHeight(path[i].left)>1){
          if(root.getHeight(path[i].right.right) >= root.getHeight(path[i].right.left)){
            path[i] = path[i].singleRotateLeft();
          }
          else {
            path[i] = path[i].doubleRotateRightLeft();
          }
        }
        path[i].updateHeight();
      }
    }

    //My initial implementation

    /*
    for(int i = numOnPath; i>=0; i--){
      if(path[i] != null){
        path[i].updateHeight();
        int balance = this.balanceFactor(path[i]);

        if(balance == 2){
          AVLNode right = path[i].right;
          if(this.balanceFactor(right) == -1){
            System.out.println("right left rotate on " + path[i].data);
            path[i].doubleRotateRightLeft();
          }
          else if(this.balanceFactor(right) == 1){
            System.out.println("left rotate on " + path[i].data);
            path[i].singleRotateLeft();
          } 
        }
        else if(balance == -2){

          AVLNode left = path[i].left;
          if(this.balanceFactor(left)== 1){
            System.out.println("left right rotate on " + path[i].data);
            path[i].doubleRotateLeftRight();
          }
          else if(this.balanceFactor(left) == -1){
            System.out.println("right rotate on " + path[i].data);
            path[i].singleRotateRight();
          }
        }
      }
    }*/
  }



        /*
        if (balance > 1){
          //compare left subtree root to newly inserted item
          if(path[numOnPath].data.compareTo(path[i].left.data) < 0){
            path[i].singleRotateRight();
          }
          //check left subtree balance
          else if(path[i].getHeight(path[i].left.left) - path[i].getHeight(path[i].left.right) >= 0 ){
            path[i].singleRotateRight();
          }
          else{
            path[i].doubleRotateLeftRight();
          }
        }
        //compare right subtree root to newly inserted item
        if(balance < -1){
          //rotate single left or double right left
          if(path[numOnPath].data.compareTo(path[i].right.data) > 0){
            path[i].singleRotateLeft();
          }
          else if(path[i].getHeight(path[i].right.left) - path[i].getHeight(path[i].right.right) <= 0){
            path[i].singleRotateLeft();
          }
          else{        
            path[i].doubleRotateRightLeft();
          }
        }
      }
    }*/

  
  public void insert(String item) {

    if(root == null){
      root = new AVLNode(item, null, null, 0);
    }
    else{
      AVLNode pointer = root;
      AVLNode trail = pointer;

      //path and counter
      AVLNode[] path = new AVLNode[root.height+2];
      int count = 0;

      //find bottom of tree
      while(pointer!=null){
        path[count] = pointer;
        count++;
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
          trail.left = new AVLNode(item, null, null, 0);
          path[count] = trail.left;
          this.rebalancePathToRoot(path, count);
        }
        else if(item.compareTo(trail.getData())>0){
          trail.right = new AVLNode(item, null, null, 0);
          path[count] = trail.right;
          this.rebalancePathToRoot(path, count);
        }
      }
    }
  }
    

  public void remove(String item) {
    if (root != null){
      AVLNode pointer = root;
      AVLNode trail = pointer;

      //path and counter variables
      AVLNode[] path = new AVLNode[root.height+1];
      int count = 0;

      //find node to be removed
      while(!trail.getData().equals(item) && pointer!=null){
        trail = pointer;
        path[count] = pointer;
        count++;
        if(item.compareTo(pointer.getData())>0){
          pointer = pointer.getRight();
        }
        else if(item.compareTo(pointer.getData())<0){
          pointer = pointer.getLeft();
        }
      }


      //start node removal process if item was found
      if(trail.getData().equals(item)){
        
        if(trail.getRight() != null){
          if(trail.getLeft() != null){
            //Node has two children

            //set trail data = to right min node
            trail.data = trail.getRight().minNode().getData();

            if(trail.getRight() == trail.getRight().minNode()){
              trail.right = trail.getRight().minNode().right;
              if(trail.right != null)
                trail.right.updateHeight();
              count--;
              this.rebalancePathToRoot(path, count);
            }

            //else find parent of minNode
            else{
              pointer = trail.right;
              AVLNode minTrail = pointer;

              //while pointer is not equal to minNode, pointer = pointer.left
              while(pointer != trail.getRight().minNode()){
                path[count] = pointer;
                count++;
                minTrail = pointer;
                pointer = pointer.left;
              }
              //path now includes everything between root and minNode parent.
              //decrement counter to make up for extra increment
              count--;
              
              //set minTrail.left(aka minNode) to minNode right tree
              minTrail.left = minTrail.left.right;
              minTrail.updateHeight();
              this.rebalancePathToRoot(path, count);
            }    
          }

          //Node has one child (right). Replace node with child.
          else{
            trail.data = trail.right.getData();
            trail.left = trail.right.getLeft();
            trail.right = trail.right.getRight();
            trail.height = trail.right.getHeight(trail.right);
            path[count] = trail;
            this.rebalancePathToRoot(path, count);
          }
        }
        //Node has one child (left). Replace node with child
        else if(trail.getLeft() != null){
          trail.data = trail.left.getData();
          trail.right = trail.left.getRight();
          trail.left = trail.left.getLeft();
          trail.height = trail.left.getHeight(trail.left);
          path[count] = trail;
          this.rebalancePathToRoot(path, count);
        }

        //Node has no children, remove node
        else{
          //if node is the root, set root to null
          if(root.data.equals(item)){
            root = null;
          }
          else{
            pointer = trail;
            AVLNode current = root; 
            boolean parent = false;
            String direction = "";
            path = new AVLNode[root.height];
            count = 0;

            //search through tree to find the node's parent
            while(parent==false){
              path[count] = current;
              count++;
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
            //path now has nodes from root to item parent
            //decrement count once since it is incremented one too many times
            count--;

            //use direction to set correct node to null
            switch(direction){
              case "left": 
                current.left = null;
                this.rebalancePathToRoot(path, count);
                break;
              case "right":
                current.right = null;
                this.rebalancePathToRoot(path, count);
                break;
            }
          }
        }
      }
    }
  }

  public void printLevelOrder() {
  
    if(root.isNull()){
      System.out.println("NULL");
    }
    else{
      LinkedList<AVLNode> q = new LinkedList<AVLNode>();
      int count = 0;
      System.out.print(root.getData() + " ");
      count++;
      q.add(root);
      while(!q.isEmpty()){
        if(count == 20){
          System.out.print("\n");
          count = 0;
        }
        if(q.peek().left != null){
          System.out.print(q.peek().left.getData() + " ");
          count++;
          q.add(q.peek().left);
        }
        else{
          System.out.print("NULL ");
          count++;
        }
        if(count == 20){
          System.out.print("\n");
          count = 0;
        }
        if(q.peek().right != null){
          System.out.print(q.peek().right.getData() + " ");
          count++;
          q.add(q.peek().right);
        }
        else {
          System.out.print("NULL ");
          count++;
        }
        q.remove();
      }
      System.out.print("\n");
    }

    
    /*

    if(root.isNull()){
      System.out.println(" ");
    }
    else{
      LinkedList<AVLNode> q = new LinkedList<AVLNode>();
      int count = 0;
      q.add(root);
      while(!q.isEmpty()){
        if(count == 20){
          System.out.print("\n");
          count = 0;
        }
        System.out.print(q.peek().getData() + " ");
        count++;

        if(q.peek().left != null)
          q.add(q.peek().left);
        if(q.peek().right != null)
          q.add(q.peek().right);
        q.remove();
      }
    }
    */
  }

  public void printPreorder() { 
    if (root != null){
      root.printPreorder("");
    } 
    else System.out.println("");
   }

  public void verifySearchOrder() { 
    if (root != null) root.verifySearchOrder(); 
  }

  public void verifyBalance() { 
    if (root != null) root.verifyBalance(); 
  }

}


/* The EncryptionTree for this project is exactly the same as for the previous
 * project, except that it now has an AVLTree as its parent class.
 */
class EncryptionTree extends AVLTree {
  EncryptionTree() {}

  public String encrypt(String item) {
    if(root == null)
      return "?";
    else{
      String path = "r";
      AVLNode pointer = root;

      while(!item.equals(pointer.data)){
        if(item.compareTo(pointer.data) < 0){
          path +='0';
          pointer = pointer.left;
        }
        else if(item.compareTo(pointer.data) > 0){
          path +='1';
          pointer = pointer.right;
        }
        if(pointer == null)
          return "?";
      }
      return path;
    }

  }

  public String decrypt(String path) {
    if(path.charAt(0) == 'r'){
      AVLNode pointer = root;

      for(int i=0; i<path.length(); i++){
        if(pointer != null){
          char move = path.charAt(i);
          if(move=='0')
            pointer = pointer.left;
          if(move=='1')
            pointer = pointer.right;
        }
        else return "?";
      }
      if(pointer == null)
        return "?";
      else 
        return (pointer.data);
    }
    else return "?";
  }
}
