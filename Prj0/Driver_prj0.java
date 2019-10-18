/* CMPT 435
 * Project 0 -- Program trace verification
 * Filename: Driver_prj0.java
 * Student name: Daniel O'Brien
 *
 * 
 */

import java.util.Scanner;
import java.util.Stack;

public class Driver_prj0 {

  /* main
   *  parameters:
   *      args -- the array of command line argument values
   *  return value: nothing
   * 
   *  
   */
  public static void main(String[] args) {
    // Here we initialize the scaner variable to read lines of input
    Scanner input = new Scanner(System.in);
    String line;

    // the callStack is used for storing the names of functions that have been
    // called and not yet returned
    Stack<String> callStack = new Stack<String>();

    // Each time we go through this while loop, we read a line of input.
    // The function hasNext() returns a boolean, which is checked by the while 
    // condition. If System.in has reached the end of the file, it will return 
    // false and the loop will exit.  Otherwise, it will return true and the 
    // loop will continue.
    int lineNumber = 0;
    int maximum_depth = 0;
    boolean err = false;
    while (input.hasNext() && err == false) {
      line = input.nextLine();
      lineNumber++;

      //Set maximum depth by comparing it to callStack size.
      if (callStack.size() > maximum_depth){
        maximum_depth = callStack.size();
      }

      //if function is being called, push onto stack
      if(line.contains("call")){
        callStack.push(line.substring(line.indexOf(" ")));
      }
      //If stack isnt empty, check if return and call functions match up.
      if(!callStack.empty()){
        if(line.contains("return")){
          String temp = callStack.pop();
          String call = temp.substring(temp.indexOf(" "));

          //If call and return don't match up, print an error.
          if(!call.equals(line.substring(line.indexOf(" ")))){
            System.out.println("Invalid trace at line " + lineNumber + "\nReturning from " + line.substring(line.indexOf(" ")).trim() + " instead of " + call.trim());
            System.out.println("Stack trace: ");
            System.out.println(call.trim());
            while(!callStack.empty()){
              System.out.println(callStack.pop().trim());
            }
            err = true;
          }
        }
      }
      //If stack is empty, print error
      else{
        System.out.println("Invalid trace at line " + lineNumber + "\nReturning from " +line.substring(line.indexOf(" ")).trim() +  " which was not called");
        System.out.println("Stack trace: ");
        while(!callStack.empty()){
          System.out.println(callStack.pop().trim());
        }
        err = true;
      }
    }
    //If the input has been fully parsed and there is still a call function unreturned, print an error
    if (callStack.size() > 0 && err == false){
      System.out.println("Invalid trace at line " + lineNumber + "\nNot all functions returned");
      System.out.println("Stack trace: ");
      while(!callStack.empty()){
        System.out.println(callStack.pop().trim());
      }
      err = true;
    }
    else if (err != true) {
      System.out.println("Valid trace \nMaximum call depth was " + maximum_depth);
    } 
  }
}

