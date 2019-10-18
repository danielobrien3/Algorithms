import java.util.Scanner;
import java.util.ArrayList;
import java.util.Vector;
import java.util.Comparator;

public class Driver_prj6{
	public static void main(String[] args) {


		Scanner input = new Scanner(System.in);
		int inputCount = input.nextInt();
		Graph g = new Graph(inputCount);
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<Vertex> results = new ArrayList<Vertex>();

		for(int i=0; i<inputCount; i++){
			String inOne = input.next();
			String inTwo = input.next();
			if(!names.contains(inOne))
				names.add(inOne);
			if(!names.contains(inTwo))
				names.add(inTwo);
			g.addEdge(names.indexOf(inOne), names.indexOf(inTwo), input.nextInt());
		}



		for(int i=0; i<names.size(); i++){
			//if(names.get(i).contains("server")){
			if(names.get(i).matches("(.*)_server")){
				results.add(new Vertex(i, 0, names.get(i), true));
			}
			//if computer isnt a server, set total delay infinite
			else {
				results.add(new Vertex(i, 0, names.get(i), false));
			}

		}


		//definitely needs reworking
		//get all totals. store in results
		for(int i=0; i<names.size(); i++){
			if(results.get(i).server == true){
				Vector<Integer> temp = g.dijkstra(results.get(i).id);
				//if vector doesnt reach every node, give it total of 1000000000
				for(int j = 0; j<temp.size(); j++){
					if(temp.get(j) >= 1000000000)
						results.get(i).totalDelay = 1000000000;
					else if(results.get(i).totalDelay < 1000000000)
						results.get(i).totalDelay += temp.get(j);
				}
			}
			//if computer isnt a server, set total delay infinite
			else {
				results.get(i).totalDelay = 1000000000;
			}
		}

		int minNdx = 0;
		//find minimum total
		for(int i=0; i<results.size(); i++){
			if(results.get(i).totalDelay < results.get(minNdx).totalDelay){
				minNdx = i;
			}
		}

		names = new ArrayList<String>();

		for(Vertex r: results){
			if (r.totalDelay == results.get(minNdx).totalDelay && r.totalDelay < 1000000000 && r.totalDelay > 0){
				names.add(r.name);
			}
		}

		if(names.size()>0){
			names.sort(new Comparator<String>(){
  			public int compare(String s1, String s2){
    			return s1.compareTo(s2);
  			}
			});
			System.out.println("Total delay: " + results.get(minNdx).totalDelay);
			System.out.println("Best server(s):");
			for(String n: names){
				System.out.println(n);
			}
		}
		else System.out.println("No server can serve the whole network");


		/*Scanner input = new Scanner(System.in);
		inputCount = input.nextInt();
		Graph g = new Graph(inputCount);
		//count for amount of servers
		int serverCount = 0;


		//array for computer names and ndx for adding names
		ArrayList<String> names = ArrayList<String>(inputCount);
		int nameNdx = 0;


		for(i=0; i<inputCount; i++){
			String inOne = input.nextLine();
			String inTwo = input.nextLine();
			//make sure both names are in names list
			if(!names.contains(inOne)){
				if(inOne.contains("server")){
					serverCount++;
				}
				names.add(inOne);
			}
			if(!names.contains(inTwo)){
				if(inTwo.contains("server")){
					serverCount++;
				}
				names.add(inTwo);
			}
			//add edge from first input to second input
			g.addEdge(names.indexOf(inOne), names.indexOf(inTwo), input.nextInt());
		}
		
		//array for storing total delay times	
		int[] totalDelays = new int[serverCount];
		int[] result = new int[inputCount];

		for(int i=0; i<names.length; i++){
			if(names[i].contains("server")){
				Vector<Integer> temp = g.dijkstra(i);
				int total = 0;
				temp.forEach((t) -> total+=t);
				result[i] = total;
			}
			//if computer isnt a server, set total delay infinite
			else result[i] = 1000000000;
		}

		int minNdx = 0;
		//find minimum total
		for(int i=0; i<result.length; i++){
			if(result[i] < result[minNdx]){
				minNdx = i;
			}
		}

		for(int i=0; i<result.length; i++){
			if(result[i] == result[minNdx]){
				System.out.println(names.get(i) + " " + result[minNdx]);
			}
		}
		System.out.println(names.get(minNdx));
		*/

	}
}