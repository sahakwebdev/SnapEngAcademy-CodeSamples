        .text
        .include "macros/syscalls.s"
        .include "macros/stack.s"

        .globl fractional2bin
        .globl value_of_max

fractional2bin:                 #  public static int fractional2bin(int fractional, int max_bits) {

        # $t0: fractional
        # $t1: max_bits
        # $t2: int counter;
        # $t3: int max;
        # $t4: int number;
                          
        push_s_registers        # Save S registers      
        move $t0, $a0           # Marshal input arguments          
        move $t1, $a1  

        #########################  max = value_of_max(fractional);
        move $a0, $t0           # Marshal input arguments
        push_t_registers        # Save T Registers
        push $ra, $fp, $sp, $gp # Save special registers
        jal value_of_max
        pop $ra, $fp, $sp, $gp  # Restore special registers
        pop_t_registers         # Restore T Registers
        move $t3, $v0           # Demarshal return value
        #########################                        
init1:                          #  ;
        li $t2, 0               #  counter = 0;
        move $t4, $t0           #  number = fractional;
loop1:  beq $t4, $zero, done1   #  while (number != 0 && counter != max_bits) {
        beq $t2, $t1, done1
body1:    nop                   #      ;
          mul $t4, $t4, 2       #      number = number * 2;
initif:   nop                   #      ;
        blt $t4, $t3, alt       #      if (number >= max) {
cons:     nop                   #          ;
          print_di(1)           #          mips.print_d(1);
          sub $t4, $t4, $t3     #          number = number - max;
          b doneif              #          // goto doneif;
                                #      } else {
alt:    nop                     #          ;
          print_di(0)           #          mips.print_d(0);
          b doneif              #          // goto doneif;
                                #      }
doneif: nop                     #      ;
next1:  nop                     #      ;
        addi $t2, $t2, 1        #      counter = counter + 1;
        b loop1                 #      continue loop1;
                                #  }
done1:  nop                     #  ;
                                #  return 0;
        move $v0, $zero         # Marshal return values    
        pop_s_registers         # Restore S registers
        jr $ra
                                #  }
              
value_of_max: nop               #  public static int value_of_max(int number) {
        # t0: number
        # t1: max               #  int max;
        # t2: i                 #  int i;
        # s0: 8                 #  int _8;
        # s1: 10                #  int _10;
     
        push_s_registers()      #  Save S registers
        move $t0, $a0           #  Demarshal input arguments
     
        li $t1, 10              #  max = 10;
        li $t2, 0               #  i=0;
        li $s0, 8               #  _8 = 8;
        li $s1, 10              #  _10 = 10;
    
loop2:  blt $t0, $t1, done2     #  for (; number >= max ;) {
          bgt $t2, $s0, loop2   #      if( i > _8) break loop2;
          mul $t1, $t1, $s1     #      max = max * _10;
          addi $t2, $t2, 1      #      i++;
          b loop2               #      continue loop2;
                                #  }
done2:  nop                     #  ;
                                #  return max;
        move $v0, $t1           #  Marshal output value
        pop_s_registers()       #  Restore S registers                          
        jr $ra
                                # }