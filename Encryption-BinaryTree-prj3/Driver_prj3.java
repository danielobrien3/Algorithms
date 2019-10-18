import java.util.Scanner;

public class Driver_prj3 {

	public static void main(String[] args) {

		//initialize codebook
		EncryptionTree codebook = new EncryptionTree();

		Scanner input = new Scanner(System.in);

		//read in first line. set command as first letter and input as rest of line.
		String in = input.nextLine();
		char command = in.charAt(0);
		in = in.substring(in.indexOf(" ")+1).trim();

		//run while command is not q. 
		while(command != 'q'){

			codebook.verifySearchOrder();
			
			//insert
			if(command == 'i'){
			  codebook.insert(in);
			}

			//remove
			if(command == 'r'){
			  codebook.remove(in);
			}

			//encrypt
			if(command == 'e'){
			  String encrypted = "";
			  //remove quotes from substring, then create string array split by spaces
			  in = in.substring(1, in.length()-1).trim();
			  String[] inArray = in.split(" ");
			  for(int i=0; i<inArray.length; i++){
			  	encrypted += codebook.encrypt(inArray[i]);
			  	encrypted += " ";
			  }
			  System.out.println(encrypted);
			}

			//decrypt
			if(command == 'd'){
			  String decrypted = "";

			  //remove quotes from substring, then create string array split by spaces
			  in = in.substring(1, in.length()-1).trim();
			  String[] inArray = in.split(" ");
			  for(int i=0; i<inArray.length; i++){
			  	decrypted += codebook.decrypt(inArray[i]);
			  	decrypted += " ";
			  }
			  System.out.println(decrypted);

			}

			//print preorder
			if(command == 'p'){
			  codebook.printPreorder();
			}

			//stream in next line of input
			in = input.nextLine().trim();
			command = in.charAt(0);
			in = in.substring(in.indexOf(" ") + 1).trim();

		}

	}
}





