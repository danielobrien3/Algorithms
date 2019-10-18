/* CHANGES FROM PROJECT 5:
 *  - There is a new array called dataToHeap, which keeps track of where each
 *    data item lives in the heap ordering. Adding this array will require
 *    changes to every method in the class except for getMinItem() and
 *    getNumItems().
 *
 *  - The insert() method returns an integer "key", which is the index where the
 *    new item is inserted into the data array. This key can be used later to
 *    get back to that item, so that the item can be changed.
 *
 *  - The isOnHeap() method checks to see if a given key is on the heap.
 *
 *  - The changeItemAtKey() method takes a key and a new item, uses the new item
 *    to replace the old item at data[key], and performs bubble operations as
 *    necessary to maintain heap order.
 *
 *  - You will need to define what a vertex is and define a way to compare
 *    vertices, i.e., compareTo(). See your textbook for details about a Vertex
 *    class.
 */

class Vertex {
  public int distance;
  public Vertex previousNode;
  public boolean server;
  public int id;
  public String name;
  public int totalDelay;
  public boolean known;

  public Vertex(int id, int distance){
    this.id = id;
    this.distance = distance;
    this.totalDelay = 0;
  }

  public Vertex(int id, int distance, String name, boolean server){
    this.id = id;
    this.distance = distance;
    this.name = name;
    this.server = server;
  }

  public boolean isLess(Vertex v){
    if(this.distance == v.distance){
      if(this.id < v.id){
        return true;
      }
      else{
        return false;
      }
    }
    else if (this.distance < v.distance){
      return true;
    }
    else {
      return false;
    }
  }
}

class ArrayHeap {
  private Vertex[] data;
  private int[] dataToHeap;
  private int[] heapAndFreeStack;

  private int numItems;
  private int capacity;

  private void doubleCapacity() {
    Vertex[] tempData = new Vertex[capacity*2];
    int[] tempHeap = new int[capacity*2];

    for(int i=0; i < numItems; i++){
      tempData[i] = data[i];
      tempHeap[i] = heapAndFreeStack[i];
    }

    for(int i=capacity; i<capacity*2; i++){
      tempHeap[i] = i;
    }

    data = tempData;
    heapAndFreeStack = tempHeap;
    capacity = capacity * 2;
  }

  private void bubbleUp(int ndx) {
    int parent = (ndx - 1)/2;
    //if data[heap[ndx]] is less than its parent data, bubble up. 
    if(data[heapAndFreeStack[ndx]].isLess(data[heapAndFreeStack[parent]])){
      int temp = heapAndFreeStack[ndx];
      heapAndFreeStack[ndx] = heapAndFreeStack[parent];
      heapAndFreeStack[parent] = temp;
      this.bubbleUp(parent);
    }
  }

  private void bubbleDown(int ndx) {
    int child1 = (2 * ndx) + 1;
    int child2 = (2 * ndx) + 2;

    //check if child 2 exists in heap
    if(child2<numItems){

      //check which child is smaller
      if(data[heapAndFreeStack[child1]].isLess(data[heapAndFreeStack[child2]])){
        //child 1 is smaller than child 2. 

        //check if data[heap[child 1]] is smaller than data[heap[ndx]]
        if(data[heapAndFreeStack[child1]].isLess(data[heapAndFreeStack[ndx]])){
          
          //data[heap[child1]] is smaller than data[heap[ndx]]
          int temp = heapAndFreeStack[ndx];
          heapAndFreeStack[ndx] = heapAndFreeStack[child1];
          heapAndFreeStack[child1] = temp;
          //bubble down from child
          this.bubbleDown(child1);
        }
      }

      //check if data[heap[child2]] is less than data[heap[ndx]]
      else if(data[heapAndFreeStack[child2]].isLess(data[heapAndFreeStack[ndx]])){
        int temp = heapAndFreeStack[ndx];
        heapAndFreeStack[ndx] = heapAndFreeStack[child2];
        heapAndFreeStack[child2] = temp;
        //bubble down from child
        this.bubbleDown(child2);
      }
    }
  }


  public ArrayHeap() {
    capacity = 1;
    numItems = 0;
    data = new Vertex[1];
    heapAndFreeStack = new int[1];
    heapAndFreeStack[0] = 0;
  }
  public ArrayHeap(ArrayHeap h) {
    // -
  }

  public int insert(Vertex item) {
    
    data[heapAndFreeStack[numItems]] = item;
    int temp = heapAndFreeStack[numItems];
    numItems++;
    this.bubbleUp(numItems-1);
    if(numItems == capacity)
      this.doubleCapacity();
    return temp;
  }

  public void removeMinItem() {
    int tempNdx = heapAndFreeStack[0];
    heapAndFreeStack[0] = heapAndFreeStack[numItems-1];
    heapAndFreeStack[numItems-1] = tempNdx;
    numItems--;
    this.bubbleDown(0);
  }

  public Vertex getMinItem() {
    return data[heapAndFreeStack[0]];
  }

  public int getNumItems() {
    return numItems;
  }

  public boolean isOnHeap(int key) {
    for(int i = 0; i<numItems; i++){
      if(data[heapAndFreeStack[i]].id == key)
        return true;
    }
    return false;
  }

  public void changeItemAtKey(int key, Vertex newItem) {
    for(int i = 0; i<numItems; i++){
      if(data[heapAndFreeStack[i]].id == key)
        data[heapAndFreeStack[i]] = newItem;
    }
  }

}
