// JAVA implementation of Heap and Rank Sort 
// Heap and rank Sort program gets input file name from program argument,
// then calls process method that:
//	Reads integer n, no of elements for heap and rank sort
//	Read n integer elements and stores them in array
//	Prints array before and after each sorting 15 elements per line.	   

import java.io.*; 
import java.util.*; 

public class sahakH10{    
   // use prt for System.out to save typing
    PrintStream prt = System.out;

	//Print array a[j] thru arr[k] formatted
	//15 elements perline
	private void prtarr(int arr[], int j, int k){
		int counter = j;
		if (j == 1) {
			counter = 0;
		}
		for (; j <= k; j++) {
			counter++;
            prt.printf("%d ", arr[j]);
            if (counter == 15) {
                prt.println();
				prt.printf("%s", "\t");
				counter = 0;
            }
        }
        prt.println();
	} // end prtarr

//Convert to max heap arr[m] thru arr[n] using max heapdn.
	private void maxheapdn(int arr[], int m, int n){
		int tmp = arr[m];
		int parent = m;
		int child = 2 * parent;
		while (child <= n) {
			if ( tmp >= arr[child] ) {
				break;
			}
			if ( child < n && arr[child] < arr[child + 1] ) {
				child++;
			}
			arr[parent] = arr[child];
			parent = child;
			child = child * 2;
		}
		arr[parent] = tmp;
	} // end heapdn(int arr[], int m, int n)

	//Apply ranksort to a[] with n elements
    private void ranksort(int a[], int b[], int n){
		for (int i = 0; i < n; i++) {
			int currentElement = a[i];
			int rank = 0;
			for (int j = 0; j < n; j++) {
				if ( (a[j] < currentElement) || ( (a[j] == currentElement) && (j < i) ) ) {
					rank++;
				}
			}
			b[rank] = currentElement;
		}
	}// end Ranksort

	//Apply heapsort to array a[] with n elements
    private void heapsort(int arr[], int n){
		int m, tmp;
		// convert to maxheap arr[n/2] thru arr[1] using heapdn
		for(m = n/2; m >= 1; m--) 
			maxheapdn(arr, m, n);
		// end for
		for(m = n; m > 1; m--){
			//swap arr[1] & arr[m] 
			tmp = arr[1];
			arr[1] = arr[m];
			arr[m] = tmp;
		//convert to max heap arr[1] thru arr[m-1] using heapdn
			maxheapdn(arr, 1, m-1);
		} // end for
	}// end heapsort

	// process method for heap 
	private void process(String fname){
		// local variables
		int j, n, a[], b[];
		Scanner inf;
		
		// Allocate space for arrays
		a = new int[25];
		b = new int[25];

		prt.printf("\n\tProcess method:" +
		"\n\t\tReads No. of elements(integer) to insert in the array "+
		"followed by elements to insert"+
		"\n\t\tPrints array elements before and after both sortings 15 numbers per line.");	  
				  
		try{  
			// open input file for heap sort
			inf = new Scanner(new File(fname)); 
						
			//read no. of elements to read
			n = inf.nextInt();
			if (n > 24) n = 24;
			
			prt.printf("\n\tRead %d elements and save in a[1] thru a[n].", n);
			for(j = 1; j <= n; j++)
				a[j] = inf.nextInt();// read arr[j] 
			// end for
			
			// close input file 		
			inf.close();   

			prt.printf("\n\tArray before heap sort:\n\t");
			prtarr(a, 1, n);
			
			//Apply heap sort
			heapsort(a, n);
			
			prt.printf("\n\tArray after  heap sort:\n\t");
			prtarr(a, 1, n);

			// open input file for rank sort
			inf = new Scanner(new File(fname)); 
			
			//read no. of elements to read
			n = inf.nextInt();

			prt.printf("\n\tRead %d elements and save in a[0] thru a[n-1].", n);
			for(j = 0; j < n; j++)
				a[j] = inf.nextInt();// read arr[j] 
			// end for
			
			// close input file 		
			inf.close();   
			
			prt.printf("\n\tArray before Rank sort:\n\t");
			prtarr(a, 0, n-1);
			
			//Apply rank sort
			ranksort(a, b, n);
			
			prt.printf("\n\tArray after  Rank sort:\n\t");
			prtarr(b, 0, n-1);
		}catch (Exception e){prt.printf("\n\tRead Error! %s", e);}
	} // end process(fname)
  
	//  main method
	public static void main(String args[]) throws Exception{
		// declare variables
		System.out.printf("\tHeap & Rank Sort program "+
		"gets input file name\n\tfrom program argument,"+
		" then calls process method:"+
		"\n\t  To compile: javac sahakH10.java" +
		"\n\t  To execute: java  sahakH10 inputfilename");
		int cnt = args.length; // get no. of arguments
		String inf;
	  
		if (cnt < 1){
		    System.out.printf("\n\tOOOPS Invalid No. of arguments!"+
			"\n\tTO Execute: sahakH10 inputfilename");
			return;
		} // end if

		// get input file name
		inf = args[0]; 
						
		System.out.printf("\n\tInput filename: %s", inf);
		
		// create an instance of heap class
		sahakH10 t = new sahakH10();

		// call process method
		t.process(inf); 

		System.out.printf("\n\tAuthor: Gevorg Sahakyan Date: " +
		java.time.LocalDate.now()); 
	} // end main
} // end class sahakH10
