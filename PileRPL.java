//Class that create, display, update a stack.
//In short, this is a class to  manipulate the stack
//of the polish calculator
public class PileRPL{
	//Declaration of an object pile which is a 
	//table of stackable object (class ObjEmp)
	ObjEmp pile [];
	//Definition of a counter for the 
	//number of object inside the stack
	int nbObj;
	//Definition of a constant as the maximum
	//stackable object in order to test if the
	//stack have th rigth behavior when its full
	final static int NBOBJMAX=3;
	int size;
	//Constructor of the stack with the size of it
	//as an argument, nbObj is initialized at -1
	//because the stack is not created. Surcharge 
	//du constructeur PileRPL
	public PileRPL(int size){
		this.size =size;
		pile = new ObjEmp[size];
		nbObj = -1;
	}
	//Default constructor when no size
	//is entered for the pile is will be 3,
	//the constant NBOBJMAX by default
	public PileRPL(){
		this(NBOBJMAX);
		nbObj = -1;
	}
	//Method that checks is the stack is empty
	boolean isEmpty(){
		if(nbObj == -1){
			return true;
		}
		return false;
	}
	//Method that check if the stack is full
	boolean isFull(){
		if(nbObj == pile.length-1){
			return true;
		}
		return false;
	}
	//Method to push an object at the top of the stack
	//It takes an object to stack as an argument
	void push(ObjEmp oe){
		//Alert the user is the stack is full
		if (isFull()){
			System.out.println("La pile est pleine impossible d'empiler");
		}
		else{
			//Add the object to the top of the stack
			pile[nbObj+1] = oe;
			//Increment the number of object inside the stack
			nbObj++;
		}
	}
	//Method to take the last object out 
	//of the stack, returns the object
 	ObjEmp pop(){
		//Alert the user if the stack is empty
		if (isEmpty()){
			System.out.println("La pile est vide impossible de depiler");
		}
		//Get the top object from the stack
		ObjEmp poper = pile[nbObj];
		//Put it to null on the stack 
		pile[nbObj]=null;
		//Decrement the number of object
		//in the stack
		nbObj--; 
	  //return the object at the top of the stack
	  return poper;      
	}
	//TO DO : CREER UNE EXCEPTION QUI GERE LE FAIT 
	//QU'IL Y AIT MOINS DE DEUX VALEURS.
	void swap(){
		if(nbObj>=1){
			ObjEmp temp=null;
			temp =pile[nbObj-1];
			pile[nbObj-1]=pile[nbObj];
			pile[nbObj]=temp;
		}
		else{
			System.out.println("Not enough values in stack to swap");
		}
	}
	void clear(){
		for(int i=0; i<size; i++){
			pile[i]=null;
			nbObj=-1;
		}
	}
	void drop(){
		if(!isEmpty()){
				pile[nbObj]=null;
				nbObj--;
		}
		else{
			System.out.println("Can't drop no values in stack");
		}
	}
	//Surcharge (override) of the toString method
	//in order for the print() method to print 
	//the stack and its content, return a string
	public String toString(){
		//Initialistion of a string
		String value = "";
		//Construction of the graphical
		//stack with a string
		value +=" +----+\n";
		//Add the current value of the objects
		//on the stack in the string
		for (int i=size-1; i>=0; i--){
			if(pile[i]!=null){
				value += i+"! "+pile[i]+"  !\n";
				value +=" +----+\n";
	 		}	
			else{
				 value += i+"!    !\n";
				value +=" +----+\n";
			}
		}
		//return the concatenated string to
		//be printed
		return value;
	}
	//Method to call the method add() from the 
	//ObjEmp on objects of the stack
	public void add(){
		//An addition can only be made when 
		//the stack contains at least two objects 
		if(nbObj>=1){
			//The following method add()
			//returns an object which is the result
			//of the operation. The method pop()
			//returns the last object from the stack
			//thus the operation is : 
			//(top object from the stack).(apply the method add on it)
			//with as argument(top object from the stack -1 since the last
			//object was already poped)
			ObjEmp res = pop().add(pop());
			//Then push the updated object 
			//to the top of the stack
			push(res);
		}
		else{
			System.out.println("Not enough value in the stack");
		}
	}
	//Same method as the add() method but with the other
	//operations
	public void sou(){
		if(nbObj>=1){
			ObjEmp res = pop().sou(pop());			
			push(res);
		}
		else{
			System.out.println("Not enough value in the stack");
		}
	}
	public void mul(){
		if(nbObj>=1){
			ObjEmp res = pop().mul(pop());			
			push(res);
		}
		else{
			System.out.println("Not enough value in the stack");
		}
	}
	public void div(){
		if(nbObj>=1){
			ObjEmp res = pop().div(pop());			
			push(res);
		}
		else{
			System.out.println("Not enough value in the stack");
		}
	}
}
