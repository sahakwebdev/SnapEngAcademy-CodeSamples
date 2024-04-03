class checksum {
   static MIPS_OS_Interface mips = new MIPS_OS_Interface();

public static int checksum() {
	        int max_int = 255;  
	        int sum = 0;
	        int header_checksum = 0;
            int i;
            int quotient;
            int remainder;
            int checksum;
            int result;
            int quotientAndRemainder;
            int maxIntPlusOne;

init:       ;
            i = 0;
loop:       for (;i < 10;) {
body:           ;
                mips.read_d();
                if (i == 5) {
nestedCons:         ;
                    header_checksum = mips.retval();
                    // goto doneIf;
                }
doneIf:         ;
                sum = sum + mips.retval();
next:           ;
                i++;
                continue loop;
            }
loopDone:   ;

            sum = sum - header_checksum;
            maxIntPlusOne = max_int + 1;       
            quotient = sum / maxIntPlusOne;
            remainder = sum % maxIntPlusOne; 
            quotientAndRemainder = quotient + remainder;    
            checksum = max_int - quotientAndRemainder;

            if (header_checksum == checksum) {
cons:           ;
                result = 0;
                // goto done
            } else {    ;
alt:            ;
                result = checksum;
                // goto done
            }
done:       ;
        
   	        return result;
}
// Task 2 complete: Thu Oct 12 23:49:47 PDT 2023

  public static void main(String[] args) {
  
    int index;

    int $v0 = checksum();

    // Augment this code based upon desired output type

    mips.print_ci('\n');   // Print extra '\n' in case the users
                           // last line does not include the 
                           // proforma '\n'.
    mips.print_d($v0);
    mips.print_ci('\n');
    return;
  }
}
