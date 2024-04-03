class binaryReal {
   static MIPS_OS_Interface mips = new MIPS_OS_Interface();

public static int binaryReal(char[] arg0, char[] arg1, char[] arg2, char[] arg3) {
   char [] arg4 = null;     // Additional Formal Arguments which is on the static

   int  radix;
   char sharp;
   int  whole;
   int  point;
   int  fractional;

   final int max_bits = 23;

   arg4 = mips.pop(arg4);

   radix      = strtol(arg0, 10);
   sharp      = arg1[0];
   whole      = strtol(arg2, radix);
   point      = arg3[0];
   fractional = strtofrac(arg4, radix);

   mips.print_ci('2'); 
   mips.print_ci('#');
   mips.print_ci(' ');

   whole2bin(whole);     
   mips.print_ci('.');
   fractional2bin(fractional, max_bits);
   mips.print_ci('\n');

   return radix;
}

public static int fractional2bin(int fractional, int max_bits) {

        int counter;
        int max;
        int number;

        max = value_of_max(fractional);

init1:  ;
        counter = 0;
        number = fractional;
loop1:  while (number != 0 && counter != max_bits) {
body1:      ;
            number = number * 2;
initif:     ;
            if (number >= max) {
cons:           ;
                mips.print_d(1);
                number = number - max;
                // goto doneif;
            } else {
alt:            ;
                mips.print_d(0);
                // goto doneif;
            }
doneif:     ;
next1:      ;
            counter = counter + 1;
            continue loop1;
        }
done1:  ;
        return 0;
}

public static int value_of_max(int number) {

        int _8;
        int _10;

        int max;
        int i;
 
        _8  =  8;
        _10 = 10;
 
        max = 10;
        i   =  0;

loop2:  for (; number >= max ;) {
            if( i > _8) break loop2;
            max = max * _10;
            i++;
            continue loop2;
        }
done2:  ;
        return max;
}
public static int strtofrac(char[] buffer, int radix) {

        int i;
        char glyph;
        int digit;
        int value;
        int neg_one;

        double d_value;
        double d_radix;
        double d_denom;
        double d_fraction;
        double d_digit;

        double d_10;

        neg_one = -1;
        d_10    = (double) 10;

        d_value = (double) 0;
        d_radix = (double) radix;
        d_denom = (double) d_radix;

        i = 0;
        glyph = buffer[i];
loop1:  for (; glyph != '\0' ;) {
           digit = glyph2int(glyph, radix);

           if (digit == -1 ) break loop1;

           d_digit    = (double) digit;
           d_fraction = (double) d_digit / d_denom;

           d_value  = (double) d_value + d_fraction;
           d_denom  = (double) d_denom * d_radix;

           i++;
           glyph = buffer[i];
           continue loop1;
        }
done1:  ;
loop2:  for (; i > 0 ;) {
          d_value = (double) d_value * d_10;
          i--;
          continue loop2;
        }
done2:  ;
        value = (int) d_value;
        return value;
}


public static int strtol( char[] buffer, int radix) {

        int digit;
        int value;
        int i;
        int x;
    
        int negativeOne;
    
        negativeOne = -1;
        value = 0;

init:  ;
        i = 0;
        x = buffer[i];
    
loop1:  for (;x != '\0';) {
body1:      ;
            digit = glyph2int((char) x, radix);
            if (digit == negativeOne) break loop1;
            value = value * radix;
            value = value + digit;
next:       ;
            i = i + 1;
            x = buffer[i];
            continue loop1;
        }
done:   ;
        return value;
}

public static int glyph2int(char glyph, int radix){
        int value;
        value = -1;

init1:  ;
        if ('0' <= glyph) {
cons1:      ;
initA:      ;
            if (glyph <= '9') {
consA:          ;
                value = glyph - '0';
                // goto doneA;
            }
doneA:      ;
            // goto done1;
        }
done1:  ;
init2:  ;
        if ('a' <= glyph) {
cons2:      ;
initB:      ;
            if (glyph <= 'f') {
consB:          ;
                value = glyph - 'a';
                value = value + 10;
                // goto doneB;
            }
doneB:      ;
            // goto done2;
        }
done2:  ;
init3:  ;
        if ('A' <= glyph) {
cons3:      ;
initC:      ;
            if (glyph <= 'F') {
consC:          ;
                value = glyph - 'A';
                value = value + 10;
                // goto doneC;
            }
doneC:      ;
            // goto done3;
        }
done3:  ;
init4:  ;
        if (value >= radix) {
cons4:      ;
            value = -1;
            // goto done4;
        }
done4:  ;

        return value;
}
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


  public static void main(String[] args) {
  
    int index;
    String string_0 = "8";
    char $a0[] = new char[ (string_0).length() + 2];
         for (index=0; index < $a0.length - 2; index++ ) {
           $a0[index] = (string_0).charAt(index);
         }
         $a0[index] = '\0' ; // the Null character
         $a0[index+1] = '\7' ; // the Alert character
    String string_1 = "#";
    char $a1[] = new char[ (string_1).length() + 2];
         for (index=0; index < $a1.length - 2; index++ ) {
           $a1[index] = (string_1).charAt(index);
         }
         $a1[index] = '\0' ; // the Null character
         $a1[index+1] = '\7' ; // the Alert character
    String string_2 = "1234";
    char $a2[] = new char[ (string_2).length() + 2];
         for (index=0; index < $a2.length - 2; index++ ) {
           $a2[index] = (string_2).charAt(index);
         }
         $a2[index] = '\0' ; // the Null character
         $a2[index+1] = '\7' ; // the Alert character
    String string_3 = ".";
    char $a3[] = new char[ (string_3).length() + 2];
         for (index=0; index < $a3.length - 2; index++ ) {
           $a3[index] = (string_3).charAt(index);
         }
         $a3[index] = '\0' ; // the Null character
         $a3[index+1] = '\7' ; // the Alert character
    String string_4 = "4300000";
    char $a4[] = new char[ (string_4).length() + 2];
         for (index=0; index < $a4.length - 2; index++ ) {
           $a4[index] = (string_4).charAt(index);
         }
         $a4[index] = '\0' ; // the Null character
         $a4[index+1] = '\7' ; // the Alert character

    mips.push($a4);
    int $v0 = binaryReal($a0, $a1, $a2, $a3);

    // Augment this code based upon desired output type

    mips.print_ci('\n');   // Print extra '\n' in case the users
                           // last line does not include the 
                           // proforma '\n'.
    mips.print_d($v0);
    mips.print_ci('\n');
    return;
  }
}
