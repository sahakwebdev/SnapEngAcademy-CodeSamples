// JAVA implementation of Binary Search Tree (BST) 
// BST program gets input file name from program argument,
// then calls process method.
// process method:
//	a) Reads No. of elements (integer) to insert in BST
//	   followed by elements to insert.
//	b) Prints inorder, preorder and postorder traversals
//	c) Reads No. of elements (integer) to search followed by element to search.
//	d) Finalcly prints BST level by level.    

import java.io.*; 
import java.util.*; 

public class sahakH9{  
  
   // use prt for System.out to save typing
    PrintStream prt = System.out;
	
	// class phone
	private class phone {
		String name;
		int num; //Assume phone no. is at most 5 digits!
		phone lchild, rchild; //left and right child
		// phone constructor
		phone (String nam, int no) {
			name= nam;
			num = no;
			lchild = rchild = null;
		} //end phone constructor
	} // end class phone

	//insert x in the BST from node t, 
	private phone insert(phone t, String name, int number){
		if (t == null){
			//BST is empty, x will be root data
			//Allocate space for x 
			t = new phone(name, number);
			return t;
		}
		phone savet, tmp, parent = null;
		//save root of the BST since it doesn't change
		savet = t;
		// Allocate space for x 
		tmp = new phone(name, number);
		
		// find parent node for x	  
		while (t != null) { 
			parent = t;   // save t
			if (name.compareTo(t.name) <= 0) 
				t = t.lchild; //Search left subtree
			else	 
				t = t.rchild; //Search right subtree
		} // end while
		
		//x should be inserted either to left or right of node parent 
		if (parent.name.compareTo(name) >= 0) 
			parent.lchild = tmp;
		else 
			parent.rchild = tmp;
		// end if
		// root did not change
		return savet;
	}  // end insert(t, x)

	// search for x from node t 
	private int search(phone t, String name){
		// Search for in the BST  
		while (t != null) { 
			if (name.compareTo(t.name) == 0) { //x not found 
				return t.num;
			} // end if
			if (name.compareTo(t.name) < 0) 
				t = t.lchild;  //Search left subtree
			else	 
				t = t.rchild;  //Search right subtree
		} // end while
		return -1;  // x not found
	} // end search (t, x)   
	
	//print contents of the BST level by level
    private void prtbylevel(phone t){
		//print BST level by level
		if (t == null){
			prt.printf("\n\tBST is empty.");
			return;
		} // endif
		
		// Create a java queue q
		Queue<phone> q = new LinkedList<phone>();
		prt.printf("\n\n\tContent of BST Level by Level:\n");
		q.offer(t);// OR   q.add(t);
		  
		//while q is not empty
		while (q.size()>0){
			int levelSize = q.size();

			prt.printf("%s", "\t");
			for (int i = 0; i < levelSize; i++) {
				t = q.remove();
				prt.printf(" %s ", t.name);    
				prt.printf("%d,", t.num);    
				if (t.lchild != null) 
					q.add(t.lchild); 
				if (t.rchild != null) 
					q.add(t.rchild);
			}
        	prt.printf("\n");
			// endif
		} // end while
	}// end prtbylevel(t)

	//preorder traversal of BST from node t
	private void preorder(phone t){
		if (t == null) return;		
		// BST is not empty
		//print content of root of subtree
		prt.printf (" %s ", t.name); 
		prt.printf ("%5d,", t.num); 
		preorder (t.lchild); //preorder left subtree
		preorder (t.rchild); //preorder right subtree
	} //end preorder(t)

	//inorder traversal of BST from node t
	private void inorder(phone t){
		if (t == null) 
			return;
		// end if
		// BST is not empty		
		inorder (t.lchild); //inorder left subtree
		//print content of root of subtree
		prt.printf (" %s ", t.name); 
		prt.printf ("%5d,", t.num); 
		inorder (t.rchild); //inorder right subtree
	} //end inorder(t)

	//postorder traversal of BST from node t
	private void postorder(phone t){
		if (t == null) return; //BST is empty
		// BST is not empty				
		postorder (t.lchild); //postorder left subtree
		postorder (t.rchild); //postorder right subtree
		//print content of root of subtree 
		prt.printf (" %s ", t.name); 
		prt.printf ("%5d,", t.num); 
	} //end postorder(t)

	// process method for BST to insert and search 
	private void process(String fname){
 		prt.printf("\n\tProcess method:"+
		"\n\t a) Reads No. of elements(integer) to insert in BST "+
		"followed by elements to insert"+
		"\n\t b) Prints preorder, inorder and postorder traversals"+
		"\n\t c) Reads No. of elements(integer) to search followed by element to search."+
		"\n\t d) Finally prints BST level by level");				  
		// local variables
		int j, n, k, x; 
		String name = "";
		//root is a pointer to root of the BST	
		phone root = null; 
		try{  
			// open input file
			Scanner inf = new Scanner(new File(fname)); 
						
			//read no. of elements to insert
			n = inf.nextInt();						
			prt.printf("\n\n\tInsert %d elements in the BST:\n\t", n);
			for(j = 1; j <= n; j++){
				name = inf.next();
				x = inf.nextInt();   // read x
				prt.printf(" %s ", name);
				prt.printf("%5d,", x);
				root = insert(root, name, x); //insert x in the BST
			} // end for
			//print 3 traversal of BST
			prt.printf("\n\tBST traversal:");
			prt.printf("\n\t  Preorder: ");
			preorder(root);
					
			prt.printf("\n\t  Inorder:  ");
			inorder(root);
					
			prt.printf("\n\t  Postorder:");
			postorder(root);
			
			//read no. of elements to search in BST
			n = inf.nextInt(); 
			prt.printf("\n\tSearch for %d elements in the BST.", n);
			for(j = 1; j <= n; j++){
				name = inf.next(); // read x to search

				k = search(root, name);  // Search for x
				prt.printf("\n\t  Search for %s in BST", name);
				if (k == -1) {
					prt.printf(": NOT Found");
				} else {
					prt.printf(": Found --> ");
					prt.printf("%5d", k);
				}
				// end if
			}// end for	

			// close input file 		
			inf.close();   
		}catch (Exception e){prt.printf("\n\tRead Error! %s", e);}
		// print BST level by level
		prtbylevel(root);
	} // end process(fname)
  
	//  main method
	public static void main(String args[]) throws Exception{
		// declare variables
		System.out.printf("\tBinary Search Tree (BST) program "+
		"gets input file name\n\tfrom program argument,"+
		" then calls process method:"+
		"\n\t  To compile: javac sahakH9.java" +
		"\n\t  To execute: java  sahakH9 inputfilename");
		int cnt = args.length; // get no. of arguments
		String inf;
	  
		if (cnt < 1){
		    System.out.printf("\n\tOOOPS Invaldata No. of aguments!");
			return;
		} // end if

		// get input file name
		inf = args[0]; 
		
		// create an instance of BST class
		sahakH9 t = new sahakH9();

		// call process method
		t.process(inf); 

		System.out.printf("\n\n\tAuthor: Gevorg Sahakyan Date: %s\n", 
		java.time.LocalDate.now()); 
	} // end main
} // end class sahakBST
