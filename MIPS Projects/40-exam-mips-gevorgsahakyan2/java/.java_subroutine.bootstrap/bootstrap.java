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
	int counter = 0;

	int max = value_of_max(fractional);
	int number = fractional;
	while (number != 0 && counter != max_bits) {
	    number = number * 2;
		if ( number >= max ) {
	       mips.print_d(1);
	       number = number - max;
	    } else {
	       mips.print_d(0);
	    }
	    counter++;
 	}
 	return 0;
}

public static int value_of_max(int number) {
	int max;

	max = 10;
	for (int i=0;  number >= max ;i++) {
		if( i > 8) break;
		max = max * 10;
	}
	return max;
}
public static int strtofrac(char[] buffer, int radix) {

    int retval;

    double value;
    double denom;

    int i;
    int digit;


    value = 0;
    denom = radix;
    for (i=0; buffer[i] != '\0'; i++ ) {
       digit = glyph2int(buffer[i], radix);

       if (digit == -1 ) break;
       
       value = value +  digit / denom;
       denom = denom * radix;
    }

    for (; i > 0 ;) {
        value = value * 10;
        i--;
    }
    return (int) value;
}

public static int strtol( char[] buffer, int radix) {

    int digit;
    int value;
    int i;
    int x;

    int negativeOne;

    negativeOne = -1;

    value = 0;
    
    for (i = 0, x = buffer[i]; x != '\0'; i++, x = buffer[i]) {
        digit = glyph2int((char) x, radix);

        if (digit == negativeOne) {
            break;
        }
        value = value * radix;
        value = value + digit;
        
        continue;
    }

    return value;
}

public static int glyph2int(char glyph, int radix){
    int value;
    value = -1;

    if ('0' <= glyph) {
        if (glyph <= '9') {
            value = glyph - '0';
        }
    }

    if ('a' <= glyph) {
        if (glyph <= 'f') {
            value = glyph - 'a';
            value = value + 10;
        }
    }

    if ('A' <= glyph) {
        if (glyph <= 'F') {
            value = glyph - 'A';
            value = value + 10;
        }
    }

    if (value >= radix) {
        value = -1;
    }

    return value;
}
public static int whole2bin(int whole) {
	int counter = 0;
    int poppedElem;
    int remainder;

	int number = whole;
    while (number != 0) {
        remainder = number % 2;
    	mips.push(remainder);
        number = number / 2;
        counter++;
    }

    while (counter != 0) {
        poppedElem = mips.pop();
        mips.print_d(poppedElem);
        counter--;
    }
    return 1;
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
