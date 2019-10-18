import java.util.ArrayList;
import java.util.Vector;

/* The Edge class represents an edge in a sparse (adjacency list) Graph. It
 * tells the vertex that the edge leads to, and the cost of the edge.
 */
class Edge {
  public int to;
  public int cost;
  
  Edge() {
    to = 0;
    cost = 0;
  }

  Edge(int aTo, int aCost) {
    to = aTo;
    cost = aCost;
  }
}

/* The Graph class represents a set of vertices and directed, weighted edges
 * between those vertices. The cost of each edge is given by an integer value,
 * and the special value INFINITE_COST is a large number which represents an
 * impossible cost. We assume that no real path in the graph has a total cost of
 * INFINITE_COST or more.
 *
 * This Graph class uses an edge list format, meaning that there is a vector of
 * lists of Edge objects, and if an edge exists between two vertices, then it
 * will be listed in the inner list of the vertex the edge leaves from. If there
 * is NO connection from vertex A to vertex B, then this is represented by the
 * absence of an edge from A to B (and NOT by the inclusion of an edge of cost
 * INFINITE_COST). Remember that the adjacency list representation does not keep
 * track of non-existent edges.
 *
 * The constructor makes a graph with enough space for the given number of
 * vertices. No edges are present in the graph at this point.
 *
 * The addEdge method adds an edge connecting (from -> to) with the given cost.
 *
 * The dijkstra method is the classical Dijkstra's shortest path algorithm,
 * which starts at the given source vertex and finds the shortest paths to all
 * other vertices in the graph. The method will return a vector that lists the
 * shortest path cost of going from the source vertex to each vertex in the
 * graph. The cost of going from a vertex to itself is always 0. If a vertex is
 * not reachable, then the distance to it should be >= INFINITE_COST.
 *
 * The inVertexRange method checks that the given index is within the
 * appropriate range for accessing vertices.
 *
 * The adjacencyList data member is a sparse representation of the edges in the
 * graph. It is a vector of lists of Edge objects. If an edge exists between
 * vertex i to vertex j, then edgeList[i] will have an Edge object that points
 * to vertex j, with an associated cost. If there is no edge between them,
 * then edgeList[i] will not have an Edge object that points to j.
 *
 * The copy constructor is off limits for this data structure.
 */

class Graph {
  private Vector<ArrayList<Edge>> adjacencyList;

  Graph(Graph g) { assert(false); }
  
  Graph(int numVertices) { 
    adjacencyList = new Vector<ArrayList<Edge>>(numVertices);
  }
  
  public final long INFINITE_COST = 1000000000;
  
  public void addEdge(int from, int to, int cost) {
    //make sure adjacency list has from and to edges
    while(adjacencyList.size()<=from){
      adjacencyList.add(new ArrayList<Edge>());
    }
    while(adjacencyList.size()<=to){
      adjacencyList.add(new ArrayList<Edge>());
    }

    adjacencyList.get(from).add(new Edge(to, cost));
  }
  
  public Vector<Integer> dijkstra(int source) {
    Vector<Integer> spt = new Vector<Integer>(adjacencyList.size());
    ArrayHeap heap = new ArrayHeap();
    ArrayList<Boolean> known = new ArrayList<Boolean>();

    //set infinite length for all vertices in spt. source length = 0
    for(int i=0; i<adjacencyList.size(); i++){
      if(i==source){
        spt.add(0);
        heap.insert(new Vertex(source, 0));
        known.add(Boolean.FALSE);
      }
      else{
        spt.add(1000000000);
        known.add(Boolean.FALSE);
      }
    }

    heap.insert(new Vertex(source, 0));

    while(heap.getNumItems()>0){
      //Save minItem as x and remove it from heap.
      Vertex x = heap.getMinItem();
      heap.removeMinItem();
      known.set(x.id, Boolean.TRUE);

      for(int i=0; i<adjacencyList.get(x.id).size(); i++){
        int edgeDistance = -1;
        int newDistance = -1;
        Vertex y = new Vertex(adjacencyList.get(x.id).get(i).to, adjacencyList.get(x.id).get(i).cost);
        //need different tester
        if(known.get(y.id) == Boolean.FALSE){
          heap.insert(y);
          if(spt.get(y.id) == 1000000000){
            edgeDistance = adjacencyList.get(x.id).get(i).cost;
            newDistance = spt.get(x.id) + edgeDistance;
            if(newDistance < spt.get(y.id)){
              spt.set(y.id, newDistance);
              heap.changeItemAtKey(y.id, new Vertex(y.id, newDistance));
            }
          }
        }
        
      }
    }
    return spt;

    /*
    Vector<Integer> spt = new Vector<Integer>(adjacencyList.size());
    ArrayHeap heap = new ArrayHeap();
    int knownCount = 0;

    for(int i=0; i<adjacencyList.size(); i++){
      if (i == source){
        spt.add(0);
      }
      else
        spt.add(1000000000);
    }

    boolean[] known = new boolean[adjacencyList.size()];
    for(int i = 0; i < known.length; i++){
      known[i] = false;
    }

    heap.insert(new Vertex(source, 0));

    if(adjacencyList.size() <= 1){
      spt.add(adjacencyList.get(0).get(0).cost);
    }
    else{
      while(knownCount<adjacencyList.size()){
        Vertex minItem = heap.getMinItem();
        if(heap.getNumItems()>0)
          heap.removeMinItem();

        //add vertex to known
        known[minItem.id] = true;
        knownCount++;

        //update vertex neighbors
        int edgeDistance = -1;
        int newDistance = -1;

        //loop through vertex neighbors
        for(int i=0; i<adjacencyList.get(minItem.id).size(); i++){
          int adjacent = adjacencyList.get(minItem.id).get(i).to;
          //check if neighbor is known
          if(known[adjacent] == false){
            edgeDistance = adjacencyList.get(minItem.id).get(i).cost;
            newDistance = spt.get(minItem.id) + edgeDistance;

            //if newDistance is < current distance. Set current = newDistance
            if(newDistance < spt.get(adjacent)){
              spt.set(adjacent, newDistance);
            }
            heap.insert(new Vertex(adjacent, spt.get(adjacent)));
          }
        }
      }
    }

    
    return spt;




    /*
    spt.add(0);
    
    Vertex sourceVertex = new Vertex(source, 0);
    heap.insert(sourceVertex);
    //add all vertices to heap
    for(int i=0; i<adjacencyList.size(); i++){
      if(i != source){
        heap.insert(new Vertex(i, 1000000000));
        spt.add(1000000000);
      }
      else{
        spt.add(0);
      }
    }
    while(heap.getNumItems()>0){
      

      
      //loop through adjacent vertices of current vertex
      //while i < innerList.size
      for(int i=0; i < adjacencyList.get(heap.getMinItem().id).size(); i++){
        //if adjacent vertex is on heap
        if(heap.isOnHeap(i)){
          //if current cost to adjacent vertex is greater than current distance 
          //+ (current-> adjacent cost), update cost
          int temp2 = (heap.getMinItem().distance + adjacencyList.get(heap.getMinItem().id).get(i).cost);
          
          System.out.print("TEST ");
          System.out.println(heap.getMinItem().distance);
          System.out.println(adjacencyList.get(heap.getMinItem().id).get(i).cost);
          
          if(spt.get(i) > (heap.getMinItem().distance + adjacencyList.get(heap.getMinItem().id).get(i).cost)){
            int temp = heap.getMinItem().distance + adjacencyList.get(heap.getMinItem().id).get(i).cost;
            System.out.println(temp);
            spt.set(i, temp);
            heap.changeItemAtKey(i, new Vertex(i, temp));
          }
        }
      }
      heap.removeMinItem();
    }
    for(int i = 0; i<spt.size(); i++){
      System.out.println("spt " + i + ": " + spt.get(i));
    }
    return spt;
    */

  }

  public boolean inVertexRange(int ndx) {
    return (0 <= ndx) && (ndx < adjacencyList.size());
  }

}
