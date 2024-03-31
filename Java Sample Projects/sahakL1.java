// Author: Gevorg Sahakyan
import java.util.*;
import java.io.*;
  
public class sahakL1{ 		 
	// class list
	private class list{
	// class list variables
	  int num; 
	  list nxt;
	  // class list constructor
	  list(int x){
		num = x; 
		nxt = null;
	  } // end class list constructor
	}// class list
	
	//use prt for System.out to save typing
	PrintStream prt = System.out;
				 			
 	// print formatted elements of linked list
	private void prtlnk(list L) {
		// Local variables
		prt.printf("\n\tLinked List: ");
		for ( ; L != null ; L = L.nxt)
			prt.printf("%2d, ", L.num);
		// end for
   	} // end prtlnk
		
 	// print formatted elements of array a[]
	private void prtarr(int a[], int n){
		// local variables
		int i;
		prt.printf("\n\t    Array[]: ");
		for (i = 0; i < n; i++) 
			prt.printf("%2d, ", a[i]);
		// end for		
	} // end prtarr
	
	//Insert x in a sorted array arr[], Currently no. element in
	//array is n, assume array has enough space for insertion
	private int insertsortedarr(int x, int arr[], int n){  
		int i = n - 1;
		while (i >= 0 && arr[i] > x) {
			arr[i+1] = arr[i];
			i--;
		}
		arr[i+1] = x;

		return n+1; //return no. of element in arr[]
	} // end insertsortedarr	  		  

	//Insert x in a sorted linked list
	private list insertsortedlnk(int x, list L){  
		list tmp = new list(x);

		if (L == null || x <= L.num) {
			tmp.nxt = L;
			return tmp;
		} else {
			list currentNode = L;
			while (currentNode.nxt != null && x > currentNode.nxt.num) {
				currentNode = currentNode.nxt;
			}
			
			tmp.nxt = currentNode.nxt;
			currentNode.nxt = tmp;
		}
		
		return L; //return front of linked list
	} // end insertsortedlnk	  		  

	//reverse linked list elements
	private list reverselnk(list L){
		list prevNode = null;
        list currentNode = L;
        list nextNode = null;

        while (currentNode != null) {
            nextNode = currentNode.nxt;
            currentNode.nxt = prevNode;
            prevNode = currentNode;
            currentNode = nextNode;

			L = prevNode;
        }

		return L; //return front of linked list
	} // end reverselnk		  

	//reverse array a[] with n elements
	private void reversearr(int a[], int n) {
		int temp;
		for (int arrayFront=0,arrayEnd=n-1; arrayEnd > arrayFront; arrayFront++,arrayEnd--) {
			temp = a[arrayFront];
			a[arrayFront] = a[arrayEnd];
            a[arrayEnd] = temp;
		}
        
	} // end reversearr		  

	// Insert, reverse, ..

	private void process(int m){
		// local variables
		list Lst = null;
		int j, n = 0, x; 

		prt.printf("\n\tProcess method generates m random"+
		" numbers and calls above 4 methods:");

		// Allocate Space for array		 	 
		int a[] = new int[25];// index 0 is used
		// Initialize inked list	 	 
		list lst = null;
		if (m > 20) m = 20;
			
		// Create an instance of Random Class
		Random rand = new Random();		
		
		//Generate m random number and insert in 
		// array a[] an linked list lst
		for(j = 0; j < m; j++){
			x = rand.nextInt(40) + 5;   //Generate x
			n = insertsortedarr(x, a, j);
			lst = insertsortedlnk(x, lst);
		}// end for
		prt.printf("\n\n\tArray and linked list after insertion");
		prtarr(a, n); //print array elements
		prtlnk(lst); //print list elements
		prt.printf("\n\n\tArray and linked list after reversing");
		reversearr(a, n);
		lst = reverselnk(lst);
		prtarr(a, n); //print array elements
		prtlnk(lst); //print list elements
	} // end process
		  
	// main method
	public static void main(String args[]) throws Exception{
		System.out.printf("\tLab Exam1: This program creates a sorted array and"+
		" linked list with m(<15) random integers,"+
        "\n\tand reverses them. Your job is to complete 4 methods:"+
		"\n\tinsertsortedarr(x, arr, n), insertsortedlnk(x, lst)"+
		"\n\treversearr(arr, n), reverse array elements and "+
		"\n\treverselnk(lst), reverse linked list elements"+
		"\n\t\tTo compile: javac sahakL1.java" +
		"\n\t\tTo execute: java  sahakL1 m");

		// declare variables
		int m, cnt = args.length; // get no. of arguments						
		if (cnt < 1){
			System.out.printf("\n\n\tOOPS Invalid No. of arguments! EXIT.\n");
			return;
		} // end if
		
		// get value of m from program arguments
		m = Integer.parseInt(args[0]);

		//create an instance of a class
		sahakL1 lab = new sahakL1();
		
		// Call process method
		lab.process(m);
					
		System.out.printf("\n\n\tAuthor: Gevorg Sahakyan Date: %s\n", 
		java.time.LocalDate.now());
	} // end main
} // end class sahakL1
