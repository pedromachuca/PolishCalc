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
	BufferedReader inputUser=null;
	PrintWriter outputUser=null;	
	FileWriter outputLog=null;
	boolean reclog =false;
	ServerSocket server=null;
	Socket socket=null;
	//Initialisation of an object PileRPL
	PileRPL pile=null;
	//The default stack size will be 10
	int stackLength = 10;


	public CalcRPL(String [] args){
		if(args.length==0){
			args =menu();
		}
		initStream(args);
		mainLoop();
		closeStream();
	}
/*		//If no args then instanciation of PileRPL with the default size
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
	}*/
	void display(PrintWriter outputUser, String message){
			outputUser.print(message);
			outputUser.flush();		
	}
	void display(PrintWriter outputUser, PileRPL pile){
			outputUser.print(pile);
			outputUser.flush();		
	}
	String [] menu(){
		outputUser = new PrintWriter(System.out);
	  	inputUser = new BufferedReader(new InputStreamReader(System.in));
		//Starting the calculator and prompt user information on the program.
		display(outputUser, "*************************************************************\n");
		display(outputUser, "*                                                           *\n");
		display(outputUser, "*    ************************************                   *\n");
		display(outputUser, "*    *                                  *                   *\n");
		display(outputUser, "*    * Welcome to the polish calculator *                   *\n");
		display(outputUser, "*    *                                  *                   *\n");
		display(outputUser, "*    ************************************                   *\n");
		display(outputUser, "*    How to :                                               *\n");
		display(outputUser, "*    Enter a value to push on the stack, press ENTER        *\n");
		display(outputUser, "*    Enter another value to push on the stack, press ENTER  *\n");
		display(outputUser, "*    Enter the operand you wish to apply, press ENTER       *\n");
		display(outputUser, "*    The following commands are also available :            *\n");
		display(outputUser, "*    drop, swap, clear, pop and push number (ex: push 2)    *\n");
		display(outputUser, "*    You can leave by entering q at anytime                 *\n");
		display(outputUser, "*                                                           *\n");
		display(outputUser, "*    Different mode are available :                         *\n");
		display(outputUser, "*    1 - Mode local                                         *\n");
		display(outputUser, "*        Use the calculator locally                         *\n");
		display(outputUser, "*    2 - Online mode                                        *\n");
		display(outputUser, "*        Use the calculator from a remote location          *\n");
		display(outputUser, "*        Launch the program as usual (server side)          *\n");
		display(outputUser, "*        Connect from a remote location (telnet, netcat...) *\n");
		display(outputUser, "*        Example : telnet IP PORT                           *\n");
		display(outputUser, "*    3 - Online mode that logs the current session          *\n");
		display(outputUser, "*                                                           *\n");
		display(outputUser, "* To launch the program directly nex time enter the         *\n");
		display(outputUser, "* Following command : java CalcRPL option1 option2          *\n");
		display(outputUser, "*                                                           *\n");
		display(outputUser, "*    option 1:          option 2:                           *\n");
		display(outputUser, "*    1- Local           1-Session without log               *\n");
		display(outputUser, "*    2- Remote          2-Session with log                  *\n");
		display(outputUser, "*                       3-Replay the log                    *\n");
		display(outputUser, "* If you see this menu it means you did not enter the       *\n");
		display(outputUser, "* correct number of argument.                               *\n");
		display(outputUser, "*                                                           *\n");
		display(outputUser, "* Please enter: option1 option2                             *\n");
		display(outputUser, "*************************************************************\n");
		String [] s1 = new String[2];
		String s = new String();
		try{
			do{
				s=(String)inputUser.readLine();
				s1=s.trim().split(" ");
			}while(!(s1[0].equals("1")||s1[0].equals("2"))&&!(s1[1].equals("1")||!s1[1].equals("2")||s1[1].equals("3")));
		}catch(IOException e){
			System.out.println(e.getMessage());
		}
		return s1;

	} 
	void initStream(String [] args){
		if(args[0].equals("1")){
	  		inputUser = new BufferedReader(new InputStreamReader(System.in));
			outputUser = new PrintWriter(System.out);
		}
		else if(args[0].equals("2")){
			try{
				server = new ServerSocket(12345, 1);
				socket = server.accept();
	  			inputUser = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				outputUser = new PrintWriter(socket.getOutputStream());
			}catch(IOException e){
				System.out.println( "problème de connexion" );
				System.exit(1);
			}
			display(outputUser, "Starting remote session");
		}
		if(args[1].equals("2")){
			reclog=true;
			try{
				outputLog = new FileWriter("session.txt", false);
			}catch(IOException e){display(outputUser,e.getMessage());}
		}
		else if(args[1].equals("3")){
			try{
				inputUser= new BufferedReader(new FileReader("session.txt"));
			}catch(IOException e){display(outputUser,e.getMessage());}
		}
	}
	void closeStream(){
		try{
			inputUser.close();
			outputUser.close();
			if(socket!=null){
				socket.close();
				server.close();
			}
			if(reclog){
				outputLog.close();
			}
		}catch(IOException e){display(outputUser,e.getMessage());}
	}
	/*void initStream(String [] args){
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
	}*/
	void writeFile(String s){
		if(reclog){		
			try{
				outputLog.write(s);
				outputLog.write("\r\n");
				outputLog.flush();
			}catch(IOException e){display(outputUser,e.getMessage());}
		}
	}
	void mainLoop(){
		pile = new PileRPL(stackLength);
		String s ="" ;
		int value =0;
		String[] splitStri=new String[2];
		display(outputUser,pile);
		do{
			try{
				display(outputUser,">>");
				s = (String)inputUser.readLine();
			}catch(IOException e){display(outputUser, e.getMessage());}
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
				display(outputUser, "State of the stack :");
				display(outputUser,pile);
				//TO DO FONCTION QUI PRENDS SORTIE (ex output) et message et
				//fait un display et un flush
			}
			else if(s.matches("[+x/-]")){
				switch(s){
					case "+":
						//Then the operations method can be called
						//to apply operation to the objects of the stack
						pile.add();
						display(outputUser,pile);
						break;
					case "-":
						pile.sou();
						display(outputUser,pile);
						break;
					case "x":
						pile.mul();
						display(outputUser,pile);
						break;
					case "/":
						pile.div();
						display(outputUser,pile);
						break;
					default:
						break;
				}
			}
			else if(s.matches("clear")){
				pile.clear();
				display(outputUser,"\nState of the stack :\n");
				display(outputUser,pile);
			}
			else if(s.matches("drop")){
				pile.drop();					
				display(outputUser,"\nState of the stack :\n");
				display(outputUser,pile);
			}
			else if(s.matches("swap")){
				pile.swap();					
				display(outputUser,"\nState of the stack :\n");
				display(outputUser,pile);
			}
			else if(s.matches("pop")){
				ObjEmp poper =pile.pop();
				display(outputUser, "The object poped : "+poper);
				display(outputUser,pile);
			}
			else if(splitStri[0].matches("push")&&splitStri[1].matches("[0-9]+")){
				ObjEmp oe1 = new ObjEmp(value);
				pile.push(oe1);
				display(outputUser,"\nState of the stack :\n");
				display(outputUser,pile);
			}
			else{
				if(s.equals("q")){
					display(outputUser,"Bye Bye !!\n");
				}
				else{
					display(outputUser,"Invalid input please try again\n");
					continue;
				}
			}
		}while(!s.equals("q"));
	}
}

