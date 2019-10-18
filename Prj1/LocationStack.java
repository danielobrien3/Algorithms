/* Class declaration for a simple stack of Location objects. It is not
 * a difficult class; it can contain only Location objects. It can
 * grow and shrink because it is a linked structure. The class
 * LocationStackNode (below) encapsulates the nodes of the stack.
 *
 * Methods push(), pop(), and getTop() provide standard stack methods.
 * Using assert() to check for problems in these three methods could
 * be useful (hint).  isEmpty() returns true if the stack is empty,
 * false otherwise. isOn() returns true if the given Location is on
 * the stack, otherwise returns false.
 *
 * streamOut() streams out the stack from bottom to top. This method
 * should NOT make a copy of the stack. Instead, it should require two
 * passes over the stack to print the stack. The first pass will
 * traverse the stack to top->bottom, reversing the links of the nodes
 * as it goes. The second pass will traverse from bottom->top,
 * printing each Location as it is visited, and undoing the reversing
 * of the node links.
 *
 * The default constructor initializes the data members as
 * appropriate. The copy constructor is not usable in this class;
 * therefore it is private and will fail on an assert() if called.
 *

 
 * The data member top is a reference to the top node.
 */

class LocationStack {
  private LocationStack(LocationStack s) { assert(false); }
  private LocationStackNode top;

  LocationStack() {
    LocationStack top = null;
  }

  void push(Location loc) {
    LocationStackNode temp = new LocationStackNode(loc, top);
    top = temp;
  }

  void pop() {
    top = top.getNextNode();
  }
  
  Location getTop() {
    return top.getLocation();
  }

  boolean isEmpty() {
    if(top == null)
      return true;
    else
      return false;
  }
  boolean isOn(Location loc) {
    LocationStackNode temp = top;
    boolean test = false;
    while(temp != null){
      if (loc.isEqual(temp.getLocation())){
        return true;
      }
      else temp = temp.getNextNode();
    }
    return false;
  }

  void streamOut(LocationStack s) {
    System.out.println("Solution found:");
    s = new LocationStack();
    while(top != null){
      s.push(top.getLocation());
      this.pop();
    }
    while(!s.isEmpty()){
      s.getTop().streamOut();
      s.pop();
    }
  }
}

/* Class declaration for a Node on a LocationStack. Each node contains
 * a Location and a link to the next LocationStackNode (the one below
 * it on the stack). 
 *
 * The only constructor that may be used for this class is the one
 * which takes values to initialize its data members. Other
 * constructors may not be called as they are not necessary. These
 * restrictions help prevent us from accidentally making multiple
 * nodes that all point to the same next node.
 *
 * If we set a LocationStack objet to null, this should invoke the garbage
 * collector and delete the nextNode in LocationStackNode, so that deleting
 * the top of the stack has a chaining effect that deletes every node
 * on the stack.
 *
 * The get/set methods for this class are self-explanatory, and are
 * the interface by which you should modify a node as necessary.
 */
class LocationStackNode {
  private LocationStackNode() { assert(false); }
  private LocationStackNode(LocationStackNode s) { assert(false); }

  private Location location;
  private LocationStackNode nextNode;

  LocationStackNode(Location loc, LocationStackNode next) {
    this.location = loc;
    this.nextNode = next;
  }

  Location getLocation() {
    return this.location;
  }
  LocationStackNode getNextNode() {
    return nextNode;
  }
  void setNextNode(LocationStackNode next) {
    this.nextNode = next;
  }
}
