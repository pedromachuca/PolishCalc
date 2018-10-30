import java.io.*;
import java.net.*;
//Class polish calculator with the main method 
//Where object PileRPL and ObjEmp are created
//and where the differents methods of the objects
//are called.



public class CalcRPL{


	public static void main(String [] args){
		new CalcRPL(args);	
	}


	//Initialisation des attributs de la classe
	BufferedReader input=null;
	PrintWriter output=null;	
	FileWriter writer=null;

	ServerSocket server;
	Socket socket=null;
	//Initialisation of an object PileRPL
	PileRPL pile=null;
	//The default stack size will be 10
	int stackLength = 10;


	public CalcRPL(String [] args){
		//If no args then instanciation of PileRPL with the default size
		if(args.length==0){
			output = new PrintWriter(System.out);
	  		input = new BufferedReader(new InputStreamReader(System.in));
			//Instanciation of an object pile
			//that will create the stack with default size.
			args=menu(output, input);
			initStream(args);
			mainLoop();

		}
		//If one arg then instanciation of PileRPL with the arg as size.
		else if(args.length==1){
				args=menu(output, input);
				initStream(args);
				mainLoop();
		}
		else if(args.length==2){
				initStream(args);
				mainLoop();
		}
		//If too many arguments were entered exits the program
		else{
			System.out.println("Too many arguments re-launch the program with the following command : ");
			System.out.println("java CalcRPL option1 option2 (option 1 and 2 being ints)");
			System.exit(1);
		}
	}
	void display(PrintWriter output, String message){
			output.print(message);
			output.flush();		
	}
	void display(PrintWriter output, PileRPL pile){
			output.print(pile);
			output.flush();		
	}
	String [] menu(PrintWriter output, BufferedReader input){
		//Starting the calculator and prompt user information on the program.
		display(output, "*************************************************************\n");
		display(output, "*                                                           *\n");
		display(output, "*    ************************************                   *\n");
		display(output, "*    *                                  *                   *\n");
		display(output, "*    * Welcome to the polish calculator *                   *\n");
		display(output, "*    *                                  *                   *\n");
		display(output, "*    ************************************                   *\n");
		display(output, "*    How to :                                               *\n");
		display(output, "*    Enter a value to push on the stack, press ENTER        *\n");
		display(output, "*    Enter another value to push on the stack, press ENTER  *\n");
		display(output, "*    Enter the operand you wish to apply, press ENTER       *\n");
		display(output, "*    The following commands are also available :            *\n");
		display(output, "*    drop, swap, clear, pop and push number (ex: push 2)    *\n");
		display(output, "*    You can leave by entering q at anytime                 *\n");
		display(output, "*                                                           *\n");
		display(output, "*    Different mode are available :                         *\n");
		display(output, "*    1 - Mode local                                         *\n");
		display(output, "*        Use the calculator locally                         *\n");
		display(output, "*    2 - Online mode (use the calculator                    *\n");
		display(output, "*        Use the calculator from a remote location          *\n");
		display(output, "*        Launch the program as usual (server side)          *\n");
		display(output, "*        Connect from a remote location (telnet, netcat...) *\n");
		display(output, "*        Example : telnet IP PORT                           *\n");
		display(output, "*    3 - Online mode that logs the current session          *\n");
		display(output, "*                                                           *\n");
		display(output, "* To launch the program directly nex time enter the         *\n");
		display(output, "* Following command : java CalcRPL option1 option2          *\n");
		display(output, "*                                                           *\n");
		display(output, "*    option 1:          option 2:                           *\n");
		display(output, "*    1- Local           1-Session without log               *\n");
		display(output, "*    2- Remote          2-Session with log                  *\n");
		display(output, "*                       3-Replay the log                    *\n");
		display(output, "* If you see this menu it means you did not enter the       *\n");
		display(output, "* correct number of argument.                               *\n");
		display(output, "*                                                           *\n");
		display(output, "* Please enter: option1 option2                             *\n");
		display(output, "*************************************************************\n");
		String [] s1 = new String[2];
		String s = new String();
		try{
			do{
				s=(String)input.readLine();
				s1=s.trim().split(" ");
			}while(!(s1[0].equals("1")||s1[0].equals("2"))&&!(s1[1].equals("1")||!s1[1].equals("2")||s1[1].equals("3")));
		}catch(IOException e){
			System.out.println(e.getMessage());
		}
		return s1;

	} 
	void initStream(String [] args){
		if(args[0].equals("1")){
			switch(args[1]){
				case "1":
					output = new PrintWriter(System.out);
	  				input = new BufferedReader(new InputStreamReader(System.in));
					display(output, "Starting local session without logs");
					break;
				case "2":
					display(output, "Starting local session with logs");
					try{
						output = new PrintWriter(System.out);
	  					input = new BufferedReader(new InputStreamReader(System.in));
						writer = new FileWriter("session.txt", false);
					}catch(IOException e){
						display(output,e.getMessage());
						System.exit(1);
					}
					break;
				case "3":
					//replay log locally
					try{
						output = new PrintWriter(System.out);
						input = new BufferedReader(new FileReader("session.txt"));
					}catch(IOException e){
						display(output,e.getMessage());
						System.exit(1);
					}
					display(output, "Replaying session logs");
					break;
				default:
					break;
			}
		}
		else if(args[0].equals("2")){
			try{
				server = new ServerSocket(12345, 1);
				socket = server.accept();
			}catch(IOException e){
				System.out.println( "problème de connexion" );
				System.exit(1);
			}
			switch(args[1]){
				case "1":
					display(output, "Starting remote session");
					try{
	  					input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						output = new PrintWriter(socket.getOutputStream());
					}catch(IOException e){
						System.out.println( "problème" );
					}
					//initialisation of remote session without log
					break;
				case "2":
					display(output, "Starting remote sessioni with logs");
					//initialisation of remote session with log
					try{
	  					input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						output = new PrintWriter(socket.getOutputStream());
						writer = new FileWriter("session.txt", false);
					}catch(IOException e){
						System.out.println( "problème" );
					}
					//initialisation of remote session without log
					break;		
				case "3":
					display(output, "Starting remote session");
					//replay log online
					try{
						input = new BufferedReader(new FileReader("session.txt"));
						output = new PrintWriter(socket.getOutputStream());
					}catch(FileNotFoundException e){display(output,e.getMessage());}
					catch(IOException e){display(output,e.getMessage());}
					break;
				default:
					break;
			}
		}
	}
	void writeFile(String s){
		if(writer !=null){		
			try{
				writer.write(s);
				writer.write("\r\n");
				writer.flush();
			}catch(IOException e){display(output,e.getMessage());}
		}
	}
	void mainLoop(){
		pile = new PileRPL(stackLength);
		String s ="" ;
		int value =0;
		String[] splitStri=new String[2];
		display(output,pile);
		do{
			try{
				display(output,">>");
				s = (String)input.readLine();
			}catch(IOException e){display(output, e.getMessage());}
			try{
				splitStri=s.trim().split("\\s+");
				if(splitStri.length>1){
					value = Integer.parseInt(splitStri[1]);
				}
				else{
					value = Integer.parseInt(s);
				}
			}catch(NumberFormatException e){}
			writeFile(s);
			if(s.matches("[0-9]+")){

				//Instanciation of two stackable objects
				//with the value of the object as arguments
				//at the moment these are ints but the program
				//is build so that other type will be allowed
				ObjEmp oe1 = new ObjEmp(value);
				//the stack object is use to insert
				//the 1st object on the stack
				pile.push(oe1);
				display(output, "State of the stack :");
				display(output,pile);
				//TO DO FONCTION QUI PRENDS SORTIE (ex output) et message et
				//fait un display et un flush
			}
			else if(s.matches("[+x/-]")){
				switch(s){
					case "+":
						//Then the operations method can be called
						//to apply operation to the objects of the stack
						pile.add();
						display(output,pile);
						break;
					case "-":
						pile.sou();
						display(output,pile);
						break;
					case "x":
						pile.mul();
						display(output,pile);
						break;
					case "/":
						pile.div();
						display(output,pile);
						break;
					default:
						break;
				}
			}
			else if(s.matches("clear")){
				pile.clear();
				display(output,"\nState of the stack :\n");
				display(output,pile);
			}
			else if(s.matches("drop")){
				pile.drop();					
				display(output,"\nState of the stack :\n");
				display(output,pile);
			}
			else if(s.matches("swap")){
				pile.swap();					
				display(output,"\nState of the stack :\n");
				display(output,pile);
			}
			else if(s.matches("pop")){
				ObjEmp poper =pile.pop();
				display(output, "The object poped : "+poper);
				display(output,pile);
			}
			else if(splitStri[0].matches("push")&&splitStri[1].matches("[0-9]+")){
				ObjEmp oe1 = new ObjEmp(value);
				pile.push(oe1);
				display(output,"\nState of the stack :\n");
				display(output,pile);
					
			}
			else{
				if(s.equals("q")){
					try{
						display(output,"Bye Bye !!\n");
						if(writer!=null){
							writer.close();
						}
						System.exit(1);
					}catch(IOException e ){display(output,e.getMessage());}
				}
				else{
					display(output,"Invalid input please try again\n");
					continue;
				}
			}
		}while(!s.equals("q"));
	}
}

