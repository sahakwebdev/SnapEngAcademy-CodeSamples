//  Call method via:
//    java_subroutine echo5 one two three four five
//
//  This method demostrates the MIPS calling convention in Java
//  MIPS passes the first 4 arguements via registers, and
//  the remaining registers via the stack.

public static int  echo5 (char[] a, char[] b, char[] c, char[] d) {
    char[] e = null;  // The fifth paramater is on the stack

    e = mips.pop(e);

    mips.print_s(a); mips.print_ci(' ');
    mips.print_s(b); mips.print_ci(' ');
    mips.print_s(c); mips.print_ci(' ');
    mips.print_s(d); mips.print_ci(' '); 
    mips.print_s(e); mips.print_ci('\n');
    return 5;
}
