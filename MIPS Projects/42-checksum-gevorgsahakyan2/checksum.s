        .text
        .globl checksum

        .include "macros/syscalls.s"

checksum: nop                                 # public static int checksum() {

                # t0: max_int                  
                # t1: sum                      
                # t2: header_checksum          
                # t3: i                        #  int i;
                # t4: quotient                 #  int quotient;
                # t5: remainder                #  int remainder;
                # t6: checksum                 #  int checksum;
                # t7: result                   #  int result;
                # t8: quotientAndRemainder     #  int quotientAndRemainder;
                # t9: maxIntPlusOne            #  int maxIntPlusOne;
         
	        li $t0, 255                    #  int max_int = 255;  
	        li $t1, 0                      #  int sum = 0;
	        li $t2, 0                      #  int header_checksum = 0;
 
init:           nop                            #  ;
                li $t3, 0                      #  i = 0;
loop:           bge $t3, 10, loopDone          #  for (;i < 10;) {
body:             nop                          #      ;
                  read_d()                     #      mips.read_d();
                bne $t3, 5, doneIf             #      if (i == 5) {
nestedCons:     nop                            #          ;
                  move $t2, $v0                #          header_checksum = mips.retval();
                  b doneIf                     #          // goto doneIf;
                                               #      }
doneIf:         nop                            #      ;
                add $t1, $t1, $v0              #      sum = sum + mips.retval();

next:             nop                          #      ;
                  addi $t3, $t3, 1             #      i++;
                b loop                         #      continue loop;
                                               #  } 
loopDone:       nop                            #  ;  
                sub $t1, $t1, $t2              #  sum = sum - header_checksum;
                addi $t9, $t0, 1               #  maxIntPlusOne = max_int + 1;
                div $t4, $t1, $t9              #  quotient = sum / maxIntPlusOne;
                rem $t5, $t1, $t9              #  remainder = sum % maxIntPlusOne;
                add $t8, $t4, $t5              #  quotientAndRemainder = quotient + remainder;
                sub $t6, $t0, $t8              #  checksum = max_int - quotientAndRemainder;
                bne $t2, $t6, alt              #  if (header_checksum == checksum) { 
cons:             nop                          #    ;
                  li $t7, 0                    #    result = 0;
                  b done                       #    // goto done
                                               #  } else {    ;
alt:              nop                          #    ;
                  move $t7, $t6                #    result = checksum;
                  b done                       #    // goto done
                                               #  }
done:           nop                            #  ;
                move $v0, $t7                  #  return result;
                jr $ra                         #
                                               #  }

