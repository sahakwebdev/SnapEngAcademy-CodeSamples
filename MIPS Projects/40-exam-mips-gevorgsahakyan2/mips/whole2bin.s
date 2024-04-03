        .text
        .include "macros/syscalls.s"
        .include "macros/stack.s"

        .globl whole2bin

whole2bin: nop                  #  public static int whole2bin(int whole) {

        # t0: whole
        # t1: int counter;
        # t2: int number;
        # t3: int poppedElem;
        # t4: int remainder;

        push_s_registers()      # Save S and T registers
        move $t0, $a0           # Demarshal input arguments
        
        li $t1, 0               #  counter = 0;

init1:  nop                     #  ;
    	move $t2, $t0           #  number = whole;
loop1:  beq $t2, $zero, done1   #  while (number != 0) {
body1:    nop                   #      ;
          rem $t4, $t2, 2       #      remainder = number % 2;
          push $t4              #      mips.push(remainder);
          div $t2, $t2, 2       #      number = number / 2;
          addi $t1, $t1, 1      #      counter = counter + 1;
next1:    nop                   #      ;
          b loop1               #      continue loop1;
                                #  }
done1:  nop                     #  ;

loop2:  beq $t1, $zero, done2   #  while (counter != 0) {
body2:    nop                   #      ;
          pop $t3               #      poppedElem = mips.pop();
          print_d($t3)          #      mips.print_d(poppedElem);
next2:    nop                   #      ;
          subi $t1, $t1, 1      #      counter = counter - 1;
          b loop2               #      continue loop2;
                                #  }
done2:  nop                     #  ;

                                #  return 0;
        li $v0, 0               # Marshal return value
        pop_s_registers()       # Restore S and T registers
        jr $ra
                                #  }
