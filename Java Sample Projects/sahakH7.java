// Java Infix to Postfix  sahakH7.java
import java.io.*; 
import java.util.*;
  
public class sahakH7{
	// use prt for System.out to save typing
	PrintStream prt = System.out;	
	// return priority of ch
    private int priority(char ch){ 
		switch(ch) {
			case '^':
				return 3;
			case '*':
			case '/':
			case '%':
				return 2;
			case '+':
			case '-': 
				return 1;
			default:
				return 0;
		}
    }  // end priority
	
    // Convert exp to postfix 
    private String postfix(String exp){

		Stack<Character> stack = new Stack<Character>();

		String postfixFormat = ""; 

		for (int i=0; i < exp.length(); i++) {
			char singlCharacter = exp.charAt(i);
			if (Character.isLetter(singlCharacter)) {
				postfixFormat += singlCharacter;
				continue;
			}
			switch(singlCharacter) {
				case '(':
				case '{':
				case '[':
					stack.push(singlCharacter);
					break;
				case ')':
				case '}':
				case ']':
					while (!stack.empty() && ((stack.peek() != '(') && (stack.peek() != '{') && (stack.peek() != '['))) {
						postfixFormat += stack.pop();
					}
					if (stack.empty() || ((stack.peek() != '(') && (stack.peek() != '{') && (stack.peek() != '['))) {
						postfixFormat = "Invalid Expression Inputted";
						return postfixFormat;
					}
					else
						stack.pop();
					break;
				default:
					while (!stack.empty() && priority(singlCharacter) <= priority(stack.peek())) {
						postfixFormat += stack.pop();
					}
					stack.push(singlCharacter);
			}
		}
		while (!stack.empty()){
			if((stack.peek() == '(') || (stack.peek() == '{') || (stack.peek() == '[')) {
				postfixFormat = "Invalid Expression Inputted";
				return postfixFormat; 
			} 
			postfixFormat += stack.pop();
		}
		return postfixFormat;
    } // end postfix
	
	// Process method reads every infix expression and
	// converts it to postfix
	private void process(String fn){ 
		prt.printf("\n\tProcess method reads every infix expression "+
		"from input file,\n\tthen computes and prints its postfix"); 
		//infix and postfix expressions are String	
		String exp, post;
		try{
			// open input file
			Scanner inf = new Scanner(new File(fn)); 
			
			while (inf.hasNext()){
			  // read next infix xpression				
			  exp = inf.next();
			  // Change exp to uppercase
			  exp = exp.toUpperCase();		
			  // call infix to postfix method
			  post = postfix(exp);
			  prt.printf("\n\t\tPosfix of %s is: %s", exp, post); 
		}// end while
		// close input file
		inf.close();
	 }catch (Exception e) {prt.printf("\nException %s\n", e);}
	}// end process method	

    public static void main (String[] args){ 
		// declare local variables
		System.out.printf("\tProgram to convert infix expression to pofix, "+
		"gets input\n\tfile name from program argument,"+
		" then calls process method:"+
		"\n\t\tTo compile: javac sahakH7.java" +
		"\n\t\tTo execute: java  sahakH7 inputfilename");
		int cnt = args.length; // get no. of atguments
		String fname;
		
		// get no. of  arguments
		cnt = args.length;

		if (cnt < 1){
			System.out.printf("\n\n\tOOPS Invalid No. of aguments! EXIT.\n");
			return;
		} // end if	
	
		// create an instance of postfix  
		sahakH7 tst = new sahakH7();

		// get input file name
		fname = args[0];
		System.out.printf("\tfname: %s", fname); 
		// Call process to convert infix exp to postfix
		tst.process(fname);
        
		System.out.printf("\n\n\tAuthor: Gevorg Sahakyan Date: %s\n", 
		java.time.LocalDate.now());
    } // end main
} // end sahakH7
