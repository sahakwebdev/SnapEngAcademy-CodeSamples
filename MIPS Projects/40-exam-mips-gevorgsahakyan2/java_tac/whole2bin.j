public static int whole2bin(int whole) {

    	int counter;
        int number;
        int poppedElem;
        int remainder;
        
        counter = 0;

init1:  ;
    	number = whole;
loop1:  while (number != 0) {
body1:      ;
            remainder = number % 2;
        	mips.push(remainder);
            number = number / 2;
            counter = counter + 1;
next1:      ;
            continue loop1;
        }
done1:  ;

loop2:  while (counter != 0) {
body2:      ;
            poppedElem = mips.pop();
            mips.print_d(poppedElem);
next2:      ;
            counter = counter - 1;
            continue loop2;
        }
done2:  ;  
        return 0;
}
