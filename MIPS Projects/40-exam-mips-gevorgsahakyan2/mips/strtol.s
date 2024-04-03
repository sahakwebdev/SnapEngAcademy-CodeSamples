        .text
        .include "macros/syscalls.s"
        .include "macros/stack.s"

        .globl strtol
        .globl glyph2int

strtol: nop                     #  public static int strtol( char[] buffer, int radix) {
        # t0: buffer
        # t1: radix
        # t2: 
        # t3: int digit;
        # t4: int value;
        # t5: int i;
        # t6: int x;
        # t7: int negativeOne;
        # t8: just a temp

        push_s_registers()      # Save S and T registers
        move $t0, $a0           # Demarshal input arguments
        move $t1, $a1 

        li $t7, -1              #  negativeOne = -1;
            
        li $t4,  0              #  value = 0;
init:  nop                      #  ;
        li $t5, 0               #  i = 0;

        la $t8, ($t0)           #  x = buffer[i];
        add $t8, $t8, $t5
        lbu $t6, 0($t8)
    
loop1:  beq $t6, 10, done       #  for (;x != '\0';) {
body1:  nop                     #      ;
        #########################      digit = glyph2int((char) x, radix);
        move $a0, $t6           # Marshal input arguments
        move $a1, $t1
        push_t_registers        # Save T Registers
        push $ra, $fp, $sp, $gp # Save special registers
        jal glyph2int
        pop $ra, $fp, $sp, $gp  # Restore special registers
        pop_t_registers         # Restore T Registers
        move $t3, $v0           # Demarshal return value
        #########################
        beq $t3, $t7, done      #      if (digit == negativeOne) break loop1;
        mul $t4, $t4, $t1       #      value = value * radix;
        add $t4, $t4, $t3       #      value = value + digit;
next:   nop                     #      ;
        addi $t5, $t5, 1        #      i = i + 1;
        la $t8, ($t0)           #      x = buffer[i];
        add $t8, $t8, $t5
        lbu $t6, 0($t8)
        b loop1                 #      continue loop1;
                                #  }
done:   nop                     #  ;
                                #  return value;
        move $v0, $t4           # Marshal return value
        pop_s_registers()       # Restore S and T registers
        jr $ra
                                #  }

glyph2int:      nop             #  public static int glyph2int(char glyph, int radix){

        # t0: glyph 
        # t1: radix
        # t2: int value;
        # t3: '0'
        # t4: '9'
        # t5: 'a'
        # t6: 'f'
        # t7: 'A'
        # t8: 'F'

        li $t3, 48
        li $t4, 57
        li $t5, 97
        li $t6, 102
        li $t7, 65
        li $t8, 70

        push_s_registers()      # Save S and T registers
        move $t0, $a0           # Demarshal input arguments
        move $t1, $a1           
        
        li $t2, -1              #  value = -1;

init1:  nop                     #  ;
        bgt $t3, $t0, done1     #  if ('0' <= glyph) {
cons1:    nop                   #      ;
initA:    nop                   #      ;
          bgt $t0, $t4, doneA   #      if (glyph <= '9') {
consA:      nop                 #          ;
            sub $t2, $t0, $t3   #          value = glyph - '0';
            b doneA             #          // goto doneA;
                                #      }
doneA:    nop                   #      ;
          b done1               #      // goto done1;
                                #  }
done1:  nop                     #  ;

init2:  nop                     #  ;
        bgt $t5, $t0, done2     #  if ('a' <= glyph) {
cons2:    nop                   #      ;
initB:    nop                   #      ;
          bgt $t0, $t6, doneB   #      if (glyph <= 'f') {
consB:      nop                 #          ;
            sub $t2, $t0, $t5   #          value = glyph - 'a';
            addi $t2, $t2, 10   #          value = value + 10;
            b doneB             #          // goto doneB;
                                #      }
doneB:    nop                   #      ;
          b done2               #      // goto done2;
                                #  }
done2:  nop                     #  ;

init3:  nop                     #  ;
        bgt $t7, $t0, done3     #  if ('A' <= glyph) {
cons3:    nop                   #      ;
initC:    nop                   #      ;
          bgt $t0, $t8, doneC   #      if (glyph <= 'F') {
consC:      nop                 #          ;
            sub $t2, $t0, $t7   #          value = glyph - 'A';
            addi $t2, $t2, 10   #          value = value + 10;
            b doneC             #          // goto doneC;
                                #      }
doneC:    nop                   #      ;
          b done3               #      // goto done3;
                                #  }
done3:  nop                     #  ;

init4:  nop                     #  ;
        blt $t2, $t1, done4     #  if (value >= radix) {
cons4:    nop                   #      ;
          li $t2, -1            #      value = -1;
          b done4               #      // goto done4;
                                #  }
done4:  nop                     #  ;
                                #  return value;
        move $v0, $t2           # Marshal return value
        pop_s_registers()       # Restore S and T registers
        jr $ra
                                #  }