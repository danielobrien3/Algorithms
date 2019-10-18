import java.util.Scanner;

public class Driver_prj5_2{
	public static void main(String[] args) {

		ArrayHeap heap =  new ArrayHeap();
		int sysClock = 0;
		Scanner input = new Scanner(System.in);
		int inputCount = input.nextInt();

		int[] startTimes = new int[inputCount];
		Process[] processes = new Process[inputCount];

		//count for how many procs have been run
		int procRun = 0;

		//count for skipped processes
		int skipCount = 0;


		sysClock = startTimes[0];
		heap.insert(processes[0]);
		processes[0] = null;

		//store processes in array. Store start times in seperate array. 
		for (int i=0; i<inputCount; i++){
			startTimes[i] = input.nextInt();
			processes[i] = new Process(i);
			processes[i].streamIn(input);
		}

		while(procRun < inputCount){
			/*While heap is not empty, 
			if minItem on heap has time to complete,
			run process and remove from heap*/
			while(heap.getNumItems()>0){
				/*loops through processes checking start time.
				if startTimes is greater than or equal to sysClock,
				process is inserted in heap*/

				if(heap.getMinItem().canComplete(sysClock)){
					sysClock = heap.getMinItem().run(sysClock);
					heap.removeMinItem();
					procRun++;
				}

				//if min item cannot complete, remove item
				else {
					System.out.println("skipping process id " + heap.getMinItem().getId() + 
					" at " + sysClock);
					skipCount++;
					heap.removeMinItem();
					sysClock++;
					procRun++;
				}

				for(int i = 0; i < processes.length; i++){
					if(sysClock >= startTimes[i] && processes[i] != null){
						heap.insert(processes[i]);
						processes[i] = null;
					}
				}
			}




			/*check if there are still processes, and if the leftover processes
			can be run with the current system time
			 advance sysClock to next start time.*/
			int minStart = startTimes[inputCount - 1];

			if(heap.getNumItems()==0 && procRun<inputCount){
				for(int i = 0; i < processes.length; i++){
					if(processes[i] != null && startTimes[i]< minStart)
						minStart = startTimes[i];
				}
				if(sysClock < minStart)
					sysClock = minStart;
			}

			for(int i = 0; i < processes.length; i++){
				if(sysClock >= startTimes[i] && processes[i] != null){
					heap.insert(processes[i]);
					processes[i] = null;
				}
			}
		}



		System.out.println("final clock is                 " + sysClock);
		System.out.println("number of processes run is     " + (procRun - skipCount));
		System.out.println("number of processes skipped is " + skipCount);

	}
}