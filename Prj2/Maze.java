import java.util.Set;
import java.util.TreeSet;
import java.util.Scanner;
import java.util.Iterator;
import java.util.Comparator;
/* Changes from Project 1:
 *
 * Data member validLocations was changed from a Location array to a
 * Set<Location>, but it still represents the group of locations that may be
 * visited in the maze.
 *
 * Data member validLocationCount was eliminated (because validLocations is no
 * longer an array).
 *
 * We let the compiler deal with the assignment operator, copy constructor, and 
 * for this version of the Maze, you should use the default constructor for the
 * _usual_ and to initally allocate memory for the Set of validLocations using
 * the TreeSet implementation of a Set.
 */

class Maze {
  private Set<Location> validLocations;
  private Location startLocation;
  private Location endLocation;

  Maze() {
    startLocation = new Location();
    endLocation = new Location();
  }

  Location getStartLocation() {
    return startLocation;
  }

  boolean isValidLocation(Location loc) {
    if(validLocations.contains(loc)){
      return true;
    }
    else return false;
  }
  
  boolean isEndLocation(Location loc) {
    if(loc != null){
      if(loc.isEqual(endLocation)){
        return true;
      }
      else 
        return false;
    }
    else return false;
    
  }

  void streamIn(Scanner input) {
    validLocations = new TreeSet<Location>();
    int count = input.nextInt();
    for(int i = 0; i<count; i++){
      Location tLoc = new Location();
      tLoc.streamIn(input);
      validLocations.add(tLoc);
    }
    startLocation.streamIn(input);
    endLocation.streamIn(input);
  }

}
