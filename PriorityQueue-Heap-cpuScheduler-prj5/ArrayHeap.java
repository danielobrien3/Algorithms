import java.lang.Math;

/* An ArrayHeap is a binary heap that is implemented using arrays. The heap can
 * grow dynamically if it gets full (it should double in capacity each time it
 * gets full).
 *
 * The default constructor constructs an empty ArrayHeap.
 *
 * The copy constructor creates a logical copy of the given ArrayHeap,
 * storing the values in its own copy. It should resize the heap as necessary 
 * so that the two have the same capacity.
 *
 * The insert method inserts the given item into the heap (at the bottom),
 * and then restores the heap order property by bubbling the item up.
 *
 * The removeMinItem method removes the minimum item at the root of the
 * heap, places the last element in the root's position, and bubbles it
 * down to restore the heap property.
 *
 * The getMinItem method returns the minimum item at the top of the heap.
 *
 * The getNumItems method returns the number of items that are on the heap.
 *
 * The doubleCapacity method doubles the capacity of the heap (the data and
 * heapAndFreeStack data members).
 *
 * The bubbleUp method can be implemented iteratively or recursively. It
 * starts at the item given by ndx, and moves it up the heap towards the
 * root in order to preserve the heap order property.
 *
 * The bubbleDown method can be implemented iteratively or recursively. It
 * starts at the item given by ndx, and moves it down the heap towards the
 * leaves in order to preserve the heap order property.
 *
 * The data member "data" contains the items that are stored in the heap.
 * Once an item is placed in data, it should not move.
 *
 * The data member "heapAndFreeStack" describes the structure of the heap (in
 * heapAndFreeStack[0] through heapAndFreeStack[numItems-1]), and it has a stack
 * of the indexes in data that are not being used (in heapAndFreeStack[numItems]
 * through heapAndFreeStack[capacity-1]).  Every value in this array (whether in
 * the heap or stack region) is an index into the array "data".
 *
 * The data member "numItems" contains the number of items on the heap, and
 * serves as the dividing line between the heap structure and the stack of free
 * items.
 *
 * The data member "capacity" represents the number of items that the heap can
 * contain before requiring extra space. The data and heapAndFreeStack arrays
 * both are capacity long.
 */

class ArrayHeap {
  private Process[] data;
  private int[] heapAndFreeStack;

  private int numItems;
  private int capacity;

  private void doubleCapacity() {
    Process[] tempData = new Process[capacity*2];
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
//after refactoring
    /*
    int child1 = (2*ndx) + 1;
    int child2 = (2*ndx) + 1;
    int lesserChild = 0;

    //get lesser child
    if(child2<numItems){
      if(data[heapAndFreeStack[child1]].isLess(data[heapAndFreeStack[child2]])){
        lesserChild = child1;
      }
      else lesserChild = child2;
    }
    else if(child1 < numItems){
      lesserChild = child1;
    }
    if(child1 < numItems){
      int temp = heapAndFreeStack[ndx];
      heapAndFreeStack[ndx] = heapAndFreeStack[lesserChild];
      heapAndFreeStack[lesserChild] = temp;
      //bubble down from child
      this.bubbleDown(lesserChild);
    }*/

    //before refactoring

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
    //child 2 does not exist in heap, check if child 1 does
    else if(child1<numItems){

      //check if data[heap[child 1]] is smaller than data[heap[ndx]]
      if(data[heapAndFreeStack[child1]].isLess(data[heapAndFreeStack[ndx]])){
        
        //data[heap[child1]] is smaller than data[heap[ndx]]
        int temp = heapAndFreeStack[ndx];
        heapAndFreeStack[ndx] = heapAndFreeStack[child1];
        heapAndFreeStack[child1] = temp;
        this.bubbleDown(child1);
      }
    }
  }


  public ArrayHeap() {
    capacity = 1;
    numItems = 0;
    data = new Process[1];
    heapAndFreeStack = new int[1];
    heapAndFreeStack[0] = 0;
  }

  public ArrayHeap(ArrayHeap h) {
    ArrayHeap temp = new ArrayHeap();
    temp = this;
  }

  public void insert(Process item) {
    data[heapAndFreeStack[numItems]] = item; 
    numItems++;
    this.bubbleUp(numItems-1);
    if(numItems == capacity)
      this.doubleCapacity();
  }

  public void removeMinItem() {
    int tempNdx = heapAndFreeStack[0];
    heapAndFreeStack[0] = heapAndFreeStack[numItems-1];
    heapAndFreeStack[numItems-1] = tempNdx;
    numItems--;
    this.bubbleDown(0);
  }

  public Process getMinItem() {
    return data[heapAndFreeStack[0]];
  }

  public int getNumItems() {
    return numItems;
  }

}
