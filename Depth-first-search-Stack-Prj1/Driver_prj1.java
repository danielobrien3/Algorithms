import java.util.Scanner;

public class Driver_prj1
{ 
	public static void main(String [] args)
	{
		Maze maze = new Maze();
		

		maze.streamIn(input);

		

		LocationStack visitedLocations = new LocationStack();

		visitedLocations.push(maze.getStartLocation());
		visitedLocations.getTop().start();
		Location currentLocation = (maze.getStartLocation());
		currentLocation.start();

		//perform loop as long as the top of the stack is not the end location, and the stack is not empty
		while(!maze.isEndLocation(currentLocation) && !visitedLocations.isEmpty()){
			//set current location equal to its next neighbor
			Location neighbor = currentLocation.nextNeighbor();
			if(maze.isValidLocation(neighbor) && !(visitedLocations.isOn(neighbor))){
				visitedLocations.push(neighbor);
				currentLocation = neighbor;
			}
			else if(currentLocation.isDone()){
				visitedLocations.pop();
				if(!visitedLocations.isEmpty()){
					currentLocation = visitedLocations.getTop();
				}
			}
		}

		if(visitedLocations.isEmpty()){
			System.out.println("No solution found");
		}

		if(maze.isEndLocation(currentLocation)){
		  visitedLocations.streamOut(visitedLocations);
  		}

		
		
		
	}

}