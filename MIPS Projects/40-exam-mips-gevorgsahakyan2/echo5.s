        .text
        .include "mips/macros/syscalls.s"
        .include "mips/macros/stack.s"
        .globl echo5


#  This method demostrates the MIPS calling convention in Java
#  MIPS passes the first 4 arguements via registers, and
#  the remaining registers via the stack.
# 
echo5:  nop     # echo5 (char[] a, char[] b, char[] c, char[] d) {
#     char[] e = null;  // The fifth paramater is on the stack
 
     pop $t4
 
     print_s($a0)
     print_ci(' ')
     print_s($a1)
     print_ci(' ')
     print_s($a2)
     print_ci(' ')
     print_s($a3)
     print_ci(' ')
     print_s($t4)
     print_ci('\n')

     li $v0, 5
     jr $ra

