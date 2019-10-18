import java.util.Scanner;

/* A Process object represents a batch-executed computer process, which has a
 * hard real-time deadline before which it must finish. It also has an amount of
 * time that it requires to execute.
 *
 * A Process object has a (unique) id, which starts at 0 and should increment
 * whenever a new process is created. It is up to the operating system to assign
 * it an ID.
 *
 * Finally, a Process object has a data member "information", which is printed
 * when the run() method executes.
 *
 * The default constructor for the Process initializes all the data members to
 * reasonable defaults, and the id to the given ID (default is 0).
 *
 * The run method prints out the information contained in this process, and
 * then returns the system time plus the required time (which is the new system
 * time).
 *
 * The canComplete method returns true if the method would be able to complete
 * if it starts now, or false if it could not finish by its deadline.
 *
 * The getId method returns the id of this Process object.
 *
 * The isLess() method is used to order Processes by:
 *  1. deadline (least to greatest)
 *  2. required time (least to greatest, if deadlines are equal)
 *  3. id (least to greatest, if deadlines & required times are equal)
 * The isLess() method returns true if (this) < p, false otherwise.
 *
 * The streamIn() method is used to read in the deadline, requiredTime, and
 * information from a Scanner input stream.
 */
class Process {
  private int id;
  private int deadline;
  private int requiredTime;
  private String information;

  public Process(int theId) {
    id = theId;
    deadline = 0;
    requiredTime = 0;
    information = ""; 
  }

  public int run(int currentSystemTime) {
    System.out.println("running process id " + id + " at " + currentSystemTime + "\n" + information);
    return (currentSystemTime + requiredTime);
  }

  public boolean canComplete(int currentSystemTime) {
    if((requiredTime + currentSystemTime) <= deadline){
      return true;
    }
    else return false;
  }

  public int getId() {
    return id;
  }


  public boolean isLess(Process p) {
    if(p == null){
      return false;
    }
    else if(this.deadline == p.deadline){
      if(this.requiredTime == p.requiredTime){
        if(this.id < p.id){
          return true;
        }
        else {
          return false;
        }
      }
      else if(this.requiredTime < p.requiredTime){
        return true;
      }
      else {
        return false;
      }
    }
    else if(this.deadline < p.deadline){
      return true;
    }
    else{ 
      return false;
    }
  }


  public void streamIn(Scanner input) {
    deadline = input.nextInt();
    requiredTime = input.nextInt();
    information = input.nextLine();
    information = information.substring(information.indexOf(" ") + 1);
  }

}

