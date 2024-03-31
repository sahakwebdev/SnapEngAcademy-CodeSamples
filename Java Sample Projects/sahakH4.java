// Author: Gevorg Sahakyan
import java.util.*;
import java.io.*;
 
public class sahakH4 {
	// sahakH4 class  variables
	//head is a pointer to beginning of linked list
	private node head = null; 
	
	//use prt for System.out to save typing
  	PrintStream prt = System.out;

	// class node
	private class node{
	// class node variables
		String data; 
		node rlink;
		// constructor
		node(String x){
			data = x; 
			rlink = null;
		} // end constructor
	}// end class node
	
  	// print list elements formatted 
	public void prtlst() {
		// Local variables
		node cur;
		prt.printf("\n\tList contents:");
		for (cur = head; cur != null ; cur = cur.rlink)
			prt.printf("%10s, ", cur.data);
		// end for
   	} // end prtlst
		
  	// insert x in a sorted linked list
    public void insertSorted(String x){
		prt.printf("\n\t\tInsert %10s in asorted linked list:", x);

		node tmp = new node(x);

		if (head == null || x.compareTo(head.data) <= 0) {
			tmp.rlink = head;
			head = tmp;
			prt.printf(" Successful Insertion");
			return;
		}

		node cur = head;
		while (cur.rlink != null && x.compareTo(cur.rlink.data) > 0) {
			cur = cur.rlink;
		}

		tmp.rlink = cur.rlink;
		cur.rlink = tmp;

		prt.printf(" Successful Insertion");
	} // end insersorted

  	// delete x from a sorted linked list
	public void deleteSorted(String x){
		prt.printf("\n\t\tDelete element %10s from sorted list:", x);

		if (head == null) {
			prt.printf("Failed...Linked list is empty");
			return;
		}
	
		int count = 1;

		if (x.equals(head.data)) {
			prt.printf(" %d", count);
			head = head.rlink;
			return;
		}
	
		node cur = head;
		node previous = null;
	
		while (cur != null && !x.equals(cur.data)) {
			previous = cur;
			cur = cur.rlink;
			count++;
		}
	
		if (cur != null && x.equals(cur.data)) {
			prt.printf(" %d", count);
			previous.rlink = cur.rlink;
		} else {
			prt.printf(" Element Not Found");
		}
	} // end deleteSorted

  	// sequential serach for x in a sorted list
  	// if successful return position of x in the list
  	// otherwise return 0;
  	public void searchSorted(String x){
    	prt.printf("\n\t\tSearch for %10s:", x);

		int count = 1;
		
		node cur;

		for (cur = head; cur != null; cur = cur.rlink) {
			int comparison = x.compareTo(cur.data);

			if (comparison == 0) {
				prt.printf(" %d", count);
				return;
			} else if (comparison < 0) {
				prt.printf(" Not Found");
				return;
			}

			count ++;
		}
	
		prt.printf(" Not Found");

  	} // end searchSorted	

	//reverse elements of a singly linked list
	private void reverse(){
		if (head == null) {
			prt.printf("Reverse Failed...Linked List is Empty");
			return;
		}
		if (head.rlink == null) {
			prt.printf("Reverse Failed...Linked List Has Only 1 Element");
			return;
		}

		node tmp;
		node next = head.rlink;
		head.rlink = null;

		while (next != null) {
			tmp = next;
			next = next.rlink;
			tmp.rlink = head;
			head = tmp;
		}
	} // end reverse

	// Sorted linked list: insert, delete, search and reverse
	private void process(String fn){
		int j, ins, del, srch;
		String x;  // local variables

		prt.printf("\tLinked list implementation of a Sorted list,"+
		"\n\tgets inputfile name from program argument, then reads:"+
		"\n\tinteger No. of elements to insert"+
		" followed by elements to insert,"+
		"\n\tinteger No. of elements to search followed by elements to search"+
		"\n\tinteger No. of elements to delete followed by "+
		"elements to delete" +
		"\n\tand finally reverses the list:"+
		"\n\t\tTo compile: javac sahakH4.java" +
		"\n\t\tTo execute: java  sahakH4 inputfilename");

      try{
		// open input file
		Scanner inf = new Scanner(new File(fn));

		//read no. of elements to insert
		ins = inf.nextInt();
		prt.printf("\n\n\tInsert %d elements in the list.", ins);
		for(j = 1; j <= ins; j++){
			x = inf.next();   // read x to insert
			insertSorted(x);  // insert x in a sorted list
		} // end for
		prtlst();//print  list elements

		//read no. of elements to search in list
		srch = inf.nextInt();
		prt.printf("\n\n\tSearch for %d elements in list.", srch);
		for(j = 1; j <= srch; j++){
			x = inf.next();  // read x to search
			searchSorted(x); // Search for x in a sorted list
		}// end for

		//read no. of positions to delete from list
		del = inf.nextInt();
		prt.printf("\n\n\tDelete %d elements from list.", del);
		for(j = 1; j <= del; j++){
			x = inf.next();  // read x to delete
			deleteSorted(x); // delete x from a sorted list
		}// end for
		prtlst();//print list elements
		prt.printf("\n\tReverse of list:");
		reverse();
		prtlst(); // print list elements

		// close input file
		inf.close();
      }catch (Exception e){prt.printf("\n\tRead Error! %s", e);}
	} // end process
   
  	// main method
  	public static void main(String args[]) throws Exception{
		// declare variables
		int cnt = args.length; // get no. of atguments
		String fname;
		if (cnt < 1){
		  System.out.printf("\n\n\tInvalid No. of aguments! EXIT.\n");
		  return;
		}
			
		// get input file name
		fname = args[0]; 

		//create an instance of a class
		sahakH4 lst = new sahakH4();		

		// Call process method
		lst.process(fname);
		
		System.out.printf("\n\tAuthor: Gevorg Sahakyan Date: %s\n",
		java.time.LocalDate.now()); 
 	} // end main
}// end class sahakH4	
