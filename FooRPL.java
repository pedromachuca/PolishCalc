import java.io.*;
import java.net.*;
//Class polish calculator with the main method 
//Where object PileRPL and ObjEmp are created
//and where the differents methods of the objects
//are called.
public class FooRPL{
	static void PRINT(PrintWriter output, String message){
			output.println(message);
			output.flush();		
	}
	static void PRINT(PrintWriter output, PileRPL pile){
			output.println(pile);
			output.flush();		
	}
	static void menu(PrintWriter output){
		//Starting the calculator and prompt user information on the program.
		PRINT(output, "*************************************************************");
		PRINT(output, "*                                                           *");
		PRINT(output, "*    ************************************                   *");
		PRINT(output, "*    *                                  *                   *");
		PRINT(output, "*    * Welcome to the polish calculator *                   *");
		PRINT(output, "*    *                                  *                   *");
		PRINT(output, "*    ************************************                   *");
		PRINT(output, "*    How to :                                               *");
		PRINT(output, "*    Enter a value to push on the stack, press ENTER        *");
		PRINT(output, "*    Enter another value to push on the stack, press ENTER  *");
		PRINT(output, "*    Enter the operand you wish to apply, press ENTER       *");
		PRINT(output, "*    The following commands are also available :            *");
		PRINT(output, "*    drop, swap, clear, pop and push number (ex: push 2)    *");
		PRINT(output, "*    You can leave by entering q at anytime                 *");
		PRINT(output, "*                                                           *");
		PRINT(output, "*************************************************************");
	}
	public static void main(String [] args){
		//Initialisation of an object PileRPL
		PileRPL pile =null;
		//The default stack size will be 3
		int stackLength = 3;
		//If no args then instanciation of PileRPL with the default size
		if(args.length==0){
			//Instanciation of an object pile
			//that will create the stack with default size.
			pile = new PileRPL(stackLength);
		}
		//If one arg then instanciation of PileRPL with the arg as size.
		else if(args.length==1){
			//Parse args[0] in int, if the arg is not an int 
			//catch an exception and exit the program
			try{
				stackLength = Integer.parseInt(args[0]);
				//Instanciation of an object pile
				//that will create the stack with the size 
				//of the stack as an argument.
				pile = new PileRPL(stackLength);
			}catch(NumberFormatException e){
				System.out.println("The argument must be a number !"+e);
				System.exit(1);
			}
		}
		//If too many arguments were entered exits the program
		else{
			System.out.println("Too many arguments re-launch the program with the following command : ");
			System.out.println("java FooRPL stackLength (stackLength being an int)");
			System.exit(1);
		}
		//TO DO ask for the choice in the menu online, online with log, local
		//DO the logs
		int test=0;
		ServerSocket server;
		Socket socket=null;
		if(test==0){

			try{
				server = new ServerSocket(12345, 1);
				socket = server.accept();
			}catch(IOException e){
				System.out.println( "problème de connexion" );
				System.exit(1);
			}
		}
		//initialisation of an and a char to get the input
		//from the user.
		int value = 0;
		int menu =0;
		String s ="" ;
		String[] splitStri=null; 
	  	BufferedReader input;
		PrintWriter output=null;
		while(!s.equals("q")){
			try{
				if(test==1){
	  				input = new BufferedReader(new InputStreamReader(System.in));
					output = new PrintWriter(System.out);
				}
				else{
	  				input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					output = new PrintWriter(socket.getOutputStream());
				}
				if(menu==0){
					menu(output);
					menu=1;
				}
        		s = (String)input.readLine();
				splitStri=s.trim().split("\\s+");
				if(splitStri.length>1){
					value = Integer.parseInt(splitStri[1]);
				}
				else{
					value = Integer.parseInt(s);
				}
  			}
  			catch(IOException e){
			 	PRINT(output,e.getMessage());
				System.exit(1);
  			}
			catch(NumberFormatException e){}
			if(s.matches("[0-9]+")){

				//Instanciation of two stackable objects
				//with the value of the object as arguments
				//at the moment these are ints but the program
				//is build so that other type will be allowed
				ObjEmp oe1 = new ObjEmp(value);
				//the stack object is use to insert
				//the 1st object on the stack
				pile.push(oe1);
				PRINT(output, "\nState of the stack :\n");
				PRINT(output,pile);
				//TO DO FONCTION QUI PRENDS SORTIE (ex output) et message et
				//fait un print et un flush
			}
			else if(s.matches("[+x/-]")){
				switch(s){
					case "+":
						//Then the operations method can be called
						//to apply operation to the objects of the stack
						pile.add();
						PRINT(output,pile);
						break;
					case "-":
						pile.sou();
						PRINT(output,pile);
						break;
					case "x":
						pile.mul();
						PRINT(output,pile);
						break;
					case "/":
						pile.div();
						PRINT(output,pile);
						break;
					default:
						break;
				}
			}
			else if(s.matches("clear")){
				pile.clear();
				PRINT(output,"\nState of the stack :\n");
				PRINT(output,pile);
			}
			else if(s.matches("drop")){
				pile.drop();					
				PRINT(output,"\nState of the stack :\n");
				PRINT(output,pile);
			}
			else if(s.matches("swap")){
				pile.swap();					
				PRINT(output,"\nState of the stack :\n");
				PRINT(output,pile);
			}
			else if(s.matches("pop")){
				ObjEmp poper =pile.pop();
				PRINT(output, "The object poped : "+poper);
				PRINT(output,pile);
			}
			else if(splitStri[0].matches("push")&&splitStri[1].matches("[0-9]+")){
				ObjEmp oe1 = new ObjEmp(value);
				pile.push(oe1);
				PRINT(output,"\nState of the stack :\n");
				PRINT(output,pile);
					
			}
			else{
				if(s.equals("q")){
					PRINT(output,"Bye Bye !!\n");
					System.exit(1);
				}
				else{
					PRINT(output,"Invalid input please try again\n");
					
					continue;
				}
			}
		}

	}
}
