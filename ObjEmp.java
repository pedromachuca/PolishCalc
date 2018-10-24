//Definition de la classe objet empilable au départ ne prend en argument que 
//des entiers devra par la suite pouvoir empiler différents type
public class ObjEmp{

	//ici non initialisé on l'appel 1
	int value;
	//Constructeur de ObjEmp donc on devra lui passer un int
	public ObjEmp(int value){
		//initialisation de value pointe sur 1
		this.value=value;
	}
	//Surchage de la méthode toString 
	//@override
	public String toString(){
		return ""+value;
	}
	//Method that returns the value of the object
	public int getValue(){
		return value;
	}
	//Method to addition two objects
	//It return the current updated value 
	//of the addition of the two objects
	//All the operations method take an
	//object as argument
	public ObjEmp add(ObjEmp val){			
		//value is the current object (this) value
		///to get the other instanace of the object 
		//value the method getValue() is used on the 
		//object which returns its (this) value.
		//the current object (this) is then updated 
		//with the result of the operation thus (this)
		//is returned
		this.value = val.getValue()+value;
		return this;
	}	
	//Method to soustract two object
	//It return the current updated value 
	//of the soustraction of the two objects
	public ObjEmp sou(ObjEmp val){			
		this.value = value -val.getValue();
		return this;
	}	
	//Method to multiply two object
	//It return the current updated value 
	//of the multiplication of the two objects
	public ObjEmp mul(ObjEmp val){			
		this.value = value*val.getValue();
		return this;
	}	
	//Method to divide two object
	//It return the current updated value 
	//of the division of the two objects
	public ObjEmp div(ObjEmp val){			
		this.value = value/val.getValue();
		return this;
	}	
}
