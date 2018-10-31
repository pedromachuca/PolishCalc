## PolishCalc

**Calculator RPN - JAVA** *Version : 11*
[Wiki Reverse Polish Notation](https://en.wikipedia.org/wiki/Reverse_Polish_notation)

##
To compile the program please enter the following command :

```javac *a```

Then to launch the program enter and get the menu :

```java CalcRPL```

##Different mode are available :                        
   
   **1- Mode local**                                         
        Use the calculator locally
        
   **2 - Online mode**
        Use the calculator from a remote location          
        Launch the program as usual (server side)         
        Connect from a remote location (telnet, netcat...) 
        Example : telnet IP PORT   
   
   **3 - Online mode that logs the current session**         

##To use the calculator :

Enter a value to push on the stack, press ENTER      
Enter another value to push on the stack, press ENTER  
Enter the operand you wish to apply, press ENTER      

The following commands are also available :         
drop, swap, clear, pop and push number (ex: push 2)  
You can leave by entering **q** at anytime                 
##                                                         
To launch the program directly next time enter the       
Following command : ```java CalcRPL option1 option2```
                                                        
   option 1:          option 2:                           
   1- Local           1-Session without log             
   2- Remote          2-Session with log              
                      3-Replay the log              
