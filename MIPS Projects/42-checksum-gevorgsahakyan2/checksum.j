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

