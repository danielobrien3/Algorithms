

/* CMPT 435
 * Project 2 -- Shortest path word melt solver
 * Filename: Driver_prj2.java
 * Student name: Daniel O'Brien
 *
 * 
 */

import java.util.Scanner;
import java.util.Stack;
import java.util.Map;
import java.util.TreeMap;
import java.util.NavigableSet;


public class Driver_prj2 {

  public static void main(String[] args) {
    
    //initialize maze, arrayqueue, map and scanner
    Scanner input = new Scanner(System.in);
    Maze maze = new Maze();
    ArrayQueue q = new ArrayQueue();

 
    
    TreeMap<Location,Location> wordMap = new TreeMap<>();

    //stream in valid locations
    maze.streamIn(input);

    //add starting location to queue
    q.add(maze.getStartLocation());

    //set current location to front of queue
    Location current = new Location();
    Location start = q.getFront();
    Location temp = new Location();
    current = start;

    wordMap.put(maze.getStartLocation(), maze.getStartLocation());
     

    //run while current location is not end location
    while(q.getLength()>0 && !maze.isEndLocation(current)){
      current = q.getFront();
      current.start();
      //run while current location isn't done iterating
      while(!current.isDone()){
        temp = current.nextNeighbor();
        //if nextNeighbor is a valid location and isnt already mapped, add it to map. 
        if(maze.isValidLocation(temp) && !wordMap.containsKey(temp) && !current.isEqual(temp)){
          q.add(temp);
          wordMap.put(temp, current);
          
          if(maze.isEndLocation(temp)){
            current.iterationMode = 3;
          }
        }
      }
      q.remove();
    }

    //If solution not found
    if(q.getLength() == 0 && !maze.isEndLocation(current)){
      System.out.println("No solution found");
    }


    if(maze.isEndLocation(current)){
      TreeMap<Location, Location> resultMap = new TreeMap<>();
      temp = current;

      while(!temp.isEqual(maze.getStartLocation())){
        resultMap.put(wordMap.get(temp),temp);
        temp = wordMap.get(temp);
      }
      
      temp = maze.getStartLocation();

      System.out.println("Solution found:");

      temp.streamOut();
      
      for(int i = 0; i < resultMap.size(); i++){
        resultMap.get(temp).streamOut();
        temp = resultMap.get(temp);
      }
      

    }
  }
}